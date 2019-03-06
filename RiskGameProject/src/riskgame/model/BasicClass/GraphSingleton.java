package riskgame.model.BasicClass;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * This class represnets a singleton pattern graph for storing world map information
 **/
public enum GraphSingleton {
    /**
     * singleton instance implemented with enum approach
     */
    INSTANCE;

    private LinkedHashMap<String, GraphNode> instance;

    GraphSingleton() {
        instance = new LinkedHashMap<>();
    }

    public LinkedHashMap<String, GraphNode> getInstance() {
        return this.instance;
    }

    /**
     * reset visited indicator of all GraphNode to false
     */
    public void resetGraphVisitedFlag() {
        for (Map.Entry<String, GraphNode> entry : this.instance.entrySet()) {
            entry.getValue().setVisited(false);
        }
    }

}
