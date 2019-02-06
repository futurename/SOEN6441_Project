package riskgame.model;

import riskgame.Main;
import riskgame.classes.Country;
import riskgame.classes.Player;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class PlayerInitialization {

    public static void initPlayers() {
        ArrayList<String> forAllocatesCountryNameList = buildAllocatesCountryNameList();

        for (int playerIndex = 0; playerIndex < Main.totalNumOfPlayers; playerIndex++) {
            Player onePlayer = new Player(playerIndex);
            getInitCountryNameList(onePlayer, forAllocatesCountryNameList);

            //System.out.println("player index: " + playerIndex + ", total:" + Main.totalNumOfPlayers);
        }
    }

    private static ArrayList<String> buildAllocatesCountryNameList() {
        ArrayList<String> result = new ArrayList<>();
        for (Map.Entry<String, Country> entry : Main.worldCountriesMap.entrySet()) {
            String oneCountryName = entry.getKey();
            result.add(oneCountryName);
        }
        return result;
    }

    private static void getInitCountryNameList(Player curPlayer, ArrayList<String> coutryNameList) {

        int avgCountryCount = Main.worldCountriesMap.size() / Main.totalNumOfPlayers;
        int allocatsCountryNum = (curPlayer.getPlayerIndex() != (Main.totalNumOfPlayers - 1)) ? avgCountryCount : coutryNameList.size();

        for (int count = 0; count < allocatsCountryNum; count++) {
            int randomIndex = new Random().nextInt(coutryNameList.size());

            System.out.println("random index: " + randomIndex + ", list size: " + coutryNameList.size() + ", player index: " + curPlayer.getPlayerIndex());

            String oneCountryName = coutryNameList.remove(randomIndex);

            Main.worldCountriesMap.get(oneCountryName).setCountryOwnerIndex(curPlayer.getPlayerIndex());

            curPlayer.addToOwnedCountryNameList(oneCountryName);
        }
        Main.playersList.add(curPlayer);
    }
}
