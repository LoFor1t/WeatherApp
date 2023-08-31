package com.LoFor1t.WeatherApp.controllers;

import com.LoFor1t.WeatherApp.entities.Location;
import com.LoFor1t.WeatherApp.entities.User;
import com.LoFor1t.WeatherApp.repositories.LocationRepository;
import com.LoFor1t.WeatherApp.services.SessionService;
import com.LoFor1t.WeatherApp.services.WeatherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

@Controller
@RequestMapping("/locations")
public class LocationsController {

    private final SessionService sessionService;
    private final WeatherService weatherService;
    private final LocationRepository locationRepository;

    public LocationsController(SessionService sessionService, WeatherService weatherService, LocationRepository locationRepository) {
        this.sessionService = sessionService;
        this.weatherService = weatherService;
        this.locationRepository = locationRepository;
    }

    @GetMapping
    public String getUserLocations(@CookieValue(value = "sessionId", defaultValue = "") String sessionId, Model model) throws URISyntaxException, IOException, InterruptedException {
        Integer userId = sessionService.getUserIdBySession(sessionId);
        if (userId == null) {
            return "redirect:/signin";
        }

        ArrayList<Location> userLocations = weatherService.getLocationsByUserId(userId);

        model.addAttribute("locations", userLocations);

        return "mainPage";
    }

    @DeleteMapping
    public String deleteLocationFromUser(@RequestParam int locationId) {
        locationRepository.deleteById(locationId);
        return "redirect:/locations";
    }

    @GetMapping("/search")
    public String searchPlaces(@CookieValue(value = "sessionId", defaultValue = "") String sessionId, @RequestParam(value = "cityName", defaultValue = "") String cityName, Model model) throws URISyntaxException, IOException, InterruptedException {
        Integer userId = sessionService.getUserIdBySession(sessionId);
        if (userId == null) {
            return "redirect:/signin";
        }
        Location location = weatherService.getCitiesByName(cityName);
        model.addAttribute("location", location);
        return "search";
    }

    @PostMapping("/addToUser")
    public String addPlaceToUser(@CookieValue(value = "sessionId", defaultValue = "") String sessionId, @ModelAttribute("location") Location location) {
        User user = sessionService.getUserBySession(sessionId);
        if (user == null) {
            return "redirect:/signin";
        }

        location.setUser(user);

        locationRepository.save(location);

        return "redirect:/locations";
    }
}
