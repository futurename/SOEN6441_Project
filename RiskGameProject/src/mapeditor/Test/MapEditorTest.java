package mapeditor.Test;


import mapeditor.MEMain;
import org.junit.Before;
import org.junit.Test;

public class MapEditorTest {

    MEMain testMEMain;
    @Before public void before(){
        testMEMain = new MEMain();
        testMEMain.createContinent("testfirstcontinent",1);
        testMEMain.createsSoloCountry("testfirstcountry");
    }

    @Test public void testMEMainCreateContinent(){

    }

    @Test public void testMEMainCreateCountry(){

    }

    @Test public void testMEMainDeleteCountry(){

    }

    @Test public void testMEMainDeleteContinent (){

    }
}
