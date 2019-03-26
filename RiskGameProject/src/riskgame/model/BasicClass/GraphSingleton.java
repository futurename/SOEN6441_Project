package riskgame.model.BasicClass;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * This class represents a singleton pattern graph for storing world map information
 * @author WW
 * @since build1
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

    public void resetInstance() {
        this.instance = new LinkedHashMap<>();
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
