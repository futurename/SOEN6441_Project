package riskgame.model.BasicClass;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * @program: RiskGameProject
 * @description: Observer class for observing Country instances
 * @author: WW
 * @date: 2019-02-20
 **/

public class ListViewObserver implements Observer {
    private ArrayList<Country> countryList;

    public ListViewObserver(Player player) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
