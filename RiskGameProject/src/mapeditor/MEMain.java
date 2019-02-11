package mapeditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mapeditor.model.MEContinent;
import mapeditor.model.MECountry;

import java.util.ArrayList;

public class MEMain extends Application {

    public static ArrayList<MEContinent> arrMEContinent = new ArrayList<MEContinent>();
    public static ArrayList<MECountry> arrMECountry = new ArrayList<MECountry>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = FXMLLoader.load(getClass().getResource("views/MapEditorHomePageView.fxml"));


        //Pane root = FXMLLoader.load(getClass().getResource("views/demoview.fxml"));
        primaryStage.setTitle("Risk Game Map Editor");
        primaryStage.setScene(new Scene(root, 1200, 900));

        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] agrs) throws Exception{
        launch(agrs);
    }


}
