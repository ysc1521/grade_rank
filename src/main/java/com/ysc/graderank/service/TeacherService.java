package com.ysc.graderank.service;

import com.ysc.graderank.mapper.TeacherMapper;
import com.ysc.graderank.pojo.Major;
import com.ysc.graderank.pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private MajorService majorService;

    public List<Teacher> selectAllFull() {
        List<Teacher> teacherList = selectAll();
        full(teacherList);
        return teacherList;
    }

    public List<Teacher> selectAll() {
        //PageHelper.startPage(page, pageSize);
        return teacherMapper.selectAll();
    }

    public void insert(Teacher teacher) {
        teacherMapper.insert(teacher);
    }

    public void update(Teacher teacher) {
        teacherMapper.updateByPrimaryKeySelective(teacher);
    }

    public Teacher getById(Integer id) {
        return teacherMapper.selectByPrimaryKey(id);
    }

    public void full(List<Teacher> teacherList) {
        for (Teacher teacher : teacherList) {
            full(teacher);
        }
    }

    public void full(Teacher teacher) {
        if (teacher == null) {
            return;
        }
        List<Major> majorList = majorService.getByTid(teacher.getId());
        teacher.setMajorList(majorList);
    }

    public boolean isExist(Integer id, String password) {
        Teacher teacher = teacherMapper.selectByPrimaryKey(id);
        return teacher != null && teacher.getPassword().equals(password);
    }
}
