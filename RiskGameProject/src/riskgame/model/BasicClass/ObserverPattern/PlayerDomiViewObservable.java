package riskgame.model.BasicClass.ObserverPattern;

import java.util.Observable;

/**
 * created on 2019/03/08_23:51
 **/

public class PlayerDomiViewObservable extends Observable {
    private float controlRatio;
    private int controlledContinentNbr;
    private int totalArmyNbr;

    public float getControlRatio() {
        return controlRatio;
    }

    public void setControlRatio(float controlRatio) {
        this.controlRatio = controlRatio;
    }

    public int getControlledContinentNbr() {
        return controlledContinentNbr;
    }

    public void setControlledContinentNbr(int controlledContinentNbr) {
        this.controlledContinentNbr = controlledContinentNbr;
    }

    public int getTotalArmyNbr() {
        return totalArmyNbr;
    }

    public void setTotalArmyNbr(int totalArmyNbr) {
        this.totalArmyNbr = totalArmyNbr;
    }

    public void setAllParams(float controlRatio, int controlledContinentNbr, int totalArmyNbr){
        this.controlRatio = controlRatio;
        this.controlledContinentNbr = controlledContinentNbr;
        this.totalArmyNbr = totalArmyNbr;
        setChanged();
    }
}
