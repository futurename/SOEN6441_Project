package UIVersion.Controller;

import UIVersion.Country;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Wei Wang
 * @version 1.0
 * @since 2019/04/12
 **/

public class MapGUIController implements Initializable {
    @FXML
    private AnchorPane acp_map;
    @FXML
    private StackPane stkp_map;

    private HashMap<String, Country> worldMap;

    private void scanMapAndGenerateUI(HashMap<String, Country> worldMap) {

        for (Map.Entry<String, Country> entry : worldMap.entrySet()) {
            Country country = entry.getValue();
            String countryName = country.getCountryName();
            double coordinateX = country.getCoordinateX();
            double coordinateY = country.getCoordinateY();
            ArrayList<String> neighbourList = country.getNeighborCountryList();

            System.out.println("\nget neighbour list: " + neighbourList);

            Button button = new Button(countryName);
            button.setLayoutX(coordinateX- button.getWidth());
            button.setLayoutY(coordinateY - button.getHeight());
            acp_map.getChildren().add(button);

            for (String neighbourName : neighbourList) {
                Country oneNeighbour = worldMap.get(neighbourName);

                System.out.println("\nread one neighbour:" + neighbourName + ", " + oneNeighbour);

                double neighbourCoordinateX = oneNeighbour.getCoordinateX();
                double neighbourCoordinateY = oneNeighbour.getCoordinateY();
                Line line = new Line(coordinateX, coordinateY, neighbourCoordinateX, neighbourCoordinateY);
                acp_map.getChildren().add(line);
            }
        }
    }

    public void setWorldMap(HashMap<String, Country> worldMap) {
        this.worldMap = worldMap;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scanMapAndGenerateUI(worldMap);
    }
}
