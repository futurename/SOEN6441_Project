package riskgame.model.BasicClass;

import riskgame.Main;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This class represents a node in world map graph data structure, including a country object, its adjacent country objects and an
 * indicator for traversal usage. It also include traversal methods of depth first and breadth first search for acquiring all reachable countries
 * from a selected country.
 * @author WW
 * @since build1
 **/
public class GraphNode {
    private Country country;
    private ArrayList<Country> adjacentCountryList;
    private boolean isVisited;

    /**
     * constructor for GraphNode class
     *
     * @param country a country object
     */
    public GraphNode(Country country) {
        this.country = country;
        this.adjacentCountryList = new ArrayList<>();
        this.isVisited = false;
    }

    /**
     * getter
     *
     * @return current Country instance
     */
    public Country getCountry() {
        return country;
    }

    /**
     * getter
     *
     * @return ArrayList of all the adjacent Country instances
     */
    public ArrayList<Country> getAdjacentCountryList() {
        return adjacentCountryList;
    }

    /**
     * getter
     *
     * @return true for visited and false for not visited
     */
    public boolean isVisited() {
        return isVisited;
    }

    /**
     * setter
     *
     * @param visited true for visited and false for not visited
     */
    void setVisited(boolean visited) {
        isVisited = visited;
    }

    /**
     * getter
     *
     * @return current country name
     */
    public String getCountryName() {
        return this.country.getCountryName();
    }

    /**
     * add a Country instance to the adjacent country list
     *
     * @param adjacentCountry an adjacent country instance
     */
    public void addAdjacentCountry(Country adjacentCountry) {
        this.adjacentCountryList.add(adjacentCountry);
    }

    /**
     * Breath first search for getting all reachable countries the player owns from the selected country
     *
     * @param playerIndex current player index
     * @param curCountry  selected country starting for traverse
     * @param list        countries that are reachable from selected country
     */
    public void getReachableCountryListBFS(int playerIndex, Country curCountry, ArrayList<Country> list) {
        String curCountryName = curCountry.getCountryName();
        GraphNode curGraphNode = Main.graphSingleton.get(curCountryName);
        ArrayList<Country> adjacentList = curGraphNode.getAdjacentCountryList();
        curGraphNode.setVisited(true);
        ArrayList<Country> queue = new ArrayList<>();
        for (Country country : adjacentList) {
            GraphNode graphNode = Main.graphSingleton.get(country.getCountryName());
            if (graphNode.getCountry().getCountryOwnerIndex() == playerIndex && !graphNode.isVisited) {
                list.add(graphNode.getCountry());
                graphNode.setVisited(true);
                queue.add(country);
            }
        }

        while (!queue.isEmpty()) {
            getReachableCountryListBFS(playerIndex, queue.remove(0), list);
        }
    }


    /**
     * Depth first search for getting all reachable countries the player owns from the selected country
     *
     * @param playerIndex current player index
     * @param curCountry  selected country starting for traverse
     * @param list        countries that are reachable from selected country
     */
    private void getReachableCountryListDFS(int playerIndex, Country curCountry, ArrayList<Country> list) {
        String curCountryName = curCountry.getCountryName();
        GraphNode curGraphNode = Main.graphSingleton.get(curCountryName);
        ArrayList<Country> adjacentList = curGraphNode.getAdjacentCountryList();
        curGraphNode.setVisited(true);

        for (Country country : adjacentList) {
            GraphNode graphNode = Main.graphSingleton.get(country.getCountryName());
            if (graphNode.getCountry().getCountryOwnerIndex() == playerIndex && !graphNode.isVisited) {
                list.add(graphNode.getCountry());
                graphNode.setVisited(true);
                getReachableCountryListDFS(playerIndex, country, list);
            }
        }
    }


}
