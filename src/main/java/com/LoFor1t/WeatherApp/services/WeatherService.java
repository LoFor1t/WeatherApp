package com.LoFor1t.WeatherApp.services;

import com.LoFor1t.WeatherApp.entities.Location;
import com.LoFor1t.WeatherApp.repositories.LocationRepository;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

@Service
public class WeatherService {
    @Value("${openWeatherAPI.key}")
    private String APIKey;

    private final LocationRepository locationRepository;

    public WeatherService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location getCitiesByName(String cityName) throws URISyntaxException, IOException, InterruptedException {
        URI uri = new URIBuilder("https://api.openweathermap.org/data/2.5/weather")
                .addParameter("q", cityName)
                .addParameter("units", "metric")
                .addParameter("appid", APIKey)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return jsonToLocation(new JSONObject(response.body()));
    }

    public ArrayList<Location> getLocationsByUserId(Integer userID) throws URISyntaxException, IOException, InterruptedException {
        Iterable<Location> userLocations = locationRepository.findByUserId(userID);

        ArrayList<Location> APILocations = new ArrayList<>();

        for (Location location: userLocations) {
            APILocations.add(getLocationByCoordinates(location));
        }

        return APILocations;
    }

    public Location getLocationByCoordinates(Location location) throws URISyntaxException, IOException, InterruptedException {
        URI uri = new URIBuilder("https://api.openweathermap.org/data/2.5/weather")
                .addParameter("lat", String.valueOf(location.getLatitude()))
                .addParameter("lon", String.valueOf(location.getLongitude()))
                .addParameter("units", "metric")
                .addParameter("appid", APIKey)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return jsonToLocation(new JSONObject(response.body()));
    }


    public Location jsonToLocation(JSONObject json) {
        return Location.builder()
                .name(json.getString("name"))
                .latitude(json.getJSONObject("coord").getFloat("lat"))
                .longitude(json.getJSONObject("coord").getFloat("lon"))
                .temperature(json.getJSONObject("main").getFloat("temp"))
                .weather(json.getJSONArray("weather").getJSONObject(0).getString("main"))
                .build();
    }
}
