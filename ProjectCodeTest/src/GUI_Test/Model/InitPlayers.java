package GUI_Test.Model;

import GUI_Test.Class.Player;

import java.util.ArrayList;

public class InitPlayers {

    public static ArrayList<Player> GenPlayers(int numOfPlayers){
        ArrayList<Player> result = new ArrayList<>();

        for(int i = 0; i < numOfPlayers; i++){
            Player onePlayer = new Player(i);
            result.add(onePlayer);

            //System.out.println("create one player, has countries: " + onePlayer.getCountries().size() + " " + onePlayer.getCountries().get(0)
            // .getCountryname());
        }

        return result;
    }
}
