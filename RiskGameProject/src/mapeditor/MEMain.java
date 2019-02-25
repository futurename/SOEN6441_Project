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
    public static String OLDMAPPATH;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = FXMLLoader.load(getClass().getResource("views/MapEditorHomePageView.fxml"));


        //Pane root = FXMLLoader.load(getClass().getResource("view/demoview.fxml"));
        primaryStage.setTitle("Risk Game Map Editor");
        primaryStage.setScene(new Scene(root, 1200, 900));

        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void createContinent(String continentName,int bonus){
        MEContinent meContinent = new MEContinent();
        meContinent.setContinentName(continentName);
        meContinent.setBonus(bonus);
        arrMEContinent.add(meContinent);
    }

    public static void createCountry(String countryName,String[] neighbor){
        MECountry meCountry = new MECountry();
        meCountry.setCountryName(countryName);
        for(int i=3;i<neighbor.length;i++){
            meCountry.setNeighbor(neighbor[i]);
        }
        arrMECountry.add(meCountry);
    }

    public static void deleteContinent(String continentName){
        for(int i = 0;i<arrMEContinent.size();i++){
            if(continentName.equals(arrMEContinent.get(i).getContinentName())){
                arrMEContinent.remove(i);
                break;
            }
        }
    }

    public static void main(String[] agrs) throws Exception{
        launch(agrs);
    }


}
