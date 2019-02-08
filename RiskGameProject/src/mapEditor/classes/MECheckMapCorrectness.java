package mapEditor.classes;

import java.util.*;

public class MECheckMapCorrectness{

    private static boolean correctnessFlag = true;
    private static boolean checkFlagCG = true;
    private static boolean checkFlagCC = true;
    private static boolean checkFlagCB = true;

    public static boolean isCorrect(ArrayList< LinkedList<String> > country, ArrayList<MEContinent> continents){
        boolean checkFlagCGResult = correctCheckConnectGraph(country);
        boolean checkFlagCCResult = correctCheckContinentCountry(continents,country);
        boolean checkFlagCBResult = correctCheckCountryBelonging(continents,country);
        boolean correctnessFlag = checkFlagCGResult&&checkFlagCCResult&&checkFlagCBResult;
        return correctnessFlag;
    }

    /**
     * first correct checkCC
     * check whether it is a connect graph or not
     */
    public static boolean correctCheckConnectGraph(ArrayList< LinkedList<String> > country){
        Queue<String> queue = new LinkedList<String>();
        HashMap<String,Boolean> visited = new HashMap<String,Boolean>();
        for(int i=0 ; i<country.size(); i++ ){
            String countryTemp = country.get(i).getFirst();
            visited.put(countryTemp,false);
        }
        String firstCountry = country.get(0).getFirst();
        queue.offer(firstCountry);
        //bfs
        while(!queue.isEmpty()){
            String queueHead = queue.poll();
            visited.put(queueHead, Boolean.TRUE);
            for(int j=0 ;j<country.size();j++){
                if(country.get(j).getFirst().equals(queueHead)){
                    for(int k = 1;k<country.get(j).size();k++){
                        String readyToAddInQueue = country.get(j).get(k);
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
    public static boolean correctCheckContinentCountry(ArrayList<MEContinent> continents, ArrayList<LinkedList<String>> country){
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
    public static boolean correctCheckCountryBelonging(ArrayList<MEContinent> continents, ArrayList<LinkedList<String>> country){
        int countryAddByContient = 0;
        for(int i = 0; i<continents.size();i++){
            countryAddByContient = continents.get(i).getCountryNumber()+countryAddByContient;
        }
        if(countryAddByContient != country.size()){
            checkFlagCB = false;
        }
        return checkFlagCB;
    }
}