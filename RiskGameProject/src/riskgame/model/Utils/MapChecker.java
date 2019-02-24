package riskgame.model.Utils;

import javafx.scene.control.Alert;

import java.io.*;
import java.util.ArrayList;

public class MapChecker {
    private static final int NO_ERROR = 9527;
    private static final int ERROR_TYPE_NO_DESCRIPTOR = 6324;
    private static final int ERROR_TYPE_NO_SEPARATOR = 605;
    private static final int ERROR_TYPE_COORDINATE_NUMBER_FORMAT = 604;
    private static final int ERROR_TYPE_CONTINENT_NUMBER_FORMAT = 607;
    private static final int ERROR_TYPE_COUNTRY_NAME_INVALID = 609;
    private static final int ERROR_TYPE_CONTINENT_NOT_STATED = 608;
    private static final int ERROR_TYPE_EMPTY_CONTINENT = 603;

    public static int checkMapValidity(String path) throws NumberFormatException, IOException {
        boolean isContinentFound = false;
        boolean isTerritoriesFound = false;
        ArrayList continentsNames = new ArrayList<String>();
        Alert alert = new Alert(Alert.AlertType.WARNING);

        BufferedReader bufferedReader = null;

        System.out.println(new File(path).getAbsolutePath());

        try {
            bufferedReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            alert.setContentText("Map file invalid, please select another one!");
            alert.showAndWait();
        }
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.contains(InitMapGraph.CONTINENT_HEADER_STRING)) {
                isContinentFound = true;
                while ((line = bufferedReader.readLine()).length() != 0) {
                    if (line.contains("=")) {
                        int status = checkContinentCountInteger(line, continentsNames);
                        if (status != NO_ERROR) {
                            return status;
                        }
                    } else {
                        return ERROR_TYPE_NO_SEPARATOR;
                    }
                }
            } else if (line.contains(InitMapGraph.COUNTRY_HEADER_STRING)) {
                isTerritoriesFound = true;
                while ((line = bufferedReader.readLine()) != null && line.length() != 0) {
                    if (line.contains(",")) {
                        int status = checkTerritoriesFormat(line, continentsNames);
                        if (status != NO_ERROR) {
                            return status;
                        }
                    } else {
                        return ERROR_TYPE_NO_DESCRIPTOR;
                    }
                }
            }
        }
        bufferedReader.close();
        if (!(isContinentFound && isTerritoriesFound)){
            return ERROR_TYPE_NO_DESCRIPTOR;
        }else {
            return NO_ERROR;
        }
    }

    private static int checkContinentCountInteger(String continentLine, ArrayList<String> continents) {
        String[] splitedLine = continentLine.split("=");
        try {
            Integer.parseInt(splitedLine[1]);
        } catch (NumberFormatException e) {
            return ERROR_TYPE_CONTINENT_NUMBER_FORMAT;
        } finally {
            continents.add((String) splitedLine[0]);
        }
        return NO_ERROR;
    }

    private static int checkTerritoriesFormat(String territoriesLine, ArrayList<String> continents) {
        String[] splitedLine = territoriesLine.split(",");
        if (splitedLine.length < 5) {
            return ERROR_TYPE_EMPTY_CONTINENT;
        }
        try {
            Integer.parseInt(splitedLine[1]);
            Integer.parseInt(splitedLine[2]);
        } catch (NumberFormatException e) {
            return ERROR_TYPE_COORDINATE_NUMBER_FORMAT;
        }
        if (!continents.contains(splitedLine[3])) {
            return ERROR_TYPE_COUNTRY_NAME_INVALID;
        }
        for (int i = 4; i < splitedLine.length; i++) {
            if (continents.contains(splitedLine[i])) {
                return ERROR_TYPE_CONTINENT_NOT_STATED;
            }
        }
        return NO_ERROR;
    }


}
