package mapeditor.model;

import mapeditor.MEMain;

import java.util.*;

public class MECheckMapCorrectness{

    private boolean checkFlagCG = true;
    private boolean checkFlagCB = true;
    private boolean checkFlagCCB = true;

    private HashMap<String,Integer> countryCheckFlag = new HashMap<String,Integer>();
    /**
     * isCorrect method compare all the three test result and ruturn to the UI.
     * @param countryArr country list
     * @param continentsArr continent list
     * @return error information error string
     */
    public String isCorrect(ArrayList< MECountry> countryArr, ArrayList<MEContinent> continentsArr){
        boolean checkFlagCGResult = correctCheckConnectGraph(countryArr);
        boolean checkFlagCCBResult = correctCheckContinentCountryBelonging(continentsArr,countryArr);
        boolean checkFlagCBResult = correctCheckCountryBelonging(continentsArr,countryArr);

        if(checkFlagCGResult == false){
            return "Unconnected Graph";
        }
        if (checkFlagCCBResult == false){
            return "has country that doesn't connect with any country of its continent";
        }
        if(checkFlagCBResult == false){
            return "Country belongs to mutiple continent";
        }

        return "True";
    }

    /**
     * first correct checkCG
     * check whether it is a connect graph or not
     * @param countryArr country list
     * @return true for correct, false for error
     */
    public boolean correctCheckConnectGraph(ArrayList<MECountry> countryArr){
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
                    for(int k = 0;k<countryNeighbors.length;k++){
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
     * second correct checkCCB
     * check whether a country is separate from other country in its continent
     * @param continents continent list
     * @param country country list
     * @return true for correct, false for error
     */

    public  boolean correctCheckContinentCountryBelonging(ArrayList<MEContinent> continents, ArrayList<MECountry> country){
        checkFlagCCB = true;
        for(int i = 0;i<continents.size();i++){
            if(continents.get(i).getCountryNumber()>1){
                countryCheckFlag.clear();
                for(Iterator iter = continents.get(i).getCountryListName().iterator(); iter.hasNext();) {
                    countryCheckFlag.put(iter.next().toString(),0);
                }
                for(int j = 0;j<continents.get(i).getCountryNumber();j++){
                    String tempCountry = continents.get(i).getCountryListName().get(j);
                    if(countryCheckFlag.get(tempCountry)==0) {
                        for (int k = 0; k < country.size(); k++) {
                            if (country.get(k).getCountryName().equals(tempCountry)) {
                                for(Iterator iters = country.get(k).getNeighborName().iterator(); iters.hasNext();) {
                                    String keyTemp = iters.next().toString();
                                    if(countryCheckFlag.containsKey(keyTemp)) {
                                        countryCheckFlag.put(tempCountry, 1);
                                        countryCheckFlag.put(keyTemp, 1);
                                    }
                                }
                            }
                        }
                    }
                }
                for (String key:countryCheckFlag.keySet()) {
                    if(countryCheckFlag.get(key)==0){
                        checkFlagCCB = false;
                    }
                }
            }
            countryCheckFlag.clear();
        }
        return checkFlagCCB;
    }
    /**
     * third correct checkCB
     * every country belongs to one and only one continent
     * @param continentsArr continent list
     * @param countryArr country list
     * @return ture for correct, false for error
     */
    public  boolean correctCheckCountryBelonging(ArrayList<MEContinent> continentsArr, ArrayList<MECountry> countryArr){
        int countryAddByContinent = 0;

        //If the map is empty.
        if(continentsArr.isEmpty() && countryArr.isEmpty()){
            return true;
        }

        for(int i = 0; i<continentsArr.size();i++){

            countryAddByContinent = continentsArr.get(i).getCountryNumber()+countryAddByContinent;
        }
        if(countryAddByContinent != countryArr.size()){
            System.out.println(countryAddByContinent);
            System.out.println(countryArr.size());
            checkFlagCB = false;
        }
        return checkFlagCB;
    }
}