package com.ssannttoss.ciandt.mvc.controller;

import com.ssannttoss.ciandt.mvc.model.business.WeatherBusinessLogic;
import com.ssannttoss.ciandt.mvc.model.entity.CurrentLocation;
import com.ssannttoss.ciandt.mvc.model.entity.Weather;
import com.ssannttoss.ciandt.service.weather.openweathermap.OpenWeatherMapService;
import com.ssannttoss.ciandt.service.weather.openweathermap.dto.OpenWeatherDto;
import com.ssannttoss.framework.logging.LogManager;
import com.ssannttoss.framework.mvc.CommonActions;
import com.ssannttoss.framework.mvc.controller.AbstractControllerExt;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.Map;

/**
 * Handlers the requests from the View and notify the View in case of
 * response from other services.
 * <p>
 * Created by ssannttoss on Nov/04/2018
 */
public class WeatherController extends AbstractControllerExt<Weather>  {
    public WeatherController() {
        super();
        EventBus.getDefault().register(this);
    }

    /**
     * Handlers the view state changes or view requests
     *
     * @param action The action to be performed
     * @param model The object used as a model for this request
     * @param extras Extra information not present in the Model attributes
     * @param responseListener A response listener for synchronous operations
     */
    @Override
    protected void onViewStateChanged(String action, Weather model, Map<String, Object> extras, ResponseListener<Weather> responseListener) {
        if (CommonActions.LOAD_DATA.equals(action)) {
            requestWeatherInformation(extras);
        }
    }

    private void requestWeatherInformation(Map<String, Object> extras) {
        LogManager.d(this, "Requesting weather information");
        CurrentLocation currentLocation = (CurrentLocation) extras.get(CurrentLocation.class.getName());
        new WeatherBusinessLogic().fetchWeather(currentLocation);
    }

    /**
     * Handlers the notification from OpenWeatherMapService of a weather information request
     * and notify the View.
     * @param event FacebookAuthEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWeatherFechedEvent(OpenWeatherMapService.OpenWeatherMapEvent<OpenWeatherDto> event) {
        LogManager.i(this, "OpenWeatherMapEvent: " + event.getData());
        Weather weather = new WeatherBusinessLogic().translate(event.getData());
        viewExt.updateView(CommonActions.LOAD_DATA, weather, Collections.emptyMap());
    }
}
