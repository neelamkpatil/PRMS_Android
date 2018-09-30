package sg.edu.nus.iss.phoenix.schedule.android.controller;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.util.HumanReadables;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.core.android.ui.MainScreen;
import sg.edu.nus.iss.phoenix.schedule.android.ui.ScheduleListScreen;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

import org.junit.FixMethodOrder;

/**
 * Created by Peiyan on 28/9/18.
 */

/**
 * Test of ScheduleController class, to check the CRUD features of program slot.
 * Note: The test program with name of 'testRPName', test user with id of 'testPresenter'
 * and 'testProducer' should be stored in database before running test case.
 * please do check and make sure the existence of program, presenter and producer before running test case.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ScheduleControllerTest {
    // Tag for logging
    private static final String TAG = ScheduleControllerTest.class.getName();

    private static ScheduleController scheduleController;
    private static MainController mainController;
    private ProgramSlot testPS;
    private String testRPName = "testRPName";
    private String testDate = "2018-10-02";
    private String testSttime = "16:00:00";
    private String testDuration = "00:30:00";
    private String testPresenter = "testPresenter";
    private String testProducer = "testProducer";
    private String testNewDate = "2018-10-07";

    @Before
    public void init(){
        mainController.setApp(mainRule.getActivity().getApplication());
        Log.d("init", mainController.getApp().toString());
    }

    @Rule
    public ActivityTestRule<MainScreen> mainRule = new  ActivityTestRule<MainScreen>(MainScreen.class)
    {
        @Override
        protected Intent getActivityIntent() {
            InstrumentationRegistry.getTargetContext();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            return intent;
        }
    };

    @Rule
    public ActivityTestRule<ScheduleListScreen> scheduleListScreenRule
            = new ActivityTestRule<ScheduleListScreen>(ScheduleListScreen.class);

    @Before
    public void setUp() throws Exception {
        mainController = ControlFactory.getMainController();
        scheduleController = ControlFactory.getScheduleController();
    }

    @After
    public void tearDown() throws Exception {
        mainController = null;
        scheduleController = null;
    }

    @Test
    public void A_selectCreateSchedule() throws Exception {
        scheduleController.selectCreateSchedule();
        delay();

        onView(withId(R.id.maintain_schedule_program_name_text_view)).check(matches(hasValue("")));
        onView(withId(R.id.maintain_schedule_date_text_view)).check(matches(hasValue("")));
        onView(withId(R.id.maintain_schedule_sttime_text_view)).check(matches(hasValue("")));
        onView(withId(R.id.maintain_schedule_duration_text_view)).check(matches(hasValue("")));
        onView(withId(R.id.maintain_schedule_presenter_text_view)).check(matches(hasValue("")));
        onView(withId(R.id.maintain_schedule_producer_text_view)).check(matches(hasValue("")));

        onView(withId(R.id.action_cancel)).perform(click());
        delay();

    }

    @Test
    public void B_selectCopySchedule() throws Exception {
        ProgramSlot programSlot = new ProgramSlot(testRPName, testDuration,
                testPresenter, testProducer);

        scheduleController.selectCopySchedule(programSlot);
        delay();

        onView(withId(R.id.maintain_schedule_program_name_text_view)).check(matches(hasValue(testRPName)));
        onView(withId(R.id.maintain_schedule_date_text_view)).check(matches(hasValue("")));
        onView(withId(R.id.maintain_schedule_sttime_text_view)).check(matches(hasValue("")));
        onView(withId(R.id.maintain_schedule_duration_text_view)).check(matches(hasValue(testDuration)));
        onView(withId(R.id.maintain_schedule_presenter_text_view)).check(matches(hasValue(testPresenter)));
        onView(withId(R.id.maintain_schedule_producer_text_view)).check(matches(hasValue(testProducer)));
        delay();

        onView(withId(R.id.action_cancel)).perform(click());
        delay();
    }

    @Test
    public void C_selectEditSchedule() throws Exception {
        ProgramSlot programSlot = new ProgramSlot("0", testRPName, testDate, testSttime, testDuration,
                testPresenter, testProducer);

        scheduleController.selectEditSchedule(programSlot);
        delay();

        onView(withId(R.id.maintain_schedule_program_name_text_view)).check(matches(hasValue(testRPName)));
        onView(withId(R.id.maintain_schedule_date_text_view)).check(matches(hasValue(testDate)));
        onView(withId(R.id.maintain_schedule_sttime_text_view)).check(matches(hasValue(testSttime)));
        onView(withId(R.id.maintain_schedule_duration_text_view)).check(matches(hasValue(testDuration)));
        onView(withId(R.id.maintain_schedule_presenter_text_view)).check(matches(hasValue(testPresenter)));
        onView(withId(R.id.maintain_schedule_producer_text_view)).check(matches(hasValue(testProducer)));
        delay();

        onView(withId(R.id.action_cancel)).perform(click());
        delay();
    }

    @Test
    public void D_selectCreateSchedule() throws Exception {
        ProgramSlot programSlot = new ProgramSlot("-1", testRPName, testDate, testSttime, testDuration,
                testPresenter, testProducer);

        scheduleController.selectEditSchedule(programSlot);
        delay();

        onView(withId(R.id.action_save)).perform(click());
        delay();
    }

    @Test
    public void E_scheduleCreated() throws Exception {
        scheduleController.startUseCase();
        delay();
        onView(withText(testRPName)).check(matches(withText(testRPName)));
        onView(withText(testDate)).check(matches(withText(testDate)));
    }


    @Test
    public void F_selectUpdateSchedule() throws Exception {
        scheduleController.startUseCase();
        delay();
        onData(anything()).inAdapterView(withId(R.id.schedule_list)).atPosition(0).perform(click());
        onView(withId(R.id.action_view)).perform(click());
        delay();

        onView(withId(R.id.maintain_schedule_date_text_view)).perform(replaceText(testNewDate), closeSoftKeyboard());
        onView(withId(R.id.maintain_schedule_sttime_text_view)).perform(replaceText("19:00:00"), closeSoftKeyboard());
        onView(withId(R.id.maintain_schedule_duration_text_view)).perform(replaceText("01:00:00"), closeSoftKeyboard());

        onView(withId(R.id.action_save)).perform(click());
        delay();
    }

    @Test
    public void G_scheduleUpdated() throws Exception {
        scheduleController.startUseCase();
        delay();
        onView(withText(testRPName)).check(matches(withText(testRPName)));
        onView(withText(testNewDate)).check(matches(withText(testNewDate)));
    }

    @Test
    public void H_selectDeleteSchedule() throws Exception {
        scheduleController.startUseCase();
        delay();
        onData(anything()).inAdapterView(withId(R.id.schedule_list)).atPosition(0).perform(click());
        onView(withId(R.id.action_view)).perform(click());
        delay();

        onView(withId(R.id.action_delete)).perform(click());
        delay();
    }

    @Test
    public void I_scheduleDeleted() throws Exception {
        scheduleController.startUseCase();
        delay();
//        assertTrue(onView(withText(testRPName)) == null);
    }



    private void delay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Matcher<View> hasValue(final String content) {

        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("Match Edit Text Value with View ID Value : :  " + content);
            }

            @Override
            public boolean matchesSafely(View view) {
                String text = ((EditText) view).getText().toString();
                Log.d(TAG, "text " + text);

                if (text.equals(content)) {
                    return true;
                }
                return false;

            }
        };
    }

    public static ViewAssertion isNotDisplayed() {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noView) {
                if (view != null && isDisplayed().matches(view)) {
                    throw new AssertionError("View is present in the hierarchy and Displayed: "
                            + HumanReadables.describe(view));
                }
            }
        };
    }
}