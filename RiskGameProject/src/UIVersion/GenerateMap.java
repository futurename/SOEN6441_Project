package UIVersion;

import UIVersion.Controller.MapGUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wei Wang
 * @version 1.0
 * @since 2019/04/12
 **/

public class GenerateMap extends Application {
    private static final String MAP_PATH = "./maps/World.map";
    private static final String TERRITORIES_STRING = "Territories";
    private static HashMap<String, Country> worldMap = new HashMap<>();

    private static void createGUI(HashMap<String,Country> worldMap) throws IOException {

        Stage stage =new Stage();
        FXMLLoader loader = new FXMLLoader(GenerateMap.class.getResource("./view/MapGUI.fxml"));
        MapGUIController controller = new MapGUIController();

        controller.setWorldMap(worldMap);
        loader.setController(controller);
        Pane pane = loader.load();
        Scene scene = new Scene(pane,1200,900);
        stage.setScene(scene);
        stage.show();

    }

    private static void loadMapfile(String mapPath) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(mapPath));
        String oneLine;
        while((oneLine = bufferedReader.readLine()) != null){
            if(oneLine.contains(TERRITORIES_STRING)){
                while(((oneLine = bufferedReader.readLine()) != null)){
                    if(!oneLine.equals("")) {
                        String[] splitStrings = oneLine.split(",");
                        String countryName = splitStrings[0];
                        double coordinateX = Integer.parseInt(splitStrings[1]) * 1.4 - 60;
                        double coordinateY = Integer.parseInt(splitStrings[2]) * 2.4 - 160;
                        ArrayList<String> neighbourList = new ArrayList<>();
                        for (int i = 4; i < splitStrings.length; i++) {
                            neighbourList.add(splitStrings[i]);
                        }
                        Country oneCountry = new Country(countryName, coordinateX, coordinateY, neighbourList);
                        worldMap.put(countryName, oneCountry);
                    }
                }

                printWorldMap();

                break;
            }
        }
    }

    private static void printWorldMap() {
        for(Map.Entry<String,Country> entry: worldMap.entrySet()){
            Country country = entry.getValue();
            System.out.println(country);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        loadMapfile(MAP_PATH);
        createGUI(worldMap);
    }
}
