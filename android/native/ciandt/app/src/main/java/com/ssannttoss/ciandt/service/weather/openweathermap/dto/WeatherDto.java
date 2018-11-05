package com.ssannttoss.ciandt.service.weather.openweathermap.dto;

public class WeatherDto {
    private String icon;
    private String description;

    public WeatherDto(String icon, String description) {
        this.icon = icon;
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }
}
