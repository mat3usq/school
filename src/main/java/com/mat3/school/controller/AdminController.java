package com.mat3.school.controller;

import com.mat3.school.model.SchoolClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {
    @RequestMapping("/displayClasses")
    public ModelAndView displayClasses() {
        ModelAndView modelAndView = new ModelAndView("classes.html");
        modelAndView.addObject("schoolClass", new SchoolClass());
        return modelAndView;
    }
}
