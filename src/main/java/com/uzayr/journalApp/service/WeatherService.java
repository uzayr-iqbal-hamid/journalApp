package com.uzayr.journalApp.service;

import com.uzayr.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    private static final String apiKey = "56aa2c035571bc4b037e21da3c9220d7";

    private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=Mumbai";

    @Autowired
    private RestTemplate restTemplate;


    public WeatherResponse getWeather(String city) {
        String finalAPI = API.replace("CITY", city).replace("API_KEY", apiKey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
        response.getStatusCode();
        WeatherResponse body = response.getBody();
        return body;
    }
}
