package com.ysc.graderank.service;

import com.ysc.graderank.mapper.YearGradeMapper;
import com.ysc.graderank.pojo.Major;
import com.ysc.graderank.pojo.SC;
import com.ysc.graderank.pojo.Student;
import com.ysc.graderank.pojo.YearGrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

@Service
public class YearGradeService {

    @Autowired
    private YearGradeMapper yearGradeMapper;
    @Autowired
    private StudentService studentService;
    @Autowired
    private MajorService majorService;
    @Autowired
    private SCService scService;

    public List<YearGrade> getBySid(int sid) {
        Example example = new Example(YearGrade.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sid", sid);
        List<YearGrade> yearGradeList = yearGradeMapper.selectByExample(example);
        return yearGradeList;
    }

    public YearGrade getBySidAndYear(int sid, int year) {
        List<YearGrade> yearGradeList = getBySid(sid);
        for (YearGrade yearGrade : yearGradeList) {
            if (yearGrade.getYear() == year) {
                return yearGrade;
            }
        }
        return null;
    }

    public YearGrade getById(int id) {
        return yearGradeMapper.selectByPrimaryKey(id);
    }

    public void fill(YearGrade yearGrade) {
        yearGrade.setStudent(studentService.getById(yearGrade.getSid()));
    }

    public void update(YearGrade yearGrade) {
        yearGradeMapper.updateByPrimaryKeySelective(yearGrade);
    }

    public void createThisYearGrade() {
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Major> majorList = majorService.selectAll();
        for (Major major : majorList) {
            List<Student> studentList = studentService.getByMid(major.getId());
            int maxYear = Math.min(thisYear, major.getGrade() + major.getGpaYears() + 1);
            for (int year = major.getGrade() + 1; year <= maxYear; ++year) {
                for (Student student : studentList) {
                    createThisYearGrade(student, year);
                }
                // 然后计算平均绩点专业排名
                calRanking(major, year);
            }
        }
    }

    public void createThisYearGrade(Student student, int year) {
        // 先判断今年的成绩记录是否创建过
        YearGrade yearGrade = getBySidAndYear(student.getId(), year);
        if (yearGrade != null) {
            return;
        }
        YearGrade newYearGrade = new YearGrade();
        newYearGrade.setSid(student.getId());
        newYearGrade.setYear(year);
        calAveGrade(newYearGrade);
        insert(newYearGrade);
    }

    // 计算平均绩点成绩
    public void calAveGrade(YearGrade yearGrade) {
        List<SC> scList = scService.getBySidAndYear(yearGrade.getSid(), yearGrade.getYear());
        Double allCredit = 0D;
        Double allScore = 0D;
        for (SC sc : scList) {
            allCredit += sc.getCourse().getCredit();
            allScore += sc.getCourse().getCredit() * sc.getScore();
        }
        yearGrade.setCredit(allCredit);
        if (allCredit != 0D) {
            yearGrade.setAveScore(allScore/allCredit);
        } else {
            yearGrade.setAveScore(0D);
        }
    }

    public void calRanking(Major major, int year) {
        List<Student> studentList = studentService.getByMid(major.getId());
        List<YearGrade> yearGradeList = new ArrayList<>();
        for (Student student : studentList) {
            YearGrade yearGrade = getBySidAndYear(student.getId(), year);
            if (yearGrade != null) {
                yearGradeList.add(yearGrade);
            }
        }
        if (yearGradeList.isEmpty() || yearGradeList.get(0).getAveRank() != null) {
            return;
        }
        yearGradeList.sort(new AveGradeComparator());
        for (int i = 0; i < yearGradeList.size(); ++i) {
            YearGrade yearGrade = yearGradeList.get(i);
            yearGradeList.get(i).setAveRank(i + 1);
            yearGradeList.get(i).setMajorStudent(yearGradeList.size());
            update(yearGrade);
        }
    }

    public void insert(YearGrade yearGrade) {
        yearGradeMapper.insert(yearGrade);
    }

    class AveGradeComparator implements Comparator<YearGrade> {

        @Override
        public int compare(YearGrade o1, YearGrade o2) {
            return Double.compare(o2.getAveScore(), o1.getAveScore());
        }
    }

    // 计算该年的综合学分绩点
    public void calComScore() {
        List<Major> majorList = majorService.selectAll();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (Major major : majorList) {
            for (int year = major.getGrade() + 1; year <= thisYear; ++year) {
                List<Student> studentList = studentService.getByMid(major.getId());
                if (studentList == null) {
                    continue;
                }
                List<YearGrade> yearGradeList = new ArrayList<>();
                for (Student student : studentList) {
                    YearGrade yearGrade = getBySidAndYear(student.getId(), year);
                    if (yearGrade == null) {
                        continue;
                    }
                    yearGradeList.add(yearGrade);
                    // 计算综合绩点，平均学分绩点占80%，基础素质加分和发展素质加分各占10%
                    double comScore = doubleFormat(yearGrade.getAveScore()) * 0.8 + (yearGrade.getBasicScore()) * 0.1 + (yearGrade.getDevScore()) * 0.1;
                    yearGrade.setComScore(comScore);
                }
                yearGradeList.sort((o1, o2) -> Double.compare(o2.getComScore(), o1.getComScore()));
                for (int i = 0; i < yearGradeList.size(); ++i) {
                    YearGrade yearGrade = yearGradeList.get(i);
                    yearGrade.setComRank(i + 1);
                    update(yearGrade);
                }
            }
        }
    }

    private double doubleFormat(Double d) {
        return d == null ? 0 : d;
    }

    // 计算学生的gpa及排名
    public void calGpa() {
        List<Major> majorList = majorService.selectAll();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (Major major : majorList) {
            if (major.getGrade() + major.getGpaYears() > thisYear) {
                continue;
            }
            List<Student> studentList = studentService.getByMid(major.getId());
            if (studentList.isEmpty()) {
                continue;
            }
            if (studentList.get(0).getGpa() != null) {
                // 改专业已经计算过gpa，跳过
                continue;
            }
            for (Student student : studentList) {
                List<YearGrade> yearGradeList = getBySid(student.getId());
                double totalScore = 0;
                double totalCredit = 0;
                for (YearGrade yearGrade : yearGradeList) {
                    totalCredit += yearGrade.getCredit();
                    totalScore += yearGrade.getComScore() * yearGrade.getCredit();
                }
                student.setGpa( totalCredit == 0 ? 0 : totalScore / totalCredit);
            }
            // 为专业里学生gpa排序
            studentList.sort((o1, o2) -> Double.compare(o2.getGpa(), o1.getGpa()));
            for (int i = 0; i < studentList.size(); ++i) {
                Student student = studentList.get(i);
                student.setGpaRank(i + 1);
                student.setGpaMajorStudent(studentList.size());
                studentService.update(student);
            }
        }
    }
}
