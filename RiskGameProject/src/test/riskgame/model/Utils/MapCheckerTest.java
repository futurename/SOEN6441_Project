package test.riskgame.model.Utils;

import org.junit.Before;
import org.junit.Test;
import riskgame.model.Utils.MapChecker;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * MapChecker tester.
 * @author Zhanfan
 */

public class MapCheckerTest {
    private ArrayList loadableMaps;
    private ArrayList errorMaps;
    private String error_path;

    @Before
    public void setUp() throws Exception {
        loadableMaps = new ArrayList<String>();
        errorMaps = new ArrayList<String>();
        loadableMaps.add("./maps/World.map");
        loadableMaps.add("./maps/Alberta.map");
        loadableMaps.add("./maps/TestMap/test_map.map");
        loadableMaps.add("./maps/USA.map");
        errorMaps.add("./maps/ErrorMap/ErrorMap_ContinentsAmount_United States.map");

        error_path = "./maps/World";
    }

    /**
     * check map format is valid or not
     * @throws IOException map file not found
     */
    @Test
    public void checkMapValidity() throws IOException {
        for (int i=0;i<loadableMaps.size();i++){
            assertEquals(9527, MapChecker.checkMapValidity((String) this.loadableMaps.get(i)));
        }
    }

    /**
     * check map file exists or not
     * @throws IOException map file not found
     */
    @Test
    public void isMapValid() throws IOException {
        for (int i=0;i<errorMaps.size();i++){
            assertFalse(MapChecker.isMapValid((String) errorMaps.get(i)));
        }
    }

    /**
     * check map path is valid or not
     */
    @Test
    public void isMapPathValid() {
        assertFalse(MapChecker.isMapPathValid(this.error_path));
    }

}