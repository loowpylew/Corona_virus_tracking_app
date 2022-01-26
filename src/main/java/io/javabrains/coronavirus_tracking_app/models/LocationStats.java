package io.javabrains.coronavirus_tracking_app.models;

public class LocationStats {
    private String state;
    private String country;
    private int latestTotalCases;
    private int diffFromPrevDay;

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    public void setDiffFromPrevDay(int diffFromPrevDay) {
        this.diffFromPrevDay = diffFromPrevDay;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public int getLatestTotalCases() {
        return latestTotalCases;
    }

    public int getDiffFromPrevDay() {
        return diffFromPrevDay;
    }

    @Override //Used to override a method with the same datatype..., but the method with the same type is run within a different class found within a separate class
    public String toString() {
        return "LocationStatus{" + "State = " +
                state + '\'' + ", country = "  +
                country + '\'' + "}, {latestTotalCases = " +
                latestTotalCases + '}';


    }
}
