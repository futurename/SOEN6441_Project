package riskgame.model.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MapChecker {
    private ArrayList<String> continentsNames;

    private boolean checkMapValidity(String path)throws IOException, NumberFormatException{
        boolean isContinentFound = false;
        boolean isTerritoriesFound = false;

        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.contains(InitMapGraph.CONTINENT_HEADER_STRING)){
                isContinentFound = true;
                while ((line = bufferedReader.readLine()).length() != 0){
                    if (line.contains("=")){
                        if (!checkContinentCountInteger(line)){
                            return false;
                        }
                    }else return false;
                }
            }else if (line.contains(InitMapGraph.COUNTRY_HEADER_STRING)){
                isTerritoriesFound = true;
                while ((line = bufferedReader.readLine()) != null){
                    if (line.length() != 0 && line.contains(",")){
                        if (!checkTerritoriesFormat(line)){
                            return false;
                        }
                    }else return false;
                }
            }
        }

        return (isContinentFound && isTerritoriesFound);
    }

    private boolean checkContinentCountInteger(String continentLine){
        String[] splitedLine = continentLine.split("=");
        try {
            Integer.parseInt(splitedLine[1]);
        }catch (NumberFormatException e){
            return false;
        }finally {
            continentsNames.add(splitedLine[0]);
        }
        return true;
    }

    private boolean checkTerritoriesFormat(String territoriesLine){
        String[] splitedLine = territoriesLine.split(",");
        if (splitedLine.length<5){
            return false;
        }
        try {
            Integer.parseInt(splitedLine[1]);
            Integer.parseInt(splitedLine[2]);
        }catch (NumberFormatException e){
            return false;
        }
        if (!continentsNames.contains(splitedLine[3])){
            return false;
        }
        for (int i=3; i<splitedLine.length; i++){
            if (continentsNames.contains(splitedLine[i])){
                return false;
            }
        }
        return true;
    }


}
