package com.weather_app_backend.service;

import com.weather_app_backend.client.WeatherApiClient;
import com.weather_app_backend.model.Weather;
import com.weather_app_backend.model.dto.WeatherInfoDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class WeatherServiceImplTest {

    @Mock
    private WeatherApiClient weatherApiClient;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    public WeatherServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetWeatherSummary_ReturnsCorrectWeather() {
        String city = "London";

        WeatherInfoDto mockWeatherInfo = new WeatherInfoDto();
        mockWeatherInfo.setDateTime("2025-07-31 12:00:00");
        mockWeatherInfo.setMain(new WeatherInfoDto.MainWeatherDto(298.0));

        List<WeatherInfoDto> mockList = Collections.singletonList(mockWeatherInfo);

        when(weatherApiClient.getForecastByCity(city)).thenReturn(mockList);

        Weather result = weatherService.getWeatherSummary(city);

        assertNotNull(result);
        assertEquals("London", result.getCity());
        assertTrue(result.getAverageTemperature() > 0);
    }
}
