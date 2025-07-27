package com.weather_app_backend.util;

import com.weather_app_backend.model.Weather;
import com.weather_app_backend.model.dto.WeatherInfoDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class WeatherDataProcessorUtil {

    public static Weather process(String city, List<WeatherInfoDto> weatherInfoList){
        Map<LocalDate,List<Double>> temperatureByDate = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (WeatherInfoDto dto : weatherInfoList) {
            LocalDateTime dateTime = LocalDateTime.parse(dto.getDateTime(), formatter);
            LocalDate date = dateTime.toLocalDate();
            double temp = dto.getMain().getTemp();

            temperatureByDate.computeIfAbsent(date, k -> new ArrayList<>()).add(temp);
        }

        // Keep only last 7 days (sorted)
        List<LocalDate> recentDates = temperatureByDate.keySet().stream()
                .sorted(Comparator.reverseOrder())
                .limit(7)
                .collect(Collectors.toList());

        Map<LocalDate, Double> dailyAvgTemp = new HashMap<>();
        for (LocalDate date : recentDates) {
            List<Double> temps = temperatureByDate.get(date);
            double avg = temps.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            dailyAvgTemp.put(date, avg);
        }

        // Compute overall average
        double overallAvg = dailyAvgTemp.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        // Find hottest and coldest days
        LocalDate hottestDay = Collections.max(dailyAvgTemp.entrySet(), Map.Entry.comparingByValue()).getKey();
        LocalDate coldestDay = Collections.min(dailyAvgTemp.entrySet(), Map.Entry.comparingByValue()).getKey();

        return Weather.builder()
                .city(city)
                .averageTemperature(overallAvg)
                .hottestDay(java.sql.Date.valueOf(hottestDay))
                .coldestDay(java.sql.Date.valueOf(coldestDay))
                .build();
    }
}
