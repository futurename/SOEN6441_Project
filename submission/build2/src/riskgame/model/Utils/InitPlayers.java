package riskgame.model.Utils;

import riskgame.Main;
import riskgame.model.BasicClass.GraphNode;
import riskgame.model.BasicClass.Player;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * This class includes methods for initlizing all players and allocating countries to each player randomly
 * @author WW
 **/
public class InitPlayers {
    /**
     * create and initialize all the player instances
     *
     * @param numOfPlayers number of players this round
     * @param graphSingleton world map singleton
     */
    public static void initPlayers(int numOfPlayers, LinkedHashMap<String, GraphNode> graphSingleton) {
        ArrayList<String> forAllocatesCountryNameList = generateUnallocatedNameList();

        for (int playerIndex = 0; playerIndex < numOfPlayers; playerIndex++) {
            Player onePlayer = new Player(playerIndex);
            getInitCountryNameList(onePlayer, forAllocatesCountryNameList, numOfPlayers, graphSingleton);
            onePlayer.updateArmyNbr();
//            initPlayerObservable(onePlayer);

            System.out.println("init player: " + playerIndex + ", army nbr: " + onePlayer.getArmyNbr());
        }
    }

    /**
     * this method acquires all unallocated country names
     *
     * @return an arraylist of all country names
     */
    private static ArrayList<String> generateUnallocatedNameList() {
        ArrayList<String> result = new ArrayList<>();
        for (Map.Entry<String, GraphNode> entry : Main.graphSingleton.entrySet()) {
            GraphNode curNode = entry.getValue();
            String curCountryName = entry.getKey();
            result.add(curCountryName);
        }
        return result;
    }

    /**
     * This method allocates all countries to a player randomly
     *
     * @param curPlayer current player
     * @param coutryNameList unallocated country names
     * @param numOfPlayers number of players
     * @param graphSingleton world map singleton
     */
    private static void getInitCountryNameList(Player curPlayer, ArrayList<String> coutryNameList, int numOfPlayers, LinkedHashMap<String,
            GraphNode> graphSingleton) {

        int avgCountryCount = graphSingleton.size() / numOfPlayers;
        int allocatsCountryNum = (curPlayer.getPlayerIndex() != (numOfPlayers - 1)) ? avgCountryCount : coutryNameList.size();

        for (int count = 0; count < allocatsCountryNum; count++) {
            int randomIndex = new Random().nextInt(coutryNameList.size());

            String oneCountryName = coutryNameList.remove(randomIndex);

            graphSingleton.get(oneCountryName).getCountry().setCountryOwnerIndex(curPlayer.getPlayerIndex());
            graphSingleton.get(oneCountryName).getCountry().addObserver(curPlayer);
            curPlayer.addToOwnedCountryNameList(oneCountryName);

        }
        Main.playersList.add(curPlayer);
    }

//    private static void initPlayerObservable(Player player){
//        player.addObserver(Main.cardExchangeViewObserver);
//        player.initObservableCard();
//        player.notifyObservers("add new player!");
//    }
}
