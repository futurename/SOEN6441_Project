package riskgame.model.BasicClass.ObserverPattern;

import java.util.Observable;

/**
 * created on 2019/03/08_23:51
 * @author WW
 * @since build2
 **/
public class PlayerDomiViewObservable extends Observable {

    public void updateObservable(){

        System.out.println("\nPlayerDomiObservable updates!\n");

        setChanged();
    }


}
