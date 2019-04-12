package UIVersion;

import java.util.ArrayList;

/**
 * @author Wei Wang
 * @version 1.0
 * @since 2019/04/12
 **/

public class Country {
    private String countryName;
    private double coordinateX;
    private double coordinateY;
    private ArrayList<String> neighborCountryList;

    public Country(String countryName, double coordinateX, double coordinateY, ArrayList<String> neighborCountryList) {
        this.countryName = countryName;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.neighborCountryList = neighborCountryList;
    }

    public String getCountryName() {
        return countryName;
    }

    public double getCoordinateX() {
        return coordinateX;
    }

    public double getCoordinateY() {
        return coordinateY;
    }

    public ArrayList<String> getNeighborCountryList() {
        return neighborCountryList;
    }

    @Override
    public String toString() {
        return "Country{" +
                "countryName='" + countryName + '\'' +
                ", coordinateX=" + coordinateX +
                ", coordinateY=" + coordinateY +
                ", neighborCountryList=" + neighborCountryList +
                '}';
    }
}
