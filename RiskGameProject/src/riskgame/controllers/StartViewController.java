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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mapeditor.model.MapObject;
import riskgame.Main;
import riskgame.model.BasicClass.Continent;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.GraphSingleton;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.InitPlayers;
import riskgame.model.Utils.ListviewRenderer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static riskgame.Main.*;
import static riskgame.model.Utils.InitWorldMap.buildWorldMapGraph;

/**
 * controller class for StartView.fxml
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

    public static int firstRoundCounter;

    public static int reinforceInitCounter;


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
        MapObject mapChecker = new MapObject();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        mapPath = txf_mapPath.getText();
        mapChecker.checkCorrectness(mapPath);

        if (inputCounter > 0) {
            if (!mapChecker.errorMsg.toString().isEmpty()) {
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

        if (mapChecker.errorMsg.toString().isEmpty()) {
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

        //System.out.println(txf_mapPath.getText() + ", " + mapChecker.checkCorrectness(mapPath));

        inputCounter--;
    }

    /**
     * display world map and country allocation
     *
     * @param path map file path
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
        setPhaseViewObservable();
        initContinentsOwner();
        setPlayerWorldDominationView();

        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Pane reinforcePane = new FXMLLoader(getClass().getResource("../view/ReinforceView.fxml")).load();
        Scene reinforceScene = new Scene(reinforcePane, 1200, 900);
        curStage.setScene(reinforceScene);
        curStage.show();
    }

    /**
     * set player domination view for reinforce phase for displaying corresponding information
     */
    private void setPlayerWorldDominationView() {
        playerDomiViewObservable.resetObservable(totalNumOfPlayers);
//        playerDomiViewObservable.updateObservable();
//        playerDomiViewObservable.notifyObservers("Initialize obs from start view");
//
//        System.out.println("\n>>>>>>domi view observer:" + playerDomiViewObserver.getControlRatioList());
    }

    /**
     * initialize continents owner after countries allocated to players randomly
     */
    private void initContinentsOwner(){
        for (Map.Entry<String, Continent> entry : Main.worldContinentMap.entrySet()) {
            Continent continent = entry.getValue();
            boolean isSameOwner = false;
            int owner = -1;
            for (int playerIndex=0; playerIndex<totalNumOfPlayers; playerIndex++){
                int count = 0;
                int max = continent.getContinentCountryGraph().values().size();
                for (Country country: continent.getContinentCountryGraph().values()){
                    if (country.getCountryOwnerIndex()!=playerIndex){
                        break;
                    }
                    count++;
                    if (count == max){
                        System.out.println("find a owner! "+playerIndex);
                        isSameOwner = true;
                        owner = playerIndex;
                    }
                }
            }
            if (isSameOwner){
                playersList.get(owner).addControlledContinent(continent.getContinentName());
                continent.setContinentOwnerIndex(owner);
            }
        }
    }


    /**
     * set phase view observable params for reinforce phase for displaying corresponding information
     */
    private void setPhaseViewObservable() {
        String nextPhaseName = "Reinforcement Phase";
        int nextPlayerIndex = curRoundPlayerIndex;
        String nextActionString = "Action:\nBegin reinforce phase, need deploy armies to your countries";

        phaseViewObservable.resetObservable();
        phaseViewObservable.setAllParam(nextPhaseName, nextPlayerIndex, nextActionString);
        phaseViewObservable.notifyObservers("message_null");
    }


    /**
     * onClick event for confirming the total number of players
     *
     * @param actionEvent confirm the number of players
     */
    @FXML
    public void clickConfirmPlayerNum(ActionEvent actionEvent) {
        totalNumOfPlayers = Integer.parseInt(txf_playerNumbers.getText());
        firstRoundCounter = totalNumOfPlayers - 1;
        reinforceInitCounter = totalNumOfPlayers;

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
        if (playersList.isEmpty()) {
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
        Main.worldContinentMap = new LinkedHashMap<>();
        GraphSingleton.INSTANCE.resetInstance();
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


}
