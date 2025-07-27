package com.weather_app_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableCaching
@SpringBootApplication
@EnableAsync
public class WeatherAppBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherAppBackendApplication.class, args);
	}

}
