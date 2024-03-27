package com.mat3.school.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
                                   @RequestParam(required = false) String logout,
                                   @RequestParam(required = false) String register,
                                   Model model) {
        String loginMessage = null;
        String registerMessage = null;
        String logoutMessage = null;
        if (error != null)
            loginMessage = "Username or Password is incorrect!";

        if (register != null)
            registerMessage = "You registration successful. Login with registered credentials!";

        if (logout != null)
            logoutMessage = "You have been successfully logged out!";

        model.addAttribute("loginMessage", loginMessage);
        model.addAttribute("registerMessage", registerMessage);
        model.addAttribute("logoutMessage", logoutMessage);
        return "login.html";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }
}
