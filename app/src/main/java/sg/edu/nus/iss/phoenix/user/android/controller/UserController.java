package sg.edu.nus.iss.phoenix.user.android.controller;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.radioprogram.android.delegate.DeleteProgramDelegate;
import sg.edu.nus.iss.phoenix.radioprogram.android.ui.MaintainProgramScreen;

import sg.edu.nus.iss.phoenix.user.android.delegate.CreateUserDelegate;
import sg.edu.nus.iss.phoenix.user.android.delegate.DeleteUserDelegate;
import sg.edu.nus.iss.phoenix.user.android.delegate.RetrieveUsersDelegate;
import sg.edu.nus.iss.phoenix.user.android.delegate.UpdateUserDelegate;
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
        System.out.print("onDisplayUserlist");
        new RetrieveUsersDelegate(this).execute("all");
    }

    public void onDisplayUser(MaintainUserScreen maintainUserScreen) {
        this.maintainUserScreen = maintainUserScreen;
        if (ur2edit == null)
            maintainUserScreen.createUser();
        else
            maintainUserScreen.editUser(ur2edit);
    }

    public void usersRetrieved(List<User> users) {
        userListScreen.showUsers(users);
    }

    public void selectEditUser(User user) {
        ur2edit = user;
        Log.v(TAG, "Editing user details: " + user.getRoles() + "...");
        Intent intent = new Intent(MainController.getApp(), MaintainUserScreen.class);
        MainController.displayScreen(intent);
    }
    public void selectDeleteUser(User user) {
        new DeleteUserDelegate(this).execute(user.getId());
    }

    public void userDeleted(boolean success) {
        // Go back to UserList screen with refreshed programs.
        if(success){
            startUseCase();
        }else{
            maintainUserScreen.deleteWarning();
        }

    }

    public void selectCancelCreateUser() {
        // Go back to UserList screen with refreshed programs.
        startUseCase();
    }
    public void selectCreateUser() {
        ur2edit = null;
        Intent intent = new Intent(MainController.getApp(), MaintainUserScreen.class);
        MainController.displayScreen(intent);
    }

    public void selectCreateUser(User user) {
        new CreateUserDelegate(this).execute(user);
    }
    public void userCreated(boolean success) {
        // Go back to ProgramList screen with refreshed programs.
        if(success){
            startUseCase();
        }else{
            maintainUserScreen.creationWarning();
        }

    }
    public void selectUpdateUser(User user) {
        new UpdateUserDelegate(this).execute(user);
    }
    public void userUpdated(boolean success) {
        // Go back to ProgramList screen with refreshed programs.
        startUseCase();
    }

    public void maintainedUser() {
        ControlFactory.getMainController().maintainedUser();
    }


}
