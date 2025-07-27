package com.weather_app_backend.client;

import com.weather_app_backend.model.dto.WeatherDto;
import com.weather_app_backend.model.dto.WeatherInfoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class WeatherApiClient {

    private final WebClient webClient;
    @Value("${weather.api.key}")
    private String apiKey;


    public WeatherApiClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://api.openweathermap.org/data/2.5").build();
    }

    public List<WeatherInfoDto> getForecastByCity(String city){
        try {
            WeatherDto response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/forecast")
                            .queryParam("q", city)
                            .queryParam("appid", apiKey)
                            .queryParam("units", "metric")
                            .build())
                    .retrieve()
                    .bodyToMono(WeatherDto.class)
                    .block();
            if(response != null){
                return response.getList();
            }else{
                throw new RuntimeException("No response received fromAPI");
            }
        }catch (Exception e){
            throw new RuntimeException("City not found or API failure: "+ e.getMessage());
        }
    }


}
