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
    @Autowired
    private SCService scService;

    public List<Course> selectAllFull() {
        List<Course> courseList = selectAll();
        fill(courseList);
        return courseList;
    }

    public void fill(Course course) {
        if (course == null) {
            return;
        }
        course.setScList(scService.getByCid(course.getId()));
    }

    public void fill(List<Course> courseList) {
        for (Course course : courseList) {
            fill(course);
        }
    }

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
