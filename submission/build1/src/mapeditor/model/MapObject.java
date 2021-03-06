package mapeditor.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class MapObject {

    private boolean checkFlagCG = true;
    private boolean checkFlagCC = true;
    private boolean checkFlagCB = true;

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
     *
     * @param continentName the name of continent you want to create
     * @param bonus         the continent bonus
     */
    public void createContinent(String continentName, int bonus) {
        MEContinent meContinent = new MEContinent();
        meContinent.setContinentName(continentName);
        meContinent.setBonus(bonus);
        arrContinent.add(meContinent);
    }

    /**
     * this is the method of creating a regular country
     *
     * @param countryName the name of country you want to create
     * @param neighbor    the neighbor country name
     */
    public void createCountry(String countryName, String[] neighbor) {
        MECountry meCountry = new MECountry();
        meCountry.setCountryName(countryName);
        for (int i = 4; i < neighbor.length; i++) {
            meCountry.setNeighbor(neighbor[i]);
        }
        arrCountry.add(meCountry);
    }

    /**
     * check map correctness interface
     *
     * @param mapPath read map path
     * @return if the map is correct, return true, else return false
     */
    public boolean checkCorrectness(String mapPath) {
        MapObject mapObj = new MapObject();

        ArrayList<String> fileRead = new ArrayList<String>();
        try {
            File file = new File(mapPath);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    fileRead.add(lineTxt);
                }
                read.close();
            } else {
                System.out.println("cannot find file");
            }
        } catch (Exception e) {
            System.out.println("wrong");
            e.printStackTrace();
        }
        for (int i = 0; i < fileRead.size(); i++) {
            if (fileRead.get(i).equals("[Continents]")) {
                for (int j = i + 1; j < fileRead.size(); j++) {
                    if (!fileRead.get(j).equals("")) {
                        mapObj.createContinent(fileRead.get(j).split("=")[0], Integer.parseInt(fileRead.get(j).split("=")[1]));
                    } else {
                        break;
                    }
                }
            } else if (fileRead.get(i).equals("[Territories]")) {
                int continentNumber = 0;
                for (int k = i + 1; k < fileRead.size(); k++) {
                    if (!fileRead.get(k).equals("")) {
                        String[] countrydata = fileRead.get(k).split(",");
                        mapObj.createCountry(countrydata[0], countrydata);
                        mapObj.arrContinent.get(continentNumber).addCountry(countrydata[0]);
                    } else {
                        continentNumber++;
                    }
                }
            }
        }
        System.out.println(" HERE " + mapObj.arrCountry.size());
        if (correctCheckConnectGraph(mapObj.arrCountry) == false) {
            System.out.println("1");
            return false;
        }
        if (correctCheckContinentCountry(mapObj.arrContinent, mapObj.arrCountry) == false) {
            System.out.println("2");
            return false;
        }
        //System.out.println("3");
        return correctCheckCountryBelonging(mapObj.arrContinent, mapObj.arrCountry) != false;
    }

    /**
     * first correct checkCC
     * check whether it is a connect graph or not
     *
     * @param countryArr country object list
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
     * check whether all countries in one continent are placed together
     *
     * @param continents continent object list
     * @param country    country object list
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
                    if (checkCountry.getNeighbor().isEmpty()) {
                        return checkFlagCC = false;
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
     * @param continentsArr continent object list
     * @param countryArr    country ojbect list
     * @return true for correct, false for not correct
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
