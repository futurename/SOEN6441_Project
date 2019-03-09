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

    public void setControlRatioList(ArrayList<Float> controlRatioList) {
        this.controlRatioList = controlRatioList;
    }

    public ArrayList<Integer> getControlledContinentNbrList() {
        return controlledContinentNbrList;
    }

    public void setControlledContinentNbrList(ArrayList<Integer> controlledContinentNbrList) {
        this.controlledContinentNbrList = controlledContinentNbrList;
    }

    public ArrayList<Integer> getTotalArmyNbrList() {
        return totalArmyNbrList;
    }

    public void setTotalArmyNbrList(ArrayList<Integer> totalArmyNbrList) {
        this.totalArmyNbrList = totalArmyNbrList;
    }

    @Override
    public void update(Observable o, Object arg) {
        this.controlRatioList = ((PlayerDomiViewObservable)o).getControlRatioList();
        this.controlledContinentNbrList = ((PlayerDomiViewObservable)o).getControlledContinentNbrList();
        this.totalArmyNbrList = ((PlayerDomiViewObservable)o).getTotalArmyNbrList();
    }
}
