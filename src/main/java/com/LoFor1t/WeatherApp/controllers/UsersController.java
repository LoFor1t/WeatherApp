package com.LoFor1t.WeatherApp.controllers;

import com.LoFor1t.WeatherApp.entities.User;
import com.LoFor1t.WeatherApp.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("users")
public class UsersController {
    private final UserRepository userRepository;

    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/new")
    public String registrationForm(Model model) {
        return "newuser";
    }

    @PostMapping
    public String createNewUser(@RequestParam("login") String login, @RequestParam("password") String password, Model model) {
        String encryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User newUser = User.builder().login(login.toLowerCase()).password(encryptedPassword).build();
        if (userRepository.existsByLogin(login.toLowerCase())) {
            model.addAttribute("userExist", true);
            return "newuser";
        }
        userRepository.save(newUser);
        return "signin";
    }
}
