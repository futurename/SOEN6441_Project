import mapeditor.Test.MapEditorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


/**
 * Suite tests for project test classes
 * @author Wei Wang
 * @version 1.0
 * @since 2019/03/26
 **/
@RunWith(Suite.class)
@Suite.SuiteClasses({

        test.riskgame.controller.ControllerSuiteTest.class,
        MapEditorTest.class
})
public class GameSuiteTest {
}
