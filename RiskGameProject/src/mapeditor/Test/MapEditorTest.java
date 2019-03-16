package mapeditor.Test;


import mapeditor.MEMain;
import mapeditor.model.MEContinent;
import mapeditor.model.MECountry;
import mapeditor.model.MapObject;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MapEditorTest {

    public static MEMain testMEMain;
    public String firstmap = "maps/ErrorMap/World-firsterror.map";
    public String secondmap = "maps/ErrorMap/World-seconderror.map";
    public String thirdmap = "maps/ErrorMap/World-thirderror.map";

    @BeforeClass public static void before(){
        testMEMain = new MEMain();


        MEMain.createContinent("testFirstContinent",1);
        MEMain.createsSoloCountry("testFirstCountry");
        MEMain.arrMEContinent.get(0).addCountry("testFirstCountry");
        MEMain.createsSoloCountry("testSecondCountry");

        System.out.println("Test map editor");
    }

    /**
     * Check add continent function
     */
    @Test public void testMEMainCreateContinent(){
        MEContinent newContinent =  MEMain.arrMEContinent.get(0);
        assertEquals("testFirstContinent",newContinent.getContinentName());
        assertEquals(1,newContinent.getBonus());
    }

    /**
     * Check add country function
     */
    @Test public void testMEMainCreateCountry(){
        MECountry newCountry =  MEMain.arrMECountry.get(0);
        assertEquals("testFirstCountry",newCountry.getCountryName());
    }

    /**
     * Check delete continent function
     */
    @Test public void testMEMainDeleteContinent () {
        MEMain.deleteContinent("testFirstContinent");
        assertEquals(0, MEMain.arrMEContinent.size());
        assertEquals(1, MEMain.arrMECountry.size());
    }

    /**
     * Check delete country function
     */
    @Test public void testMEMainDeleteCountry(){
        MEMain.deleteCountry("testSecondCountry");
        assertEquals(0, MEMain.arrMECountry.size());
    }

    /**
     * Test for checking whether all countries in map are connected
     */
    /*
    @Test public void testCheckConnectGraph(){
        MapObject mapObject = new MapObject();
        assertFalse(mapObject.checkCorrectness(firstmap));
    }
*/
    /**
     * Test for checking whether all countries in one continent are placed together
     */
    /*
    @Test public void testCheckContinentCountry(){
        MapObject mapObject = new MapObject();
        assertFalse(mapObject.checkCorrectness(secondmap));
    }
*/
    /**
     * Test for checking whether a country only belongs to one continent
     */
    /*
    @Test public void testCheckContinentBelonging(){
        MapObject mapObject = new MapObject();
        assertFalse(mapObject.checkCorrectness(thirdmap));
    }
*/
}
