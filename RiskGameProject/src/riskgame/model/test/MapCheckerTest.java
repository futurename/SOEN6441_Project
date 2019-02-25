package riskgame.model.test;

import org.junit.Before;
import org.junit.Test;
import riskgame.model.Utils.MapChecker;

import java.io.IOException;

import static org.junit.Assert.*;

public class MapCheckerTest {
    private String path;

    @Before
    public void setUp() throws Exception {
        path = "./maps/World.map";
    }

    @Test
    public void checkMapValidity() throws IOException {
        assertEquals(9527, MapChecker.checkMapValidity(this.path));
    }

    @Test
    public void isMapValid(){

    }
}