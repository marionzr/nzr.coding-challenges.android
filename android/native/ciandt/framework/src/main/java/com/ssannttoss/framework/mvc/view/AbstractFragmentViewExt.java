// Copyright © 2018 by Mário Heleno Nazareth Santos - ssannttoss@gmail.com
// All rights reserved. No part of this publication may be reproduced, distributed, or transmitted
// in any form or by any means, including photocopying, recording, or other electronic or mechanical
// methods, without the prior written permission of the publisher
// For permission requests, send an e-mail write to the publisher address ssannttoss@gmail.com

package com.ssannttoss.framework.mvc.view;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssannttoss.framework.mvc.controller.ControllerExt;

import java.util.HashMap;
import java.util.Map;

/**
 * A {@link Fragment} base class for activities that use the action bar features and
 * also gives extends features.
 * <p>
 * Created by ssannttoss on Nov/03/2018.
 */
public abstract class AbstractFragmentViewExt<T> extends Fragment implements ViewExt<T> {
    protected final Bundle bundle;
    protected final String packageName;
    protected final Map<String, Object> extras = new HashMap<>();

    public AbstractFragmentViewExt(String packageName) {
        this.bundle = new Bundle();
        this.packageName = packageName;
        this.setArguments(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        View view = inflater.inflate(getLayoutId(), container, false);
        onFinishCreateView(view);
        return view;
    }

    /**
     * Called after onCreateView. Use it to get widget instances and do view initialization.
     *
     * @param view
     */
    protected void onFinishCreateView(View view) {
    }

    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * Shows an alert dialog with the error message and also adds a error log entry.
     *
     * @param id
     * @param e
     */
    protected void showException(@StringRes int id, Exception e) {
        ((AbstractActViewExt) getActivity()).showException(id, e);
    }

    /**
     * Shows an alert dialog with the error message and also adds a error log entry.
     *
     * @param message
     * @param e
     */
    public void showException(String message, Exception e) {
        ((AbstractActViewExt) getActivity()).showException(message, e);
    }

    protected void onViewStateChanged(String action, T model, Map<String, Object> extras, ControllerExt.ResponseListener<T> responseListener) {
        ((AbstractActViewExt) getActivity()).onViewStateChanged(action, model, extras, responseListener);
    }

    public final void updateView(String action, T model, Map<String, Object> extras) {
        onUpdateView(action, model, extras);
    }

    protected void onUpdateView(String action, T model, Map<String, Object> extras) {

    }

    public void navigate(Class<? extends ControllerExt> controllerClass, Map<String, Object> extras) {
        ((AbstractActViewExt) getActivity()).navigate(controllerClass, extras);
    }

    protected void hideKeyboard() {
        ((AbstractActViewExt) getActivity()).hideKeyboard();
    }

    protected boolean checkNetwork(View view, View.OnClickListener retry) {
        return ((AbstractActViewExt) getActivity()).checkNetwork(view, retry);
    }
}
