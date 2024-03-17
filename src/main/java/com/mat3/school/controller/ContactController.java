package com.mat3.school.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContactController {
    @RequestMapping("/contact")
    public String displayContactPage() {
        return "contact.html";
    }

}
