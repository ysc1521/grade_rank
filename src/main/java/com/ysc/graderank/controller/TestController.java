package com.ysc.graderank.controller;

import com.ysc.graderank.pojo.Admin;
import com.ysc.graderank.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/index")
    public String index() {
        return "/login";
    }

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


    public static void main(String[] args) {
        Object o;
    }

}
