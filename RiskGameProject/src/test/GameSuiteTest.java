package test;

import mapeditor.Test.MapEditorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.riskgame.controller.ControllerSuiteTest;
import test.riskgame.model.Utils.UtilSuiteTest;

/**
 * @author Wei Wang
 * @version 1.0
 * @since 2019/03/26
 **/
@RunWith(Suite.class)
@Suite.SuiteClasses({
        UtilSuiteTest.class,
        ControllerSuiteTest.class,
        MapEditorTest.class
})
public class GameSuiteTest {
}
