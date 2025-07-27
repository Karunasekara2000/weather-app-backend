package com.weather_app_backend.service;

import com.weather_app_backend.model.Weather;

import java.util.concurrent.CompletableFuture;

public interface WeatherService {

    Weather getWeatherSummary(String city);

    CompletableFuture<Weather> getWeatherSummaryAsync(String city);
}
