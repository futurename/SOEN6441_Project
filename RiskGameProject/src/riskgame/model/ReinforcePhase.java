package riskgame.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import riskgame.Main;
import riskgame.classes.Continent;
import riskgame.classes.Player;
;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ReinforcePhase {
    private static final int DEFAULT_DIVISION_FACTOR = 3;
    private static final int DEFAULT_MIN_REINFORCE_ARMY_NBR = 3;

    public static int getStandardReinforceArmyNum(int countryNum) {
        int calResult = countryNum / DEFAULT_DIVISION_FACTOR;
        return calResult > DEFAULT_MIN_REINFORCE_ARMY_NBR ? calResult : DEFAULT_MIN_REINFORCE_ARMY_NBR;
    }

    public static ObservableList<PieChart.Data> getPieChartData(Player player) {
        ObservableList<PieChart.Data> result = FXCollections.observableArrayList();

        ArrayList<String> countryList = player.getOwnedCountryNameList();

        HashMap<String, Integer> countryDistributionMap = PlayerInfoRetriver.getCountryDistributionMap(countryList);

        for (Map.Entry<String, Integer> entry : countryDistributionMap.entrySet()) {
            String oneCountryName = entry.getKey();
            int count = entry.getValue();
            PieChart.Data onePieChartData = new PieChart.Data(oneCountryName, count);
            result.add(onePieChartData);

            System.out.println("country name: " + oneCountryName + ", curCount: " + count);
        }
        return result;
    }

    public static ArrayList<String> getContinentNameList() {
        ArrayList<String> result = new ArrayList<>();
        for (Continent continent : Main.worldContinentsList) {
            String curContinentName = continent.getContinentName();
            result.add(curContinentName);
        }
        return result;
    }
}


