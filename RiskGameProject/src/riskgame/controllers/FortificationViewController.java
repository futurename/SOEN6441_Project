package riskgame.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.GraphNode;
import riskgame.model.BasicClass.ObserverPattern.CardExchangeViewObserver;
import riskgame.model.BasicClass.Player;
import riskgame.model.BasicClass.StrategyPattern.UtilMethods;
import riskgame.model.Utils.InfoRetriver;
import riskgame.model.Utils.ListviewRenderer;
import riskgame.model.Utils.SaveProgress;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import static riskgame.Main.*;
import static riskgame.controllers.StartViewController.firstRoundCounter;

/**
 * controller class for FortificationView.fxml
 *
 * @author WW
 * @since build1
 **/
public class FortificationViewController implements Initializable {

    /**
     * default minimum army number in a country
     */
    private static final int MIN_ARMY_NUMBER_IN_COUNTRY = 1;
    @FXML
    private Label lbl_maxArmyNumber;
    @FXML
    private Label lbl_deployArmyNumber;
    @FXML
    private ScrollBar scb_armyNbrAdjustment;
    @FXML
    private ListView<Country> lsv_ownedCountries;
    @FXML
    private ListView<Country> lsv_reachableCountry;
    @FXML
    private Button btn_confirmMoveArmy;
    @FXML
    private Button btn_nextStep;
    @FXML
    private Button btn_skipFortification;
    @FXML
    private Button btn_saveGame;
    @FXML
    private Label lbl_phaseViewName;
    @FXML
    private Label lbl_rechanble_countries;
    @FXML
    private Label lbl_countries;
    @FXML
    private Label lbl_playerName;
    @FXML
    private Label lbl_undeployArmyPrompt;
    @FXML
    private Label lbl_deployCountPrompt;
    @FXML
    private Label lbl_actionString;
    @FXML
    private PieChart pct_countryDomiChart;

    /**
     * fortification move counter
     */
    private int counter = 1;

    /**
     * current player in this phase
     */
    /**
     * index of current player
     */
    private int curPlayerIndex;

    /**
     * current game phase string
     */
    private String curGamePhase;

    /**
     * current player name for display
     */
    private String curPlayerName;

    /**
     * current action string for display
     */
    private String curActionString;

    /**
     * current player object
     */
    private Player curPlayer;
    private String defaultPath = "./";

    /**
     * warning alert used for notification
     */
    private Alert alert = new Alert(Alert.AlertType.WARNING);

    /**
     * init method for fortification phase view
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPhaseView();

        lsv_ownedCountries.setItems(InfoRetriver.getObservableCountryList(curPlayer));
        ListviewRenderer.renderCountryItems(lsv_ownedCountries);
        InfoRetriver.updatePiechart(pct_countryDomiChart);

        boolean isOneCountryCanFortificate = isExistFortificationCountry(lsv_ownedCountries.getItems());
        if (!isOneCountryCanFortificate) {
            btn_nextStep.setVisible(true);
            btn_confirmMoveArmy.setVisible(false);
            btn_skipFortification.setVisible(false);
        }
    }

    /**
     * validate whether exists a country which can fortificate to other countries.
     *
     * @param countryObservableList country object list
     * @return true for valid, false for none
     */
    private boolean isExistFortificationCountry(ObservableList<Country> countryObservableList) {
        boolean result = false;
        for (Country country : countryObservableList) {
            int armyNbr = country.getCountryArmyNumber();
            if (armyNbr > 1) {
                ObservableList<Country> reachableCountryList = InfoRetriver.getReachableCountryObservableList(curPlayer,
                        country.getCountryName());
                if (!reachableCountryList.isEmpty()) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * init phaseview observer pattern
     */
    private void initPhaseView() {
        initObserver();

        curPlayer = playersList.get(curPlayerIndex);
        Color curPlayerColor = curPlayer.getPlayerColor();

        lbl_phaseViewName.setText(curGamePhase);
        lbl_phaseViewName.setTextFill(curPlayerColor);
        lbl_playerName.setText(curPlayerName);
        lbl_playerName.setTextFill(curPlayerColor);
        lbl_actionString.setText(curActionString);
        lbl_actionString.setWrapText(true);

        lbl_countries.setTextFill(curPlayerColor);
        lbl_rechanble_countries.setTextFill(curPlayerColor);
    }

    /**
     * init phaseview observer value
     */
    private void initObserver() {
        curGamePhase = phaseViewObserver.getPhaseName();
        curPlayerIndex = phaseViewObserver.getPlayerIndex();
        curActionString = phaseViewObservable.getActionString();
        curPlayerName = "Player_" + curPlayerIndex;
    }

    /**
     * set ownedcountry and adjacentcountry ListViews uncontrollable.
     */
    private void setListViewsTransparent() {
        lsv_ownedCountries.setMouseTransparent(true);
        lsv_ownedCountries.setFocusTraversable(false);
        lsv_reachableCountry.setMouseTransparent(true);
        lsv_reachableCountry.setFocusTraversable(false);
    }

    /**
     * onClick event when a country item in the ListView is selected, display its adjacent country list in adjacent ListView
     *
     * @param mouseEvent a country item is selected
     */
    @FXML
    public void selectOneCountry(MouseEvent mouseEvent) {
        if (counter == 0) {
            btn_skipFortification.setVisible(false);
            btn_confirmMoveArmy.setVisible(false);
            btn_nextStep.setVisible(true);
            setListViewsTransparent();
        } else {
            int selectedCountryIndex = lsv_ownedCountries.getSelectionModel().getSelectedIndex();
            String selectedCountryName = curPlayer.getOwnedCountryNameList().get(selectedCountryIndex);
            Country selectedCountry = lsv_ownedCountries.getSelectionModel().getSelectedItem();

            System.out.println("selected country name: " + selectedCountryName + ", army: " + selectedCountry.getCountryArmyNumber());

            if (selectedCountry.getCountryArmyNumber() <= MIN_ARMY_NUMBER_IN_COUNTRY) {
                btn_confirmMoveArmy.setVisible(false);
                alert.setContentText("No enough army for fortification!");
                alert.showAndWait();
            } else {
                ObservableList<Country> reachableCountryList = InfoRetriver.getReachableCountryObservableList(curPlayer,
                        selectedCountryName);
                if (reachableCountryList.isEmpty()) {
                    btn_confirmMoveArmy.setVisible(false);
                    lsv_reachableCountry.setItems(null);
                    lbl_deployArmyNumber.setText("0");
                    lbl_maxArmyNumber.setText("0");
                } else {
                    btn_nextStep.setVisible(false);
                    btn_confirmMoveArmy.setVisible(true);
                    btn_skipFortification.setVisible(true);
                    lsv_reachableCountry.setItems(reachableCountryList);
                    ListviewRenderer.renderReachableCountryItems(lsv_reachableCountry);
                    updateDeploymentInfo(selectedCountry);
                    scb_armyNbrAdjustment.valueProperty()
                            .addListener((observable, oldValue, newValue) -> lbl_deployArmyNumber.setText(Integer.toString(newValue.intValue())));
                }
            }
        }
    }

    /**
     * get count of countries whose army number is greate than one
     *
     * @return true for two or monre countries, false for less than two countries.
     */
    private boolean getCountOfValidCountries() {
        ObservableList<Country> countryObservableList = lsv_ownedCountries.getItems();
        int count = 0;
        for (Country country : countryObservableList) {
            if (country.getCountryArmyNumber() > MIN_ARMY_NUMBER_IN_COUNTRY) {
                count++;
            }
        }
        return count > 1;
    }

    /**
     * check whether reachable country lists from all owned countries are empty
     *
     * @param curPlayer current player
     * @return true for all list empty, false for existing reachable country list
     */
    private boolean isAllReachableCountryEmpty(Player curPlayer) {
        boolean result = true;
        ArrayList<String> ownedCountryList = curPlayer.getOwnedCountryNameList();
        int playerIndex = curPlayer.getPlayerIndex();
        for (String countryName : ownedCountryList) {
            GraphNode curGraphNode = graphSingleton.get(countryName);
            Country curCountry = curGraphNode.getCountry();
            ArrayList<Country> tempCountryList = new ArrayList<>();
            curGraphNode.getReachableCountryListBFS(curPlayer, curCountry, tempCountryList);
            if (!tempCountryList.isEmpty()) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * onClick event for moving to attack phase view.
     *
     * @param actionEvent button is clicked
     * @throws IOException AttackView.fxml is not found
     */
    @FXML
    public void clickNextStep(ActionEvent actionEvent) throws IOException {
        Stage curStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        System.out.println("\n\nfirst round counter: " + firstRoundCounter + "\n\n");

//        if (firstRoundCounter > 0) {
//            firstRoundCounter--;
//            if (firstRoundCounter == 0) {
//                curRoundPlayerIndex = -1;
//            }
//            UtilMethods.notifyFortificationEnd(true, curPlayer);
//        } else {
//            UtilMethods.notifyFortificationEnd(false, curPlayer);
//        }
//        UtilMethods.callNextRobotPhase();

        UtilMethods.endFortification(curPlayer);
        Scene scene = UtilMethods.startView(phaseViewObserver.getPhaseName(), this);
        curStage.setScene(scene);
        curStage.show();
    }

    /**
     * onClick event for confirming moving army from one country to another owned country
     *
     * @param actionEvent button is clicked
     */
    public void clickConfirmMoveArmy(ActionEvent actionEvent) {
        counter--;

        ObservableList<Country> countryObservableList = lsv_ownedCountries.getItems();
        ObservableList<Country> adjacentCountryObservableList = lsv_reachableCountry.getItems();

        int selectedOwnedCountryIndex = lsv_ownedCountries.getSelectionModel().getSelectedIndex();
        int selectedReachableCountryIndex = lsv_reachableCountry.getSelectionModel().getSelectedIndex();

        if (selectedOwnedCountryIndex == -1 && selectedReachableCountryIndex == -1) {
            alert.setContentText("Both source and target countries are selected!");
            alert.showAndWait();
        } else if (selectedOwnedCountryIndex == -1) {
            alert.setContentText("Please select a country to be fortified!");
            alert.showAndWait();
        } else if (selectedReachableCountryIndex == -1) {
            alert.setContentText("Please select a target country for fortification!");
            alert.showAndWait();
        } else {
            String selectedCountryName = countryObservableList.get(selectedOwnedCountryIndex).getCountryName();
            Country selectedCountry = graphSingleton.get(selectedCountryName).getCountry();

            String selectedTargetCountryName = adjacentCountryObservableList.get(selectedReachableCountryIndex).getCountryName();
            Country selectedTargetCountry = graphSingleton.get(selectedTargetCountryName).getCountry();

            System.out.println("before move, selected: " + selectedCountryName + ": " + selectedCountry.getCountryArmyNumber() + ", target: "
                    + selectedTargetCountryName + ": " + selectedTargetCountry.getCountryArmyNumber());

            int deployArmyNumber = Integer.parseInt(lbl_deployArmyNumber.getText());

            System.out.println("deploy number: " + deployArmyNumber);

            curPlayer.executeFortification(selectedCountry, selectedTargetCountry, deployArmyNumber);
//            selectedCountry.reduceFromCountryArmyNumber(deployArmyNumber);
//            selectedTargetCountry.addToCountryArmyNumber(deployArmyNumber);

            System.out.println("after move, selected: " + selectedCountryName + ": " + selectedCountry.getCountryArmyNumber() + ", target: "
                    + selectedTargetCountryName + ": " + selectedTargetCountry.getCountryArmyNumber());

            lsv_ownedCountries.refresh();
            lsv_reachableCountry.refresh();
            updateDeploymentInfo(selectedCountry);
            setUIControllers();
        }
    }

    /**
     * set fortification realted UI controllers invisible and visibalize Next button
     */
    private void setUIControllers() {
        btn_confirmMoveArmy.setVisible(false);
        btn_skipFortification.setVisible(false);
        btn_nextStep.setVisible(true);
        scb_armyNbrAdjustment.setVisible(false);

    }

    /**
     * calculate modification of army numbers and update undeployment data
     *
     * @param selectedCountry selected country object
     */
    private void updateDeploymentInfo(Country selectedCountry) {
        int curUndeployedArmy = selectedCountry.getCountryArmyNumber() - MIN_ARMY_NUMBER_IN_COUNTRY;
        scb_armyNbrAdjustment.setMax(curUndeployedArmy);
        scb_armyNbrAdjustment.setMin(MIN_ARMY_NUMBER_IN_COUNTRY);
        scb_armyNbrAdjustment.adjustValue(curUndeployedArmy);
        lbl_maxArmyNumber.setText(Integer.toString(curUndeployedArmy));
        int minNumber = curUndeployedArmy >= MIN_ARMY_NUMBER_IN_COUNTRY ? curUndeployedArmy : 0;
        lbl_deployArmyNumber.setText(String.valueOf(minNumber));
    }

    /**
     * onClick event for skipping fortification phase this round
     *
     * @param actionEvent skip button is pressed
     */
    public void clickSkipFortification(ActionEvent actionEvent) {
        btn_confirmMoveArmy.setVisible(false);
        btn_skipFortification.setVisible(false);
        btn_nextStep.setVisible(true);
    }

    /**
     * save game
     * @param actionEvent
     */
    public void clickSaveGame(ActionEvent actionEvent) {
        Stage fileStage = null;
        String filePath = "";
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select map file");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.dir")));

        File file = directoryChooser.showDialog(fileStage);
        if(file.getPath()!=null) {
            filePath = file.getPath();
        }
        else{
            filePath = defaultPath;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String fileNameCurTime = dateFormat.format(new Date());
        System.out.println(fileNameCurTime);
        SaveProgress saveProgress = new SaveProgress();

        CardExchangeViewObserver cardExchangeViewObserver = new CardExchangeViewObserver();
        phaseViewObservable.addObserver(cardExchangeViewObserver);
        phaseViewObservable.initObservableExchangeTime();
        phaseViewObservable.notifyObservers();
        try {
            saveProgress.SaveFile("Attack",curPlayer.getPlayerIndex(),filePath,fileNameCurTime,btn_confirmMoveArmy.isVisible(),true,cardExchangeViewObserver.getExchangeTime(),-1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
