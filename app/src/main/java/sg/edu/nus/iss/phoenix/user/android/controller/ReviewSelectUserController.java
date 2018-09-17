package sg.edu.nus.iss.phoenix.user.android.controller;

import android.content.Intent;
import android.util.Log;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.radioprogram.android.controller.ReviewSelectProgramController;
import sg.edu.nus.iss.phoenix.radioprogram.android.delegate.RetrieveProgramsDelegate;
import sg.edu.nus.iss.phoenix.radioprogram.android.ui.ReviewSelectProgramScreen;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.user.android.delegate.RetrieveUsersDelegate;
import sg.edu.nus.iss.phoenix.user.android.ui.ReviewSelectUserScreen;
import sg.edu.nus.iss.phoenix.user.entity.User;

/**
 * Created by wangzuxiu on 17/9/18.
 */

public class ReviewSelectUserController {
    // Tag for logging.
    private static final String TAG = ReviewSelectUserController.class.getName();

    private ReviewSelectUserScreen reviewSelectUserScreen;
    private User urSelected = null;

    public void startUseCase() {
        urSelected = null;
        Intent intent = new Intent(MainController.getApp(), ReviewSelectUserScreen.class);
        MainController.displayScreen(intent);
    }

    public void onDisplay(ReviewSelectUserScreen reviewSelectUserScreen) {
        this.reviewSelectUserScreen = reviewSelectUserScreen;
        new RetrieveUsersDelegate(this).execute("all");
    }

    public void usersRetrieved(List<User> users) {
        reviewSelectUserScreen.showUsers(users);
    }

    public void selectUser(User user) {
        urSelected = user;
        Log.v(TAG, "Selected radio program: " + user.getName() + ".");
        // To call the base use case controller with the selected radio program.
        // At present, call the MainController instead.
        ControlFactory.getMainController().selectedUser(urSelected);
    }

    public void selectCancel() {
        urSelected = null;
        Log.v(TAG, "Cancelled the seleciton of radio program.");
        // To call the base use case controller without selection;
        // At present, call the MainController instead.
        ControlFactory.getMainController().selectedUser(urSelected);
    }
}
