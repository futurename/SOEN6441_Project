package riskgame.model.Utils;

import riskgame.Main;
import riskgame.model.BasicClass.Continent;
import riskgame.model.BasicClass.Country;

import java.util.LinkedHashMap;
import java.util.Map;

public class AttackResultProcess {

    public static boolean isCountryConquered(Country country){
        boolean result  = false;
        int remainingArmyNbr = country.getCountryArmyNumber();
        if(remainingArmyNbr == 0){
            result = true;
        }

        System.out.println("country conquered: " + result);

        return result;
    }

    public static boolean isContinentConquered(int playerIndex, String continentName){
        boolean result = true;

        Continent curContinent = Main.worldContinentMap.get(continentName);
        LinkedHashMap<String, Country> continentCountryGraph = curContinent.getContinentCountryGraph();

        for(Map.Entry<String, Country> entry : continentCountryGraph.entrySet()){
            int curOwnerIndex = entry.getValue().getCountryOwnerIndex();
            if(curOwnerIndex != playerIndex){
                result = false;
                break;
            }
        }

        System.out.println("continent conquered: " + result);

        return result;
    }

    public static void updateContinentOwner(int attackerIndex,String continentName) {
        boolean result=true;
        Continent curContinent = Main.worldContinentMap.get(continentName);
        LinkedHashMap<String, Country> continentCountryGraph = curContinent.getContinentCountryGraph();

        for(Map.Entry<String,Country> entry : continentCountryGraph.entrySet()) {
            int curOwnerIndex = entry.getValue().getCountryOwnerIndex();
            if (curOwnerIndex != attackerIndex) {
                result = false;
                break;
            }
        }
        if(result==true){
            curContinent.setContinentOwnerIndex(attackerIndex);
        }

    }

    public static void updateWorldOwner(int attackerIndex) {
        boolean result=true;

        for(Map.Entry<String,Continent> entry : Main.worldContinentMap.entrySet()) {
            int curOwnerIndex = entry.getValue().getContinentOwnerIndex();
            if (curOwnerIndex != attackerIndex) {
                result = false;
                break;
            }
        }
        if(result==true){
            System.out.println(attackerIndex + "wins");
        }

    }
}
