package riskgame.model;

import riskgame.Main;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * model class that includes methods for player initialization
 *
 * @author WW
 */
public class PlayerInitialization {

    /**
     * create and initialize all the player instances
     */
    public static void initPlayers() {
        ArrayList<String> forAllocatesCountryNameList = buildAllocatesCountryNameList();

        for (int playerIndex = 0; playerIndex < Main.totalNumOfPlayers; playerIndex++) {
            Player onePlayer = new Player(playerIndex);
            getInitCountryNameList(onePlayer, forAllocatesCountryNameList);

            //System.out.println("player index: " + playerIndex + ", total:" + Main.totalNumOfPlayers);
        }
    }

    /**
     * @return an arraylist of all country names
     */
    private static ArrayList<String> buildAllocatesCountryNameList() {
        ArrayList<String> result = new ArrayList<>();
        for (Map.Entry<String, Country> entry : Main.worldCountriesMap.entrySet()) {
            String oneCountryName = entry.getKey();
            result.add(oneCountryName);
        }
        return result;
    }

    /**
     * @method randomly allocates all countries to all players
     * @param curPlayer a player instance
     * @param coutryNameList the arraylist of its country names
     */
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
