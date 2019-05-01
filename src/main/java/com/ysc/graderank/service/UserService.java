package com.ysc.graderank.service;

import com.ysc.graderank.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private AdminService adminService;

    public boolean isExist(User user) {
        switch (user.getIdentity()) {
            case ADMIN:
                return adminService.isExist(user.getId(), user.getPassword());
            case TEACHER:
                return teacherService.isExist(user.getId(), user.getPassword());
            case STUDENT:
                return studentService.isExist(user.getId(), user.getPassword());
        }
        return false;
    }
}
