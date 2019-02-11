package mapeditor.model;

import java.util.*;

public class MECheckMapCorrectness{

    private static boolean correctnessFlag = true;
    private static boolean checkFlagCG = true;
    private static boolean checkFlagCC = true;
    private static boolean checkFlagCB = true;

    public static boolean isCorrect(ArrayList< MECountry> countryArr, ArrayList<MEContinent> continentsArr){
        boolean checkFlagCGResult = correctCheckConnectGraph(countryArr);
        boolean checkFlagCCResult = correctCheckContinentCountry(continentsArr,countryArr);
        boolean checkFlagCBResult = correctCheckCountryBelonging(continentsArr,countryArr);
        boolean correctnessFlag = checkFlagCGResult&&checkFlagCCResult&&checkFlagCBResult;
        return correctnessFlag;
    }

    /**
     * first correct checkCC
     * check whether it is a connect graph or not
     */
    public static boolean correctCheckConnectGraph(ArrayList<MECountry > countryArr){
        Queue<String> queue = new LinkedList<String>();
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
                    countryNeighbor = countryNeighbor.replaceAll(" ","");
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
        for(int i=0 ;i<continents.size();i++){
            if(continents.get(i).getCountryNumber()!=1){
                //TO DO
            }
        }
        return checkFlagCC;
    }

    /**
     * third correct check
     *  every country belongs to one and only one continent
     */
    public static boolean correctCheckCountryBelonging(ArrayList<MEContinent> continentsArr, ArrayList<MECountry> countryArr){
        int countryAddByContient = 0;
        for(int i = 0; i<continentsArr.size();i++){
            countryAddByContient = continentsArr.get(i).getCountryNumber()+countryAddByContient;
        }
        if(countryAddByContient != countryArr.size()){
            checkFlagCB = false;
        }
        return checkFlagCB;
    }
}