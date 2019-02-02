package GUI_Test.Controller;

import java.io.IOException;
import java.util.ArrayList;

import GUI_Test.Class.Country;
import GUI_Test.Class.Player;
import GUI_Test.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class AttackViewController {

    private static boolean isDeploymentDone = false;

    @FXML
    public Label lb_PlayerNum;
    public TextArea ta_PlayerInfo;
    public ListView lv_PlayerCountryList;
    public ListView lv_AdjacentCountry;
    public Button bt_ConfirmDeploy;
    public Label lb_ReinforceArmyNum;

    private ObservableList<String> countriesList = FXCollections.observableArrayList();
    private ObservableList<String> adjacentCountryList = FXCollections.observableArrayList();

    public AttackViewController() {
        System.out.println("player seq: " + Main.playerSeqCounter);

    }

    public void initialize() {
        lb_PlayerNum.setText("Player: " + Main.playerSeqCounter);

        Player thisPlayer = Main.playerList.get(Main.playerSeqCounter);
        String outputText = thisPlayer.getPlayername() + "\n"
                + "Rounds left: " + Main.rounds + "\n"
                + "Occupied countries:" + "\n";

        ArrayList<String> countryList = thisPlayer.getCountries();
        for (int i = 0; i < countryList.size(); i++) {
            System.out.println("get country name: " + Main.worldMap.get(countryList.get(i)).getCountryname() + ", armies: " + Main.worldMap.get(countryList.get(i)).getArmies());
            outputText += Main.worldMap.get(countryList.get(i)).getCountryname() + " (" + Main.worldMap.get(countryList.get(i)).getArmies() + ")  ";
        }

        System.out.println(outputText);
        ta_PlayerInfo.setText(outputText);

        displayPlayerCountryList(countryList);
    }

    private void displayPlayerCountryList(ArrayList<String> list) {


        for (int i = 0; i < list.size(); i++) {
            Country curCountry = Main.worldMap.get(list.get(i));
            int armyNum = curCountry.getArmies();
            String curLine = list.get(i) + ": " + armyNum;

            countriesList.add(curLine);
        }
        lv_PlayerCountryList.setItems(countriesList);
        countriesList = FXCollections.observableArrayList();

    }

    // Event Listener on Button.onMouseClicked
    @FXML
    public void ClickConfirmAtttack(ActionEvent event) throws IOException {

        Parent fortificationViewParent = FXMLLoader.load(getClass().getResource("/GUI_Test/View/FortificationView.fxml"));
        Scene fortificationScene = new Scene(fortificationViewParent, 800, 400);

        Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        windowStage.setScene(fortificationScene);
        windowStage.setTitle("Player: " + Main.playerSeqCounter);
        //lb_PlayerNum.setText("Player: " + String.valueOf(Main.playerSeqCounter));
        windowStage.show();

        isDeploymentDone = false;

    }

    public void selectThisCountry(MouseEvent mouseEvent) {

        String curCountryNameTemp = lv_PlayerCountryList.getSelectionModel().getSelectedItem().toString();

        String[] tempArray = curCountryNameTemp.split(":");

        String curCountryName = tempArray[0];

        System.out.println("curcountrname: --->" + curCountryName);

        Country curCountry = Main.worldMap.get(curCountryName);
        ArrayList<String> curAdjacentCountryList = curCountry.getAdjacentCountries();

        for (int i = 0; i < curAdjacentCountryList.size(); i++) {
            String oneAdjacentCountryName = curAdjacentCountryList.get(i);

            Country oneAdjacentCountry = Main.worldMap.get(oneAdjacentCountryName);

            int armyNumOfAdjacentContry = oneAdjacentCountry.getArmies();
            String oneAdjacentCountryInfo = oneAdjacentCountry.getCountryname() + ":" + armyNumOfAdjacentContry;
            adjacentCountryList.add(oneAdjacentCountryInfo);
        }


        lv_AdjacentCountry.setItems(adjacentCountryList);

        //lv_AdjacentCountry.refresh();

        adjacentCountryList = FXCollections.observableArrayList();
    }

    public void ClickConfirmDeployButton(ActionEvent actionEvent) {

        int selectedCountryIndex = lv_PlayerCountryList.getSelectionModel().getSelectedIndex();

        if (selectedCountryIndex == -1) {
            Alert errorAlert = new Alert(Alert.AlertType.WARNING);
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Please select a country for army deployment");
            errorAlert.showAndWait();
        }else if(!isDeploymentDone){
            String tempString = lv_PlayerCountryList.getSelectionModel().getSelectedItem().toString();
            String[] tempStringArray = tempString.split(":");
            String selectedCountryName = tempStringArray[0];
            Country selectCountry = Main.worldMap.get(selectedCountryName);

            int reinforceArmy = Integer.parseInt(lb_ReinforceArmyNum.getText());
            selectCountry.addArmies(reinforceArmy);

            isDeploymentDone = true;

            Player thisPlayer = Main.playerList.get(Main.playerSeqCounter);
            ArrayList<String> thisPlayerCountryList = thisPlayer.getCountries();            

            for(int i = 0; i < thisPlayerCountryList.size(); i++){
                Country curAdjacentCountry = Main.worldMap.get(thisPlayerCountryList.get(i));
                int armyNum = curAdjacentCountry.getArmies();
                String curLine = curAdjacentCountry.getCountryname() + ": " + armyNum;
                countriesList.add(curLine);
            }
            lv_PlayerCountryList.setItems(countriesList);
            countriesList = FXCollections.observableArrayList();
        }


    }
}
