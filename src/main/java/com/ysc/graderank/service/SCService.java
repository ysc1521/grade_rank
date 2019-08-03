package com.ysc.graderank.service;

import com.ysc.graderank.mapper.SCMapper;
import com.ysc.graderank.pojo.Course;
import com.ysc.graderank.pojo.SC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SCService {
    @Autowired
    private SCMapper scMapper;
    @Autowired
    private CourseService courseService;

    public List<SC> getBySid(Integer sid) {
        Example example = new Example(SC.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sid", sid);
        List<SC> scList = scMapper.selectByExample(example);
        return scList;
    }

    public List<SC> getFullBySid(Integer sid) {
        List<SC> scList = getBySid(sid);
        fill(scList);
        return scList;
    }

    public void fill(List<SC> scList) {
        for (SC sc : scList) {
            fill(sc);
        }
    }

    public void fill(SC sc) {
        if (sc == null) {
            return;
        }
        Course course = courseService.getById(sc.getCid());
        sc.setCourse(course);
    }

    public void insert(SC sc) {
        scMapper.insert(sc);
    }

    public List<SC> getByCid(Integer cid) {
        Example example = new Example(SC.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("cid", cid);
        List<SC> scList = scMapper.selectByExample(example);
        return scList;
    }

    public List<SC> getBySidAndYear(int sid, int year) {
        Example example = new Example(SC.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sid", sid).andEqualTo("selectYear", year);
        List<SC> scList = scMapper.selectByExample(example);
        fill(scList);
        return scList;
    }

}
