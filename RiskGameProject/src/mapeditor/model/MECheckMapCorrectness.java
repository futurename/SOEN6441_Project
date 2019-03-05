package mapeditor.model;

import java.util.*;

public class MECheckMapCorrectness{

    private static boolean checkFlagCG = true;
    private static boolean checkFlagCC = true;
    private static boolean checkFlagCB = true;

    /**
     * isCorrect method compare all the three test result and ruturn to the UI.
     * @param countryArr
     * @param continentsArr
     * @return
     */
    public static String isCorrect(ArrayList< MECountry> countryArr, ArrayList<MEContinent> continentsArr){
        boolean checkFlagCGResult = correctCheckConnectGraph(countryArr);
        boolean checkFlagCCResult = correctCheckContinentCountry(continentsArr,countryArr);
        boolean checkFlagCBResult = correctCheckCountryBelonging(continentsArr,countryArr);
        if(checkFlagCGResult == false){
           return "Unconnected Graph";
        }
        if(checkFlagCCResult == false){
            return "Unconnected Country in Continent";
        }
        if(checkFlagCBResult == false){
            return "Country belongs to mutiple continent";
        }
        return "True";
    }

    /**
     * check map correctness interface
     * @param countryArr
     * @param continentsArr
     * @return
     */
    public static boolean checkCorrectness(ArrayList< MECountry> countryArr, ArrayList<MEContinent> continentsArr){
        if(correctCheckConnectGraph(countryArr) == false){
            return false;
        }
        if(correctCheckContinentCountry(continentsArr,countryArr) == false){
            return false;
        }
        if(correctCheckCountryBelonging(continentsArr,countryArr) == false) {
            return false;
        }
        return true;
    }

    /**
     * first correct checkCC
     * check whether it is a connect graph or not
     */
    public static boolean correctCheckConnectGraph(ArrayList<MECountry> countryArr){
        Queue<String> queue = new LinkedList<String>();

        //If it is a empty map.
        if(countryArr.isEmpty()){
            return true;
        }

        HashMap<String,Boolean> visited = new HashMap<String,Boolean>();
        for(int i=0 ; i<countryArr.size(); i++ ){
            String countryTemp = countryArr.get(i).getCountryName();
            visited.put(countryTemp,false);
        }
        String firstCountry = countryArr.get(0).getCountryName();
        queue.offer(firstCountry);
        //bfs
        while(!queue.isEmpty()){
            String queueHead = queue.poll();
            visited.put(queueHead, Boolean.TRUE);
            for(int j=0 ;j<countryArr.size();j++){
                if(countryArr.get(j).getCountryName().equals(queueHead)){
                    String countryNeighbor = countryArr.get(j).getNeighbor();
                    countryNeighbor = countryNeighbor.replaceAll("\\[","");
                    countryNeighbor = countryNeighbor.replaceAll("\\]","");
                    countryNeighbor = countryNeighbor.replaceAll(", ",",");
                    String[] countryNeighbors = countryNeighbor.split(",");
                    for(int k = 1;k<countryNeighbors.length;k++){
                        String readyToAddInQueue = countryNeighbors[k];
                        if(visited.get(readyToAddInQueue)==false){
                            queue.offer(readyToAddInQueue);
                        }
                    }
                }
            }
        }
        for (String key: visited.keySet()) {
            if(visited.get(key)==false){
                checkFlagCG = false;
                break;
            }
        }
        return checkFlagCG;
    }

    /**
     * second correct check
     * check whether all countries in one continent are placed together
     */
    public static boolean correctCheckContinentCountry(ArrayList<MEContinent> continents, ArrayList<MECountry> country){
        //If it is a empty map.
        if(continents.isEmpty() && country.isEmpty()) {
            return true;
        }

        for(int i=0 ;i<continents.size();i++){
            if(continents.get(i).getCountryNumber() >= 1){
                //
                for(int j=0;j<country.size();j++){
                    MECountry checkCountry = country.get(j);
                    if(checkCountry.getNeighbor().isEmpty()){
                        return checkFlagCC = false;
                    }
                }
            }else {
                continue;
            }
        }
        return checkFlagCC;
    }

    /**
     * third correct check
     *  every country belongs to one and only one continent
     */
    public static boolean correctCheckCountryBelonging(ArrayList<MEContinent> continentsArr, ArrayList<MECountry> countryArr){
        int countryAddByContinent = 0;

        //If the map is empty.
        if(continentsArr.isEmpty() && countryArr.isEmpty()){
            return true;
        }

        for(int i = 0; i<continentsArr.size();i++){

            countryAddByContinent = continentsArr.get(i).getCountryNumber()+countryAddByContinent;
        }
        if(countryAddByContinent != countryArr.size()){
            checkFlagCB = false;
        }
        return checkFlagCB;
    }
}