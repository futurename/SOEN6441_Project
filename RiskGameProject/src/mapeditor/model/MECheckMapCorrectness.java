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
    public String isCorrect(ArrayList<MECountry> countryArr, ArrayList<MEContinent> continentsArr){
        boolean checkFlagCGResult = correctCheckConnectGraph(countryArr);
        boolean checkFlagCCBResult = correctCheckConnectContinent(continentsArr,countryArr);
        boolean checkFlagCBResult = correctCheckCountryBelonging(continentsArr,countryArr);

        if(checkFlagCGResult == false){
            return "Unconnected Graph";
        }
        if (checkFlagCCBResult == false){
            return "Country doesn't connect with any country of its continent";
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
                    if(!countryNeighbor.equals("")){
                        for (int k = 0; k < countryNeighbors.length; k++) {
                            String readyToAddInQueue = countryNeighbors[k];
                            if (visited.get(readyToAddInQueue) == false) {
                                queue.offer(readyToAddInQueue);
                            }
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
     * check whether countries in a continent is unconnected
     * @param continentsArr continent array
     * @param countryArr country array
     * @return true for correct, false for error
     */
    public boolean correctCheckConnectContinent(ArrayList<MEContinent> continentsArr, ArrayList<MECountry> countryArr){
        Queue<String> checkQueue = new LinkedList<String>();
        HashMap<String, Boolean> checkList = new HashMap<String, Boolean>();
        //Check all the continent
        for(int i=0;i< continentsArr.size();i++){
            MEContinent newContinent = continentsArr.get(i);
            String newCountryName = "";

            //If there is no country or a single country
            if(newContinent.countryList.size() <= 1){
                continue;
            }

            for(int j=0;j< newContinent.countryList.size(); j++) {
                newCountryName = newContinent.countryList.get(j);
                checkList.put(newCountryName, false);
            }
            for(int j=0;j< countryArr.size(); j++){
                MECountry newCountry = countryArr.get(j);

                //If find the right country that in the current continent
                if(newCountry.getCountryName().equals(newCountryName)){
                    for(int k=0; k< newCountry.getNeighborSize(); k++){
                        checkQueue.offer(newCountry.getCountryName());

                        while(!checkQueue.isEmpty()){
                            String curCountryName = checkQueue.poll();
                            MECountry countryObj = new MECountry();

                            for(int m =0; m < countryArr.size(); m++){
                                if(countryArr.get(m).getCountryName().equals(curCountryName)){
                                    countryObj = countryArr.get(m);
                                }
                            }
                            LinkedList<String> neighbors = countryObj.getNeighborName();

                            for(int n = 0; n < neighbors.size(); n++) {
                                for(int f = 0; f < newContinent.countryList.size(); f++){

                                    if(newContinent.countryList.get(f).equals(neighbors.get(n))) {
                                        if(!checkList.get(neighbors.get(n))){
                                            checkList.replace(neighbors.get(n), true);
                                            checkQueue.offer(neighbors.get(n));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //After bfs all the country in that continent
            Iterator<String> countrySet = checkList.keySet().iterator();
            while (countrySet.hasNext()) {
                boolean value = checkList.get(countrySet.next());

                if (value == false) {
                    return false;
                }
            }
            checkList.clear();
        }
        return true;
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

        for(int i = 0; i < countryArr.size();i++){
            MECountry newCountry = countryArr.get(i);
            for(int j =0; j < countryArr.size(); j++){
                if(j == i){
                    continue;
                }
                MECountry compareCountry = countryArr.get(j);
                if(newCountry.getCountryName().equals(compareCountry.getCountryName())){
                    checkFlagCB = false;
                }
            }
        }
        return checkFlagCB;
    }
}