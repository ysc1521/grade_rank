package com.ysc.graderank.controller;

import com.ysc.graderank.pojo.Admin;
import com.ysc.graderank.pojo.Teacher;
import com.ysc.graderank.service.AdminService;
import com.ysc.graderank.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private TeacherService teacherService;

    @RequestMapping("/getAllAdmin")
    @ResponseBody
    public String getAllAdmin() {
        return adminService.selectAll().toString();
    }

    @RequestMapping("/getAdmin")
    @ResponseBody
    public String getAdmin() {
        Admin admin = new Admin();
        admin.setId(111L);
//        admin.setPassword("123");
        return String.valueOf(adminService.isExist(admin));
    }

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello2";
    }

    @RequestMapping("/layout")
    public String layout(ModelMap map) {
        map.addAttribute("test", "this is a test value");
        return "layout/demo";
    }

    @RequestMapping("/getTeacher")
    @ResponseBody
    public String getTeacher() {
        List<Teacher> teacherList = teacherService.selectAll();
        return teacherList.toString();
    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }

}
