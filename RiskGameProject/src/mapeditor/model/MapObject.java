package mapeditor.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class MapObject {

    private boolean checkFlagCG = true;
    private boolean checkFlagCCB = true;
    private boolean checkFlagCB = true;
    public StringBuilder errorMsg = new StringBuilder("") ;
    private HashMap<String,Integer> countryCheckFlag = new HashMap<String,Integer>();

    /**
     * arrContinent storage new continent
     */
    public ArrayList<MEContinent> arrContinent = new ArrayList<MEContinent>();

    /**
     * arrayCountry storage new country
     */
    public ArrayList<MECountry> arrCountry = new ArrayList<MECountry>();

    /**
     * this is the method of creating a continent
     * @param continentName the name of continent you want to create
     * @param bonus the continent bonus
     */
    public void createContinent(String continentName,int bonus){
        MEContinent meContinent = new MEContinent();
        meContinent.setContinentName(continentName);
        meContinent.setBonus(bonus);
        arrContinent.add(meContinent);
    }

    /**
     * this is the method of creating a regular country
     * @param countryName the name of country you want to create
     * @param neighbor the neighbor country name
     */
    public void createCountry(String countryName,String[] neighbor){
        MECountry meCountry = new MECountry();
        meCountry.setCountryName(countryName);
        for(int i=4;i<neighbor.length;i++){
            meCountry.setNeighbor(neighbor[i]);
        }
        arrCountry.add(meCountry);
    }

    /**
     * check map correctness interface
     * @param mapPath read map path
     * @return if the map is correct, return true, else return false
     */
    public void checkCorrectness(String mapPath){
        MapObject mapObj = new MapObject();

        ArrayList<String> fileRead = new ArrayList<String>();
        try {
            File file=new File(mapPath);
            if(file.isFile()&&file.exists()){
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader=new BufferedReader(read);
                String lineTxt=null;
                while((lineTxt=bufferedReader.readLine())!=null){
                    fileRead.add(lineTxt);
                }
                read.close();
            }else{
                errorMsg.append(MEErrorMsg.FILE_NOT_EXIST.getMsg());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        for(int i = 0;i<fileRead.size();i++) {
            if (fileRead.get(i).equals("[Continents]")) {
                for (int j = i + 1; j < fileRead.size(); j++) {
                    if (!fileRead.get(j).equals("")) {
                        mapObj.createContinent(fileRead.get(j).split("=")[0], Integer.parseInt(fileRead.get(j).split("=")[1]));
                    } else {
                        break;
                    }
                }
            } else if (fileRead.get(i).equals("[Territories]")) {
                for (int k = i + 1; k < fileRead.size(); k++) {
                    if (!fileRead.get(k).equals("")) {
                        String[] countrydata = fileRead.get(k).split(",");
                        mapObj.createCountry(countrydata[0], countrydata);
                        for (int z = 0; z < mapObj.arrContinent.size(); z++) {
                            if (mapObj.arrContinent.get(z).getContinentName().equals(countrydata[3]))
                                mapObj.arrContinent.get(z).addCountry(countrydata[0]);
                        }
                    }
                }
            }
        }
        if(correctCheckConnectGraph(mapObj.arrCountry) == false){
            errorMsg.append(MEErrorMsg.UNCONNECTED_GRAPH_ERROR.getMsg());
        }
        if(correctCheckContinentCountryBelonging(mapObj.arrContinent,mapObj.arrCountry) == false){
            errorMsg.append(MEErrorMsg.COUNTRY_SEPARATE_COUNTINENT_ERROR.getMsg());
        }
        if (correctCheckCountryBelonging(mapObj.arrContinent, mapObj.arrCountry) == false){
            errorMsg.append(MEErrorMsg.MULTIPLE_CONTINENT_ERROR.getMsg());
        }
    }

    /**
     * first correct checkCC
     * check whether it is a connect graph or not
     * @param countryArr country object list
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
     * @param continentsArr continent object list
     * @param countryArr country ojbect list
     * @return true for correct, false for not correct
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
            checkFlagCB = false;
        }
        return checkFlagCB;
    }

    /**
     * check map format
     * @param mapPath map path
     * @return if map format is true then return true, else return false
     */
    public boolean mapFormatCheck(String mapPath){
        boolean checkresult = true;
        boolean checkresult1 = false;
        boolean checkresult2 = false;
        boolean checkresult3 = false;
        ArrayList<String> fileRead = new ArrayList<String>();
        try {
            File file=new File(mapPath);
            if(file.isFile()&&file.exists()){
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader=new BufferedReader(read);
                String lineTxt=null;
                while((lineTxt=bufferedReader.readLine())!=null){
                    fileRead.add(lineTxt);
                }
                read.close();
            }else{
                System.out.println("cannot find file");
            }
        } catch (Exception e){
            System.out.println("wrong");
            e.printStackTrace();
        }
        for(int i = 0;i<fileRead.size();i++){
            if(fileRead.get(i).contains("[Map]")){
                checkresult1 = true;
            }
            if(fileRead.get(i).contains("[Continents]")){
                checkresult2 = true;
            }
            if(fileRead.get(i).contains("[Territories]")) {
                checkresult3 = true;
            }
        }
        checkresult = checkresult1&&checkresult2&&checkresult3;
        return checkresult;
    }

}
