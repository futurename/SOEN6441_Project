package riskgame.model.BasicClass;

import riskgame.Main;

import java.util.ArrayList;

/**
 * @program: RiskGameProject
 * @description: Node class of the world map graph
 * @author: WW
 * @date: 2019-02-10
 **/

public class GraphNode {
    private Country country;
    private ArrayList<Country> adjacentCountryList;
    private boolean isVisited;

    /**
     * constructor for class GraphNode
     *
     * @param country string name of the graph node
     */
    public GraphNode(Country country) {
        this.country = country;
        this.adjacentCountryList = new ArrayList<>();
        this.isVisited = false;
    }

    /**
     * get Country instance of the graph node
     *
     * @return current Country instance
     */
    public Country getCountry() {
        return country;
    }

    /**
     * get all adjacent Country instances of current Country instance.
     *
     * @return ArrayList of all the adjacent Country instances
     */
    public ArrayList<Country> getAdjacentCountryList() {
        return adjacentCountryList;
    }

    /**
     * retrive the state if the node is visited
     *
     * @return true for visited and false for not visited
     */
    public boolean isVisited() {
        return isVisited;
    }

    /**
     * set the flag whether the node is visited or not
     *
     * @param visited true for visited and false for not visited
     */
    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    /**
     * get the country name of the node
     *
     * @return current country name
     */
    public String getCountryName() {
        return this.country.getCountryName();
    }

    /**
     * add a Country instance to the list of current Country instance
     *
     * @param adjacentCountry an adjacent country instance
     */
    public void addAdjacentCountry(Country adjacentCountry) {
        this.adjacentCountryList.add(adjacentCountry);
    }

    /**
     * Breath first search
     *
     * @return list of all reachable countries the owner has
     */
    public ArrayList<Country> BreathFirstSearch() {


        System.out.println("BFS");
        return null;
    }


    /**
     * Depth first search for getting all reachable countries the player owns from the selected country
     *
     */
    public void getReachableCountryListDFS(int playerIndex, Country curCountry, ArrayList<Country> list) {
        ArrayList<Country> adjacentList = getAdjacentCountryList();

        for(Country country: adjacentList){
            GraphNode curGraphNode = Main.graphSingleton.get(country.getCountryName());
            if(curGraphNode.getCountry().getCountryOwnerIndex() == playerIndex && !curGraphNode.isVisited){
                list.add(curGraphNode.getCountry());
                curGraphNode.setVisited(true);
                getReachableCountryListDFS(playerIndex,country,list);
            }
        }
    }



}
