package test.riskgame.model.Utils;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import riskgame.model.Utils.InfoRetriverTest;

/**
 * Suite test for util classes
 *
 * @author Wei Wang
 * @version 1.0
 * @since 2019/03/26
 **/
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AttackProcessTest.class,
        MapCheckerTest.class,
        InfoRetriverTest.class
})
public class UtilSuiteTest {
}
