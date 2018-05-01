// Copyright © 2018 by Mário Heleno Nazareth Santos - ssannttoss@gmail.com
// All rights reserved. No part of this publication may be reproduced, distributed, or transmitted
// in any form or by any means, including photocopying, recording, or other electronic or mechanical
// methods, without the prior written permission of the publisher
// For permission requests, send an e-mail write to the publisher address ssannttoss@gmail.com

package com.ssannttoss.framework.mvc.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A {@link Fragment} base class for activities that use the action bar features and
 * also gives extends features.
 * <p>
 * Created by ssannttoss on 1/21/2018.
 */

public abstract class AbstractFragmentViewExt<T> extends Fragment implements ViewExt<T> {
    protected final Bundle bundle;
    protected final String packageName;

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
    protected abstract void onFinishCreateView(View view);

    @LayoutRes
    protected abstract int getLayoutId();

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onSaveInstanceState(bundle);
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public T getModel() {
        return ((AbstractActViewExt<T>) getActivity()).getModel();
    }

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
    protected void showException(String message, Exception e) {
        ((AbstractActViewExt) getActivity()).showException(message, e);
    }
}
