package com.mat3.school.controller;

import com.mat3.school.model.Mark;
import com.mat3.school.model.Person;
import com.mat3.school.repository.MarkRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("student")
public class StudentController {
    private final MarkRepository markRepository;

    @Autowired
    public StudentController(MarkRepository markRepository) {
        this.markRepository = markRepository;
    }

    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(HttpSession session) {
        Person person = (Person) session.getAttribute("loggedInPerson");
        ModelAndView modelAndView = new ModelAndView("courses_enrolled.html");
        modelAndView.addObject("person", person);
        return modelAndView;
    }

    @GetMapping("/displayMarks")
    public ModelAndView displayMarks(HttpSession session) {
        Person person = (Person) session.getAttribute("loggedInPerson");
        List<Mark> marks = new ArrayList<>();
        if (person.getStudentClasses() != null)
            person.getStudentClasses().getTeachers().forEach(t -> {
                markRepository.findAllByTeacher(t).forEach(m -> {
                    if (m.getStudent().equals(person))
                        marks.add(m);
                });
            });
        ModelAndView modelAndView = new ModelAndView("marks");
        modelAndView.addObject("marks", marks);
        modelAndView.addObject("student", person);
        return modelAndView;
    }
}