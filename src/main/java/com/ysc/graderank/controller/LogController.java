package com.ysc.graderank.controller;

import com.ysc.graderank.enums.IdentityEnum;
import com.ysc.graderank.pojo.User;
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

    @PostMapping("/login")
    public String login(User user, ModelMap map, HttpServletRequest request) {
        if (!userService.isExist(user)) {
            map.addAttribute("errMsg", "账号或密码错误");
            return "login";
        }
        request.getSession().setAttribute("user", user);
        if (user.getIdentity() == IdentityEnum.ADMIN) {
            return "redirect:/admin/index";
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "redirect:/index";
    }

}
