package test.riskgame.model.Utils;

import org.junit.Assert;
import org.junit.Test;
import riskgame.model.Utils.LoadGame;

import java.io.IOException;

/**
 * @author Karamveer
 */
public class LoadGameTest {

    @Test
    public void testLoadGame() throws IOException {
        String FilePath = "src\\test\\riskgame\\model\\Utils\\testcase.save";
        LoadGame lg = new LoadGame();
        Assert.assertEquals(null, LoadGame.loadGame(FilePath, null, null,null));
    }
}
