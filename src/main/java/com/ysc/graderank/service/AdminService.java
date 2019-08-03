package com.ysc.graderank.service;

import com.ysc.graderank.mapper.AdminMapper;
import com.ysc.graderank.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    public List<Admin> selectAll() {
        List<Admin> adminList = adminMapper.selectAll();
        return adminList;
    }

    public boolean isExist(Integer id, String password) {
        Admin admin = adminMapper.selectByPrimaryKey(id);
        return admin != null && admin.getPassword().equals(password);
    }

    public Admin getByIdAndPwd(Admin admin) {
//        List<Admin> adminList = adminMapper.select(admin);

        Admin admin2 = adminMapper.selectByPrimaryKey(admin.getId());
        return admin2;
    }

    public Admin getById(int id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    public void update(Admin admin) {
        adminMapper.updateByPrimaryKeySelective(admin);
    }

}
