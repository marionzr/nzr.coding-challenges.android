package com.ssannttoss.android_challenge.mvc.view.map;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.ssannttoss.android_challenge.BuildConfig;
import com.ssannttoss.android_challenge.R;
import com.ssannttoss.android_challenge.mvc.controller.MapController;
import com.ssannttoss.android_challenge.mvc.model.logic.DeleteMyAddressResult;
import com.ssannttoss.android_challenge.mvc.model.logic.SaveMyAddressResult;
import com.ssannttoss.android_challenge.mvc.view.search.AddressViewModel;
import com.ssannttoss.framework.mvc.view.AbstractActViewExt;

import java.util.List;

/**
 * Container view for the Map View.
 * Controls the user interactions over the map fragment.
 */
public class MapActView extends AbstractActViewExt<List<AddressViewModel>> {
    public static final String ADDRESS_VIEW_MODEL_EXTRA = ".ADDRESS_VIEW_MODEL_EXTRA";
    public static final String ADDRESS_VIEW_DISPLAY_ALL_MODE = ".ADDRESS_VIEW_DISPLAY_ALL_MODE";
    private AddressViewModel mapSelectedAddressModel;
    private MapController controller;

    /**
     * Construtor
     */
    public MapActView() {
        super();
        super.displayBackButton = true;
    }

    @Override
    @IdRes
    public int getCustomContainerId() {
        return R.id.frm_content;
    }

    @Override
    @LayoutRes
    public int getLayoutContentId() {
        return R.layout.map_act_view;
    }

    /**
     * Template method. Called just after the onCreate
     */
    @Override
    protected void onAddFragments() {
        MapFragView mapFragView = MapFragView.newInstance(new MapFragView.ActionListener() {
            @Override
            public void onAddressViewModelSelected(AddressViewModel addressViewModel) {
                MapActView.this.onAddressViewModelSelected(addressViewModel);
            }
        });

        addFragment(mapFragView);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent = getIntent();
        final boolean displayAllMode = intent.getBooleanExtra(BuildConfig.APPLICATION_ID + ADDRESS_VIEW_DISPLAY_ALL_MODE, false);
        final List<AddressViewModel> addressViewModelList = intent.getParcelableArrayListExtra(BuildConfig.APPLICATION_ID + ADDRESS_VIEW_MODEL_EXTRA);
        setActionBarTitle(R.string.map_view_title, displayAllMode ? R.string.map_view_subtitle_display_all_mode : 0);

        setController(controller = new MapController());
        controller.initialize();
        controller.loadModel(addressViewModelList);
    }

    /**
     * Configures the options menu based on the selected AddressviewModel object
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_menu, menu);
        MenuItem mniSave = menu.getItem(0);

        if (mapSelectedAddressModel != null) {
            mniSave.setVisible(true);
            mniSave.setIcon(mapSelectedAddressModel.getId() > 0 ?
                    R.drawable.ic_delete_grey_500_24dp :
                    android.R.drawable.ic_menu_save
            );
        } else {
            mniSave.setVisible(false);
        }

        return true;
    }

    protected boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mni_save:
                try {
                    saveOrDeleteAddress();
                } catch (Exception e) {
                    showException(R.string.show_error_unexpected_error, e);
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void saveOrDeleteAddress() {
        if (mapSelectedAddressModel != null) {
            if (mapSelectedAddressModel.getId() > 0) {

                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_delete_grey_500_24dp)
                        .setTitle(R.string.delete_confirmation_title)
                        .setMessage(R.string.delete_confirmation)
                        .setPositiveButton(R.string.delete_confirmation_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        deleteMyAddress();
                                        break;
                                }
                            }
                        })
                        .setNegativeButton(R.string.delete_confirmation_cancel, null)
                        .show();

            } else {
                saveMyAddress();
            }
        }
    }

    private void deleteMyAddress() {
        try {
            DeleteMyAddressResult result = controller.delete(mapSelectedAddressModel);

            switch (result) {
                case SUCCESS:
                    Toast.makeText(this, R.string.delete_success, Toast.LENGTH_SHORT).show();
                    invalidateOptionsMenu();
                    break;
                case INVALID_ID:
                    Toast.makeText(this, R.string.delete_failed_invalid_id, Toast.LENGTH_SHORT).show();;
                    break;
                case UNKNOWN_ERROR:
                    Toast.makeText(this, R.string.delete_failed_unexpected, Toast.LENGTH_SHORT).show();;
                    break;
            }
        } catch (Exception e) {
            showException(R.string.show_error_unexpected_error, e);
        }
    }

    private void saveMyAddress() {
        try {
            SaveMyAddressResult result = controller.save(mapSelectedAddressModel);

            switch (result) {
                case SUCCESS:
                    Toast.makeText(this, R.string.save_success, Toast.LENGTH_SHORT).show();
                    invalidateOptionsMenu();
                    break;
                case INVALID_FEATURE_NAME:
                    Toast.makeText(this, R.string.save_failed_invalid_feature_name, Toast.LENGTH_SHORT).show();;
                    break;
                case INVALID_LOCATION:
                    Toast.makeText(this, R.string.save_failed_invalid_location, Toast.LENGTH_SHORT).show();;
                    break;
                case FAIL:
                    Toast.makeText(this, R.string.save_failed_unexpected, Toast.LENGTH_SHORT).show();;
                    break;
            }
        } catch (Exception e) {
            showException(R.string.show_error_unexpected_error, e);
        }
    }


    private void onAddressViewModelSelected(AddressViewModel addressViewModel) {
        try {
            this.mapSelectedAddressModel = addressViewModel;
            invalidateOptionsMenu();
        } catch (Exception e) {
            showException(R.string.show_error_unexpected_error, e);
        }
    }
}
