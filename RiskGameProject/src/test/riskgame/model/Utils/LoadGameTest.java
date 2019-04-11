package test.riskgame.model.Utils;

import org.junit.Assert;
import org.junit.Test;
import riskgame.model.Utils.LoadGame;;
import java.io.*;

public class LoadGameTest {

    @Test
    public void testLoadGame() throws IOException {
        String FilePath = "src\\test\\riskgame\\model\\Utils\\testcase.save";
        LoadGame lg = new LoadGame();
        Assert.assertEquals(null, lg.loadGame(FilePath, null, null,null));
    }
}
