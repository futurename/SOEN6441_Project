package riskgame.model.BasicClass.ObserverPattern;

import riskgame.Main;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.InfoRetriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;

/**
 * Observer class for player domination view
 * created on 2019/03/08_23:53
 * @author WW
 * @since build2
 **/
public class PlayerDomiViewObserver implements Observer {
    private ArrayList<Integer> controlledCountryNbrList;
    private ArrayList<Double> controlRatioList;
    private ArrayList<Integer> controlledContinentNbrList;
    private ArrayList<Integer> totalArmyNbrList;
    private ArrayList<Integer> continentBonusList;

    public ArrayList<Integer> getControlledCountryNbrList() {
        return controlledCountryNbrList;
    }

    public ArrayList<Double> getControlRatioList() {
        return controlRatioList;
    }

    public ArrayList<Integer> getControlledContinentNbrList() {
        return controlledContinentNbrList;
    }

    public ArrayList<Integer> getTotalArmyNbrList() {
        return totalArmyNbrList;
    }

    public ArrayList<Integer> getContinentBonusList() {
        return continentBonusList;
    }

    @Override
    public void update(Observable o, Object arg) {
        int totalCountryNbr = Main.graphSingleton.size();
        int totalPlayerNbr = Main.totalNumOfPlayers;

        for(int playerIndex = 0; playerIndex < totalPlayerNbr; playerIndex++){
            Player curPlayer = Main.playersList.get(playerIndex);
            int ownedCountryNbr = curPlayer.getOwnedCountryNameList().size();

            System.out.printf("update Domi observable to: Own %d countries\n", ownedCountryNbr);

            int controlledCountryNbr = curPlayer.getOwnedCountryNameList().size();
            controlledCountryNbrList.set(playerIndex, controlledCountryNbr);

            double curControlRatio = (double)ownedCountryNbr / totalCountryNbr;
            controlRatioList.set(playerIndex, curControlRatio);

            int curControlContinentNbr = InfoRetriver.getConqueredContinentNbr(curPlayer);
            controlledContinentNbrList.set(playerIndex, curControlContinentNbr);

            curPlayer.updateArmyNbr();
            int curArmyNbr = curPlayer.getArmyNbr();
            totalArmyNbrList.set(playerIndex, curArmyNbr);

            continentBonusList.set(playerIndex, curPlayer.getContinentBonus());
        }


       /* this.controlRatioList = ((PlayerDomiViewObservable)o).getControlRatioList();
        this.controlledContinentNbrList = ((PlayerDomiViewObservable)o).getControlledContinentNbrList();
        this.totalArmyNbrList = ((PlayerDomiViewObservable)o).getTotalArmyNbrList();
        this.continentBonusList = ((PlayerDomiViewObservable)o).getContinentBonusList();
*/
        System.out.printf("PlayerDomiObserver updates: %s.\n",arg);
    }


    public void resetObservable(int playerCount){
        this.controlledCountryNbrList = new ArrayList(Collections.nCopies(playerCount,-1));
        this.controlRatioList = new ArrayList(Collections.nCopies(playerCount,-1.0));
        this.controlledContinentNbrList = new ArrayList<>(Collections.nCopies(playerCount,-1));
        this.totalArmyNbrList = new ArrayList<>(Collections.nCopies(playerCount,-1));
        this.continentBonusList = new ArrayList<>(Collections.nCopies(playerCount,-1));
    }
}
