package riskgame.model.BasicClass.Observer;

import riskgame.Main;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.Player;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class CountryChangedObserver implements Observer {
    int playerIndex;

    public CountryChangedObserver(int playerIndex){
        this.playerIndex = playerIndex;
    }

    @Override
    public void update(Observable o, Object countryName) {
        Player curPlayer = Main.playersList.get(playerIndex);
        Country curCountry = Main.graphSingleton.get(countryName).getCountry();
        ArrayList<String> ownedCountryNameList = curPlayer.getOwnedCountryNameList();

        if(ownedCountryNameList.contains(countryName)){
            if(curCountry.getCountryOwnerIndex() != curPlayer.getPlayerIndex()){
                curPlayer.getOwnedCountryNameList().remove(countryName);
            }
        }else{
            curPlayer.getOwnedCountryNameList().add((String) countryName);
        }
    }
}
