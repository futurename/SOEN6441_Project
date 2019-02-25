package riskgame.model.BasicClass;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * singleton class for world map
 */
public enum GraphSingleton {
    /**
     * singleton instance implemented with enum approach
     */
    INSTANCE;

    private LinkedHashMap<String, GraphNode> instance;

    GraphSingleton(){
        instance = new LinkedHashMap<>();
    }

    public LinkedHashMap<String, GraphNode> getInstance(){
        return this.instance;
    }

    /**
     * set @param isVisited to false in all graph nodes
     */
    public void resetGraphVisitedFlag(){
       for(Map.Entry<String, GraphNode> entry: this.instance.entrySet()){
           entry.getValue().setVisited(false);
       }
    }

}
