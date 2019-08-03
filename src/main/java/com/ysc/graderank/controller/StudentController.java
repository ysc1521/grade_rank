package com.ysc.graderank.controller;

import com.ysc.graderank.enums.DevKindEnum;
import com.ysc.graderank.pojo.*;
import com.ysc.graderank.service.DevDetailService;
import com.ysc.graderank.service.SCService;
import com.ysc.graderank.service.StudentService;
import com.ysc.graderank.service.YearGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private YearGradeService yearGradeService;
    @Autowired
    private DevDetailService devDetailService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private SCService scService;

    @GetMapping("/index")
    public String index(ModelMap modelMap, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Student student = studentService.getById(user.getId());
        studentService.fill(student);
        modelMap.addAttribute("student", student);
        return "student/index";
    }

    @GetMapping("/password")
    public String password() {
        return "student/password";
    }

    @GetMapping("/password/update")
    @ResponseBody
    public String passwordUpdate(String oldPwd, String newPwd, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Student student = studentService.getById(user.getId());
        if (!user.getPassword().equals(oldPwd)) {
            return "原密码错误";
        }
        student.setPassword(newPwd);
        studentService.update(student);
        return "success";
    }

    @RequestMapping("/basic")
    public String basic(HttpServletRequest request, ModelMap modelMap) {
        User user = (User) request.getSession().getAttribute("user");
        modelMap.addAttribute("yearGradeList", yearGradeService.getBySid(user.getId()));
        return "student/basic";
    }

    @RequestMapping("/basic/detail")
    public String basicDetail(int year, HttpServletRequest request, ModelMap modelMap) {
        User user = (User) request.getSession().getAttribute("user");
        YearGrade yearGrade = yearGradeService.getBySidAndYear(user.getId(), year);
        modelMap.addAttribute("yearGrade", yearGrade);
        return "student/basicDetail";
    }

    @RequestMapping("/basic/update")
    @ResponseBody
    public String basicUpdate(int id, String detail) {
        YearGrade yearGrade = yearGradeService.getById(id);
        if (yearGrade.getYear() < Calendar.getInstance().get(Calendar.YEAR)) {
            return "已过期，不可修改";
        }
        if (yearGrade.getBasicScore() != null) {
            return "已评分，不可修改";
        }
        yearGrade.setBasicDetail(detail);
        yearGradeService.update(yearGrade);
        return "success";
    }

    @RequestMapping("/dev")
    public String dev(HttpServletRequest request, ModelMap modelMap) {
        User user = (User) request.getSession().getAttribute("user");
        modelMap.addAttribute("yearGradeList", yearGradeService.getBySid(user.getId()));
        return "student/dev";
    }

    @RequestMapping("/dev/detail")
    public String devDetail(int yid, ModelMap modelMap, HttpServletRequest request) {
        YearGrade yearGrade = yearGradeService.getById(yid);
        modelMap.addAttribute("yearGrade", yearGrade);
        List<DevDetailRe> devDetailReList = devDetailService.getByYid(yid);
        modelMap.addAttribute("devDetailReList", devDetailReList);
        return "student/devDetail";
    }

    @RequestMapping("/dev/modify")
    public String devModify(int yid, Integer id, ModelMap modelMap) {
        String type = id == null ? "新增" : "更新";
        modelMap.addAttribute("type", type);
        DevDetailRe devDetailRe = devDetailService.getById(id);
        modelMap.addAttribute("devDetailRe", devDetailRe);
        YearGrade yearGrade = yearGradeService.getById(yid);
        modelMap.addAttribute("yearGrade", yearGrade);
        List<DevKindEnum> devKindEnumList = Arrays.asList(DevKindEnum.values());
        modelMap.addAttribute("devKindEnumList", devKindEnumList);

        return "student/devModify";
    }

    @RequestMapping("/dev/add")
    @ResponseBody
    public String devAdd(DevDetail devDetail) {
        if (devDetail.getId() == null) {
            devDetailService.insert(devDetail);
        } else {
            devDetailService.update(devDetail);
        }
        return "success";
    }

    @RequestMapping("/ave")
    public String ave(HttpServletRequest request, ModelMap modelMap) {
        User user = (User) request.getSession().getAttribute("user");
        modelMap.addAttribute("yearGradeList", yearGradeService.getBySid(user.getId()));
        return "student/ave";
    }

    @RequestMapping("/com")
    public String com(HttpServletRequest request, ModelMap modelMap) {
        User user = (User) request.getSession().getAttribute("user");
        modelMap.addAttribute("yearGradeList", yearGradeService.getBySid(user.getId()));
        return "student/com";
    }

    @GetMapping("/course")
    public String course(HttpServletRequest request, ModelMap modelMap) {
        User user = (User) request.getSession().getAttribute("user");
        List<SC> scList  = scService.getFullBySid(user.getId());
        modelMap.addAttribute("scList", scList);
        return "student/course";
    }
}
