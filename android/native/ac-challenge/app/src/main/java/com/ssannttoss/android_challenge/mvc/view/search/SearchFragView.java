package com.ssannttoss.android_challenge.mvc.view.search;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ssannttoss.android_challenge.BuildConfig;
import com.ssannttoss.android_challenge.R;
import com.ssannttoss.framework.mvc.view.AbstractFragmentViewExt;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 * Use the {@link SearchFragView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragView extends AbstractFragmentViewExt<List<AddressViewModel>> {
    private static final String LOCATION_SEARCH_EXTRA = ".LOCATION_SEARCH_EXTRA";
    private ImageButton btnSearch;
    private TextView txtResult;
    private EditText edtLocation;
    private RecyclerView mRcvList;
    private ActionListener actionListener;

    public interface ActionListener {
        void onAddressViewModelSelected(AddressViewModel addressViewModel);
        void onSearchButtonClick(String locationNam);
    }

    public SearchFragView() {
        super(BuildConfig.APPLICATION_ID);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchFragView.
     */
    public static SearchFragView newInstance(@NonNull ActionListener actionListener) {
        SearchFragView fragment = new SearchFragView();
        fragment.actionListener = actionListener;
        return fragment;
    }

    @LayoutRes
    protected int getLayoutId() {
        return R.layout.search_frag_view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (edtLocation != null) {
            outState.putString(BuildConfig.APPLICATION_ID + LOCATION_SEARCH_EXTRA, edtLocation.getText().toString());
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (edtLocation != null && savedInstanceState != null) {
            String locationToSeach = savedInstanceState.getString(BuildConfig.APPLICATION_ID + LOCATION_SEARCH_EXTRA, "");
            edtLocation.setText(locationToSeach);
            loadFromModel();
        }
    }

    /**
     * Setup the handlers for the widgets.
     * @param view
     */
    @Override
    protected void onFinishCreateView(View view) {
        edtLocation = view.findViewById(R.id.edt_location);
        btnSearch = view.findViewById(R.id.btn_search);
        mRcvList = view.findViewById(R.id.rcv_search_result);
        txtResult = view.findViewById(R.id.txt_search_result);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchClick();
            }
        });
        txtResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            onAddressViewModelSelected(null);
            }
        });

        mRcvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRcvList.setVisibility(View.GONE);
        txtResult.setVisibility(View.GONE);
    }

    /**
     * Loads the results from Geocoding into a recycle view.
     * If no results is returned, then "No Results" will be shown to the user.
     * If more than one result are returned, then "Display all on Map" will be shown above the results.
     */
    public void loadFromModel() {
        if (getModel().size() > 0) {
            mRcvList.setVisibility(View.VISIBLE);

            if (getModel().size() > 1) {
                txtResult.setText(R.string.display_all_on_map);
                txtResult.setVisibility(View.VISIBLE);
            } else {
                txtResult.setVisibility(View.GONE);
            }

            SearchResultItemAdapter searchResultItemAdapter = new SearchResultItemAdapter(getActivity(), getModel(), new SearchResultItemAdapter.OnActionListener() {
                @Override
                public void onSelectedItem(AddressViewModel item) {
                    onAddressViewModelSelected(item);
                }

                @Override
                public void onViewMapClicked(AddressViewModel item) {
                    onAddressViewModelSelected(item);
                }
            });

            mRcvList.setAdapter(searchResultItemAdapter);
        } else {
            txtResult.setVisibility(View.VISIBLE);
            txtResult.setText(R.string.no_results);
            mRcvList.setVisibility(View.GONE);
        }
    }

    /**
     * Notifies the activity a Address was selected or if the user has clicked on
     * Display all on Map button.
     * @param addressViewModel
     */
    private void onAddressViewModelSelected(AddressViewModel addressViewModel) {
        try {
            actionListener.onAddressViewModelSelected(addressViewModel);
        } catch (Exception e) {
            showException(R.string.show_error_unexpected_error, e);
        }

    }

    /**
     * Notifies the activity to start the onSearchClick for the location.
     */
    private void onSearchClick() {
        String locationName = edtLocation.getText().toString();
        actionListener.onSearchButtonClick(locationName);
    }
}
