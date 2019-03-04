package riskgame.model.phases;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import riskgame.Main;
import riskgame.model.BasicClass.Continent;
import riskgame.model.BasicClass.Player;
import riskgame.model.Utils.InfoRetriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class includes methods which are used for reinforce phase
 *
 * @author WW
 **/
public class ReinforcePhase {
    /**
     * default factor for calculting standard number of army used for reinforce phase
     */
    private static final int DEFAULT_DIVISION_FACTOR = 3;

    /**
     * minimun army number that is assigned to each player
     */
    private static final int DEFAULT_MIN_REINFORCE_ARMY_NBR = 3;

    /**
     * calculate army number for reinforcement with default value
     *
     * @param countryNum number of all countries a player owns
     * @return calculated number of army for reinforcement phase
     */
    public static int getStandardReinforceArmyNum(int countryNum) {
        int calResult = countryNum / DEFAULT_DIVISION_FACTOR;
        return calResult > DEFAULT_MIN_REINFORCE_ARMY_NBR ? calResult : DEFAULT_MIN_REINFORCE_ARMY_NBR;
    }

    /**
     * acquire ObservableList for displaying in pie chart. The pie chart presents number of countries in different continents a play has.
     *
     * @param player a player instance
     * @return distribution of (continent, number of country) this player owns
     */
    public static ObservableList<PieChart.Data> getPieChartData(Player player) {
        ObservableList<PieChart.Data> result = FXCollections.observableArrayList();

        ArrayList<String> countryList = player.getOwnedCountryNameList();

        HashMap<String, Integer> countryDistributionMap = InfoRetriver.getCountryDistributionMap(countryList);

        for (Map.Entry<String, Integer> entry : countryDistributionMap.entrySet()) {
            String oneCountryName = entry.getKey();
            int count = entry.getValue();
            PieChart.Data onePieChartData = new PieChart.Data(oneCountryName, count);
            result.add(onePieChartData);

            System.out.println("country name: " + oneCountryName + ", curCount: " + count);
        }
        return result;
    }

}


