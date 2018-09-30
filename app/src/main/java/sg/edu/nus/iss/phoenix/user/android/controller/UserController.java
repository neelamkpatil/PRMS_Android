package sg.edu.nus.iss.phoenix.user.android.controller;

import android.content.Intent;
import android.util.Log;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.user.android.delegate.CreateUserDelegate;
import sg.edu.nus.iss.phoenix.user.android.delegate.DeleteUserDelegate;
import sg.edu.nus.iss.phoenix.user.android.delegate.RetrieveUsersDelegate;
import sg.edu.nus.iss.phoenix.user.android.delegate.UpdateUserDelegate;
import sg.edu.nus.iss.phoenix.user.android.ui.MaintainUserScreen;
import sg.edu.nus.iss.phoenix.user.android.ui.UserListScreen;
import sg.edu.nus.iss.phoenix.user.entity.User;

/**
 * Created by wangzuxiu on 17/9/18.
 * Updated by Surbhi, Sriraj
 */

public class UserController {

    private static final String TAG = UserController.class.getName();

    private UserListScreen userListScreen;
    private MaintainUserScreen maintainUserScreen;
    private User ur2edit = null;

    /**
     * Inflates MaintainUserScreen for browsing users.
     */
    public void startUseCase() {
        ur2edit = null;
        Intent intent = new Intent(MainController.getApp(), UserListScreen.class);
        MainController.displayScreen(intent);
    }

    /**
     * Inflates UserListScreen with all the existing users.
     * This method will call asynTack RetrieveUserDelegate to retrieve all the user
     * @param userListScreen The parameter contains the UI instance to be loaded.
     */
    public void onDisplayUserList(UserListScreen userListScreen) {
        this.userListScreen = userListScreen;
        System.out.print("onDisplayUserlist");
        new RetrieveUsersDelegate(this).execute("all");
    }

    /**
     *
     * Display MaintainUserScreen with an specified User object.
     * According to attributes of theis User, it will call the create/edit method of UI instance.
     * @param maintainUserScreen This parameter contains the UI instance to be loaded
     */
    public void onDisplayUser(MaintainUserScreen maintainUserScreen) {
        this.maintainUserScreen = maintainUserScreen;
        if (ur2edit == null)
            maintainUserScreen.createUser();
        else
            maintainUserScreen.editUser(ur2edit);
    }

    /**
     * After retrieved all the users, display UserListScreen with all the list of users.
     * @param users The user list of all the users
     */
    public void usersRetrieved(List<User> users) {
        userListScreen.showUsers(users);
    }

    /**
     * Inflates MaintainUserScreen with an existing user,
     * Provide the functionality to user to edit a existing user.
     * @param user The user want to be edited.
     */
    public void selectEditUser(User user) {
        ur2edit = user;
        Log.v(TAG, "Editing user details: " + user.getRoles() + "...");
        Intent intent = new Intent(MainController.getApp(), MaintainUserScreen.class);
        MainController.displayScreen(intent);
    }

    /**
     * Call asyncTask DeleteUserDelegate to delete a new user with specified attributes.
     * Provide the functionality to user to delete a new user.
     * @param user The user want to be deleted
     */
    public void selectDeleteUser(User user) {
        new DeleteUserDelegate(this).execute(user.getId());
    }

    /**
     * When the user deleted, according to return,
     * startUseCase or call the warning method of maintainUserScreen.
     * @param success The result of the User delete
     */
    public void userDeleted(boolean success) {
        // Go back to UserList screen with refreshed programs.
        if(success){
            startUseCase();
        }else{
            maintainUserScreen.deleteWarning();
        }

    }

    /**
     * Go back to UserList screen with refreshed programs.
     */
    public void selectCancelCreateUser() {
        // Go back to UserList screen with refreshed programs.
        startUseCase();
    }

    /**
     * Inflates MaintainUserScreen with an null object,
     * Provide the functionality to user to create a new user.
     *
     */
    public void selectCreateUser() {
        ur2edit = null;
        Intent intent = new Intent(MainController.getApp(), MaintainUserScreen.class);
        MainController.displayScreen(intent);
    }

    /**
     * Call asyncTask CreateUserDelegate to create a new user with specified attributes.
     * Provide the functionality to user to create a new user.
     * @param user The user want to be created.
     */
    public void selectCreateUser(User user) {
        new CreateUserDelegate(this).execute(user);
    }

    /**
     * When the user created, according to return,
     * startUseCase or call the warning method of maintainUserScreen.
     * @param success The result of the User create
     */
    public void userCreated(boolean success) {
        // Go back to ProgramList screen with refreshed programs.
        if(success){
            startUseCase();
        }else{
            maintainUserScreen.creationWarning();
        }

    }

    /**
     * Call asyncTask UpdateUserDelegate to update a specified user.
     * @param user The existing User object
     * @see UpdateUserDelegate
     */
    public void selectUpdateUser(User user) {
        new UpdateUserDelegate(this).execute(user);
    }

    /**
     * When the user updated, according to return,
     * startUseCase or call the warning method of maintainUserScreen.
     * @param success The result of the User update
     */
    public void userUpdated(boolean success) {
        // Go back to ProgramList screen with refreshed programs.
        startUseCase();
    }

    /**
     * After finished maintaining the user,call the MainController
     */
    public void maintainedUser() {
        ControlFactory.getMainController().maintainedUser();
    }


}
