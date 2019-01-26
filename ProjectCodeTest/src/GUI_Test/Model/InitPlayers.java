package GUI_Test.Model;

import GUI_Test.Class.Country;
import GUI_Test.Class.Player;
import GUI_Test.Main;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class InitPlayers {
    public static ArrayList<String> allCountriesList;

    public static ArrayList<Player> GenPlayers(int numOfPlayers){
        ArrayList<Player> result = new ArrayList<>();

        getAllCountryNameArray();

        for(int i = 0; i < numOfPlayers; i++){
            Player onePlayer = new Player(i);

            ArrayList<String> countries = getInitPlayerCountries();
            onePlayer.setCountries(countries);

            result.add(onePlayer);

            //System.out.println("create one player, has countries: " + onePlayer.getCountries().size() + " " + onePlayer.getCountries().get(0)
            // .getCountryname());
        }


        return result;
    }

    private static ArrayList<String> getInitPlayerCountries() {
        ArrayList<String>  result = new ArrayList<>();


        System.out.println("size: " + allCountriesList.size());

        int totalCountryNum = Main.worldMap.size();
        int avgCountryNumForPlayer = totalCountryNum / Main.totalNumOfPlayers;

        System.out.println("avg countries: " + avgCountryNumForPlayer);

        if(allCountriesList.size() < avgCountryNumForPlayer + Main.totalNumOfPlayers){

            System.out.println("size: " + allCountriesList.size());

            result = allCountriesList ;
        }else{
            for(int i = 0; i < avgCountryNumForPlayer; i++){
                int randomNum = new Random().nextInt(allCountriesList.size());
                result.add(allCountriesList.get(randomNum));
                allCountriesList.remove(randomNum);

                System.out.println("size: " + allCountriesList.size());
            }
        }

        System.out.println(result);

        return result;

    }

    private static void getAllCountryNameArray(){
        allCountriesList = new ArrayList<>();

        for(Map.Entry<String, Country> entry : Main.worldMap.entrySet()){
            allCountriesList.add(entry.getKey());
        }

        System.out.println(allCountriesList);

    }


}
