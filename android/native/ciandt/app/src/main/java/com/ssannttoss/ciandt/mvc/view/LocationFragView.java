package com.ssannttoss.ciandt.mvc.view;

import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ssannttoss.ciandt.BuildConfig;
import com.ssannttoss.ciandt.R;
import com.ssannttoss.ciandt.mvc.model.entity.CurrentLocation;
import com.ssannttoss.framework.mvc.CommonActions;
import com.ssannttoss.framework.mvc.view.AbstractFragmentViewExt;
import com.ssannttoss.framework.ui.adapter.GoogleMapInfoWindowExtAdapter;

import java.util.Map;
import java.util.Optional;

public class LocationFragView extends AbstractFragmentViewExt<CurrentLocation> {
    private GoogleMap googleMap;

    public LocationFragView() {
        super(BuildConfig.APPLICATION_ID);
    }

    public static LocationFragView newInstance() {
        return new LocationFragView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.location_frag_view;
    }

    @Override
    protected void onFinishCreateView(View view) {
        super.onFinishCreateView(view);
        SupportMapFragment smf = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        Optional.ofNullable(smf).ifPresent(map -> map.getMapAsync(this::onGoogleMapReady));
    }

    @Override
    protected void onUpdateView(String action, CurrentLocation model, Map<String, Object> extras) {
        super.onUpdateView(action, model, extras);

        if (googleMap == null || model == null) {
            return;
        }
        googleMap.clear();

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        LatLng position = new LatLng(model.getLatitude(), model.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.draggable(false);
        markerOptions.position(position);

        Marker marker = googleMap.addMarker(markerOptions);
        marker.setTitle(getString(R.string.location_source_gps));
        builder.include(marker.getPosition());

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 10);
        googleMap.animateCamera(cameraUpdate);
    }

    private void onGoogleMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        if (googleMap != null) {
            googleMap.setInfoWindowAdapter(new GoogleMapInfoWindowExtAdapter(getLayoutInflater()));
            googleMap.setOnMarkerClickListener(this::showMarkerInfo);

            onViewStateChanged(CommonActions.LOAD_DATA, null, null, (error, model, extras) -> {
                if (error != null) {
                    showException(getString(R.string.unable_to_get_current_location), error);
                }
            });
        } else {
            Toast.makeText(getContext(), getContext().getString(R.string.map_feature_not_available), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean showMarkerInfo(Marker marker) {
        if (marker == null) {
            return false;
        }

        CurrentLocation model = new CurrentLocation(marker.getPosition().latitude, marker.getPosition().longitude);
        onViewStateChanged(CommonActions.ITEM_SELECTED, model, null, null);
        return false;
    }
}
