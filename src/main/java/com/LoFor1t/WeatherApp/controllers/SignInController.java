package com.LoFor1t.WeatherApp.controllers;

import com.LoFor1t.WeatherApp.repositories.UserRepository;
import com.LoFor1t.WeatherApp.services.SignInService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/signin")
public class SignInController {
    private final UserRepository userRepository;
    private final SignInService signInService;

    public SignInController(UserRepository userRepository, SignInService signInService) {
        this.userRepository = userRepository;
        this.signInService = signInService;
    }

    @GetMapping
    public String signInForm() {
        return "signin";
    }

    @PostMapping
    public String singInUser(@RequestParam("login") String login, @RequestParam("password") String password, Model model, HttpServletResponse response) {
        if (!userRepository.existsByLogin(login)) {
            model.addAttribute("userNotExist", true);
            return "signin";
        }

        if(!signInService.isPasswordRight(login, password)) {
            model.addAttribute("notRightPassword", true);
            return "signin";
        }

        signInService.createSession(login, response);

        return "redirect:/locations";
    }
}
