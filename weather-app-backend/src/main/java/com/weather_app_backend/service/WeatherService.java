package com.weather_app_backend.service;

import com.weather_app_backend.model.Weather;

public interface WeatherService {

    Weather getWeatherSummary(String city);
}
