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

    public void update(YearGrade yearGrade) {
        yearGradeMapper.updateByPrimaryKeySelective(yearGrade);
    }

    public void createThisYearGrade() {
        List<Student> studentList = studentService.selectAll();
        if (studentList.isEmpty()) {
            return;
        }
        for (Student student : studentList) {
            createThisYearGrade(student);
        }
        // 然后计算平均绩点专业排名
        List<Major> majorList = majorService.selectAll();
        for (Major major : majorList) {
            calRanking(major);
        }
    }

    public void createThisYearGrade(Student student) {
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        Major major = majorService.getById(student.getMid());
        // 绩点计算完成的学生无需创建成绩记录
        if (major.getGrade() + major.getGpaYears() < thisYear) {
            return;
        }
        // 先判断今年的成绩记录是否创建过
        YearGrade yearGrade = getBySidAndYear(student.getId(), thisYear);
        if (yearGrade != null) {
            return;
        }
        YearGrade newYearGrade = new YearGrade();
        newYearGrade.setSid(student.getId());
        newYearGrade.setYear(thisYear);
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
        }
    }

    public void calRanking(Major major) {
        List<Student> studentList = studentService.getByMid(major.getId());
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        List<YearGrade> yearGradeList = new ArrayList<>();
        for (Student student : studentList) {
            YearGrade yearGrade = getBySidAndYear(student.getId(), thisYear);
            if (yearGrade != null) {
                yearGradeList.add(yearGrade);
            }
        }
        if (yearGradeList.isEmpty()) {
            return;
        }
        yearGradeList.sort(new AveGradeComparator());
        for (int i = 0; i < yearGradeList.size(); ++i) {
            yearGradeList.get(i).setAveRank(i + 1);
        }
        for (YearGrade yearGrade : yearGradeList) {
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
}
