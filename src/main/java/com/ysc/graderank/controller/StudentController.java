package com.ysc.graderank.controller;

import com.ysc.graderank.enums.DevKindEnum;
import com.ysc.graderank.pojo.DevDetail;
import com.ysc.graderank.pojo.DevDetailRe;
import com.ysc.graderank.pojo.User;
import com.ysc.graderank.pojo.YearGrade;
import com.ysc.graderank.service.DevDetailService;
import com.ysc.graderank.service.YearGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

    @RequestMapping("/index")
    public String index() {
        return "student/index";
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
            devDetail.setVerify("未审核");
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

}
