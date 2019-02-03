package riskgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import riskgame.classes.Country;
import riskgame.classes.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {
    public static HashMap<String, Country> worldCountriesMap;
    public static ArrayList<Player> playersList;
    public static int curRoundPlayerIndex;



    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/startview.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        //primaryStage.setFullScreen(true);
        primaryStage.setResizable(true);
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
