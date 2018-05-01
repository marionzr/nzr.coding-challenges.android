package com.ssannttoss.android_challenge.mvc.controller;

import com.ssannttoss.android_challenge.mvc.model.entity.MyAddress;
import com.ssannttoss.android_challenge.mvc.model.logic.DeleteMyAddressResult;
import com.ssannttoss.android_challenge.mvc.model.logic.MyAddressLogic;
import com.ssannttoss.android_challenge.mvc.model.logic.SaveMyAddressResult;
import com.ssannttoss.android_challenge.mvc.view.search.AddressViewModel;
import com.ssannttoss.framework.mvc.controller.AbstractControllerExt;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the user interactions that needs to be validate or modify the model.
 * Created by ssannttoss on 1/22/2018.
 */

public class MapController extends AbstractControllerExt<List<AddressViewModel>> {

    @Override
    public void initialize() {

    }

    public void loadModel(List<AddressViewModel> addressViewModelList) {
        setModel(new ArrayList<>(addressViewModelList));
        retrivedSavedStatus(getModel());
    }

    public DeleteMyAddressResult delete(AddressViewModel addressViewModel) {
        final MyAddressLogic myAddressLogic = new MyAddressLogic();
        final MyAddress myAddress = new MyAddress(addressViewModel.getId(), addressViewModel.getLatLng().latitude,
                addressViewModel.getLatLng().longitude, addressViewModel.getFeatureName());
        DeleteMyAddressResult result = myAddressLogic.delete(myAddress);
        addressViewModel.setId(result == DeleteMyAddressResult.SUCCESS ? 0 : addressViewModel.getId());
        return result;
    }

    public SaveMyAddressResult save(AddressViewModel addressViewModel) {
        final MyAddressLogic myAddressLogic = new MyAddressLogic();
        final MyAddress myAddress = new MyAddress(addressViewModel.getId(), addressViewModel.getLatLng().latitude,
                addressViewModel.getLatLng().longitude, addressViewModel.getFeatureName());

        SaveMyAddressResult result = myAddressLogic.save(myAddress);
        addressViewModel.setId(myAddress.getId());
        return result;
    }

    public void retrivedSavedStatus(List<AddressViewModel> addressViewModelList) {
        final MyAddressLogic myAddressLogic = new MyAddressLogic();
        List<MyAddress> myAddressList = myAddressLogic.getAllSaved();

        // TODO: Improve the code bellow. Due API 14 restriction, we could not use java.util.stream.
        for(MyAddress myAddress : myAddressList) {
            for(AddressViewModel addressViewModel : addressViewModelList) {
                if (addressViewModel.getId() != 0) {
                    continue;
                } else {
                    if (addressViewModel.getLatLng().latitude == myAddress.getLatitude()
                            && addressViewModel.getLatLng().longitude == myAddress.getLongitude() &&
                            myAddress.getFeatureName().equals(addressViewModel.getFeatureName())) {
                        addressViewModel.setId(myAddress.getId());
                    }
                }
            }
        }
    }

}
