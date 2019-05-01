package com.ysc.graderank.service;

import com.ysc.graderank.enums.DevKindEnum;
import com.ysc.graderank.mapper.DevDetailMapper;
import com.ysc.graderank.pojo.DevDetail;
import com.ysc.graderank.pojo.DevDetailRe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class DevDetailService {

    @Autowired
    private DevDetailMapper devDetailMapper;

    public List<DevDetailRe> getByYid(int yid) {
        Example example = new Example(DevDetail.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("yid", yid);
        List<DevDetail> devDetailList = devDetailMapper.selectByExample(example);
        return toRe(devDetailList);
    }


    public DevDetailRe getById(Integer id) {
        return toRe(devDetailMapper.selectByPrimaryKey(id));
    }

    public List<DevDetailRe> toRe(List<DevDetail> devDetailList) {
        List<DevDetailRe> devDetailReList = new ArrayList<>();
        for (DevDetail devDetail : devDetailList) {
            devDetailReList.add(toRe(devDetail));
        }
        return devDetailReList;
    }

    public DevDetailRe toRe(DevDetail devDetail) {
        if (devDetail == null) {
            return null;
        }
        DevDetailRe devDetailRe = new DevDetailRe();
        devDetailRe.setId(devDetail.getId());
        devDetailRe.setKind(Enum.valueOf(DevKindEnum.class, devDetail.getKind()));
        devDetailRe.setName(devDetail.getName());
        devDetailRe.setScore(devDetail.getScore());
        devDetailRe.setVerify(devDetail.getVerify());
        devDetailRe.setYid(devDetail.getYid());
        return devDetailRe;
    }

    public void insert(DevDetail devDetail) {
        devDetailMapper.insert(devDetail);
    }

    public void update(DevDetail devDetail) {
        devDetailMapper.updateByPrimaryKeySelective(devDetail);
    }
}
