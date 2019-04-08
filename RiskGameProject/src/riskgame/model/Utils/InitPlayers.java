package riskgame.model.Utils;

import riskgame.model.BasicClass.Continent;
import riskgame.model.BasicClass.GraphNode;
import riskgame.model.BasicClass.Player;
import riskgame.model.BasicClass.StrategyPattern.Strategy;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * This class includes methods for initlizing all players and allocating countries to each player randomly
 *
 * @author WW
 **/
public class InitPlayers {
    /**
     * create and initialize all the player instances
     *
     * @param numOfPlayers  number of players this round
     * @param worldHashMap world map
     */
    public static void initPlayers(int numOfPlayers, LinkedHashMap<String, GraphNode> worldHashMap,
                                   LinkedHashMap<String, Continent> continentMapInstance, ArrayList<Strategy> strategyList, ArrayList<Player> playerArrayList) {

        System.out.println("strategy list: " + strategyList + ", playerlist: " + playerArrayList);


        ArrayList<String> forAllocatesCountryNameList = generateUnallocatedNameList(worldHashMap);

        for (int playerIndex = 0; playerIndex < numOfPlayers; playerIndex++) {
            Strategy curStrategy = strategyList.get(playerIndex);
            Player onePlayer = new Player(playerIndex, curStrategy, worldHashMap, continentMapInstance);
            getInitCountryNameList(onePlayer, forAllocatesCountryNameList, numOfPlayers, worldHashMap, playerArrayList);
            onePlayer.updateArmyNbr();

            System.out.println("init player: " + playerIndex + ", army nbr: " + onePlayer.getArmyNbr());
        }
    }

    /**
     * This method allocates countries to the player by record
     *
     * @param curPlayer      current player
     * @param countryNameList unallocated country names
     * @param graphSingleton world map singleton
     */
    private static void setCountryNameList(Player curPlayer, ArrayList<String> countryNameList, LinkedHashMap<String,
            GraphNode> graphSingleton, ArrayList<Player> playerArrayList) {

        for(int i=0; i<countryNameList.size();i++) {
            String oneCountryName = countryNameList.remove(i);
            graphSingleton.get(oneCountryName).getCountry().setCountryOwner(curPlayer);
            graphSingleton.get(oneCountryName).getCountry().addObserver(curPlayer);
            curPlayer.addToOwnedCountryNameList(oneCountryName);
            playerArrayList.add(curPlayer);
        }
    }

    /**
     * this method acquires all unallocated country names
     *
     * @param worldHashMap
     * @return an arraylist of all country names
     */
    private static ArrayList<String> generateUnallocatedNameList(LinkedHashMap<String, GraphNode> worldHashMap) {
        ArrayList<String> result = new ArrayList<>();
        for (Map.Entry<String, GraphNode> entry : worldHashMap.entrySet()) {
            GraphNode curNode = entry.getValue();
            String curCountryName = entry.getKey();
            result.add(curCountryName);
        }
        return result;
    }

    /**
     * This method allocates all countries to a player randomly
     *
     * @param curPlayer      current player
     * @param countryNameList unallocated country names
     * @param numOfPlayers   number of players
     * @param graphSingleton world map singleton
     */
    private static void getInitCountryNameList(Player curPlayer, ArrayList<String> countryNameList, int numOfPlayers, LinkedHashMap<String,
            GraphNode> graphSingleton, ArrayList<Player> playerArrayList) {

        int avgCountryCount = graphSingleton.size() / numOfPlayers;
        int allocatsCountryNum = (curPlayer.getPlayerIndex() != (numOfPlayers - 1)) ? avgCountryCount : countryNameList.size();

        for (int count = 0; count < allocatsCountryNum; count++) {
            int randomIndex = new Random().nextInt(countryNameList.size());

            String oneCountryName = countryNameList.remove(randomIndex);

            graphSingleton.get(oneCountryName).getCountry().setCountryOwner(curPlayer);
            graphSingleton.get(oneCountryName).getCountry().addObserver(curPlayer);
            curPlayer.addToOwnedCountryNameList(oneCountryName);

        }
        playerArrayList.add(curPlayer);
    }

}
