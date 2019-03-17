package riskgame.model.BasicClass.ObserverPattern;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * created on 2019/03/08_23:53
 **/

public class PlayerDomiViewObserver implements Observer {
    private ArrayList<Float> controlRatioList;
    private ArrayList<Integer> controlledContinentNbrList;
    private ArrayList<Integer> totalArmyNbrList;

    public ArrayList<Float> getControlRatioList() {
        return controlRatioList;
    }

    public ArrayList<Integer> getControlledContinentNbrList() {
        return controlledContinentNbrList;
    }

    public ArrayList<Integer> getTotalArmyNbrList() {
        return totalArmyNbrList;
    }

    @Override
    public void update(Observable o, Object arg) {
        this.controlRatioList = ((PlayerDomiViewObservable)o).getControlRatioList();
        this.controlledContinentNbrList = ((PlayerDomiViewObservable)o).getControlledContinentNbrList();
        this.totalArmyNbrList = ((PlayerDomiViewObservable)o).getTotalArmyNbrList();
        System.out.printf("PlayerDomiObserver updates: %s.\n",arg);
    }
}
