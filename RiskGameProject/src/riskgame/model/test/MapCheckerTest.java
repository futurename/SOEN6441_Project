package riskgame.model.test;

import org.junit.Before;
import org.junit.Test;
import riskgame.model.Utils.MapChecker;

import java.io.IOException;

import static org.junit.Assert.*;

public class MapCheckerTest {
    private String path, error_path, error_map;

    @Before
    public void setUp() throws Exception {
        path = "./maps/World.map";
        error_path = "./maps/World";
        error_map = "./maps/TestWorld.map";
    }

    @Test
    public void checkMapValidity() throws IOException {
        assertEquals(9527, MapChecker.checkMapValidity(this.path));
    }

    @Test
    public void isMapValid() throws IOException {
        assertFalse(MapChecker.isMapValid(this.error_map));
    }

    @Test
    public void isMapPathValid(){
        assertFalse(MapChecker.isMapPathValid(this.error_path));
    }
}