package riskgame.model.BasicClass;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 */
public class Continent {
    private final String continentName;
    private int continentOwnerIndex;
    private final int continentBonusValue;
    private LinkedHashMap<String, Country> continentCountryGraph;

    public Continent(String continentName, int continentBonusValue) {
        this.continentName = continentName;
        this.continentOwnerIndex = -1;
        this.continentBonusValue = continentBonusValue;
        this.continentCountryGraph = new LinkedHashMap<>();
    }

    public String getContinentName() {
        return continentName;
    }

    public int getContinentOwnerIndex() {
        return continentOwnerIndex;
    }

    public void setContinentOwnerIndex(int continentOwnerIndex) {
        this.continentOwnerIndex = continentOwnerIndex;
    }

    public int getContinentBonusValue() {
        return continentBonusValue;
    }

    public LinkedHashMap<String, Country> getContinentCountryGraph() {
        return continentCountryGraph;
    }

    public ArrayList<String> getContinentCountryNameList(){
        ArrayList<String> result = new ArrayList<>();
        for(Map.Entry<String, Country> entry: continentCountryGraph.entrySet()){
            result.add(entry.getKey());
        }
        return result;
    }

    @Override
    public String toString(){
        String result = "";

        result += "Continent name: " + continentName + ", bonus: " + continentBonusValue + "\n";
        for(Map.Entry<String, Country> entry: continentCountryGraph.entrySet()){
            result += entry.getKey() + "\n";
        }
        return result;
    }


}
