
package com.ssannttoss.android_challenge.service.geocode;

import java.util.List;

public class GeocodeAddress {

    private List<AddressComponent> addressComponents = null;
    private String formatted_address;
    private Geometry geometry;
    private String placeId;
    private List<String> types = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GeocodeAddress() {
    }

    /**
     * 
     * @param placeId
     * @param formattedAddress
     * @param types
     * @param addressComponents
     * @param geometry
     */
    public GeocodeAddress(List<AddressComponent> addressComponents, String formattedAddress, Geometry geometry, String placeId, List<String> types) {
        super();
        this.addressComponents = addressComponents;
        this.formatted_address = formattedAddress;
        this.geometry = geometry;
        this.placeId = placeId;
        this.types = types;
    }

    public List<AddressComponent> getAddressComponents() {
        return addressComponents;
    }

    public void setAddressComponents(List<AddressComponent> addressComponents) {
        this.addressComponents = addressComponents;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

}
