package riskgame.model.Utils;

import javafx.scene.control.Alert;

import java.io.*;
import java.util.ArrayList;

/**
 * Map checker examines the map format before display it.
 * @date Feb 24
 * @since 1.0
 * @version 1.0
 */
public class MapChecker {
    private static final int NO_ERROR = 9527;
    private static final int ERROR_TYPE_NO_DESCRIPTOR = 6324;
    private static final int ERROR_TYPE_NO_SEPARATOR = 605;
    private static final int ERROR_TYPE_COORDINATE_NUMBER_FORMAT = 604;
    private static final int ERROR_TYPE_CONTINENT_NUMBER_FORMAT = 607;
    private static final int ERROR_TYPE_COUNTRY_NAME_INVALID = 609;
    private static final int ERROR_TYPE_CONTINENT_NOT_STATED = 608;
    private static final int ERROR_TYPE_EMPTY_CONTINENT = 603;

    /**
     * Wrapper function for checkMapValidity()
     * @param path
     * @throws IOException
     * @see MapChecker#checkMapValidity
     */
    public static boolean isMapValid(String path) throws IOException {
        if (checkMapValidity(path) == NO_ERROR){
            return true;
        }else return false;
    }
    /**
     * static method for outer call
     * @param path map path
     * @return error type
     * @throws IOException if path invalid
     */
    public static int checkMapValidity(String path) throws IOException {
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
                        return ERROR_TYPE_NO_SEPARATOR;
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

    /**
     * The number of the continent should be a integer at the description.
     * @param continentLine a read-in line
     * @param continents read in all continents stated
     * @return error type
     */
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

    /**
     * Territories description should be formated.
     * @param territoriesLine a read-in line
     * @param continents all pre-stated continents at continent description.
     * @return error type
     */
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
