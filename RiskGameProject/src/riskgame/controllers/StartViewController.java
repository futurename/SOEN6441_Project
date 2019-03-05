package riskgame.controllers;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import riskgame.Main;
import riskgame.model.BasicClass.*;
import riskgame.model.Utils.InitPlayers;
import riskgame.model.Utils.ListviewRenderer;
import riskgame.model.Utils.MapObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static riskgame.Main.graphSingleton;

/**
 * controller class for StartView.fxml
 *
 * @author WW
 **/

public class StartViewController {
    @FXML
    private TextField txf_mapPath;
    @FXML
    private TextField txf_playerNumbers;
    @FXML
    private Button btn_confirmLoadFile;
    @FXML
    private Button btn_loadFile;
    @FXML
    private Button btn_reducePlayerNumber;
    @FXML
    private Button btn_plusPlayerNumber;
    @FXML
    private HBox hbx_infoDisplayHbox;
    @FXML
    private Button btn_nextStep;
    @FXML
    private Button btn_confirmPlayerNum;
    @FXML
    private TextField txf_mapPromptInfo;
    @FXML
    private Button btn_infoSwitcher;

    /**
     * default number of players
     */
    private static final int DEFAULT_NUM_OF_PLAYERS = 3;

    /**
     * defalut maximum player number supported in the program
     */
    private static final int MAX_NUM_OF_PLAYERS = 8;

    /**
     * default minimum player number supported in the program
     */
    private static final int MIN_NUM_OF_PLAYERS = 2;

    /**
     * default path of map file
     */
    private static final String DEFAULT_MAP_PATH = "maps/World.map";

    /**
     * variable for storing map file path
     */
    private String mapPath;

    /**
     * player number bean
     */
    private IntegerProperty numOfPlayersProperty;

    /**
     * counter for recording times of error selection of map file
     */
    private int inputCounter = 3;

    /**
     * indicator for marking whether world map file has been read and initialized
     */
    private boolean isMapInfoOn = false;

    /**
     * default string value for marking continent section in map file
     */
    private static final String CONTINENT_HEADER_STRING = "[Continents]";

    /**
     * default string value for marking country section in map file
     */
    private static final String COUNTRY_HEADER_STRING = "[Territories]";

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
     * set default map path, default number of players and its range, constrain the range of number of player with UI controls
     */
    public void initialize() {
        txf_playerNumbers.setText(Integer.toString(DEFAULT_NUM_OF_PLAYERS));

        numOfPlayersProperty = new SimpleIntegerProperty(DEFAULT_NUM_OF_PLAYERS);

        if (numOfPlayersProperty.get() <= MIN_NUM_OF_PLAYERS) {
            btn_reducePlayerNumber.setVisible(false);
        }
        if (numOfPlayersProperty.get() >= MAX_NUM_OF_PLAYERS) {
            btn_plusPlayerNumber.setVisible(false);
        }
    }

    /**
     * onClick event for modifying UI controls and their values
     *
     * @param actionEvent reduce number of players by one
     */
    @FXML
    public void clickReducePlayerNumber(ActionEvent actionEvent) {
        if (numOfPlayersProperty.get() > MIN_NUM_OF_PLAYERS) {
            numOfPlayersProperty.set(numOfPlayersProperty.get() - 1);
            txf_playerNumbers.setText(numOfPlayersProperty.getValue().toString());
        }
        if (numOfPlayersProperty.get() == MIN_NUM_OF_PLAYERS) {
            btn_reducePlayerNumber.setVisible(false);
        }
        if (numOfPlayersProperty.get() < MAX_NUM_OF_PLAYERS) {
            btn_plusPlayerNumber.setVisible(true);
        }

    }

    /**
     * onClick event for modifying UI controls and their values
     *
     * @param actionEvent increase number of players by one
     */
    @FXML
    public void clickIncreasePlayerNumber(ActionEvent actionEvent) {
        if (numOfPlayersProperty.get() < MAX_NUM_OF_PLAYERS) {
            numOfPlayersProperty.set(numOfPlayersProperty.get() + 1);
            txf_playerNumbers.setText(numOfPlayersProperty.getValue().toString());
        }
        if (numOfPlayersProperty.get() == MAX_NUM_OF_PLAYERS) {
            btn_plusPlayerNumber.setVisible(false);
        }
        if (numOfPlayersProperty.get() > MIN_NUM_OF_PLAYERS) {
            btn_reducePlayerNumber.setVisible(true);
        }
    }

    /**
     * onClick event for confirming load map file from selected path
     *
     * @param actionEvent load map to memeory
     * @throws IOException file reading fails
     */
    @FXML
    public void clickConfirmLoadMap(ActionEvent actionEvent) throws IOException {
        MapObject check = new MapObject();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        mapPath = txf_mapPath.getText();
        if (!check.checkCorrectness(mapPath)) {
            alert.setContentText("Map file not exist!");
            alert.showAndWait();
            return;
        }
        if (inputCounter > 0) {
            if (!check.checkCorrectness(mapPath)) {
                alert.setContentText("Map file invalid, please select another one!\nCounter: " + inputCounter);
                alert.showAndWait();
                txf_mapPath.setText(DEFAULT_MAP_PATH);
            }
        }
        if (inputCounter == 0) {
            alert.setContentText("Use default map!");
            alert.showAndWait();
            mapPath = DEFAULT_MAP_PATH;
        }
        if (check.checkCorrectness(mapPath)) {
            buildWorldMapGraph(mapPath, graphSingleton);

            btn_confirmLoadFile.setVisible(false);
            btn_loadFile.setVisible(false);
            txf_mapPath.setEditable(false);

            displayWorldMap(mapPath);

            if (!btn_confirmPlayerNum.isVisible()) {
                btn_infoSwitcher.setVisible(true);
                btn_infoSwitcher.setText("Players Info");
                btn_nextStep.setVisible(true);

                if (Main.playersList.isEmpty()) {
                    InitPlayers.initPlayers(Main.totalNumOfPlayers, graphSingleton);
                }
            }
        }

        System.out.println(txf_mapPath.getText() + ", " + check.checkCorrectness(mapPath));

        inputCounter--;
    }

    /**
     * display world map and country allocation
     *
     * @throws IOException map file not found
     */
    private void displayWorldMap(String path) throws IOException {
        if (graphSingleton.isEmpty()) {
            buildWorldMapGraph(path, graphSingleton);
        }

        hbx_infoDisplayHbox.getChildren().clear();
        txf_mapPromptInfo.setText("World Map");
        txf_mapPromptInfo.setVisible(true);

        ArrayList<ListView<String>> mapListviews = new ArrayList<>();

        double hboxPaneWidth = hbx_infoDisplayHbox.getWidth();
        double avgListviewWidth = hboxPaneWidth / Main.worldContinentMap.size();

        for (Map.Entry<String, Continent> entry : Main.worldContinentMap.entrySet()) {
            Continent curContinent = entry.getValue();
            String curContinentName = entry.getKey();

            ObservableList<String> datalist = FXCollections.observableArrayList();
            datalist.add(curContinentName);
            datalist.add("");
            datalist.addAll(curContinent.getContinentCountryNameList());

            ListView<String> curListView = ListviewRenderer.getRenderedStartview(Main.totalNumOfPlayers, datalist, avgListviewWidth);
            mapListviews.add(curListView);
        }

        hbx_infoDisplayHbox.getChildren().addAll(mapListviews);
        isMapInfoOn = true;
    }

    /**
     * onClick event for moving to reinforce phase view
     *
     * @param actionEvent proceed to reinforcement phase
     * @throws IOException reinforcview.fxml not found
     */
    @FXML
    public void clickNextToReinforcePhase(ActionEvent actionEvent) throws IOException {
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane reinforcePane = new FXMLLoader(getClass().getResource("../view/ReinforceView.fxml")).load();
        Scene reinforceScene = new Scene(reinforcePane, 1200, 900);

        curStage.setScene(reinforceScene);

        curStage.show();
    }

    /**
     * onClick event for confirming the total number of players
     *
     * @param actionEvent confirm the number of players
     */
    @FXML
    public void clickConfirmPlayerNum(ActionEvent actionEvent) {
        Main.totalNumOfPlayers = Integer.parseInt(txf_playerNumbers.getText());

        btn_reducePlayerNumber.setVisible(false);
        btn_plusPlayerNumber.setVisible(false);
        btn_confirmPlayerNum.setVisible(false);

        if (!btn_confirmLoadFile.isVisible()) {
            btn_infoSwitcher.setVisible(true);
            btn_infoSwitcher.setText("Map Info");
            displayPlayerInfo();
            btn_nextStep.setVisible(true);
        }
    }

    /**
     * display information of all players and their owned countries
     */
    private void displayPlayerInfo() {
        isMapInfoOn = false;
        if (Main.playersList.isEmpty()) {
            InitPlayers.initPlayers(Main.totalNumOfPlayers, graphSingleton);
        }
        txf_mapPromptInfo.setText("Players Info");

        hbx_infoDisplayHbox.getChildren().clear();

        ArrayList<ListView<String>> playerListViews = new ArrayList<>();

        double hboxPaneWidth = hbx_infoDisplayHbox.getWidth();
        double avgListviewWidth = hboxPaneWidth / Main.totalNumOfPlayers;

        for (int playerIndex = 0; playerIndex < Main.totalNumOfPlayers; playerIndex++) {
            Player curPlayer = Main.playersList.get(playerIndex);
            String playerString = "Player : " + playerIndex;

            ObservableList<String> datalist = FXCollections.observableArrayList();
            datalist.add(playerString);

            //split player string and following country names
            datalist.add("");

            ArrayList<String> curPlayerCountryNameList = curPlayer.getOwnedCountryNameList();

            datalist.addAll(curPlayerCountryNameList);

            ListView<String> curListView = ListviewRenderer.getRenderedStartview(playerIndex, datalist, avgListviewWidth);

            playerListViews.add(curListView);
        }
        hbx_infoDisplayHbox.getChildren().addAll(playerListViews);
    }

    /**
     * onClick event for switch from world map display to players/countries display
     *
     * @param actionEvent switch information display between world map(country distribution in continents) and players(with their allocated coutries)
     * @throws IOException map file not found
     */
    @FXML
    public void clickInfoDisplaySwitcher(ActionEvent actionEvent) throws IOException {
        if (isMapInfoOn) {
            btn_infoSwitcher.setText("Map Info");
            displayPlayerInfo();
        } else {
            btn_infoSwitcher.setText("Players Info");
            displayWorldMap(mapPath);
        }
    }

    /**
     * reset all parameters to original state
     *
     * @param actionEvent reset player settings to initlized state
     * @throws IOException map file not found
     */
    @FXML
    public void clickReset(ActionEvent actionEvent) throws IOException {
        resetStaticVariables();
        Stage startviewStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/startview.fxml"));
        Pane root = fxmlLoader.load();
        startviewStage.setScene(new Scene(root, 1200, 900));

        startviewStage.setResizable(false);
        startviewStage.show();
    }

    /**
     * reset all variables to original values
     */
    private void resetStaticVariables() {
        Main.totalNumOfPlayers = -1;
        Main.playersList = new ArrayList<>();
        Main.curRoundPlayerIndex = 0;
        //Main.worldCountriesMap = new HashMap<>();
        Main.worldContinentMap = new LinkedHashMap<>();
        graphSingleton = GraphSingleton.INSTANCE.getInstance();
    }


    /**
     * onClick event for confirming the map file selection
     *
     * @param actionEvent confirm button is clicked
     * @throws IOException map file not found
     */
    public void clickLoadMap(ActionEvent actionEvent) throws IOException {
        Stage fileStage = null;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select map file");

        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Map files(*.map)", "*.map");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(fileStage);
        txf_mapPath.setText(file.getAbsolutePath());
    }

    /**
     * this method read and initialize world map
     *
     * @param path path of map file
     * @throws IOException map file not found
     */
    public static void buildWorldMapGraph(String path, LinkedHashMap<String, GraphNode> graphSingleton) throws IOException {
        System.out.println(new File(path).getAbsolutePath());

        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String curLine;

        while ((curLine = bufferedReader.readLine()) != null) {
            if (curLine.contains(CONTINENT_HEADER_STRING)) {
                while ((curLine = bufferedReader.readLine()).length() != 0) {
                    String[] curLineSplitArray = curLine.split("=");
                    String curContinnentName = curLineSplitArray[0];
                    int curContinentBonusValue = Integer.parseInt(curLineSplitArray[1]);

                    //Initialize a new Continent object
                    Continent oneContinent = new Continent(curContinnentName, curContinentBonusValue);
                    Main.worldContinentMap.put(curContinnentName, oneContinent);
                }
            }

            if (curLine.contains(COUNTRY_HEADER_STRING)) {
                while ((curLine = bufferedReader.readLine()) != null) {
                    if (curLine.length() != 0) {
                        String[] curLineSplitArray = curLine.split(",");

                        String curCountryName = curLineSplitArray[0];
                        Country curCountry;
                        GraphNode curGraphNode;
                        if (!graphSingleton.containsKey(curCountryName)) {
                            curCountry = new Country(curCountryName);
                            curGraphNode = new GraphNode(curCountry);
                        } else {
                            curGraphNode = graphSingleton.get(curCountryName);
                            curCountry = curGraphNode.getCountry();
                        }

                        graphSingleton.put(curCountryName, curGraphNode);

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

                            if (!graphSingleton.containsKey(adjacentCountryName)) {
                                oneCountry = new Country(adjacentCountryName);
                                oneGraphNode = new GraphNode(oneCountry);
                            } else {
                                oneGraphNode = graphSingleton.get(adjacentCountryName);
                                oneCountry = oneGraphNode.getCountry();
                            }

                            curGraphNode.addAdjacentCountry(oneCountry);
                            graphSingleton.put(adjacentCountryName, oneGraphNode);
                        }
                    }
                }
            }
        }
        printGraph();
        printContinent();
        bufferedReader.close();
    }

    /**
     * this method prints continents and their countries in console
     */
    private static void printContinent() {
        for (Map.Entry<String, Continent> entry : Main.worldContinentMap.entrySet()) {
            Continent curContinent = entry.getValue();
            String curContinentName = entry.getKey();

            System.out.println("\n---[" + curContinentName + "]---");
            System.out.println(curContinent);
        }
    }

    /**
     * this method prints world map graph in console
     */
    private static void printGraph() {
        for (Map.Entry<String, GraphNode> entry : graphSingleton.entrySet()) {
            String countryName = entry.getKey();
            GraphNode node = entry.getValue();
            System.out.println(">>>>>>>>>>>> country: " + countryName + ", continent: " + node.getCountry().getContinentName() + " <<<<<<<<<<<<<<<");
            printGraphNode(node);
        }
    }

    /**
     * this method prints informaton of selected graph node
     *
     * @param node selected graph node
     */
    private static void printGraphNode(GraphNode node) {
        for (Country country : node.getAdjacentCountryList()) {
            String countryName = country.getCountryName();
            System.out.printf("%s, ", countryName);
        }
        System.out.println("\n");
    }

    /**
     * getter
     *
     * @return default continent header string
     */
    public static String getContinentHeaderString() {
        return CONTINENT_HEADER_STRING;
    }

    /**
     * getter
     *
     * @return default country header string
     */
    public static String getCountryHeaderString() {
        return COUNTRY_HEADER_STRING;
    }

}
