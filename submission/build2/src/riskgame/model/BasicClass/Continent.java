package riskgame.model.BasicClass;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class includes attributes a continent need and required methods. Countries inside a continent object is stored in graph with
 * LinkedHashMap.
 * @author WW
 * @since build1
 **/
public class Continent {
    private final String continentName;
    private int continentOwnerIndex;
    private final int continentBonusValue;
    private LinkedHashMap<String, Country> continentCountryGraph;

    /**
     * class constructor
     *
     * @param continentName       continent name
     * @param continentBonusValue bonus value for the continent
     */
    public Continent(String continentName, int continentBonusValue) {
        this.continentName = continentName;
        this.continentOwnerIndex = -1;
        this.continentBonusValue = continentBonusValue;
        this.continentCountryGraph = new LinkedHashMap<>();
    }

    /**
     * getter
     *
     * @return continent name
     */
    public String getContinentName() {
        return continentName;
    }

    /**
     * getter
     *
     * @return player index of the continent. -1 means no owner
     */
    public int getContinentOwnerIndex() {
        return continentOwnerIndex;
    }

    /**
     * setter
     *
     * @param continentOwnerIndex player index who conquers the continent
     */
    public void setContinentOwnerIndex(int continentOwnerIndex) {
        this.continentOwnerIndex = continentOwnerIndex;
    }

    /**
     * getter
     *
     * @return bonus value of the continent
     */
    public int getContinentBonusValue() {
        return continentBonusValue;
    }

    /**
     * getter
     *
     * @return LinkedHashMap of stroing country objects the continent owns
     */
    public LinkedHashMap<String, Country> getContinentCountryGraph() {
        return continentCountryGraph;
    }

    /**
     * getter
     *
     * @return arraylist of string type country name
     */
    public ArrayList<String> getContinentCountryNameList() {
        ArrayList<String> result = new ArrayList<>();
        for (Map.Entry<String, Country> entry : continentCountryGraph.entrySet()) {
            result.add(entry.getKey());
        }
        return result;
    }

    /**
     * override toString method for printing continent information
     *
     * @return string of formatted continent and coutries information
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("Continent name: ").append(continentName).append(", bonus: ").append(continentBonusValue).append("\n");
        for (Map.Entry<String, Country> entry : continentCountryGraph.entrySet()) {
            result.append(entry.getKey()).append("\n");
        }
        return result.toString();
    }


}
