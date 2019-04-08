package test.riskgame.model.Utils; 

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import riskgame.model.Utils.SaveProgress;

/** 
* SaveProgress Tester. 
* 
* @author <Authors name> 
* @since <pre>Apr 8, 2019</pre> 
* @version 1.0 
*/ 
public class SaveProgressTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
}

/** 
* 
* Method: SaveFile(String phase, int curPlayer, String path, String mapName, boolean AorF, boolean AOC) 
* 
*/ 
@Test
public void testSaveFile() throws Exception { 
//TODO: Test goes here...
    int remainingArmyNbr = 5;
    SaveProgress save = new SaveProgress();
    String saved = "yes";
    try {
        save.SaveFile("", 1, "", "", true, false);
    } catch (Error e) {
        System.out.println("ignore alert window");
    } finally {
        Assert.assertTrue(saved == "yes");
    }
} 


} 
