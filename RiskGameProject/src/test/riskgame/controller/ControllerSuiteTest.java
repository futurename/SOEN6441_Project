package test.riskgame.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Suite test for controller classes
 *
 * @author Wei Wang
 * @version 1.0
 * @since 2019/03/26
 **/
@RunWith(Suite.class)
@Suite.SuiteClasses({
        FortificationPhaseTest.class,
        PlayerTest.class,
        ReinforcePhaseTest.class
})
public class ControllerSuiteTest {
}
