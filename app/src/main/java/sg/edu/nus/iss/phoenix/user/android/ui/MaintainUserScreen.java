package sg.edu.nus.iss.phoenix.user.android.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.user.entity.Role;
import sg.edu.nus.iss.phoenix.user.entity.User;

/**
 * Created by wangzuxiu on 17/9/18.
 */

public class MaintainUserScreen extends AppCompatActivity {
    // Tag for logging
    private static final String TAG = MaintainUserScreen.class.getName();

    private EditText mURIdEditText;
    private EditText mURNameEditText;
    private ArrayList<Role> roles = new ArrayList<>();
    private ArrayList<String> userRoles = new ArrayList<>();
    private EditText mURPasswordEditText;
    private User ur2edit = null;
    KeyListener mURIdEditTextKeyListener = null;
    private boolean adminChecked;
    private boolean managerChecked;
    private boolean presenterChecked;
    private boolean producerChecked;
    private CheckBox mUserAdminCheckbox;
    private CheckBox mUserManagerCheckbox;
    private CheckBox mUserPresenterCheckbox;
    private CheckBox mUserProducerCheckbox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Find all relevant views that we will need to read user input from
        mURIdEditText = (EditText) findViewById(R.id.maintain_user_id);
        mURNameEditText = (EditText) findViewById(R.id.maintain_user_name);
        mURPasswordEditText = (EditText) findViewById(R.id.maintain_user_password);
        mUserAdminCheckbox = (CheckBox) findViewById(R.id.maintain_user_role_admin)   ;
        mUserManagerCheckbox = (CheckBox) findViewById(R.id.maintain_user_role_manager);
        mUserPresenterCheckbox = (CheckBox) findViewById(R.id.maintain_user_role_presenter);
        mUserProducerCheckbox = (CheckBox) findViewById(R.id.maintain_user_role_producer);
        // Keep the KeyListener for name EditText so as to enable editing after disabling it.

        mURIdEditTextKeyListener = mURIdEditText.getKeyListener();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ControlFactory.getUserController().onDisplayUser(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new radioprogram, hide the "Delete" menu item.
        if (ur2edit == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save radio program.
                if (ur2edit == null) { // Newly created.
//                    ur2edit.setRoles(userRoles);
                    //validation
                    CheckBox mURadminCheckbox = (CheckBox) findViewById(R.id.maintain_user_role_admin)   ;
                    CheckBox mURmanagerCheckbox = (CheckBox) findViewById(R.id.maintain_user_role_manager);
                    CheckBox mURpresenterCheckbox = (CheckBox) findViewById(R.id.maintain_user_role_presenter);
                    CheckBox mURproducerCheckbox = (CheckBox) findViewById(R.id.maintain_user_role_producer);
                   adminChecked  = mURadminCheckbox.isChecked();
                   managerChecked  = mURmanagerCheckbox.isChecked();
                   presenterChecked  = mURpresenterCheckbox.isChecked();
                    producerChecked = mURproducerCheckbox.isChecked();
                    if(adminChecked){
                        userRoles.add("admin");
                    }
                    if(managerChecked){
                        userRoles.add("manager");
                    }
                    if(presenterChecked){
                        userRoles.add("presenter");
                    }
                    if(producerChecked){
                        userRoles.add("producer");
                    }
                    for (String role: userRoles){
                        if(role.equals("admin")){
                            Role roletobeadded = new Role("admin", "system administrator");
                            roles.add(roletobeadded);

                        }
                        if(role.equals("manager")){
                            Role roletobeadded = new Role("manager", "station manager");
                            roles.add(roletobeadded);

                        }
                        if(role.equals("presenter")){
                            Role roletobeadded = new Role("presenter", "radio program presenter");
                            roles.add(roletobeadded);

                        }
                        if(role.equals("producer")){
                            Role roletobeadded = new Role("producer", "program producer");
                            roles.add(roletobeadded);

                        }
                    }
                    //ur2edit.setRoles(roles);
                    Log.v(TAG, "Saving user " + mURIdEditText.getText().toString() + "...");
                    User user = new User(mURIdEditText.getText().toString(),mURPasswordEditText.getText().toString(),mURNameEditText.getText().toString(),roles);
                   if(isvalid()) {
                        ControlFactory.getUserController().selectCreateUser(user);
                    }

                }
                else { // Edited.
                    Log.v(TAG, "Saving user " + ur2edit.getName() + "...");
                    ur2edit.setName(mURNameEditText.getText().toString());
                    ur2edit.setId(mURIdEditText.getText().toString());
                    ur2edit.setPassword(mURPasswordEditText.getText().toString());
                    Log.v(TAG, "user role ...." +ur2edit.getRoles().get(0).getRole());
                    CheckBox mURadminCheckbox = (CheckBox) findViewById(R.id.maintain_user_role_admin)   ;
                    CheckBox mURmanagerCheckbox = (CheckBox) findViewById(R.id.maintain_user_role_manager);
                    CheckBox mURpresenterCheckbox = (CheckBox) findViewById(R.id.maintain_user_role_presenter);
                    CheckBox mURproducerCheckbox = (CheckBox) findViewById(R.id.maintain_user_role_producer);
                    adminChecked  = mURadminCheckbox.isChecked();
                    managerChecked  = mURmanagerCheckbox.isChecked();
                    presenterChecked  = mURpresenterCheckbox.isChecked();
                    producerChecked = mURproducerCheckbox.isChecked();
                    if(adminChecked){
                        userRoles.add("admin");
                    }
                    if(managerChecked){
                        userRoles.add("manager");
                    }
                    if(presenterChecked){
                        userRoles.add("presenter");
                    }
                    if(producerChecked){
                        userRoles.add("producer");
                    }

                    for (String role: userRoles){
                        if(role.equals("admin")){
                            Role roletobeadded = new Role("admin", "system administrator");
                            roles.add(roletobeadded);

                        }
                        if(role.equals("manager")){
                            Role roletobeadded = new Role("manager", "station manager");
                            roles.add(roletobeadded);

                        }
                        if(role.equals("presenter")){
                            Role roletobeadded = new Role("presenter", "radio program presenter");
                            roles.add(roletobeadded);

                        }
                        if(role.equals("producer")){
                            Role roletobeadded = new Role("producer", "program producer");
                            roles.add(roletobeadded);

                        }
                    }
                    ur2edit.setRoles(roles);
                    if(isvalid()) {
                        ControlFactory.getUserController().selectUpdateUser(ur2edit);
                    }
                }
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                Log.v(TAG, "Deleting user " + ur2edit.getId() + "...");
                ControlFactory.getUserController().selectDeleteUser(ur2edit);
                return true;
            // Respond to a click on the "Cancel" menu option
            case R.id.action_cancel:
                Log.v(TAG, "Canceling creating/editing radio program...");
                ControlFactory.getUserController().selectCancelCreateUser();
                return true;
        }

        return true;
    }

    private boolean isvalid() {
        boolean isValid = true;
        String userID = mURIdEditText.getText().toString();
        String userName = mURNameEditText.getText().toString();
        String password = mURPasswordEditText.getText().toString();

        if(userID.isEmpty()){
            mURIdEditText.setError("Please enter userID");
            isValid =false;
        }
        if(userName.isEmpty()){
            mURNameEditText.setError("Please enter username");
            isValid = false;
        }
        if(password.isEmpty()){
            mURPasswordEditText.setError("Password can not be empty");
            isValid = false;
        }

        if(!(adminChecked||managerChecked||presenterChecked||producerChecked)){
            Toast.makeText(getApplicationContext(), "Please enter roles", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;

    }

    @Override
    public void onBackPressed() {
        Log.v(TAG, "Canceling creating/editing user...");
        ControlFactory.getUserController().selectCancelCreateUser();
    }

    public void createUser() {
        this.ur2edit = null;
        mURIdEditText.setText("", TextView.BufferType.EDITABLE);
        mURNameEditText.setText("", TextView.BufferType.EDITABLE);
        mURPasswordEditText.setText("", TextView.BufferType.EDITABLE);
       // mUserAdminCheckbox.setChecked(Boolean.TRUE);
       // checkbox.getText == roles.getRole() checkbox.ischecked
    }

    public void editUser(User ur2edit) {
        this.ur2edit = ur2edit;
        if (ur2edit != null) {
            mURIdEditText.setText(ur2edit.getId(), TextView.BufferType.NORMAL);
            mURNameEditText.setText(ur2edit.getName(), TextView.BufferType.EDITABLE);
            mURPasswordEditText.setText(ur2edit.getPassword(), TextView.BufferType.EDITABLE);
            Log.d(TAG, "passwod+++++++++++++++" + ur2edit.getPassword());

            ArrayList<Role> roles = ur2edit.getRoles();
            for(Role role : roles){
                Log.d(TAG, "role+++++++++++++++" + role.getRole());
                if(role.getRole().equals("admin")){
                    mUserAdminCheckbox.setChecked(true);
                }
                if(role.getRole().equals("manager")){
                    mUserManagerCheckbox.setChecked(true);
                }
                if(role.getRole().equals("presenter")){
                    mUserPresenterCheckbox.setChecked(true);
                }
                if(role.getRole().equals("producer")){
                    mUserProducerCheckbox.setChecked(true);
                }

            }
//            mURRolesEditText.setText(ur2edit.getRoles(), TextView.BufferType.EDITABLE);
            mURIdEditText.setKeyListener(null);
        }
    }
    public void deleteWarning() {
        Log.d(TAG,"can not create user! already exists");
        Toast.makeText(this,"User can not be deleted because he is assigned to a program slot!",Toast.LENGTH_LONG).show();
    }

    public void creationWarning() {
        Log.d(TAG,"can not create user! already exists");
        Toast.makeText(this,"User already exists!",Toast.LENGTH_LONG).show();
    }
}