package mapeditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mapeditor.model.MEContinent;
import mapeditor.model.MECountry;

import java.util.ArrayList;

/**
 * @author Yunqiang Wei
 */
public class MEMain extends Application {

    public static ArrayList<MEContinent> arrMEContinent = new ArrayList<MEContinent>();
    public static ArrayList<MECountry> arrMECountry = new ArrayList<MECountry>();
    public static String OLDMAPPATH;
    public static boolean EDITPAGEFLAG = false;

    /**
     * start
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = FXMLLoader.load(getClass().getResource("views/MapEditorHomePageView.fxml"));


        //Pane root = FXMLLoader.load(getClass().getResource("view/demoview.fxml"));
        primaryStage.setTitle("Risk Game Map Editor");
        primaryStage.setScene(new Scene(root, 1200, 900));

        primaryStage.setResizable(false);
        primaryStage.show();
    }


    /**
     * this is the method of creating a continent
     * @param continentName the name of continent you want to create
     * @param bonus the continent bonus
     */
    public static void createContinent(String continentName,int bonus){
        MEContinent meContinent = new MEContinent();
        meContinent.setContinentName(continentName);
        meContinent.setBonus(bonus);
        arrMEContinent.add(meContinent);
    }

    /**
     * this is the method of creating a regular country
     * @param countryName the name of country you want to create
     * @param neighbor the neighbor country name
     */
    public static void createCountry(String countryName,String[] neighbor){
        MECountry meCountry = new MECountry();
        meCountry.setCountryName(countryName);
        for(int i=3;i<neighbor.length;i++){
            meCountry.setNeighbor(neighbor[i]);
        }
        arrMECountry.add(meCountry);
    }

    /**
     * this method is creating a country when it is the first country in a continent, which means it has no neighbor when creating it
     * @param soloCountryName the name of country you want to create
     */
    public static void createsSoloCountry(String soloCountryName){
        MECountry meCountry = new MECountry();
        meCountry.setCountryName(soloCountryName);
        arrMECountry.add(meCountry);
    }

    /**
     * this is the method of deleting continent
     * @param continentName the name of continent you want to delete
     */
    public static void deleteContinent(String continentName){
        String deletecontinentcountry;
        String[] deletecontinentcountryarr;
        for(int i = 0;i<arrMEContinent.size();i++){
            if(continentName.equals(arrMEContinent.get(i).getContinentName())){
                deletecontinentcountry = arrMEContinent.get(i).getCountryList();
                deletecontinentcountry = deletecontinentcountry.replaceAll("\\[","");
                deletecontinentcountry = deletecontinentcountry.replaceAll("\\]","");
                deletecontinentcountry = deletecontinentcountry.replaceAll(", ",",");
                deletecontinentcountryarr = deletecontinentcountry.split(",");
                for(int j= 0;j<deletecontinentcountryarr.length;j++){
                    deleteCountry(deletecontinentcountryarr[j]);
                }
                arrMEContinent.remove(i);
                break;
            }
        }
    }

    /**
     * this is the method of deleting country
     * @param countryName the name of country you want to delete
     */
    public static void deleteCountry(String countryName){
        for(int i = 0;i<arrMECountry.size();i++){
            if(arrMECountry.get(i).getCountryName().equals(countryName)){
                arrMECountry.remove(i);
                break;
            }

        }
        for(int j=0;j<arrMEContinent.size();j++){
            if(arrMEContinent.get(j).getCountryList().contains(countryName)) {
                arrMEContinent.get(j).deleteCountry(countryName);
                break;
            }

        }
        for(int k= 0;k<arrMECountry.size();k++){
            if(arrMECountry.get(k).getNeighbor().contains(countryName)){
                arrMECountry.get(k).deleteNeighbor(countryName);
            }
        }
    }

    /**
     * main method
     * @param agrs
     * @throws Exception
     */
    public static void main(String[] agrs) throws Exception{
        launch(agrs);
    }


}
