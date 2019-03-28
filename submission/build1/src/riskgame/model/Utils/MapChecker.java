package riskgame.model.Utils;

import riskgame.controllers.StartViewController;

import java.io.*;
import java.util.ArrayList;

/**
 * Map checker examines the map format before display it.
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
     * check map file path
     *
     * @param path map path
     * @return true for correct, false for error
     */
    public static boolean isMapPathValid(String path) {
        System.out.println(new File(path).getAbsolutePath());
        try {
            new FileReader(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Wrapper function for checkMapValidity()
     *
     * @param path string of map file
     * @return true for correct, false for error
     * @throws IOException map file not found
     * @see MapChecker#checkMapValidity
     */
    public static boolean isMapValid(String path) throws IOException {
        return checkMapValidity(path) == NO_ERROR;
    }

    /**
     * static method for outer call
     *
     * @param path map path
     * @return error type
     * @throws IOException if buffered reader fails
     */
    public static int checkMapValidity(String path) throws IOException {
        boolean isContinentFound = false;
        boolean isTerritoriesFound = false;
        ArrayList continentsNames = new ArrayList<String>();

        BufferedReader bufferedReader = null;
        System.out.println(new File(path).getAbsolutePath());
        bufferedReader = new BufferedReader(new FileReader(path));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.contains(StartViewController.getContinentHeaderString())) {
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
            } else if (line.contains(StartViewController.getCountryHeaderString())) {
                isTerritoriesFound = true;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.length() == 0) {
                        continue;
                    }
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
        if (!(isContinentFound && isTerritoriesFound)) {
            return ERROR_TYPE_NO_DESCRIPTOR;
        } else {
            return NO_ERROR;
        }
    }

    /**
     * The number of the continent should be a integer at the description.
     *
     * @param continentLine a read-in line
     * @param continents    read in all continents stated
     * @return error type
     */
    private static int checkContinentCountInteger(String continentLine, ArrayList<String> continents) {
        String[] splitedLine = continentLine.split("=");
        try {
            Integer.parseInt(splitedLine[1]);
        } catch (NumberFormatException e) {
            return ERROR_TYPE_CONTINENT_NUMBER_FORMAT;
        } finally {
            continents.add(splitedLine[0]);
        }
        return NO_ERROR;
    }

    /**
     * Territories description should be formated.
     *
     * @param territoriesLine a read-in line
     * @param continents      all pre-stated continents at continent description.
     * @return error type
     */
    private static int checkTerritoriesFormat(String territoriesLine, ArrayList<String> continents) {
        System.out.println(territoriesLine);
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
