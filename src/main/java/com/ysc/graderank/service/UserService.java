package com.ysc.graderank.service;

import com.ysc.graderank.mapper.UserMapper;
import com.ysc.graderank.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public boolean isExist(User user) {
        List<User> userList = userMapper.select(user);
        return userList.size() > 0;
    }
}
