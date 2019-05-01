package com.ysc.graderank.controller;

import com.ysc.graderank.pojo.User;
import com.ysc.graderank.service.AdminService;
import com.ysc.graderank.service.StudentService;
import com.ysc.graderank.service.TeacherService;
import com.ysc.graderank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogController {

    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/login")
    public String login(User user, ModelMap map, HttpServletRequest request) {
        boolean success = false;
        String returnPage = "";
        switch (user.getIdentity()) {
            case ADMIN:
                success = adminService.isExist(user.getId(), user.getPassword());
                returnPage = "/admin/index";
                break;
            case STUDENT:
                success = studentService.isExist(user.getId(), user.getPassword());
                returnPage = "/student/index";
                break;
            case TEACHER:
                success = teacherService.isExist(user.getId(), user.getPassword());
                returnPage = "/teacher/index";
                break;
        }
        if (!success) {
            map.addAttribute("errMsg", "账号或密码错误");
            return "login";
        }
        request.getSession().setAttribute("user", user);
        return "redirect:" + returnPage;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "redirect:/index";
    }

}
