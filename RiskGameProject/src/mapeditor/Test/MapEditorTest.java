package mapeditor.Test;


import mapeditor.MEMain;
import mapeditor.model.MEContinent;
import mapeditor.model.MECountry;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MapEditorTest {

    MEMain testMEMain;
    @Before public void before(){
        testMEMain = new MEMain();
        testMEMain.createContinent("testFirstContinent",1);
        testMEMain.arrMEContinent.get(0).addCountry("testFirstCountry");
        testMEMain.createsSoloCountry("testFirstCountry");
    }

   @Test public void testMEMainCreateContinent(){
        MEContinent newContinent =  testMEMain.arrMEContinent.get(0);
        assertEquals("testFirstContinent",newContinent.getContinentName());
        assertEquals(1,newContinent.getBonus());
    }

    @Test public void testMEMainCreateCountry(){
        MECountry newCountry =  testMEMain.arrMECountry.get(0);
        assertEquals("testFirstCountry",newCountry.getCountryName());
    }

    @Test public void testMEMainDeleteContinent (){
        testMEMain.deleteContinent("testFirstContinent");
        assertEquals(0,testMEMain.arrMEContinent.size());
        assertEquals(0,testMEMain.arrMECountry.size());
    }

    @Test public void testMEMainDeleteCountry(){
        testMEMain.deleteCountry("testFirstCountry");
        assertEquals(1,testMEMain.arrMEContinent.size());
        assertEquals(0,testMEMain.arrMECountry.size());
    }

}
