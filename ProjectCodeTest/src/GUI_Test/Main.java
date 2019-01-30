package GUI_Test;

import GUI_Test.Class.Continent;
import GUI_Test.Class.Country;
import GUI_Test.Class.Player;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {
    public static int totalNumOfPlayers;
    public static int playerSeqCounter = 0;
    public static int rounds = 3;
    public static ArrayList<Player> playerList;


    public static HashMap<String, Country> worldMap = new HashMap<>();
    public static ArrayList<Continent> continentList = new ArrayList<>();



    @Override
    public void start(Stage primaryStage) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/GUI_Test/View/InitView.fxml"));
            Scene scene = new Scene(root, 800, 400);


            primaryStage.setScene(scene);
            primaryStage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        launch(args);


    }
}
