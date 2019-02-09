package mapeditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MEMain extends Application {

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
