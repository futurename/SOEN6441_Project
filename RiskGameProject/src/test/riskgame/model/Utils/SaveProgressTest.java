package test.riskgame.model.Utils; 

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import riskgame.Main;
import riskgame.controllers.StartViewController;
import riskgame.model.BasicClass.*;
import riskgame.model.BasicClass.ObserverPattern.CardExchangeViewObserver;
import riskgame.model.Utils.SaveProgress;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import static riskgame.Main.phaseViewObservable;

/** 
* SaveProgress Tester. 
* 
* @author Karamveer
* @since <pre>Apr 8, 2019</pre> 
* @version 1.0 
*/ 
public class SaveProgressTest {

    /**
    *
    * Method: SaveFile(String phase, int curPlayer, String path, String mapName, boolean AorF, boolean AOC)
    *
    */
    @Test
    public void testSaveFile() throws Exception {
        File writename = new File("./saveTest.save");
        System.out.println(writename);
        writename.createNewFile();
        BufferedWriter out = new BufferedWriter(new FileWriter(writename));
        out.write("[Map]\r\n");
        out.write("author=author\r\n");
        out.write("image=world.bmp\r\n");
        out.write("wrap=no\r\n");
        out.write("scroll=horizontal\r\n");
        out.write("warn=yes\r\n");
        out.write("\r\n");

        out.write("[Continents]\r\n");
        out.write("Asia"+"="+"3"+"\r\n");
        out.write("\r\n");

        out.write("[Territories]\r\n");
        out.write("Japan"+",0,0,"+"Asia"+","+"0"+","+"2"+"China"+"\r\n");
        out.write("\r\n");

        out.write("[Players]\r\n");
        out.write("3"+"\r\n");

        out.write("Japan"+"Canada"+"\r\n");
        out.write("\r\n");

        out.flush();
        out.close();
    }
} 
