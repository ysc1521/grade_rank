package com.ysc.graderank.service;

import com.ysc.graderank.mapper.MajorMapper;
import com.ysc.graderank.pojo.Major;
import com.ysc.graderank.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class MajorService {

    @Autowired
    public MajorMapper majorMapper;

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;

    public List<Major> selectAllFull() {
        List<Major> majorList = selectAll();
        fill(majorList);
        return majorList;
    }

    public List<Major> selectAll() {
        return majorMapper.selectAll();
    }

    public void fill(List<Major> majorList) {
        for (Major major : majorList) {
            fill(major);
        }
    }

    public void fill(Major major) {
        if (major == null) {
            return;
        }
        Integer tid = major.getTid();
        if (tid != null) {
            major.setTeacher(teacherService.getById(tid));
        }
        List<Student> studentList = studentService.getByMid(major.getId());
        major.setStudentList(studentList);
    }

    public Major getById(Integer id) {
        return majorMapper.selectByPrimaryKey(id);
    }

    public void insert(Major major) {
        majorMapper.insert(major);
    }

    public void update(Major major) {
        majorMapper.updateByPrimaryKey(major);
    }

    public List<Major> getByTid(Integer tid) {
        Example example = new Example(Major.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("tid", tid);

        List<Major> majorList = majorMapper.selectByExample(example);
        return majorList;
    }
}
