package com.ysc.graderank.service;

import com.ysc.graderank.mapper.CourseMapper;
import com.ysc.graderank.pojo.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private StudentService studentService;

//    public List<Course> selectAllFull() {
//        List<Course> courseList = selectAll();
//        return courseList;
//    }

    public List<Course> selectAll() {
        return courseMapper.selectAll();
    }

    public Course getById(Integer id) {
        return courseMapper.selectByPrimaryKey(id);
    }

    public void insert(Course course) {
        courseMapper.insert(course);
    }

    public void update(Course course) {
        courseMapper.updateByPrimaryKeySelective(course);
    }

}
