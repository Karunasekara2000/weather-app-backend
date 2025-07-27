package com.weather_app_backend.service;

import com.weather_app_backend.client.WeatherApiClient;
import com.weather_app_backend.model.Weather;
import com.weather_app_backend.model.dto.WeatherInfoDto;
import com.weather_app_backend.util.WeatherDataProcessorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService{

    private final WeatherApiClient weatherApiClient;

    public WeatherServiceImpl(WeatherApiClient weatherApiClient) {
        this.weatherApiClient = weatherApiClient;
    }

    @Override
    public Weather getWeatherSummary(String city) {
        List<WeatherInfoDto> forecastList = weatherApiClient.getForecastByCity(city);
        return WeatherDataProcessorUtil.process(city,forecastList);
    }
}
