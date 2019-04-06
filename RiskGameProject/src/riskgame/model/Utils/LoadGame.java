package riskgame.model.Utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import mapeditor.model.MapObject;
import riskgame.Main;
import riskgame.controllers.StartViewController;
import riskgame.model.BasicClass.*;
import riskgame.model.BasicClass.StrategyPattern.*;

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
            if (curLine.contains("[Player]")) {
                int playerNumber = Integer.parseInt(curLine);
                for(int i=0; i< playerNumber; i++){
                    // Basic player info
                    String[] curLineSplitPlayerInfo = curLine.split(",");

                    int playerIndex = Integer.parseInt(curLineSplitPlayerInfo[0]);

                    boolean status = Boolean.parseBoolean(curLineSplitPlayerInfo[1]);

                    Color playerColor = Color.valueOf(curLineSplitPlayerInfo[3]);

                    int continentBonus = Integer.parseInt(curLineSplitPlayerInfo[4]);

                    int armyNbr = Integer.parseInt(curLineSplitPlayerInfo[5]);

                    String cards = curLineSplitPlayerInfo[5];
                    String[] cardsArray = cards.split(";");

                    ArrayList<Card> playerCards = new ArrayList<>();
                    for(int j=0; j< cardsArray.length; j++){
                        if(cardsArray[j].equals("Infantry")) {
                            Card newCard = Card.INFANTRY;
                            playerCards.add(newCard);
                        }else if(cardsArray[j].equals("Cavalry")){
                            Card newCard = Card.CAVALRY;
                            playerCards.add(newCard);
                        }else if(cardsArray[j].equals("Artillery")){
                            Card newCard = Card.ARTILLERY;
                            playerCards.add(newCard);
                        }
                    }

                    // Owned countries
                    ArrayList<String> countryNameList = new ArrayList<>();
                    curLine = bufferedReader.readLine();
                    String[] curLineSplitCountryInfo = curLine.split(",");
                    for(int j=0;j<curLineSplitCountryInfo.length; j++){
                        countryNameList.add(curLineSplitCountryInfo[j]);
                    }

                    Strategy curStrategy = null;
                    if(curLineSplitCountryInfo[2].equals("Human")) {
                        curStrategy = new StrategyHuman();
                    }else if(curLineSplitCountryInfo[2].equals("Aggressive")){
                        curStrategy = new StrategyAggressive();
                    }else if(curLineSplitCountryInfo[2].equals("Benevolent")){
                        curStrategy = new StrategyBenevolent();
                    }else if(curLineSplitCountryInfo[2].equals("Cheater")){
                        curStrategy = new StrategyCheater();
                    }else if(curLineSplitCountryInfo[2].equals("Random")){
                        curStrategy = new StrategyRandom();
                    }

                    Player onePlayer = new Player(playerIndex, curStrategy,armyNbr,playerCards,countryNameList,playerColor,
                            continentBonus,status,graphSingleton, worldContinentMap);

                    System.out.println("init player: " + playerIndex + ", army nbr: " + onePlayer.getArmyNbr());

                }
            }

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

                        int armyNbr = Integer.parseInt(curLineSplitArray[CONTINENT_POSITION+2]);
                        curCountry.setArmy(armyNbr);

                        for (int i = CONTINENT_POSITION + 3; i < curLineSplitArray.length; i++) {
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

            // Add country owner
            if (curLine.contains("[Connection]")) {
                int n = Integer.parseInt(curLine = bufferedReader.readLine());
                for(int j=0;j<n;j++){
                    curLine = bufferedReader.readLine();
                    String[] reader = curLine.split(",");
                    Player p1 = playersList.get(Integer.parseInt(reader[0]));
                    GraphNode curGraphNode;
                    for(int k =1 ; k< reader.length;k++){
                        String ownerCountryName = reader[k];
                        curGraphNode = linkedHashMap.get(ownerCountryName);
                        Country curCountry = curGraphNode.getCountry();
                        curCountry.setCountryOwner(p1);
                        curGraphNode.replaceCountry(curCountry);
                        linkedHashMap.replace("ownerCountryName",curGraphNode);
                    }
                }
            }

            // jump to the correct page and set the game data
            if (curLine.contains("[Phase]")) {
                while ((curLine = bufferedReader.readLine()) != null) {

                    if(curLine.contains("Initial")){

                    }else if(curLine.contains("Reinforcement")){

                    }else if(curLine.contains("Attack")){

                    }else if(curLine.contains("Fortification")){

                    }
                }
            }
        }
        printGraph(linkedHashMap);
        printContinent(continentLinkedHashMap);
        bufferedReader.close();
    }
}