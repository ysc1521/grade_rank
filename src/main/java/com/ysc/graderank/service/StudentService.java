package com.ysc.graderank.service;

import com.ysc.graderank.mapper.StudentMapper;
import com.ysc.graderank.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private MajorService majorService;

    public List<Student> selectAllFull() {
        List<Student> studentList = selectAll();
        full(studentList);
        return studentList;
    }

    public List<Student> selectAll() {
        return studentMapper.selectAll();
    }

    public void full(List<Student> studentList) {
        for (Student student : studentList) {
            full(student);
        }
    }

    public void full(Student student) {
        if (student == null) {
            return;
        }
        student.setMajor(majorService.getById(student.getMid()));
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

}
