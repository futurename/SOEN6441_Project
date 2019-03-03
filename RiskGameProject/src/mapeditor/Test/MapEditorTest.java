package mapeditor.Test;


import mapeditor.MEMain;
import mapeditor.model.MEContinent;
import mapeditor.model.MECountry;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MapEditorTest {

    public static MEMain testMEMain;

    @BeforeClass public static void before(){
        testMEMain = new MEMain();
        testMEMain.createContinent("testFirstContinent",1);
        testMEMain.createsSoloCountry("testFirstCountry");
        testMEMain.arrMEContinent.get(0).addCountry("testFirstCountry");
        testMEMain.createsSoloCountry("testSecondCountry");
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

    @Test public void testMEMainDeleteContinent () {
        testMEMain.deleteContinent("testFirstContinent");
        assertEquals(0, testMEMain.arrMEContinent.size());
        assertEquals(1, testMEMain.arrMECountry.size());
    }

    @Test public void testMEMainDeleteCountry(){
        testMEMain.deleteCountry("testSecondCountry");
        assertEquals(0,testMEMain.arrMECountry.size());
    }

}
