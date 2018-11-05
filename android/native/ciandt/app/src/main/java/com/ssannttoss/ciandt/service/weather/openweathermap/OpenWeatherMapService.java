package com.ssannttoss.ciandt.service.weather.openweathermap;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.ssannttoss.ciandt.R;
import com.ssannttoss.ciandt.service.weather.openweathermap.dto.OpenWeatherDto;
import com.ssannttoss.framework.event.ChangeEvent;
import com.ssannttoss.framework.ioc.DependencyManager;
import com.ssannttoss.framework.logging.LogManager;
import com.ssannttoss.framework.util.GsonRequest;

import org.greenrobot.eventbus.EventBus;

/**
 * Service to retrieve weather information from OpenWeather.
 * <p>
 * Created by ssannttoss on Nov/04/2018
 */
public class OpenWeatherMapService implements Runnable {
    private double latitude;
    private double longitude;
    private Context context;

    public final class OpenWeatherMapEvent<OpenWeatherResponse> extends ChangeEvent<OpenWeatherResponse> {
        private OpenWeatherMapEvent(OpenWeatherResponse model) {
            super(null, model, null);
        }
    }

    /**
     * Creates an OpenWeatherMapService.
     * @param latitude The latitude of current location
     * @param longitude The longitude of current location
     */
    public OpenWeatherMapService(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        context = DependencyManager.getInstance().get(Context.class);
    }

    private void fetchWeather(String url) {
        GsonRequest<OpenWeatherDto> request = new GsonRequest<>(url,
                OpenWeatherDto.class, null,
                this::process,
                error -> LogManager.e(this, error.getMessage(), error)
        );

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    private void process(OpenWeatherDto response) {
        EventBus.getDefault().post(new OpenWeatherMapService.OpenWeatherMapEvent<>(response));
    }

    @Override
    public void run() {
        String url = context.getString(R.string.openweathermap_api_url, latitude, longitude, context.getString(R.string.openweathermap_api_key));
        fetchWeather(url);
    }
}
