package com.ssannttoss.ciandt.service.weather.openweathermap.dto;

public class CountryDto {
    private String country;

    public CountryDto(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }
}
