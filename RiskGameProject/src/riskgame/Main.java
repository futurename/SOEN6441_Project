package riskgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import riskgame.classes.Continent;
import riskgame.classes.Country;
import riskgame.classes.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;



public class Main extends Application {
    public static HashMap<String, Country> worldCountriesMap = new HashMap<>();
    public static ArrayList<Player> playersList = new ArrayList<>();
    public static ArrayList<Continent> worldContinentsList = new ArrayList<>();
    public static int curRoundPlayerIndex  =0;
    public static int totalNumOfPlayers = -1;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = FXMLLoader.load(getClass().getResource("views/startview.fxml"));
        primaryStage.setTitle("Risk Game");
        primaryStage.setScene(new Scene(root, 1200, 900));

        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
