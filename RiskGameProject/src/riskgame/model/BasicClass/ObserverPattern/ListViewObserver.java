package riskgame.model.BasicClass.ObserverPattern;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import riskgame.model.BasicClass.Country;
import riskgame.model.Utils.ListviewRenderer;

import java.util.Observable;
import java.util.Observer;

/**
 * ListView observer will update its display information when country information has changes.
 * <p>
 * created on 2019/03/08_13:48
 **/

public class ListViewObserver implements Observer {

    /**
     * ListView for displaying country information
     */
    private ListView listView;

    /**
     * ObservableList for storing country list
     */
    private ObservableList<Country> observableList;

    /**
     * Constructor
     *
     * @param listView              predefined ListView in view page.
     * @param countryObservableList Observable country list
     */
    public ListViewObserver(ListView listView, ObservableList<Country> countryObservableList) {
        this.listView = listView;
        this.observableList = countryObservableList;
        listView.setItems(this.observableList);
        ListviewRenderer.renderCountryItems(listView);
    }

    /**
     * getter
     *
     * @return ListView object
     */
    public ListView getListView() {
        return listView;
    }

    /**
     * setter
     *
     * @param listView a ListView object used for setting
     */
    public void setListView(ListView listView) {
        this.listView = listView;
    }

    /**
     * getter
     *
     * @return current ObservableList object
     */
    public ObservableList<Country> getObservableList() {
        return observableList;
    }

    /**
     * setter
     *
     * @param observableList an ObservableList used for setting
     */
    public void setObservableList(ObservableList<Country> observableList) {
        this.observableList = observableList;
        this.listView.setItems(this.observableList);

    }

    @Override
    public void update(Observable o, Object arg) {
        listView.refresh();
    }
}
