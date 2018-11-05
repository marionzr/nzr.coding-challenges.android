package com.ssannttoss.ciandt.service.weather.openweathermap.dto;

import java.util.List;

public class OpenWeatherDto {
    private String name;
    private CountryDto sys;
    private TemperatureDto main;
    private List<WeatherDto> weather;

    public OpenWeatherDto(String name, CountryDto sys, TemperatureDto main, List<WeatherDto> weather) {
        this.name = name;
        this.sys = sys;
        this.main = main;
        this.weather = weather;
    }

    public String getName() {
        return name;
    }

    public CountryDto getSys() {
        return sys;
    }

    public TemperatureDto getMain() {
        return main;
    }

    public List<WeatherDto> getWeather() {
        return weather;
    }
}
