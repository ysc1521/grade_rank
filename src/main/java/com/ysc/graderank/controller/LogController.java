package com.ysc.graderank.controller;

import com.ysc.graderank.pojo.Admin;
import com.ysc.graderank.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public String login(Long name, String password, int identity, ModelMap map) {
        switch (identity) {
            case 1:
                Admin admin = new Admin();
                admin.setId(name);
                admin.setPassword(password);
                if (adminService.isExist(admin)) {
                    return "index";
                }
        }
        map.addAttribute("errMsg", "账号或密码错误");
        return "login";
    }

}
