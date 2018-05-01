package com.ssannttoss.android_challenge.mvc.view.search;

import android.Manifest;
import android.content.Intent;
import android.location.Address;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ssannttoss.android_challenge.BuildConfig;
import com.ssannttoss.android_challenge.R;
import com.ssannttoss.android_challenge.mvc.controller.SearchController;
import com.ssannttoss.android_challenge.mvc.view.map.MapActView;
import com.ssannttoss.android_challenge.service.LocationExtIntentService;
import com.ssannttoss.framework.logging.LogManager;
import com.ssannttoss.framework.mvc.view.AbstractActViewExt;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity to display the search result and provide access to map view.
 */
public class SearchActView extends AbstractActViewExt<List<AddressViewModel>> {
    private LocationServiceExtResultReceiver locationServiceExtResultReceiver;
    private SearchFragView searchFragView;
    private ProgressBar progressBar;

    public SearchActView() {
        super();
        super.displayBackButton = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle(R.string.search_view, 0);

        setController(new SearchController());
        getController().initialize();
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    @IdRes
    public int getCustomContainerId() {
        return R.id.frm_content;
    }

    @Override
    @LayoutRes
    public int getLayoutContentId() {
        return R.layout.search_act_view;
    }

    /**
     * Template method. Called just after the onCreate
     */
    @Override
    protected void onAddFragments() {
        searchFragView = SearchFragView.newInstance(new SearchFragView.ActionListener() {
            @Override
            public void onAddressViewModelSelected(AddressViewModel addressViewModel) {
                showMap(addressViewModel);
            }

            @Override
            public void onSearchButtonClick(String locationName) {
                search(findViewById(R.id.snackbar_base), locationName);
            }
        });

        addFragment(searchFragView);
    }

    private void search(final View view, final String locationName) {
        try {
            hideKeyboard();

            boolean hasConnection = checkNetwork(view, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    search(view, locationName);
                }
            });

            if (!hasConnection) {
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            locationServiceExtResultReceiver = new LocationServiceExtResultReceiver(null);
            Intent intent = new Intent(this, LocationExtIntentService.class);
            intent.putExtra(BuildConfig.APPLICATION_ID + LocationExtIntentService.LOCATION_NAME_EXTRA, locationName);
            intent.putExtra(BuildConfig.APPLICATION_ID + LocationExtIntentService.RECEIVER_EXTRA, locationServiceExtResultReceiver);
            intent.putExtra(BuildConfig.APPLICATION_ID + LocationExtIntentService.API_SOURCE_EXTRA, true);
            startService(intent);
        } catch (Exception e) {
            showException(R.string.show_error_unexpected_error, e);
        }
    }

    private void showMap(AddressViewModel addressViewModelSelected) {
        try {
            String apiKey = getString(R.string.google_map_api_key);

            if (TextUtils.isEmpty(apiKey)) {
                LogManager.e(this, getString(R.string.google_map_not_available), null);
                showException(getString(R.string.google_map_not_available), null);
                Toast.makeText(this, R.string.google_map_not_available, Toast.LENGTH_LONG).show();
                return;
            }

            if (getModel().size() > 0) {
                for (AddressViewModel addressViewModel : getModel()) {
                    addressViewModel.setSelected(addressViewModel == addressViewModelSelected); // checking reference
                }

                Intent intent = new Intent(this, MapActView.class);
                intent.putExtra(BuildConfig.APPLICATION_ID + MapActView.ADDRESS_VIEW_DISPLAY_ALL_MODE, addressViewModelSelected == null);
                intent.putParcelableArrayListExtra(BuildConfig.APPLICATION_ID + MapActView.ADDRESS_VIEW_MODEL_EXTRA,
                        new ArrayList<Parcelable>(getModel()));
                startActivity(intent);
            }
        } catch (Exception e) {
            showException(R.string.show_error_unexpected_error, e);
        }
    }

    class LocationServiceExtResultReceiver extends ResultReceiver {
        public LocationServiceExtResultReceiver(Handler handler) {
            super(handler);
        }

        /**
         * Called when the Intent Services has finished its job doing geocoding.
         * @param resultCode
         * @param resultData
         */
        @Override
        protected void onReceiveResult(int resultCode, final Bundle resultData) {
            try {
                if (resultCode == LocationExtIntentService.SUCCESS_CODE) {
                    final ArrayList<Address> addressList =
                            resultData.getParcelableArrayList(BuildConfig.APPLICATION_ID + LocationExtIntentService.RESULT_DATA_EXTRA);

                    getModel().clear();
                    for (Address address : addressList) {
                        getModel().add(new AddressViewModel(address));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            searchFragView.loadFromModel();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getModel().clear();
                            searchFragView.loadFromModel();
                            showException(R.string.show_error_unexpected_error, null);
                        }
                    });
                }
            } catch (Exception e) {
                showException(R.string.show_error_unexpected_error, e);
            } finally {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }
    }
}
