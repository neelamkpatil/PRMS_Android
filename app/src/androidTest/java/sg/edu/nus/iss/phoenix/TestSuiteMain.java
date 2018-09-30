package sg.edu.nus.iss.phoenix;

/**
 * Created by Peiyan on 28/09/2018.
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import sg.edu.nus.iss.phoenix.schedule.android.controller.ScheduleControllerTest;
import sg.edu.nus.iss.phoenix.user.android.controller.UserControllerTest;

// Runs all unit tests.
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ScheduleControllerTest.class,
        UserControllerTest.class,
})
public class TestSuiteMain {}
