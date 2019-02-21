package riskgame.model.Utils;

import javafx.scene.control.Alert;

import java.io.*;
import java.util.ArrayList;

public class MapChecker {

    public static boolean checkMapValidity(String path) throws NumberFormatException, IOException {
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
                        if (!checkContinentCountInteger(line, continentsNames)) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            } else if (line.contains(InitMapGraph.COUNTRY_HEADER_STRING)) {
                isTerritoriesFound = true;
                while ((line = bufferedReader.readLine()) != null && line.length() != 0) {
                    if (line.contains(",")) {
                        if (!checkTerritoriesFormat(line, continentsNames)) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
        }
        bufferedReader.close();
        return (isContinentFound && isTerritoriesFound);
    }

    private static boolean checkContinentCountInteger(String continentLine, ArrayList<String> continents) {
        String[] splitedLine = continentLine.split("=");
        try {
            Integer.parseInt(splitedLine[1]);
        } catch (NumberFormatException e) {
            return false;
        } finally {
            continents.add((String) splitedLine[0]);
        }
        return true;
    }

    private static boolean checkTerritoriesFormat(String territoriesLine, ArrayList<String> continents) {
        String[] splitedLine = territoriesLine.split(",");
        if (splitedLine.length < 5) {
            System.out.println("1");
            return false;
        }
        try {
            Integer.parseInt(splitedLine[1]);
            Integer.parseInt(splitedLine[2]);
        } catch (NumberFormatException e) {
            System.out.println("2");
            return false;
        }
        if (!continents.contains(splitedLine[3])) {
            System.out.println("3");
            return false;
        }
        for (int i = 4; i < splitedLine.length; i++) {
            if (continents.contains(splitedLine[i])) {
                System.out.println("44");
                return false;
            }
        }
        return true;
    }


}
