package com.ssannttoss.ciandt.mvc.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.ssannttoss.ciandt.R;
import com.ssannttoss.ciandt.mvc.model.entity.CurrentLocation;
import com.ssannttoss.ciandt.mvc.model.entity.Weather;
import com.ssannttoss.framework.mvc.CommonActions;
import com.ssannttoss.framework.mvc.view.AbstractActViewExt;

import java.util.Map;

public class WeatherActView extends AbstractActViewExt<Weather> {
    private ProgressBar progressBar;
    public WeatherActView() {
        super();
        super.displayBackButton = true;
    }
    @Override
    public int getCustomContainerId() {
        return R.id.frm_content;
    }

    @Override
    public int getLayoutContentId() {
        return R.layout.weather_act_view;
    }

    @Override
    protected void onAddFragments() {
        addFragment(WeatherFragView.newInstance());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        CurrentLocation currentLocation = intent.getParcelableExtra(CurrentLocation.class.getName());
        extras.put(CurrentLocation.class.getName(), currentLocation);
        onViewStateChanged(CommonActions.LOAD_DATA, null, extras, null);
    }

    @Override
    protected void onUpdateView(String action, Weather model, Map<String, Object> extras) {
        super.onUpdateView(action, model, extras);

        progressBar.setVisibility(View.GONE);
    }
}
