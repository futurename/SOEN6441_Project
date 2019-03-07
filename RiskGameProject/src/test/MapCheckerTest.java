package test;

import org.junit.Before;
import org.junit.Test;
import riskgame.model.Utils.MapChecker;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * MapChecker tester.
 */

public class MapCheckerTest {
    private String path, error_path, error_map;
    private String newMapPath = "maps/USA.map";

    @Before
    public void setUp() throws Exception {
        path = "./maps/World.map";
        error_path = "./maps/World";
        error_map = "./maps/TestWorld.map";
    }

    @Test
    public void checkMapValidity() throws IOException {

        System.out.println((new File(newMapPath).getAbsolutePath()));

        assertEquals(9527, MapChecker.checkMapValidity(this.newMapPath));

    }

    @Test
    public void isMapPathValid() {
        assertFalse(MapChecker.isMapPathValid(this.error_path));
    }
}