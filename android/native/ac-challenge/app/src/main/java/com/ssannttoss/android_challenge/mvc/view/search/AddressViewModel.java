package com.ssannttoss.android_challenge.mvc.view.search;

import android.location.Address;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.ssannttoss.framework.mvc.model.ViewEntity;

/**
 * A view entity to helper display address data.
 * Created by ssannttoss on 1/21/2018.
 */

public class AddressViewModel extends ViewEntity<AddressViewModel> implements Parcelable {
    public static final Parcelable.Creator<AddressViewModel> CREATOR = new Parcelable.Creator<AddressViewModel>() {
        @Override
        public AddressViewModel createFromParcel(Parcel source) {
            return new AddressViewModel(source);
        }

        @Override
        public AddressViewModel[] newArray(int size) {
            return new AddressViewModel[size];
        }
    };

    private Address address;
    private boolean selected;

    public AddressViewModel(Address address) {
        this.address = address;
    }

    public AddressViewModel(Parcel in) {
        this((Address)null);
        readFromParcel(in);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel in) {
        super.readFromParcel(in);
        address = in.readParcelable(Address.class.getClassLoader());
        selected = in.readInt() == 1;
    }

    @Override
    public AddressViewModel clone() {
        AddressViewModel clone = new AddressViewModel(address);
        clone.setId(getId());
        return clone;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(address, flags);
        dest.writeInt(selected ? 1 : 0);
    }

    public String getFeatureName() {
        if (address.getMaxAddressLineIndex() < 0) {
            return address.getFeatureName();
        } else {
            return address.getAddressLine(0);
        }
    }

    public LatLng getLatLng() {
        return new LatLng(address.getLatitude(), address.getLongitude());
    }

    /**
     * Holds information about use selection from SearchView to be used in the MapView
     * @return
     */
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
