package sg.edu.nus.iss.phoenix.user.android.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
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


    public void startUseCase(String userType) {
        urSelected = null;
        Intent intent = new Intent(MainController.getApp(), ReviewSelectUserScreen.class);
        intent.putExtra("userType",userType);
        MainController.displayScreen(intent);

    }

    public void onDisplay(ReviewSelectUserScreen reviewSelectUserScreen, String userType) {
        this.reviewSelectUserScreen = reviewSelectUserScreen;
        new RetrieveUsersDelegate(this).execute("all", userType);
    }

    public void usersRetrieved(List<User> users) {
        reviewSelectUserScreen.showUsers(users);
    }

    public void selectUser(User user, String userType) {
        urSelected = user;
        Log.v(TAG, "Selected radio program: " + user.getName() + ".");
        // To call the base use case controller with the selected radio program.
        ControlFactory.getScheduleController().selectedUser(urSelected, userType);
    }

    public void selectCancel(String userType) {
        urSelected = null;
        Log.v(TAG, "Cancelled the selection of user.");
        // To call the base use case controller without selection;
        ControlFactory.getScheduleController().selectedUser(urSelected, userType);
    }
}
