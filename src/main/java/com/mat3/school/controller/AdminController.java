package com.mat3.school.controller;

import com.mat3.school.constants.SchoolConstants;
import com.mat3.school.model.Address;
import com.mat3.school.model.Person;
import com.mat3.school.model.Profile;
import com.mat3.school.model.SchoolClass;
import com.mat3.school.repository.CoursesRepository;
import com.mat3.school.repository.PersonRepository;
import com.mat3.school.repository.RolesRepository;
import com.mat3.school.repository.SchoolClassRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
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
    public final CoursesRepository coursesRepository;
    public final RolesRepository rolesRepository;

    @Autowired
    public AdminController(PersonRepository personRepository, SchoolClassRepository schoolClassRepository, CoursesRepository coursesRepository, RolesRepository rolesRepository) {
        this.personRepository = personRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.coursesRepository = coursesRepository;
        this.rolesRepository = rolesRepository;
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
        ModelAndView modelAndView = new ModelAndView("studentsTeachers");
        Optional<SchoolClass> schoolClass = schoolClassRepository.findById(classId);
        modelAndView.addObject("schoolClass", schoolClass.get());
        modelAndView.addObject("person", new Person());
        session.setAttribute("schoolClass", schoolClass.get());
        if (studentEmailError != null)
            modelAndView.addObject("errorMessage", "Invalid Student Email entered!");
        if (teacherEmailError != null)
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
        if (person.isPresent()) {
            person.get().setStudentClasses(null);
            schoolClass.getStudents().remove(person.get());
            SchoolClass schoolClassSaved = schoolClassRepository.save(schoolClass);
            session.setAttribute("schoolClass", schoolClassSaved);
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents?classId=" + schoolClass.getClassId());
        return modelAndView;
    }

    @GetMapping("/deleteTeacher")
    public ModelAndView deleteTeacher(@RequestParam int personId, HttpSession session) {
        SchoolClass schoolClass = (SchoolClass) session.getAttribute("schoolClass");
        Optional<Person> person = personRepository.findById(personId);

        if (person.isPresent()) {
            person.get().getTeacherClasses().remove(schoolClass);
            schoolClass.getTeachers().remove(person.get());
            SchoolClass schoolClassSaved = schoolClassRepository.save(schoolClass);
            session.setAttribute("schoolClass", schoolClassSaved);
        }

        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents?classId=" + schoolClass.getClassId());
        return modelAndView;
    }

    @GetMapping("/displayUsers")
    public ModelAndView displayUsers() {
        ModelAndView modelAndView = new ModelAndView("users");
        modelAndView.addObject("person", new Person());
        modelAndView.addObject("users", personRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/deleteUser")
    public ModelAndView deleteUser(@RequestParam int personId) {
        personRepository.deleteById(personId);
        return new ModelAndView("redirect:/admin/displayUsers");
    }

    @GetMapping("/displayUserDetails")
    public ModelAndView displayMessages(@RequestParam int personId) {
        Person person = personRepository.findById(personId).orElse(new Person());
        Profile userDetails = new Profile();
        userDetails.setName(person.getName());
        userDetails.setTeacherSubject(person.getTeacherSubject());
        userDetails.setMobileNumber(person.getMobileNumber());
        userDetails.setEmail(person.getEmail());
        userDetails.setRoleName(person.getRoles().getRoleName());
        if (person.getAddress() != null && person.getAddress().getAddressId() > 0) {
            userDetails.setAddress1(person.getAddress().getAddress1());
            userDetails.setAddress2(person.getAddress().getAddress2());
            userDetails.setCity(person.getAddress().getCity());
            userDetails.setState(person.getAddress().getState());
            userDetails.setZipCode(person.getAddress().getZipCode());
        }
        ModelAndView modelAndView = new ModelAndView("adminUserDetails");
        modelAndView.addObject("profile", userDetails);
        modelAndView.addObject("personId", personId);
        return modelAndView;
    }

    @PostMapping(value = "/updateUserDetails")
    public String updateProfile(Profile profile, @RequestParam int personId) {
        Person person = personRepository.findById(personId).orElse(new Person());
        person.setName(profile.getName());
        person.setTeacherSubject(profile.getTeacherSubject());
        person.setEmail(profile.getEmail());
        person.setMobileNumber(profile.getMobileNumber());
        switch (profile.getRoleName().toUpperCase()) {
            case SchoolConstants.STUDENT_ROLE ->
                    person.setRoles(rolesRepository.getByRoleName(SchoolConstants.STUDENT_ROLE));
            case SchoolConstants.TEACHER_ROLE ->
                    person.setRoles(rolesRepository.getByRoleName(SchoolConstants.TEACHER_ROLE));
            case SchoolConstants.ADMIN_ROLE ->
                    person.setRoles(rolesRepository.getByRoleName(SchoolConstants.ADMIN_ROLE));
        }

        if (person.getAddress() == null || !(person.getAddress().getAddressId() > 0))
            person.setAddress(new Address());

        person.getAddress().setAddress1(profile.getAddress1());
        person.getAddress().setAddress2(profile.getAddress2());
        person.getAddress().setCity(profile.getCity());
        person.getAddress().setState(profile.getState());
        person.getAddress().setZipCode(profile.getZipCode());
        personRepository.save(person);
        return "redirect:/admin/displayUsers";
    }
}
