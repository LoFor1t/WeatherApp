package com.LoFor1t.WeatherApp.controllers;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("users")
public class UsersController {
    @GetMapping("/new")
    public String registrationForm(Model model) {
        return "newuser";
    }
}
