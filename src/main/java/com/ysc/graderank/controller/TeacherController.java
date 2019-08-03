package com.ysc.graderank.controller;

import com.ysc.graderank.pojo.*;
import com.ysc.graderank.service.*;
import com.ysc.graderank.util.ComClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private MajorService majorService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private SCService scService;
    @Autowired
    private YearGradeService yearGradeService;
    @Autowired
    private DevDetailService devDetailService;
    @Autowired
    private TeacherService teacherService;

    // 分别表示各种加分项的最大加分：科技创新50分，文体特长30分，学生工作20分
    private static final double[] maxDevKindScore = {50, 30, 20};

    @GetMapping("/index")
    public String index(ModelMap modelMap, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        modelMap.addAttribute("teacher", teacherService.getById(user.getId()));
        return "teacher/index";
    }

    @GetMapping("/password")
    public String password() {
        return "teacher/password";
    }

    @GetMapping("/password/update")
    @ResponseBody
    public String passwordUpdate(String oldPwd, String newPwd, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Teacher teacher = teacherService.getById(user.getId());
        if (!user.getPassword().equals(oldPwd)) {
            return "原密码错误";
        }
        teacher.setPassword(newPwd);
        teacherService.update(teacher);
        return "success";
    }

    @RequestMapping("/major")
    public String major(HttpServletRequest request, ModelMap modelMap) {
        User user = (User) request.getSession().getAttribute("user");
        List<Major> majorList = majorService.getByTid(user.getId());
        majorService.fill(majorList);
        modelMap.addAttribute("majorList", majorList);
        return "teacher/major";
    }

    @GetMapping("/student")
    public String student(Integer mid, HttpServletRequest request, ModelMap modelMap) {
        User user = (User) request.getSession().getAttribute("user");
        List<Major> majorList = majorService.getByTid(user.getId());
        modelMap.addAttribute("majorList", majorList);
        Major major = majorService.getById(mid);
        modelMap.addAttribute("major", major);
        List<Student> studentList;
        if (major != null) {
            studentList = studentService.selectAllFull(mid, null);
        } else {
            studentList = new ArrayList<>();
            if (majorList != null) {
                for (Major major2 : majorList) {
                    studentList.addAll(studentService.selectAllFull(major2.getId(), null));
                }
            }
        }
        modelMap.addAttribute("studentList", studentList);
        modelMap.addAttribute("mid", mid);
        return "teacher/student";
    }

    @GetMapping("/student/course")
    public String studentCourse(int sid, Integer mid, ModelMap modelMap) {
        List<SC> scList = scService.getBySid(sid);
        scService.fill(scList);
        modelMap.addAttribute("scList", scList);
        Student student = studentService.getById(sid);
        modelMap.addAttribute("student", student);
        modelMap.addAttribute("mid", mid);
        return "teacher/studentCourse";
    }

    @GetMapping("/student/grade")
    public String studentGrade(int sid, ModelMap modelMap) {
        Student student = studentService.getById(sid);
        List<YearGrade> yearGradeList = yearGradeService.getBySid(sid);

        modelMap.addAttribute("student", student);
        modelMap.addAttribute("yearGradeList", yearGradeList);
        return "teacher/studentGrade";
    }

    @GetMapping("/student/grade/basic")
    public String studentGradeBasic(int yid, ModelMap modelMap) {
        YearGrade yearGrade = yearGradeService.getById(yid);
        yearGradeService.fill(yearGrade);

        modelMap.addAttribute("yearGrade", yearGrade);
        return "teacher/studentGradeBasic";
    }

    @GetMapping("/student/grade/basic/update")
    @ResponseBody
    public String studentGradeBasicUpdate(int yid, double score) {
        YearGrade yearGrade = yearGradeService.getById(yid);
        if (yearGrade.getYear() < Calendar.getInstance().get(Calendar.YEAR)) {
            return "已过期，不可修改";
        }
        yearGrade.setBasicScore(score);
        yearGradeService.update(yearGrade);
        return "success";
    }

    @GetMapping("/student/grade/dev")
    public String studentGradeDev(int yid, ModelMap modelMap) {
        YearGrade yearGrade = yearGradeService.getById(yid);
        yearGradeService.fill(yearGrade);
        List<DevDetailRe> devDetailReList = devDetailService.getByYid(yid);

        modelMap.addAttribute("yearGrade", yearGrade);
        modelMap.addAttribute("devDetailReList", devDetailReList);
        return "teacher/studentGradeDev";
    }

    @GetMapping("/student/grade/dev/update")
    @ResponseBody
    public String studentGradeDevUpdate(DevDetail devDetail) {
        if (devDetail.getScore() != null && devDetail.getScore() != 0 && ComClass.isEmptyStr(devDetail.getVerify())) {
            devDetail.setVerify("已通过");
        }
        devDetailService.update(devDetail);
        return "success";
    }

    @GetMapping("/student/grade/devCal")
    @ResponseBody
    public String studentGradeDevCal(int yid) {
        YearGrade yearGrade = yearGradeService.getById(yid);
        List<DevDetailRe> devDetailReList = devDetailService.getByYid(yid);
        double[] devKindScore = {0, 0, 0};
        for (DevDetailRe devDetailRe : devDetailReList) {
            switch (devDetailRe.getKind()) {
                case TECHNOLOGY:
                    devKindScore[0] += devDetailRe.getScore();
                    break;
                case LITERARY:
                    devKindScore[1] += devDetailRe.getScore();
                    break;
                case STUDENT_WORK:
                    devKindScore[2] += devDetailRe.getScore();
            }
        }
        double devScore = 0;
        for (int i = 0; i < maxDevKindScore.length; ++i) {
            if (devKindScore[i] > maxDevKindScore[i]) {
                devKindScore[i] = maxDevKindScore[i];
            }
            devScore += devKindScore[i];
        }
        yearGrade.setDevScore(devScore);
        yearGradeService.update(yearGrade);
        return "success";
    }

}
