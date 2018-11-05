package com.ssannttoss.ciandt.mvc.model.business;

import com.ssannttoss.ciandt.mvc.model.entity.CurrentLocation;
import com.ssannttoss.ciandt.mvc.model.entity.Weather;
import com.ssannttoss.ciandt.service.weather.openweathermap.OpenWeatherMapService;
import com.ssannttoss.ciandt.service.weather.openweathermap.dto.OpenWeatherDto;

/**
 * Perform business logic and validations related to weather services.
 * <p>
 * Created by ssannttoss on Nov/04/2018
 */
public class WeatherBusinessLogic {
    private static CurrentLocation defaultLocation;

    /**
     * If the user log in as a Guest a default location will be used to show the weather.
     */
    static {
        defaultLocation = new CurrentLocation(51.5073, -0.1277); // London
    }

    /**
     * Gets the current weather by a given location.
     * If the location is null, the default value is used.
     * @param location The current user location
     */
    public void fetchWeather(CurrentLocation location) {
        if (location == null) {
            location = defaultLocation;
        }

        new Thread(new OpenWeatherMapService(location.getLatitude(), location.getLongitude())).start();
    }

    /**
     * Translates the dto entities from openweather services to Weather.
     * @param from
     * @return
     */
    public Weather translate(OpenWeatherDto from) {
        return new Weather()
                .withCountry(from.getSys().getCountry())
                .withCity(from.getName())
                .withDescription(from.getWeather().get(0).getDescription())
                .withIcon(from.getWeather().get(0).getIcon())
                .withTemperature(from.getMain().getTemp())
                .withTemperatureMax(from.getMain().getTempMax())
                .withTemperatureMin(from.getMain().getTempMin());
    }
}
