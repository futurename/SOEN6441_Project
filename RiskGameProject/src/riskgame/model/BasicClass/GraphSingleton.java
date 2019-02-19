package riskgame.model.BasicClass;

import java.util.HashMap;

/**
 * singleton class for world map
 */
public enum GraphSingleton {
    /**
     * singleton instance implemented with enum approach
     */
    INSTANCE;

    private HashMap<String, GraphNode> instance;

    GraphSingleton(){
        instance = new HashMap<>();
    }

    public HashMap<String, GraphNode> getInstance(){
        return this.instance;
    }

    /**
     * set @param isVisited to false in all graph nodes
     */
    public void resetGraphVisitedFlag(){

    }

}
