package com.weather_app_backend.service;

import com.weather_app_backend.client.WeatherApiClient;
import com.weather_app_backend.model.Weather;
import com.weather_app_backend.model.dto.WeatherInfoDto;
import com.weather_app_backend.util.WeatherDataProcessorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class WeatherServiceImpl implements WeatherService{

    private final WeatherApiClient weatherApiClient;

    public WeatherServiceImpl(WeatherApiClient weatherApiClient) {
        this.weatherApiClient = weatherApiClient;
    }

    @Cacheable(value = "weather", key = "#city")
    @Override
    public Weather getWeatherSummary(String city) {
        List<WeatherInfoDto> forecastList = weatherApiClient.getForecastByCity(city);
        return WeatherDataProcessorUtil.process(city,forecastList);
    }

    @Override
    @Async
    public CompletableFuture<Weather> getWeatherSummaryAsync(String city) {
        Weather weather = getWeatherSummary(city);
        return CompletableFuture.completedFuture(weather);
    }
}
