package mapeditor.model;


import java.util.LinkedList;

/**
 * country class
 */
public class MECountry {

    /**
     * Country name attribute
     */
    private String countryName;

    /**
     * Neighbor countries list
     */
    private LinkedList<String> neighbor = new LinkedList<String>();

    /**
     * set country name
     *
     * @param newCountryName country name
     */
    public void setCountryName(String newCountryName) {
        this.countryName = newCountryName;
    }

    /**
     * set neighbor name
     *
     * @param newNeighbor neighbor country name
     */
    public void setNeighbor(String newNeighbor) {
        neighbor.offer(newNeighbor);
    }

    /**
     * delete neighbor
     *
     * @param oldNeighbor old neighbor country name
     */
    public void deleteNeighbor(String oldNeighbor) {
        if (neighbor.indexOf(oldNeighbor) != -1) {
            neighbor.remove(oldNeighbor);
        }
    }

    /**
     * get country name
     *
     * @return country name
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * get all neighbors name for example"[A, B, C]"
     *
     * @return neighbors name
     */
    public String getNeighbor() {
        return neighbor.toString();
    }

}