package com.ssannttoss.framework.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.ssannttoss.framework.R;

/**
 * Extends the {@link GoogleMap.InfoWindowAdapter} with a title and subtitle TextView
 * to better display information on Google Map markers.
 * <p>
 * Created by ssannttoss on 1/20/2018.
 */

public class GoogleMapInfoWindowExtAdapter implements GoogleMap.InfoWindowAdapter {
    private final LayoutInflater layoutInflater;
    private View view;

    public GoogleMapInfoWindowExtAdapter(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return (null);
    }

    @Override
    public View getInfoContents(Marker marker) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.google_map_info_window_ext, null);
        }

        TextView txtTitle = view.findViewById(R.id.txt_info_window_title);
        TextView txtSubTitle = view.findViewById(R.id.txt_info_window_subtitle);

        txtTitle.setText(marker.getTitle());
        txtSubTitle.setText(marker.getSnippet());
        return view;
    }
}
