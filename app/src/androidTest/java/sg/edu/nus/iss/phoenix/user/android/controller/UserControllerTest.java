package sg.edu.nus.iss.phoenix.user.android.controller;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.core.android.ui.MainScreen;
import sg.edu.nus.iss.phoenix.user.android.ui.UserListScreen;
import sg.edu.nus.iss.phoenix.user.entity.User;

import static android.content.ContentValues.TAG;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

/**
 * Created by wangzuxiu on 29/9/18.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {
    private static UserController userController;
    private static MainController mainController;

    @Before
    public void setUp() throws Exception {
        mainController.setApp(mainRule.getActivity().getApplication());
        userController = ControlFactory.getUserController();
        mainController = ControlFactory.getMainController();
    }

    @Rule
    public ActivityTestRule<UserListScreen> userListScreenRule
            = new ActivityTestRule<UserListScreen>(UserListScreen.class);

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

    @After
    public void tearDown() throws Exception {
        userController = null;
        mainController = null;
    }

    @Test
    public void A_selectCreateUser() throws Exception {

        userController.selectCreateUser();
        //delay();
        onView(withId(R.id.maintain_user_id)).check(matches(hasValue("")));
        onView(withId(R.id.maintain_user_password)).check(matches(hasValue("")));
        onView(withId(R.id.maintain_user_name)).check(matches(hasValue("")));

        delay();

        onView(withId(R.id.action_cancel)).perform(click());
    }

    @Test
    public void B_selectSaveUser() throws Exception {
        userController.selectCreateUser();
        onView(withId(R.id.maintain_user_id)).perform(typeText("abc"));
        onView(withId(R.id.maintain_user_password)).perform(typeText("123"));
        onView(withId(R.id.maintain_user_name)).perform(typeText("aTest"));
        onView(withId(R.id.maintain_user_role_admin)).perform(click());
        onView(withId(R.id.action_save)).perform(click());
        delay();
        onView(withText("abc")).check(matches(withText("abc")));

    }

    @Test
    public void C_selectEditUser() throws Exception {

        userController.startUseCase();
        delay();
        onData(anything()).inAdapterView(withId(R.id.radio_ur_list)).atPosition(0).perform(click());
        onView(withId(R.id.action_view)).perform(click());
        delay();

        onView(withId(R.id.maintain_user_id)).check(matches(hasValue("abc")));
        onView(withId(R.id.maintain_user_name)).check(matches(hasValue("aTest")));

        onView(withId(R.id.action_cancel)).perform(click());
        delay();
    }

    @Test
    public void D_selectUpdateUser() throws Exception {
        userController.startUseCase();
        delay();
        onData(anything()).inAdapterView(withId(R.id.radio_ur_list)).atPosition(0).perform(click());
        onView(withId(R.id.action_view)).perform(click());
        delay();

        onView(withId(R.id.maintain_user_name)).perform(replaceText("aAfterUpdateTest"));
        onView(withId(R.id.maintain_user_password)).perform(replaceText("abc"));
        onView(withId(R.id.action_save)).perform(click());
        delay();
        onView(withText("aAfterUpdateTest")).check(matches(withText("aAfterUpdateTest")));

    }

    @Test
    public void E_selectDeleteUser() throws Exception {
        userController.startUseCase();
        delay();
        onData(anything()).inAdapterView(withId(R.id.radio_ur_list)).atPosition(0).perform(click());
        onView(withId(R.id.action_view)).perform(click());
        delay();

        onView(withId(R.id.action_delete)).perform(click());
        delay();

    }

    private void delay() {
        try {
            Thread.sleep(5000);
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
}