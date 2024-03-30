package com.mat3.school.controller;

import com.mat3.school.model.Person;
import com.mat3.school.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class DashboardController {
    private final PersonRepository personRepository;

    @Autowired
    public DashboardController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @RequestMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication, HttpSession session) {
        Person person = personRepository.readByEmail(authentication.getName());
        session.setAttribute("loggedInPerson", person);
        model.addAttribute("username", person.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        return "dashboard.html";
    }
}
