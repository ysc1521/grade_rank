package com.ysc.graderank.controller;

import com.ysc.graderank.pojo.Teacher;
import com.ysc.graderank.service.AdminService;
import com.ysc.graderank.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Comparator;
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

    static class testClass {
        public testClass(int value) {
            this.value = value;
        }
        public int value;
        public int rank;
    }

    public static void main(String[] args) {
        List<testClass> list = new ArrayList<>();
        list.add(new testClass(1));
        list.add(new testClass(2));
        list.sort(new Comparator<testClass>() {
            @Override
            public int compare(testClass o1, testClass o2) {
                return o2.value - o1.value;
            }
        });
        for (testClass t : list) {
            System.out.println(t.value + "," + t.rank);
        }
    }

}
