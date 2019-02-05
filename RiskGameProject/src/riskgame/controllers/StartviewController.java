package riskgame.controllers;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import javafx.stage.Stage;
import riskgame.Main;
import riskgame.classes.Continent;
import riskgame.classes.Player;
import riskgame.model.MapInitialization;
import riskgame.model.PlayerInitialization;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class StartviewController implements Initializable {

    @FXML
    private TextField txf_mapPath;
    @FXML
    private TextField txf_playerNumbers;
    @FXML
    private Button btn_loadMap;
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


    private final int DEFAULT_NUM_OF_PLAYERS = 3;

    //need modify the values in the tooltips of the buttons to match following threshold numbers
    private final int MAX_NUM_OF_PLAYERS = 8;
    private final int MIN_NUM_OF_PLAYERS = 2;

    private final int DEFAULT_STEP_VALUE = 1;
    private final String DEFAULT_MAP_PATH = "./maps/World.map";
    private IntegerProperty numOfPlayersProperty;
    private String mapPath;
    private boolean isMapInfoOn = false;

    //Initialize default values for display
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txf_playerNumbers.setText(Integer.toString(DEFAULT_NUM_OF_PLAYERS));
        mapPath = "./maps/World.map";

        numOfPlayersProperty = new SimpleIntegerProperty(DEFAULT_NUM_OF_PLAYERS);

        if (numOfPlayersProperty.get() <= MIN_NUM_OF_PLAYERS) {
            btn_reducePlayerNumber.setVisible(false);
        }
        if (numOfPlayersProperty.get() >= MAX_NUM_OF_PLAYERS) {
            btn_plusPlayerNumber.setVisible(false);
        }
    }

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

    public void clickLoadMap(ActionEvent actionEvent) throws IOException {
        btn_loadMap.setVisible(false);

        displayWorldMap();

        if (!btn_confirmPlayerNum.isVisible()) {
            btn_infoSwitcher.setVisible(true);
            btn_infoSwitcher.setText("Players Info");
            btn_nextStep.setVisible(true);

            if (Main.playersList.isEmpty()) {
                PlayerInitialization.initPlayers();
            }
        }
    }

    private void displayWorldMap() throws IOException {
        if (Main.worldCountriesMap.isEmpty()) {
            MapInitialization.buildWorldMap(DEFAULT_MAP_PATH);
        }
        hbx_infoDisplayHbox.getChildren().clear();
        txf_mapPromptInfo.setText("World Map");
        txf_mapPromptInfo.setVisible(true);

        ArrayList<ListView<String>> mapListviews = new ArrayList<>();

        double hboxPaneWidth = hbx_infoDisplayHbox.getWidth();
        double avgListviewWidth = hboxPaneWidth / Main.worldContinentsList.size();

        for (int i = 0; i < Main.worldContinentsList.size(); i++) {
            Continent curContinent = Main.worldContinentsList.get(i);

            ObservableList<String> datalist = FXCollections.observableArrayList();
            datalist.add(curContinent.getContinentName());
            datalist.add("");
            datalist.addAll(curContinent.getContinentCountryNameList());

            ListView<String> curListView = getRenderedListview(datalist, avgListviewWidth);
            mapListviews.add(curListView);
        }

        hbx_infoDisplayHbox.getChildren().addAll(mapListviews);
        isMapInfoOn = true;
    }

    public void clickNextToReinforcePhase(ActionEvent actionEvent) throws IOException {
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Pane reinforcePane = new FXMLLoader(getClass().getResource("../views/reinforceview.fxml")).load();
        Scene reinforceScene = new Scene(reinforcePane,1200,900);

        curStage.setScene(reinforceScene);

        curStage.show();

    }

    public void clickConfirmPlayerNum(ActionEvent actionEvent) {
        Main.totalNumOfPlayers = Integer.parseInt(txf_playerNumbers.getText());

        btn_reducePlayerNumber.setVisible(false);
        btn_plusPlayerNumber.setVisible(false);
        btn_confirmPlayerNum.setVisible(false);

        if (!btn_loadMap.isVisible()) {
            btn_infoSwitcher.setVisible(true);
            btn_infoSwitcher.setText("Map Info");
            displayPlayerInfo();
            btn_nextStep.setVisible(true);
        }

    }

    private void displayPlayerInfo() {
        isMapInfoOn = false;
        if (Main.playersList.isEmpty()) {
            PlayerInitialization.initPlayers();
        }
        txf_mapPromptInfo.setText("Players Info");

        hbx_infoDisplayHbox.getChildren().clear();

        ArrayList<ListView<String>> playerListViews = new ArrayList<>();

        double hboxPaneWidth = hbx_infoDisplayHbox.getWidth();
        double avgListviewWidth = hboxPaneWidth / Main.totalNumOfPlayers;

        for (int playerIndex = 0; playerIndex < Main.totalNumOfPlayers; playerIndex++) {
            Player curPlayer = Main.playersList.get(playerIndex);
            String displayedPlayerName = "Player : " + playerIndex;

            ObservableList<String> datalist = FXCollections.observableArrayList();
            datalist.add(displayedPlayerName);
            datalist.add("");

            ArrayList<String> curPlayerCountryNameList = curPlayer.getOwnedCountryNameList();

            datalist.addAll(curPlayerCountryNameList);

            ListView<String> curListView = getRenderedListview(datalist,avgListviewWidth);

            playerListViews.add(curListView);
        }
        hbx_infoDisplayHbox.getChildren().addAll(playerListViews);
    }

    private ListView<String> getRenderedListview(ObservableList<String> datalist, double avgListviewWidth) {
        ListView<String> result = new ListView<>();

        result.setItems(datalist);
        result.setPrefWidth(avgListviewWidth);

        result.setCellFactory(cell -> {
            return new ListCell<String>() {
                private Text text;

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item != null) {
                        text = new Text(item);
                        text.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
                        text.setWrappingWidth(avgListviewWidth - 10);
                        text.setTextAlignment(TextAlignment.CENTER);
                        if (getIndex() == 0) {
                            text.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                            setStyle("-fx-background-color: yellow");
                            text.setUnderline(true);
                        }
                        setGraphic(text);
                    }
                }
            };
        });
        return  result;
    }

    public void clickInfoDisplaySwitcher(ActionEvent actionEvent) throws IOException {
        if (isMapInfoOn) {
            btn_infoSwitcher.setText("Map Info");
            displayPlayerInfo();
        } else {
            btn_infoSwitcher.setText("Players Info");
            displayWorldMap();
        }
    }

    public void clickReset(ActionEvent actionEvent) throws IOException {
        resetStaticVariables();

        Stage startviewStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/startview.fxml"));
        Pane root = fxmlLoader.load();
        startviewStage.setScene(new Scene(root, 1200, 900));

        startviewStage.setResizable(false);
        startviewStage.show();
    }

    private void resetStaticVariables() {
        Main.totalNumOfPlayers = DEFAULT_NUM_OF_PLAYERS;
        Main.playersList = new ArrayList<>();
        Main.curRoundPlayerIndex = 0;
        Main.worldCountriesMap = new HashMap<>();
        Main.worldContinentsList = new ArrayList<>();
    }


}
