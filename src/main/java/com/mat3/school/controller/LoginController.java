package com.mat3.school.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class LoginController {
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String displayLoginPage(@RequestParam(required = false) String error,
                                   @RequestParam(required = false) String logout, Model model) {
        String loginMessage = null;
        String logoutMessage = null;
        if (error != null)
            loginMessage = "Username or Password is incorrect !!";

        if (logout != null)
            logoutMessage = "You have been successfully logged out !!";

        model.addAttribute("loginMessage", loginMessage);
        model.addAttribute("logoutMessage", logoutMessage);
        return "login.html";
    }
}
