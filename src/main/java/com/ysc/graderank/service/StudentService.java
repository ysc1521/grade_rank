package com.ysc.graderank.service;

import com.ysc.graderank.mapper.StudentMapper;
import com.ysc.graderank.pojo.SC;
import com.ysc.graderank.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private MajorService majorService;
    @Autowired
    private SCService scService;
    @Autowired
    private YearGradeService yearGradeService;

    public List<Student> selectAllFull(Integer mid, Integer cid) {
        List<Student> studentList = getByMid(mid);
        if (cid != null) {
            List<Student> studentList2 = getByCid(cid);
            studentList = intersection(studentList, studentList2);
        }
        fill(studentList);
        return studentList;
    }

    public List<Student> intersection(List<Student> list1, List<Student> list2) {
        HashSet<Integer> idSet = new HashSet<>();
        for (Student student : list1) {
            idSet.add(student.getId());
        }
        List<Student> studentList = new ArrayList<>();
        for (Student student : list2) {
            if(idSet.contains(student.getId())) {
                studentList.add(student);
            }
        }
        return studentList;
    }

    public List<Student> getByCid(Integer cid) {
        List<SC> scList = scService.getByCid(cid);
        Set<Integer> sidSet = new HashSet<>();
        for (SC sc : scList) {
            sidSet.add(sc.getSid());
        }
        List<Student> studentList = new ArrayList<>();
        for (Integer id : sidSet) {
            studentList.add(getById(id));
        }
        return studentList;
    }


    public List<Student> selectAll() {
        return studentMapper.selectAll();
    }

    public void fill(List<Student> studentList) {
        for (Student student : studentList) {
            fill(student);
        }
    }

    public void fill(Student student) {
        if (student == null) {
            return;
        }
        student.setMajor(majorService.getById(student.getMid()));
        List<SC> scList = scService.getBySid(student.getId());
        student.setScList(scList);
    }

    public Student getById(Integer id) {
        return studentMapper.selectByPrimaryKey(id);
    }

    public void insert(Student student) {
        studentMapper.insert(student);
    }

    public void update(Student student) {
        studentMapper.updateByPrimaryKeySelective(student);
    }

    public List<Student> getByMid(Integer mid) {
        Example example = new Example(Student.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("mid", mid);
        List<Student> studentList = studentMapper.selectByExample(example);
        return studentList;
    }

    public boolean isExist(Integer id, String password) {
        Student student = studentMapper.selectByPrimaryKey(id);
        return student != null && student.getPassword().equals(password);
    }

}
