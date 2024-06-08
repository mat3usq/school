package com.mat3.school.controller;

import com.mat3.school.constants.SchoolConstants;
import com.mat3.school.model.Person;
import com.mat3.school.model.SchoolClass;
import com.mat3.school.repository.CoursesRepository;
import com.mat3.school.repository.PersonRepository;
import com.mat3.school.repository.SchoolClassRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {
    public final PersonRepository personRepository;
    public final SchoolClassRepository schoolClassRepository;
    public final CoursesRepository coursesRepository;

    @Autowired
    public AdminController(PersonRepository personRepository, SchoolClassRepository schoolClassRepository, CoursesRepository coursesRepository) {
        this.personRepository = personRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.coursesRepository = coursesRepository;
    }

    @RequestMapping("/displayClasses")
    public ModelAndView displayClasses() {
        List<SchoolClass> schoolClasses = schoolClassRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("classes");
        modelAndView.addObject("schoolClasses", schoolClasses);
        modelAndView.addObject("schoolClass", new SchoolClass());
        modelAndView.addObject("toEditSchoolClass", new SchoolClass());
        return modelAndView;
    }

    @PostMapping("/addNewClass")
    public ModelAndView addNewClass(@ModelAttribute("schoolClass") SchoolClass schoolClass) {
        schoolClassRepository.save(schoolClass);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @PostMapping("/editClass")
    public ModelAndView editClass(@RequestParam int classId, @ModelAttribute("toEditSchoolClass") SchoolClass schoolClass) {
        SchoolClass oldSchoolClass = schoolClassRepository.findById(classId).orElse(new SchoolClass());
        oldSchoolClass.setName(schoolClass.getName());
        schoolClassRepository.save(oldSchoolClass);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @RequestMapping("/deleteClass")
    public ModelAndView deleteClass(@RequestParam int id) {
        Optional<SchoolClass> schoolClass = schoolClassRepository.findById(id);
        for (Person person : schoolClass.get().getStudents()) {
            person.setStudentClasses(null);
            personRepository.save(person);
        }
        schoolClassRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @GetMapping("/displayStudents")
    public ModelAndView displayStudents(@RequestParam int classId, HttpSession session, @RequestParam(value = "studentEmailError", required = false) String studentEmailError, @RequestParam(value = "teacherEmailError", required = false) String teacherEmailError) {
        ModelAndView modelAndView = new ModelAndView("students");
        Optional<SchoolClass> schoolClass = schoolClassRepository.findById(classId);
        modelAndView.addObject("schoolClass", schoolClass.get());
        modelAndView.addObject("person", new Person());
        session.setAttribute("schoolClass", schoolClass.get());
        if (studentEmailError != null)
            modelAndView.addObject("errorMessage", "Invalid Student Email entered!");
        if(teacherEmailError != null)
            modelAndView.addObject("errorMessage", "Invalid Teacher Email entered!");
        return modelAndView;
    }

    @PostMapping("/addStudent")
    public ModelAndView addStudent(@ModelAttribute("person") Person person, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        SchoolClass schoolClass = (SchoolClass) session.getAttribute("schoolClass");
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if (personEntity == null || !(personEntity.getPersonId() > 0) || !personEntity.getRoles().getRoleName().equals(SchoolConstants.STUDENT_ROLE)) {
            modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + schoolClass.getClassId() + "&studentEmailError=true");
            return modelAndView;
        }
        personEntity.setStudentClasses(schoolClass);
        personRepository.save(personEntity);
        schoolClass.getStudents().add(personEntity);
        schoolClassRepository.save(schoolClass);
        modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + schoolClass.getClassId());
        return modelAndView;
    }

    @PostMapping("/assignTeacher")
    public ModelAndView assignTeacher(@ModelAttribute("person") Person person, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        SchoolClass schoolClass = (SchoolClass) session.getAttribute("schoolClass");
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if (personEntity == null || !(personEntity.getPersonId() > 0) || !personEntity.getRoles().getRoleName().equals(SchoolConstants.TEACHER_ROLE)) {
            modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + schoolClass.getClassId() + "&teacherEmailError=true");
            return modelAndView;
        }
        personEntity.getTeacherClasses().add(schoolClass);
        personRepository.save(personEntity);
        schoolClass.getTeachers().add(personEntity);
        schoolClassRepository.save(schoolClass);
        modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + schoolClass.getClassId());
        return modelAndView;
    }

    @GetMapping("/deleteStudent")
    public ModelAndView deleteStudent(@RequestParam int personId, HttpSession session) {
        SchoolClass schoolClass = (SchoolClass) session.getAttribute("schoolClass");
        Optional<Person> person = personRepository.findById(personId);
        person.get().setStudentClasses(null);
        schoolClass.getStudents().remove(person.get());
        SchoolClass schoolClassSaved = schoolClassRepository.save(schoolClass);
        session.setAttribute("schoolClass", schoolClassSaved);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents?classId=" + schoolClass.getClassId());
        return modelAndView;
    }
}
