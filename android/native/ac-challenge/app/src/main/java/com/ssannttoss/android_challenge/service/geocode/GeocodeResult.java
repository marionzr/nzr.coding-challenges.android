
package com.ssannttoss.android_challenge.service.geocode;

import java.util.List;

public class GeocodeResult {

    private List<GeocodeAddress> results = null;
    private String status;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GeocodeResult() {
    }

    /**
     * 
     * @param results
     * @param status
     */
    public GeocodeResult(List<GeocodeAddress> results, String status) {
        super();
        this.results = results;
        this.status = status;
    }

    public List<GeocodeAddress> getResults() {
        return results;
    }

    public void setResults(List<GeocodeAddress> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
