package riskgame.model.Utils;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Suite test for util classes
 *
 * @author Wei Wang
 * @version 1.0
 * @since 2019/03/26
 **/
@RunWith(Suite.class)
@Suite.SuiteClasses({
        test.riskgame.model.Utils.AttackProcessTest.class,
        test.riskgame.model.Utils.MapCheckerTest.class,
        test.riskgame.model.Utils.InfoRetriverTest.class
})
public class UtilSuiteTest {
}
