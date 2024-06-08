package com.mat3.school.controller;

import com.mat3.school.model.Courses;
import com.mat3.school.model.Person;
import com.mat3.school.repository.CoursesRepository;
import com.mat3.school.repository.PersonRepository;
import com.mat3.school.repository.SchoolClassRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("teacher")
public class TeacherController {
    public final PersonRepository personRepository;
    public final SchoolClassRepository schoolClassRepository;
    public final CoursesRepository coursesRepository;

    @Autowired
    public TeacherController(PersonRepository personRepository, SchoolClassRepository schoolClassRepository, CoursesRepository coursesRepository) {
        this.personRepository = personRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.coursesRepository = coursesRepository;
    }
    @GetMapping("/displayCourses")
    public ModelAndView displayCourses() {
//        List<Courses> courses = coursesRepository.findByOrderByName();
        List<Courses> courses = coursesRepository.findAll(Sort.by("name").ascending());

        ModelAndView modelAndView = new ModelAndView("courses_secure");
        modelAndView.addObject("courses", courses);
        modelAndView.addObject("course", new Courses());
        modelAndView.addObject("toEditCourse", new Courses());
        return modelAndView;
    }

    @PostMapping("/addNewCourse")
    public ModelAndView addNewCourse(@ModelAttribute("course") Courses course) {
        ModelAndView modelAndView = new ModelAndView();
        coursesRepository.save(course);
        modelAndView.setViewName("redirect:/teacher/displayCourses");
        return modelAndView;
    }

    @PostMapping("/editCourse")
    public ModelAndView editCourse(@RequestParam int courseId, @ModelAttribute("toEditCourse") Courses courses) {
        Courses oldCourses = coursesRepository.findById(courseId).orElse(new Courses());
        oldCourses.setName(courses.getName());
        oldCourses.setFees(courses.getFees());
        coursesRepository.save(oldCourses);
        ModelAndView modelAndView = new ModelAndView("redirect:/teacher/displayCourses");
        return modelAndView;
    }

    @GetMapping("/deleteCourse")
    public ModelAndView deleteCourse(@RequestParam int id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Courses> courses = coursesRepository.findById(id);
        for (Person person : courses.get().getPersons()) {
            person.setCourses(null);
            personRepository.save(person);
        }
        coursesRepository.deleteById(id);
        modelAndView.setViewName("redirect:/teacher/displayCourses");
        return modelAndView;
    }

    @GetMapping("/viewStudents")
    public ModelAndView viewStudents(@RequestParam int id, HttpSession session, @RequestParam(required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("course_students.html");
        Optional<Courses> courses = coursesRepository.findById(id);
        modelAndView.addObject("courses", courses.get());
        modelAndView.addObject("person", new Person());
        session.setAttribute("courses", courses.get());
        if (error != null)
            modelAndView.addObject("errorMessage", "Invalid Email entered!");
        return modelAndView;
    }

    @PostMapping("/addStudentToCourse")
    public ModelAndView addStudentToCourse(@ModelAttribute("person") Person person, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Courses courses = (Courses) session.getAttribute("courses");
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if (personEntity == null || !(personEntity.getPersonId() > 0)) {
            modelAndView.setViewName("redirect:/teacher/viewStudents?id=" + courses.getCourseId() + "&error=true");
            return modelAndView;
        }
        personEntity.getCourses().add(courses);
        courses.getPersons().add(personEntity);
        personRepository.save(personEntity);
        session.setAttribute("courses", courses);
        modelAndView.setViewName("redirect:/teacher/viewStudents?id=" + courses.getCourseId());
        return modelAndView;
    }

    @GetMapping("/deleteStudentFromCourse")
    public ModelAndView deleteStudentFromCourse(@RequestParam int personId, HttpSession session) {
        Courses courses = (Courses) session.getAttribute("courses");
        Optional<Person> person = personRepository.findById(personId);
        person.get().getCourses().remove(courses);
        courses.getPersons().remove(person);
        personRepository.save(person.get());
        session.setAttribute("courses", courses);
        ModelAndView modelAndView = new ModelAndView("redirect:/teacher/viewStudents?id=" + courses.getCourseId());
        return modelAndView;
    }
}
