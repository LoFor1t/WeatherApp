package com.LoFor1t.WeatherApp.controllers;

import com.LoFor1t.WeatherApp.services.SessionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/locations")
public class LocationsController {

    private final SessionService sessionService;

    public LocationsController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public String getUserLocations(@CookieValue(value = "sessionId", defaultValue = "") String sessionId) {
        Integer userId = sessionService.getUserIdBySession(sessionId);
        if (userId == null) {
            return "redirect:/signin";
        }
        return "";
    }
}
