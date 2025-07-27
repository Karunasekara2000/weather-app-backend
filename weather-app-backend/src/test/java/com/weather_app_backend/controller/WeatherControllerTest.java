package com.weather_app_backend.controller;

import com.weather_app_backend.model.Weather;
import com.weather_app_backend.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class WeatherControllerTest {

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private WeatherController weatherController;

    public WeatherControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetWeatherSummary_ReturnsWeather() throws ExecutionException, InterruptedException {
        String city = "London";
        Weather mockWeather = new Weather();
        mockWeather.setCity(city);
        mockWeather.setAverageTemperature(290.0);

        when(weatherService.getWeatherSummary(city)).thenReturn(mockWeather);

        CompletableFuture<ResponseEntity<Weather>> futureResponse = weatherController.getWeatherSummary(city);
        ResponseEntity<Weather> response = futureResponse.get();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(city, response.getBody().getCity());
    }
}
