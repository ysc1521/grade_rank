package com.ysc.graderank.controller;

import com.ysc.graderank.annotation.Layout;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Layout
public class LayoutController {
    @GetMapping(value = {"", "/", "/test"})
    public String index() {
        return "layout";
    }
}
