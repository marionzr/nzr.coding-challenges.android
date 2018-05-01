package com.ssannttoss.android_challenge.mvc.view.map;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ssannttoss.android_challenge.BuildConfig;
import com.ssannttoss.android_challenge.R;
import com.ssannttoss.android_challenge.mvc.view.search.AddressViewModel;
import com.ssannttoss.framework.mvc.view.AbstractFragmentViewExt;
import com.ssannttoss.framework.ui.adapter.GoogleMapInfoWindowExtAdapter;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 * Use the {@link MapFragView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragView extends AbstractFragmentViewExt<List<AddressViewModel>> {
    private GoogleMap googleMap;
    private HashMap<String, AddressViewModel> markerMap;
    private int googleMapsZoomOne = 21;
    private int googleMapsZoomAll = 5;
    private ActionListener actionListener;

    public MapFragView() {
        super(BuildConfig.APPLICATION_ID);
        markerMap = new HashMap<>();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MapFragView.
     */
    public static MapFragView newInstance(@NonNull ActionListener actionListener) {
        MapFragView fragment = new MapFragView();
        fragment.actionListener = actionListener;
        return fragment;
    }

    @LayoutRes
    protected int getLayoutId() {
        return R.layout.map_frag_view;
    }

    @Override
    protected void onFinishCreateView(View view) {
        googleMapsZoomOne = getResources().getInteger(R.integer.google_map_zoom_level_show_one);
        googleMapsZoomAll = getResources().getInteger(R.integer.google_map_zoom_level_show_all);

        SupportMapFragment smf = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        smf.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                onGoogleMapReady(googleMap);
            }
        });
    }

    /**
     * Loads the information from model to display it on the map view.
     */
    private void loadFromModel() {
        if (googleMap == null || getModel() == null) {
            return;
        }

        CameraUpdate cameraUpdate = null;

        final LatLngBounds.Builder builder = new LatLngBounds.Builder();

        // Creates a marker for each AddressViewModel and set up the camera view.
        for (AddressViewModel addressViewModel : getModel()) {
            final MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.draggable(false);
            markerOptions.position(addressViewModel.getLatLng());
            markerOptions.title(addressViewModel.getFeatureName());
            final Marker marker = googleMap.addMarker(markerOptions);
            markerMap.put(marker.getId(), addressViewModel);

            builder.include(marker.getPosition());

            if (addressViewModel.isSelected()) {
                cameraUpdate = CameraUpdateFactory.newLatLngZoom(addressViewModel.getLatLng(), googleMapsZoomOne);
            }
        }

        if (cameraUpdate == null) {
            LatLngBounds latLngBounds = builder.build();
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.12); // offset from edges of the map 12% of screen
            cameraUpdate = CameraUpdateFactory.newLatLngBounds(latLngBounds, width, height, padding);

        }

        googleMap.animateCamera(cameraUpdate);

    }

    private void onGoogleMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        if (googleMap != null) {
            loadFromModel();

            googleMap.setInfoWindowAdapter(new GoogleMapInfoWindowExtAdapter(getLayoutInflater()));
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    return showMarkerInfo(marker);
                }
            });

            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    actionListener.onAddressViewModelSelected(null);
                }
            });
        } else {
            String error = getContext().getString(R.string.map_feature_not_available);
            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Displays feature name and location when the user clicks on a marker.
     * @param marker
     * @return
     */
    private boolean showMarkerInfo(Marker marker) {
        AddressViewModel addressViewModel = null;
        try {
            if (markerMap.containsKey(marker.getId())) {
                addressViewModel = markerMap.get(marker.getId());
                marker.setTitle(addressViewModel.getFeatureName());
                marker.setSnippet(String.format("%.6f %.6f", addressViewModel.getLatLng().latitude,
                        addressViewModel.getLatLng().longitude));
                marker.showInfoWindow();

                final CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(addressViewModel.getLatLng(), googleMapsZoomOne);
                googleMap.animateCamera(cameraUpdate);

                return true;
            } else {
                return false;
            }
        } finally {
            actionListener.onAddressViewModelSelected(addressViewModel);
        }
    }

    /**
     * Actions performed by the user on the map that needs to be handler
     */
    public interface ActionListener {
        void onAddressViewModelSelected(AddressViewModel addressViewModel);
    }

}
