package riskgame.model.Utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import mapeditor.model.MapObject;
import riskgame.Main;
import riskgame.model.BasicClass.Continent;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.GraphNode;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static riskgame.Main.graphSingleton;
import static riskgame.Main.playersList;
import static riskgame.Main.worldContinentMap;
import static riskgame.model.Utils.InitWorldMap.buildWorldMapGraph;


public class LoadGame{
    /**
     * default position index for coordinate x in map file
     */
    private static final int COORDINATE_X_POSITION = 1;

    /**
     * default position index for coordinate y in map file
     */
    private static final int COORDINATE_Y_POSITION = 2;

    /**
     * default position index for indicating continent in map file
     */
    private static final int CONTINENT_POSITION = 3;

    /**
     * onClick event for confirming load map file from selected path
     *
     * @param saveFilePath load map directory
     * @throws IOException file reading fails
     */
    @FXML
    public void clickConfirmLoadMap(String saveFilePath) throws IOException {
        //saveChecker.checkCorrectness(saveFilePath);

        // if (saveChecker.errorMsg.toString().isEmpty()) {
        // build the map structure to the game
        buildWorldMapGraph(saveFilePath, graphSingleton, worldContinentMap);
        //if(){
        //}
    }

    public static void printGraph(LinkedHashMap<String, GraphNode> worldHashMap) {
        for (Map.Entry<String, GraphNode> entry : worldHashMap.entrySet()) {
            String countryName = entry.getKey();
            GraphNode node = entry.getValue();

            System.out.println(">>>>>>>>>>>> country: " + countryName + ", continent: " + node.getCountry().getContinentName()
                    + "<<<<<<<<<<<<<<<");
            printGraphNode(node);
        }
    }

    private static void printGraphNode(GraphNode node) {
        for (Country country : node.getAdjacentCountryList()) {
            String countryName = country.getCountryName();
            String curPlayerName;

            System.out.printf("[%s] : %d\n", countryName, country.getOwnerIndex());
        }
        System.out.println("\n");
    }

    private static void printContinent(LinkedHashMap<String, Continent> continentLinkedHashMap) {
        for (Map.Entry<String, Continent> entry : continentLinkedHashMap.entrySet()) {
            Continent curContinent = entry.getValue();
            String curContinentName = entry.getKey();

            System.out.println("\n---[" + curContinentName + "]---");
            System.out.println(curContinent);
        }
    }

    public static void LoadGame(String path, LinkedHashMap<String, GraphNode> linkedHashMap,
                                LinkedHashMap<String, Continent> continentLinkedHashMap) throws IOException {
        System.out.println(new File(path).getAbsolutePath());

        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String curLine;

        while ((curLine = bufferedReader.readLine()) != null) {
            if (curLine.contains("[Continents]")) {
                while ((curLine = bufferedReader.readLine()).length() != 0) {
                    String[] curLineSplitArray = curLine.split("=");
                    String curContinentName = curLineSplitArray[0];
                    int curContinentBonusValue = Integer.parseInt(curLineSplitArray[1]);

                    //Initialize a new Continent object
                    Continent oneContinent = new Continent(curContinentName, curContinentBonusValue);
                    Main.worldContinentMap.put(curContinentName, oneContinent);
                }
            }

            if (curLine.contains("[Territories]")) {
                while ((curLine = bufferedReader.readLine()) != null) {
                    if (curLine.length() != 0) {
                        String[] curLineSplitArray = curLine.split(",");

                        String curCountryName = curLineSplitArray[0];
                        Country curCountry;
                        GraphNode curGraphNode;
                        if (!linkedHashMap.containsKey(curCountryName)) {
                            curCountry = new Country(curCountryName);
                            curGraphNode = new GraphNode(curCountry);
                        } else {
                            curGraphNode = linkedHashMap.get(curCountryName);
                            curCountry = curGraphNode.getCountry();
                        }

                        linkedHashMap.put(curCountryName, curGraphNode);

                        String curCoordinateX = curLineSplitArray[COORDINATE_X_POSITION];
                        String curCoordinateY = curLineSplitArray[COORDINATE_Y_POSITION];
                        curCountry.setCoordinateX(curCoordinateX);
                        curCountry.setCoordinateY(curCoordinateY);

                        String curContinentName = curLineSplitArray[CONTINENT_POSITION];
                        curCountry.setContinentName(curContinentName);
                        Main.worldContinentMap.get(curContinentName).getContinentCountryGraph().put(curCountryName, curCountry);

                        for (int i = CONTINENT_POSITION + 1; i < curLineSplitArray.length; i++) {
                            Country oneCountry;
                            GraphNode oneGraphNode;
                            String adjacentCountryName = curLineSplitArray[i];

                            if (!linkedHashMap.containsKey(adjacentCountryName)) {
                                oneCountry = new Country(adjacentCountryName);
                                oneGraphNode = new GraphNode(oneCountry);
                            } else {
                                oneGraphNode = linkedHashMap.get(adjacentCountryName);
                                oneCountry = oneGraphNode.getCountry();
                            }

                            curGraphNode.addAdjacentCountry(oneCountry);
                            linkedHashMap.put(adjacentCountryName, oneGraphNode);
                        }
                    }
                }
            }
        }
        printGraph(linkedHashMap);
        printContinent(continentLinkedHashMap);
        bufferedReader.close();
    }
}
