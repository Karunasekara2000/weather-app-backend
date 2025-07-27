package com.weather_app_backend.controller;

import com.weather_app_backend.model.Weather;
import com.weather_app_backend.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<Weather>> getWeatherSummary(@RequestParam String city) {
        return weatherService.getWeatherSummaryAsync(city)
                .thenApply(ResponseEntity::ok);
    }

}
