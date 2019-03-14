package riskgame.model.BasicClass.ObserverPattern;

import riskgame.Main;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.InfoRetriver;

import java.util.ArrayList;
import java.util.Observable;

/**
 * created on 2019/03/08_23:51
 **/

public class PlayerDomiViewObservable extends Observable {
    private ArrayList<Float> controlRatioList;
    private ArrayList<Integer> controlledContinentNbrList;
    private ArrayList<Integer> totalArmyNbrList;

    public PlayerDomiViewObservable(){

        this.controlRatioList = new ArrayList<>();
        this.controlledContinentNbrList = new ArrayList<>();
        this.totalArmyNbrList = new ArrayList<>();
    }

    public ArrayList<Float> getControlRatioList() {
        return controlRatioList;
    }

    public ArrayList<Integer> getControlledContinentNbrList() {
        return controlledContinentNbrList;
    }

    public ArrayList<Integer> getTotalArmyNbrList() {
        return totalArmyNbrList;
    }

    public void updateObservable(){
        int totalCountryNbr = Main.graphSingleton.size();
        int totalPlayerNbr = Main.totalNumOfPlayers;

        for(int playerIndex = 0; playerIndex < totalPlayerNbr; playerIndex++){
            Player curPlayer = Main.playersList.get(playerIndex);
            int ownedCountryNbr = curPlayer.getOwnedCountryNameList().size();
            float curControlRatio = (float)ownedCountryNbr / totalCountryNbr;
            controlRatioList.add(curControlRatio);

            int curControlContinentNbr = InfoRetriver.getConqueredContinentNbr(curPlayer);
            controlledContinentNbrList.add(curControlContinentNbr);

            int curArmyNbr = curPlayer.getArmyNbr();
            totalArmyNbrList.add(curArmyNbr);
        }
        setChanged();
    }



    public void resetObservable(){
        this.controlRatioList = new ArrayList<>();
        this.controlledContinentNbrList = new ArrayList<>();
        this.totalArmyNbrList = new ArrayList<>();
    }

}
