package com.ssannttoss.ciandt.mvc.model.entity;

public class Weather {
    private String city;
    private String country;
    private Double temperature;
    private Double temperatureMax;
    private Double temperatureMin;
    private String icon;
    private String description;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Weather withCity(String city) {
        setCity(city);
        return this;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Weather withCountry(String country) {
        setCountry(country);
        return this;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Weather withTemperature(Double temperature) {
        setTemperature(temperature);
        return this;
    }

    public Double getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(Double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public Weather withTemperatureMax(Double temperatureMax) {
        setTemperatureMax(temperatureMax);
        return this;
    }

    public Double getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(Double temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public Weather withTemperatureMin(Double temperatureMin) {
        setTemperatureMin(temperatureMin);
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Weather withIcon(String icon) {
        setIcon(icon);
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Weather withDescription(String description) {
        setDescription(description);
        return this;
    }
}
