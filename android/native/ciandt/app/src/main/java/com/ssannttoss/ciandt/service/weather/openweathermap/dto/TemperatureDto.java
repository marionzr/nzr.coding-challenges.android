package com.ssannttoss.ciandt.service.weather.openweathermap.dto;

public class TemperatureDto {
    private Double temp;
    private Double temp_max;
    private Double temp_min;

    public TemperatureDto(Double temp, Double temp_max, Double temp_min) {
        this.temp = temp;
        this.temp_max = temp_max;
        this.temp_min = temp_min;
    }

    public Double getTemp() {
        return temp;
    }

    public Double getTempMax() {
        return temp_max;
    }

    public Double getTempMin() {
        return temp_min;
    }
}
