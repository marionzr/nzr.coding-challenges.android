package com.ssannttoss.android_challenge.mvc.model.logic;


import com.ssannttoss.android_challenge.mvc.model.entity.MyAddress;
import com.ssannttoss.android_challenge.mvc.model.repository.MyAddressDao;
import com.ssannttoss.framework.ioc.DependencyManager;
import com.ssannttoss.framework.logging.LogManager;

import java.util.List;

/**
 * Handles the logic for manipulating the saved {@link MyAddress address} .
 * <p>
 * Created by ssannttoss on 1/21/2018.
 */

public class MyAddressLogic {

    /**
     * Saves {@link MyAddress address} to the database.
     * @param myAddress
     * @return
     */
    public SaveMyAddressResult save(MyAddress myAddress) {
        // Validates the address feature name and location.
        if (myAddress.getFeatureName() == null || myAddress.getFeatureName().length() == 0) {
            LogManager.e(this, SaveMyAddressResult.INVALID_FEATURE_NAME.toString());
            return SaveMyAddressResult.INVALID_FEATURE_NAME;
        } else if (myAddress.getLatitude() < -90 || myAddress.getLatitude() > 90 ||
                myAddress.getLongitude() < -180 || myAddress.getLongitude() > 180) {
            LogManager.e(this, String.format("%s: %s", SaveMyAddressResult.INVALID_LOCATION,
                    myAddress.toString()));
            return SaveMyAddressResult.INVALID_LOCATION;
        }

        // Gets the DAO implementation instance from dependency manager.
        MyAddressDao dao = DependencyManager.getInstance().get(MyAddressDao.class);
        long result = dao.insert(myAddress);

        // Checks if the return of DAO insert is the save id of the saved address.
        if (result == myAddress.getId()) {
            LogManager.i(this, String.format("Saved: %s", myAddress.toString()));
            return SaveMyAddressResult.SUCCESS;
        } else {
            LogManager.i(this, String.format("%s: %s", SaveMyAddressResult.FAIL, myAddress.toString()));
            return SaveMyAddressResult.FAIL;
        }
    }

    /**
     * Deletes {@link MyAddress address} from the database.
     * @param template Object used as template for querying before delete.
     * @return
     */
    public DeleteMyAddressResult delete(MyAddress template) {
        // Checks if the template id is valid.
        if (template.getId() == 0) {
            LogManager.e(this, String.format("%s: %s", DeleteMyAddressResult.INVALID_ID, template.toString()));
            return DeleteMyAddressResult.INVALID_ID;
        }

        // Gets the DAO implementation instance from dependency manager.
        MyAddressDao dao = DependencyManager.getInstance().get(MyAddressDao.class);
        List<MyAddress> myAddressList = dao.select(template);

        // Checks if a saved address was found or if 2 or more instances was found.
        if (myAddressList.size() == 0) {
            LogManager.e(this, String.format("%s: %s", DeleteMyAddressResult.NOT_FOUND, template.toString()));
            return DeleteMyAddressResult.NOT_FOUND;
        } else if (myAddressList.size() > 1) {
            LogManager.e(this, String.format("%s: %s - %d", DeleteMyAddressResult.MULTIPLE_ADDRESS_FOUND,
                    template.toString(), myAddressList.size()));
            return DeleteMyAddressResult.MULTIPLE_ADDRESS_FOUND;
        }

        // Gets the saved address and delete it.
        MyAddress myAddress = myAddressList.get(0);
        int result = dao.delete(myAddress);

        if (result == 1) {
            LogManager.i(this, String.format("Deleted:  %s", myAddress.toString()));
            return DeleteMyAddressResult.SUCCESS;
        } else {
            LogManager.e(this, String.format("%: %s", DeleteMyAddressResult.UNKNOWN_ERROR, myAddress.toString()));
            return DeleteMyAddressResult.UNKNOWN_ERROR;
        }
    }

    // Gets all saved address to update the status on view map.
    public List<MyAddress> getAllSaved() {
        MyAddressDao dao = DependencyManager.getInstance().get(MyAddressDao.class);
        List<MyAddress> myAddressList = dao.selectAll(); // Retrieve all at once to avoid IO. Cache will take care of next requests
        return myAddressList;
    }
}
