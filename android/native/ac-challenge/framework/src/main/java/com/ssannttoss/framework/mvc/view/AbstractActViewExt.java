// Copyright © 2018 by Mário Heleno Nazareth Santos - ssannttoss@gmail.com
// All rights reserved. No part of this publication may be reproduced, distributed, or transmitted
// in any form or by any means, including photocopying, recording, or other electronic or mechanical
// methods, without the prior written permission of the publisher
// For permission requests, send an e-mail write to the publisher address ssannttoss@gmail.com

package com.ssannttoss.framework.mvc.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.ssannttoss.framework.R;
import com.ssannttoss.framework.logging.LogManager;
import com.ssannttoss.framework.mvc.controller.ControllerExt;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link AppCompatActivity} base class for activities that use the action bar features and
 * also gives extends features.
 * <p>
 * Created by ssannttoss on 1/21/2018.
 */

public abstract class AbstractActViewExt<T> extends AppCompatActivity implements ViewExt<T> {
    protected final Bundle bundle;
    protected boolean displayBackButton;
    private ControllerExt<T> controller;
    public static final int REQUEST_PERMISSION = 795;

    public AbstractActViewExt() {
        bundle = new Bundle();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutContentId());

        Toolbar toolbar = findViewById(R.id.toolbar);
        final AbstractActViewExt<T> _this = this;

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return _this.onMenuItemClick(item);
                }
            });
        }

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(displayBackButton);
            actionBar.setDisplayShowHomeEnabled(displayBackButton);
        }

        onAddFragments();
    }

    @IdRes
    public abstract int getCustomContainerId();

    @LayoutRes
    public abstract int getLayoutContentId();

    /**
     * Template method. Called just after the onCreate.
     * Use it to pass a instance of one fragment to the activity
     */
    protected abstract void onAddFragments();

    protected void addFragment(AbstractFragmentViewExt fragmentViewExt) {
        @IdRes int containerId = getCustomContainerId();
        getSupportFragmentManager().beginTransaction().replace(containerId, fragmentViewExt).commit();
    }

    protected void setActionBarTitle(@NonNull final int titleId, final int subTitleId) {
        setActionBarTitle(getString(titleId), subTitleId != 0 ? getString(subTitleId) : "");
    }

    protected void setActionBarTitle(@NonNull final String title, final String subTitle) {
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setSubtitle(subTitle);
        }
    }

    /**
     * Template method to handle menu item click
     *
     * @param item
     * @return
     */
    protected boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    protected ControllerExt<T> getController() {
        return controller;
    }

    protected void setController(ControllerExt controller) {
        this.controller = controller;
    }

    @Override
    public T getModel() {
        return controller.getModel();
    }

    /**
     * Shows an alert dialog with the error message and also adds a error log entry.
     *
     * @param id
     * @param e
     */
    protected void showException(@StringRes int id, Exception e) {
        String message = getString(id);
        showException(message, e);
    }

    /**
     * Shows an alert dialog with the error message and also adds a error log entry.
     *
     * @param message
     * @param e
     */
    protected void showException(String message, Exception e) {
        LogManager.e(this, message, e);
        new AlertDialog.Builder(this)
                .setTitle(R.string.show_error_title)
                .setMessage(message)
                .setPositiveButton(R.string.show_message_button_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                break;
                        }
                    }
                })
                .show();

    }

    protected void hideKeyboard() {
        // Check if no view has focus:
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }

        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    protected boolean checkNetwork(View view, View.OnClickListener retry) {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        } else {
            if (request(this, REQUEST_PERMISSION,
                    Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET)) {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }
        }
        if (networkInfo == null || !networkInfo.isConnected()) {
            Snackbar
                    .make(view, R.string.no_network_connection, Snackbar.LENGTH_SHORT)
                    .setAction( R.string.no_network_connection_retry, retry)
                    .show();
            return false;
        }

        return true;
    }

    public static boolean request(Activity activity, int requestCode, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final List<String> perms = new ArrayList<>();
            for (String permission : permissions) {
                int permissionCheck = ContextCompat.checkSelfPermission(activity, permission);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    perms.add(permission);
                }
            }
            if (perms != null && perms.size() > 0) {
                String[] permissionsNotGranted = new String[perms.size()];
                perms.toArray(permissionsNotGranted);
                activity.requestPermissions(permissionsNotGranted, requestCode);
                return false;
            }
        }
        return true;
    }
}
