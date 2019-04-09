package test.riskgame.model.BasicClass;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.riskgame.model.BasicClass.StrategyPattern.StragegyPatternSuiteTest;

/**
 * @author Wei Wang
 * @version 1.0
 * @since 2019/04/09
 **/
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TournamentGameTest.class,
        StragegyPatternSuiteTest.class
})
public class BasicClassSuiteTest {
}
