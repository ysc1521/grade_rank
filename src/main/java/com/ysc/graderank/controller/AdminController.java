package com.ysc.graderank.controller;

import com.ysc.graderank.pojo.*;
import com.ysc.graderank.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private MajorService majorService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private SCService scService;
    @Autowired
    private EntrySwitchService entrySwitchService;
    @Autowired
    private YearGradeService yearGradeService;

    private static final String COM_PASSWORD = "sdu123";

    @GetMapping("/index")
    public String index() {
        return "admin/index";
    }

    // 辅导员老师
    @GetMapping("/teacher")
    public String teacher(ModelMap modelMap) {
        modelMap.addAttribute("teacherList", teacherService.selectAllFull());
        return "admin/teacher";
    }

    @GetMapping("teacher/getMajor")
    public String getMajor() {
        return null;
    }

    @GetMapping("/teacher/detail")
    public String teacherDetail(Integer id, ModelMap map) {
        String type = id == null ? "新增" : "更新";
        map.addAttribute("type", type);
        Teacher teacher = teacherService.getById(id);
        map.addAttribute("teacher", teacher);
        return "admin/teacherDetail";
    }

    @GetMapping("/teacher/add")
    @ResponseBody
    public String addTeacher(Teacher teacher, HttpServletRequest request) {
        if (teacher.getId() == null) {
            // 新增
            teacher.setPassword(COM_PASSWORD);
            teacherService.insert(teacher);
        } else {
            // 更新
            teacherService.update(teacher);
        }
        return "success";
    }

    // 专业
    @GetMapping("/major")
    public String major(ModelMap modelMap) {
        modelMap.addAttribute("majorList", majorService.selectAllFull());
        return "admin/major";
    }

    @GetMapping("/major/detail")
    public String adminDetail(Integer id, ModelMap map) {
        String type = id == null ? "新增" : "更新";
        map.addAttribute("type", type);
        Major major = majorService.getById(id);
        majorService.fill(major);
        map.addAttribute("major", major);
        map.addAttribute("teacherList", teacherService.selectAll());
        return "admin/majorDetail";
    }

    @GetMapping("/major/add")
    @ResponseBody
    public String addMajor(Major major) {
        if (major.getId() == null) {
            majorService.insert(major);
        } else {
            majorService.update(major);
        }
        return "success";
    }

    // student
    @GetMapping("/student")
    public String student(Integer mid, Integer cid, ModelMap modelMap) {
        modelMap.addAttribute("mid", mid);
        modelMap.addAttribute("cid", cid);
        modelMap.addAttribute("studentList",studentService.selectAllFull(mid, cid));
        List<Major> majorList = majorService.selectAll();
        modelMap.addAttribute("majorList", majorList);
        List<Course> courseList = courseService.selectAll();
        modelMap.addAttribute("courseList", courseList);
        return "admin/student";
    }

    @GetMapping("/student/detail")
    public String studentDetail(Integer id, ModelMap map) {
        String type = id == null ? "新增" : "更新";
        map.addAttribute("type", type);
        Student student = studentService.getById(id);
        map.addAttribute("student", student);
        map.addAttribute("majorList", majorService.selectAll());
        return "admin/studentDetail";
    }

    @GetMapping("/student/add")
    @ResponseBody
    public String addStudent(Student student, HttpServletRequest request) {
        if (student.getId() == null) {
            // 新增
            student.setPassword(COM_PASSWORD);
            studentService.insert(student);
        } else {
            // 更新
            studentService.update(student);
        }
        return "success";
    }

    @GetMapping("/student/course")
    public String studentCourse(int id, ModelMap modelMap) {
        modelMap.addAttribute("id", id);
        List<SC>  scList = scService.getBySid(id);
        scService.fill(scList);
        modelMap.addAttribute("scList", scList);
        return "admin/studentCourse";
    }

    @GetMapping("/student/courseDetail")
    public String studentCourseDetail(int id, ModelMap modelMap) {
        Student student = studentService.getById(id);
        modelMap.addAttribute("student", student);
        List<Course> allCourseList = courseService.selectAll();
        List<Integer> cidList = getCidList(scService.getBySid(id));
        List<Course> noSelectedCourseList = new ArrayList<>();
        for (Course course : allCourseList) {
            if (!cidList.contains(course.getId())) {
                noSelectedCourseList.add(course);
            }
        }
        modelMap.addAttribute("courseList", noSelectedCourseList);
        return "admin/studentCourseDetail";
    }

    private List<Integer> getCidList(List<SC> scList) {
        List<Integer> list = new ArrayList<>();
        for (SC sc : scList) {
            list.add(sc.getCid());
        }
        return list;
    }

    @GetMapping("/student/course/add")
    @ResponseBody
    public String studentCourseAdd(SC sc) {
        scService.insert(sc);
        return "success";
    }


    // 课程
    @GetMapping("/course")
    public String course(ModelMap modelMap) {
        modelMap.addAttribute("courseList", courseService.selectAllFull());
        return "admin/course";
    }


    @GetMapping("/course/detail")
    public String courseDetail(Integer id, ModelMap modelMap) {
        String type = id == null ? "新增" : "更新";
        modelMap.addAttribute("type", type);
        Course course = courseService.getById(id);
        modelMap.addAttribute("course", course);
        return "admin/courseDetail";
    }

    @GetMapping("/course/add")
    @ResponseBody
    public String addCourse(Course course) {
        if (course.getId() == null) {
            courseService.insert(course);
        } else {
            courseService.update(course);
        }
        return "success";
    }

    @GetMapping("/infoEntrySwitch")
    public String infoEntrySwitch(ModelMap modelMap) {
        EntrySwitch entrySwitch = entrySwitchService.getOne();
        modelMap.addAttribute("entrySwitch", entrySwitch);
        return "admin/infoEntrySwitch";
    }

    @GetMapping("/updateEntrySwitch")
    @ResponseBody
    public String updateEntrySwitch(EntrySwitch entrySwitch) {
        entrySwitchService.update(entrySwitch);
        if ("ON".equals(entrySwitch.getStatus())) {
            yearGradeService.createThisYearGrade();
            return "成功开启";
        } else {
            return "成功关闭";
        }
    }


}
