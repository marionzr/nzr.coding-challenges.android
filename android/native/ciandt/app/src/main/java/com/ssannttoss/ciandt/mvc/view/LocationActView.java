package com.ssannttoss.ciandt.mvc.view;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ProgressBar;

import com.ssannttoss.ciandt.R;
import com.ssannttoss.ciandt.mvc.model.entity.Login;
import com.ssannttoss.framework.ioc.DependencyManager;
import com.ssannttoss.framework.mvc.view.AbstractActViewExt;

import java.util.Map;

public class LocationActView<CurrentLocation> extends AbstractActViewExt<CurrentLocation> {
    private LocationFragView locationFragView;
    private ProgressBar progressBar;

    public LocationActView() {
        super();
        super.displayBackButton = true;
    }

    @Override
    public int getCustomContainerId() {
        return R.id.frm_content;
    }

    @Override
    public int getLayoutContentId() {
        return R.layout.location_act_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        showCurrentUser();
    }

    private void showCurrentUser() {
        String currentUser = DependencyManager.getInstance().get(Login.class);
        View parentLayout = findViewById(android.R.id.content);
        String loggedAs = getString(R.string.logged_as, currentUser);
        Snackbar.make(parentLayout, loggedAs, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onAddFragments() {
        locationFragView = LocationFragView.newInstance();
        addFragment(locationFragView);
    }

    @Override
    protected void onUpdateView(String action, CurrentLocation model, Map<String, Object> extras) {
        super.onUpdateView(action, model, extras);
        progressBar.setVisibility(View.GONE);
    }
}
