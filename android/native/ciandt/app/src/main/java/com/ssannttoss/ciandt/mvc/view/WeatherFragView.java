package com.ssannttoss.ciandt.mvc.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ssannttoss.ciandt.BuildConfig;
import com.ssannttoss.ciandt.R;
import com.ssannttoss.ciandt.mvc.model.entity.Weather;
import com.ssannttoss.framework.mvc.view.AbstractFragmentViewExt;

import java.util.Map;

public class WeatherFragView extends AbstractFragmentViewExt<Weather> {
    private Picasso picasso;
    private ImageView imgWeather;
    private TextView txtLocation;
    private TextView txtWeather;
    private TextView txtWeatherHi;
    private TextView txtWeatherLo;
    private TextView txtWeatherDescription;

    public WeatherFragView() {
        super(BuildConfig.APPLICATION_ID);
    }

    public static WeatherFragView newInstance() {
        return new WeatherFragView();
    }

    @Override
    protected void onFinishCreateView(View view) {
        super.onFinishCreateView(view);

        imgWeather = view.findViewById(R.id.imgWeather);
        txtLocation = view.findViewById(R.id.txtLocation);
        txtWeather = view.findViewById(R.id.txtWeather);
        txtWeatherHi = view.findViewById(R.id.txtWeatherHi);
        txtWeatherLo = view.findViewById(R.id.txtWeatherLo);
        txtWeatherDescription = view.findViewById(R.id.txtWeatherDescription);
        picasso = Picasso.with(getActivity());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.weather_frag_view;
    }

    @Override
    protected void onUpdateView(String action, Weather model, Map<String, Object> extras) {
        picasso.load(getString(R.string.openweather_icon_url, model.getIcon()))
                .into(imgWeather);
        txtLocation.setText(model.getCity().concat(", ").concat(model.getCountry()));
        txtWeather.setText(String.valueOf(model.getTemperature().intValue()));
        txtWeatherHi.setText(getString(R.string.temperature_hi) + String.valueOf(model.getTemperatureMax().intValue()));
        txtWeatherLo.setText(getString(R.string.temperature_low) + String.valueOf(model.getTemperatureMin().intValue()));
        txtWeatherDescription.setText(model.getDescription());
    }
}
