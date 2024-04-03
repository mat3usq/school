package com.mat3.school.controller;

import com.mat3.school.model.Person;
import com.mat3.school.model.SchoolClass;
import com.mat3.school.repository.PersonRepository;
import com.mat3.school.repository.SchoolClassRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {
    public final PersonRepository personRepository;
    public final SchoolClassRepository schoolClassRepository;

    @Autowired
    public AdminController(PersonRepository personRepository, SchoolClassRepository schoolClassRepository) {
        this.personRepository = personRepository;
        this.schoolClassRepository = schoolClassRepository;
    }

    @RequestMapping("/displayClasses")
    public ModelAndView displayClasses() {
        List<SchoolClass> schoolClasses = schoolClassRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("classes.html");
        modelAndView.addObject("schoolClasses", schoolClasses);
        modelAndView.addObject("schoolClass", new SchoolClass());
        return modelAndView;
    }

    @PostMapping("/addNewClass")
    public ModelAndView addNewClass(@ModelAttribute("schoolClass") SchoolClass schoolClass) {
        schoolClassRepository.save(schoolClass);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @RequestMapping("/deleteClass")
    public ModelAndView deleteClass(@RequestParam int id) {
        Optional<SchoolClass> schoolClass = schoolClassRepository.findById(id);
        for (Person person : schoolClass.get().getPersons()) {
            person.setSchoolClass(null);
            personRepository.save(person);
        }
        schoolClassRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @GetMapping("/displayStudents")
    public ModelAndView displayStudents(@RequestParam int classId, HttpSession session, @RequestParam(value = "error", required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("students.html");
        Optional<SchoolClass> schoolClass = schoolClassRepository.findById(classId);
        modelAndView.addObject("schoolClass", schoolClass.get());
        modelAndView.addObject("person", new Person());
        session.setAttribute("schoolClass", schoolClass.get());
        if (error != null)
            modelAndView.addObject("errorMessage", "Invalid Email entered!");
        return modelAndView;
    }

    @PostMapping("/addStudent")
    public ModelAndView addStudent(@ModelAttribute("person") Person person, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        SchoolClass schoolClass = (SchoolClass) session.getAttribute("schoolClass");
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if (personEntity == null || !(personEntity.getPersonId() > 0)) {
            modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + schoolClass.getClassId() + "&error=true");
            return modelAndView;
        }
        personEntity.setSchoolClass(schoolClass);
        personRepository.save(personEntity);
        schoolClass.getPersons().add(personEntity);
        schoolClassRepository.save(schoolClass);
        modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + schoolClass.getClassId());
        return modelAndView;
    }

    @GetMapping("/deleteStudent")
    public ModelAndView deleteStudent(@RequestParam int personId, HttpSession session) {
        SchoolClass schoolClass = (SchoolClass) session.getAttribute("schoolClass");
        Optional<Person> person = personRepository.findById(personId);
        person.get().setSchoolClass(null);
        schoolClass.getPersons().remove(person.get());
        SchoolClass schoolClassSaved = schoolClassRepository.save(schoolClass);
        session.setAttribute("schoolClass", schoolClassSaved);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents?classId=" + schoolClass.getClassId());
        return modelAndView;
    }
}
