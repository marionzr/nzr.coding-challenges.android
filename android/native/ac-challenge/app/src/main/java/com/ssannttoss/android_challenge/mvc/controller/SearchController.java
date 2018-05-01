package com.ssannttoss.android_challenge.mvc.controller;

import com.ssannttoss.android_challenge.mvc.view.search.AddressViewModel;
import com.ssannttoss.framework.mvc.controller.AbstractControllerExt;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the user interactions that needs to be validate or modify the model.
 * Created by ssannttoss on 1/22/2018.
 */

public class SearchController extends AbstractControllerExt<List<AddressViewModel>> {

    @Override
    public void initialize() {
        setModel(new ArrayList<AddressViewModel>());
    }
}
