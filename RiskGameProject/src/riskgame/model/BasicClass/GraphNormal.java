package riskgame.model.BasicClass;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author WW
 * @version 1.0
 * @since 2019-03-30
 */
public class GraphNormal {
    private LinkedHashMap<String, GraphNode> worldHashMap;

    public GraphNormal() {
        this.worldHashMap = new LinkedHashMap<>();
    }

    public LinkedHashMap<String, GraphNode> getWorldHashMap() {
        return worldHashMap;
    }

}
