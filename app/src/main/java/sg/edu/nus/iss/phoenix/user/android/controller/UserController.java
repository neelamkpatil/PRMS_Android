package sg.edu.nus.iss.phoenix.user.android.controller;

import android.content.Intent;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.user.android.delegate.RetrieveUsersDelegate;
import sg.edu.nus.iss.phoenix.user.android.ui.MaintainUserScreen;
import sg.edu.nus.iss.phoenix.user.android.ui.UserListScreen;
import sg.edu.nus.iss.phoenix.user.entity.User;

/**
 * Created by wangzuxiu on 17/9/18.
 */

public class UserController {

    private static final String TAG = UserController.class.getName();

    private UserListScreen userListScreen;
    private MaintainUserScreen maintainUserScreen;
    private User ur2edit = null;

    public void startUseCase() {
        ur2edit = null;
        Intent intent = new Intent(MainController.getApp(), UserListScreen.class);
        MainController.displayScreen(intent);
    }

    public void onDisplayUserList(UserListScreen userListScreen) {
        this.userListScreen = userListScreen;
        System.out.print("he");
        new RetrieveUsersDelegate(this).execute("all");
    }

    public void usersRetrieved(List<User> users) {
        userListScreen.showUsers(users);
    }

    public void maintainedUser() {
        ControlFactory.getMainController().maintainedUser();
    }


}
