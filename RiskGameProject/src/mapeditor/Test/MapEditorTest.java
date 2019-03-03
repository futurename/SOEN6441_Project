package mapeditor.Test;


import mapeditor.MEMain;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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

    @Test public void testMEMainDeleteContinent (){
        testMEMain.deleteContinent("testfirstcontinent");
        assertEquals(0,testMEMain.arrMEContinent.size());
        assertEquals(0,testMEMain.arrMECountry.size());
    }

    @Test public void testMEMainDeleteCountry(){
        assertEquals(1,testMEMain.arrMEContinent.size());
        assertEquals(0,testMEMain.arrMECountry.size());
    }

}
