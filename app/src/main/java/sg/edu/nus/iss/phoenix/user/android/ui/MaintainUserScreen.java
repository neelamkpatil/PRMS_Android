package sg.edu.nus.iss.phoenix.user.android.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
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
    private CheckBox mURadminCheckbox;
    private CheckBox mURmanagerCheckbox;
    private CheckBox mURpresenterCheckbox;
    private CheckBox mURproducerCheckbox;
    KeyListener mURIdEditTextKeyListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Find all relevant views that we will need to read user input from
        mURIdEditText = (EditText) findViewById(R.id.maintain_user_id);
        mURNameEditText = (EditText) findViewById(R.id.maintain_user_name);
        mURPasswordEditText = (EditText) findViewById(R.id.maintain_user_password);
        mURadminCheckbox = (CheckBox) findViewById(R.id.maintain_user_role_admin);
        mURmanagerCheckbox = (CheckBox) findViewById(R.id.maintain_user_role_manager);
        mURpresenterCheckbox = (CheckBox) findViewById(R.id.maintain_user_role_presenter);
        mURproducerCheckbox = (CheckBox) findViewById(R.id.maintain_user_role_producer);

/*        CheckBox chk = (CheckBox) findViewById(R.id.maintain_user_role_admin);
        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked){
                    Role role = new Role("admin", "system administrator");
                    roles.add(role);
                }else{

                }

            }
        });*/
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
                    ControlFactory.getUserController().selectCreateUser(user);
                }
                else { // Edited.
                    Log.v(TAG, "Saving user " + ur2edit.getName() + "...");
                    ur2edit.setName(mURNameEditText.getText().toString());
                    ur2edit.setId(mURIdEditText.getText().toString());
                    ur2edit.setPassword(mURPasswordEditText.getText().toString());
                   // ArrayList<Role> roles = ur2edit.getRoles();
/*                    for(Role role : roles){
                        Log.d(TAG, "role save+++++++++++++++" + role.getRole());
                        if(role.getRole().equals("admin")){
                            roles.add( new Role("admin", "system administrator"));
                        }
                        if(role.getRole().equals("manager")){
                            roles.add( new Role("manager", "station manager"));
                        }
                        if(role.getRole().equals("presenter")){
                            roles.add( new Role("presenter", "radio program presenter"));
                        }
                        if(role.getRole().equals("producer")){
                            roles.add( new Role("producer", "program producer"));
                        }

                    }*/
                    Log.v(TAG, "user role ...." +ur2edit.getRoles().get(0).getRole());
/*                    ArrayList<Role> previousRole = ur2edit.getRoles();
                    ArrayList<String> prevRoleString = new ArrayList<>();
                    for(Role role : previousRole){
                        prevRoleString.add(role.getRole());
                    }
                    userRoles.addAll(prevRoleString);*/
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

                    ControlFactory.getUserController().selectUpdateUser(ur2edit);
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
       // mURadminCheckbox.setChecked(Boolean.TRUE);
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
                    mURadminCheckbox.setChecked(true);
                }
                if(role.getRole().equals("manager")){
                    mURmanagerCheckbox.setChecked(true);
                }
                if(role.getRole().equals("presenter")){
                    mURpresenterCheckbox.setChecked(true);
                }
                if(role.getRole().equals("producer")){
                    mURproducerCheckbox.setChecked(true);
                }

            }
//            mURRolesEditText.setText(ur2edit.getRoles(), TextView.BufferType.EDITABLE);
            mURIdEditText.setKeyListener(null);
        }
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.maintain_user_role_admin:
                if (checked) {
                    userRoles.add("admin");
                } else {
                    for(String role: userRoles) {
                        if (role.toString().equals("admin")) {
                            userRoles.remove(role);
                            break;
                        }
                    }
                }
                break;
            case R.id.maintain_user_role_manager:
                if (checked) {
                    userRoles.add("manager");
                } else {
                    for(String role: userRoles) {
                        if (role.toString().equals("manager")) {
                            userRoles.remove(role);
                            break;
                        }
                    }
                }
                break;
            case R.id.maintain_user_role_presenter:
                if (checked) {
                    userRoles.add("presenter");
                } else {
                    for(String role: userRoles) {
                        if (role.toString().equals("presenter")) {
                            userRoles.remove(role);
                            break;
                        }
                    }
                }
                break;
            case R.id.maintain_user_role_producer:
                if (checked) {
                    userRoles.add("presenter");
                } else {
                    for(String role: userRoles) {
                        if (role.toString().equals("presenter")) {
                            userRoles.remove(role);
                            break;
                        }
                    }
                }
                break;
        }
/*                for (String role: userRoles){
                    if(role.equals("admin")){
                        Role roletobeadded = new Role("admin", "system administrator");
                        roles.add(roletobeadded);

                    }
                    if(role.equals("manager")){
                        Role roletobeadded = new Role("manager", "station manager");
                        roles.add(roletobeadded);

                    }
                }*/



    }
}