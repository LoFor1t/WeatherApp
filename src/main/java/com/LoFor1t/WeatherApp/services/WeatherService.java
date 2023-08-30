package com.LoFor1t.WeatherApp.services;

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

@Service
public class WeatherService {
    @Value("${openWeatherAPI.key}")
    private String APIKey;

    public JSONObject getCitiesByName(String cityName) throws URISyntaxException, IOException, InterruptedException {
        System.out.println(APIKey);
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

        return new JSONObject(response.body());
    }

}
