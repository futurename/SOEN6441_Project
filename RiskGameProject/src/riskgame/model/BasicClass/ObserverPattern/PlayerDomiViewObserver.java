package riskgame.model.BasicClass.ObserverPattern;

import java.util.Observable;
import java.util.Observer;

/**
 * created on 2019/03/08_23:53
 **/

public class PlayerDomiViewObserver implements Observer {
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

    @Override
    public void update(Observable o, Object arg) {
        this.controlRatio = ((PlayerDomiViewObservable)o).getControlRatio();
        this.controlledContinentNbr = ((PlayerDomiViewObservable)o).getControlledContinentNbr();
        this.totalArmyNbr = ((PlayerDomiViewObservable)o).getTotalArmyNbr();
    }
}
