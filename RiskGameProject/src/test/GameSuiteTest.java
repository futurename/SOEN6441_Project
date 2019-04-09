import mapeditor.Test.MapEditorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.riskgame.controller.ControllerSuiteTest;
import test.riskgame.model.BasicClass.BasicClassSuiteTest;
import test.riskgame.model.BasicClass.StrategyPattern.StragegyPatternSuiteTest;
import test.riskgame.model.Utils.UtilSuiteTest;


/**
 * Suite tests for project test classes
 * @author Wei Wang
 * @version 1.0
 * @since 2019/03/26
 **/
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ControllerSuiteTest.class,
        MapEditorTest.class,
        StragegyPatternSuiteTest.class,
        BasicClassSuiteTest.class,
        UtilSuiteTest.class
})
public class GameSuiteTest {
}
