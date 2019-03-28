package mapeditor.model;

import mapeditor.MEMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class MECheckMapCorrectness {

    private boolean checkFlagCG = true;
    private boolean checkFlagCC = true;
    private boolean checkFlagCB = true;

    /**
     * isCorrect method compare all the three test result and ruturn to the UI.
     *
     * @param countryArr    country list
     * @param continentsArr continent list
     * @return error information error string
     */
    public String isCorrect(ArrayList<MECountry> countryArr, ArrayList<MEContinent> continentsArr) {
        boolean checkFlagCGResult = correctCheckConnectGraph(countryArr);
        boolean checkFlagCCResult = correctCheckContinentCountry(continentsArr, countryArr);
        boolean checkFlagCBResult = correctCheckCountryBelonging(continentsArr, countryArr);
        if (checkFlagCGResult == false) {
            return "Unconnected Graph";
        }
        if (checkFlagCCResult == false) {
            return "Unconnected Country in Continent";
        }
        if (checkFlagCBResult == false) {
            return "Country belongs to mutiple continent";
        }
        return "True";
    }

    /**
     * first correct checkCC
     * check whether it is a connect graph or not
     *
     * @param countryArr country list
     * @return true for correct, false for error
     */
    public boolean correctCheckConnectGraph(ArrayList<MECountry> countryArr) {
        Queue<String> queue = new LinkedList<String>();

        //If it is a empty map.
        if (countryArr.isEmpty()) {
            return true;
        }

        HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
        for (int i = 0; i < countryArr.size(); i++) {
            String countryTemp = countryArr.get(i).getCountryName();
            visited.put(countryTemp, false);
        }
        String firstCountry = countryArr.get(0).getCountryName();
        queue.offer(firstCountry);
        //bfs
        while (!queue.isEmpty()) {
            String queueHead = queue.poll();
            visited.put(queueHead, Boolean.TRUE);
            for (int j = 0; j < countryArr.size(); j++) {
                if (countryArr.get(j).getCountryName().equals(queueHead)) {
                    String countryNeighbor = countryArr.get(j).getNeighbor();
                    countryNeighbor = countryNeighbor.replaceAll("\\[", "");
                    countryNeighbor = countryNeighbor.replaceAll("\\]", "");
                    countryNeighbor = countryNeighbor.replaceAll(", ", ",");
                    String[] countryNeighbors = countryNeighbor.split(",");
                    for (int k = 1; k < countryNeighbors.length; k++) {
                        String readyToAddInQueue = countryNeighbors[k];
                        if (visited.get(readyToAddInQueue) == false) {
                            queue.offer(readyToAddInQueue);
                        }
                    }
                }
            }
        }
        for (String key : visited.keySet()) {
            if (visited.get(key) == false) {
                checkFlagCG = false;
                break;
            }
        }
        return checkFlagCG;
    }

    /**
     * second correct check
     * check whether a country is separate from other country in its continent
     *
     * @param continents continent list
     * @param country    country list
     * @return true for correct, false for error
     */
    public boolean correctCheckContinentCountry(ArrayList<MEContinent> continents, ArrayList<MECountry> country) {
        //If it is a empty map.
        if (continents.isEmpty() && country.isEmpty()) {
            return true;
        }

        for (int i = 0; i < continents.size(); i++) {
            if (continents.get(i).getCountryNumber() >= 1) {
                //
                for (int j = 0; j < country.size(); j++) {
                    MECountry checkCountry = country.get(j);
                    if (MEMain.arrMEContinent.get(i).getCountryNumber() > 1) {
                        if (checkCountry.getNeighbor().isEmpty()) {
                            return checkFlagCC = false;
                        }
                    }
                }
            } else {
                continue;
            }
        }
        return checkFlagCC;
    }

    /**
     * third correct check
     * every country belongs to one and only one continent
     *
     * @param continentsArr continent list
     * @param countryArr    country list
     * @return ture for correct, false for error
     */
    public boolean correctCheckCountryBelonging(ArrayList<MEContinent> continentsArr, ArrayList<MECountry> countryArr) {
        int countryAddByContinent = 0;

        //If the map is empty.
        if (continentsArr.isEmpty() && countryArr.isEmpty()) {
            return true;
        }

        for (int i = 0; i < continentsArr.size(); i++) {

            countryAddByContinent = continentsArr.get(i).getCountryNumber() + countryAddByContinent;
        }
        if (countryAddByContinent != countryArr.size()) {
            System.out.println(countryAddByContinent);
            System.out.println(countryArr.size());
            checkFlagCB = false;
        }
        return checkFlagCB;
    }
}