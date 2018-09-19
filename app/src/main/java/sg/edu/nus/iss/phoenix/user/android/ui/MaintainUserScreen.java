package sg.edu.nus.iss.phoenix.user.android.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.user.entity.User;

/**
 * Created by wangzuxiu on 17/9/18.
 */

public class MaintainUserScreen extends AppCompatActivity {
    // Tag for logging
    private static final String TAG = MaintainUserScreen.class.getName();

    private EditText mURIdEditText;
    private EditText mURNameEditText;
    private EditText mURRolesEditText;
    private User ur2edit = null;
    KeyListener mURIdEditTextKeyListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Find all relevant views that we will need to read user input from
        mURIdEditText = (EditText) findViewById(R.id.maintain_user_id);
        mURNameEditText = (EditText) findViewById(R.id.maintain_user_name);
        mURRolesEditText = (EditText) findViewById(R.id.maintain_user_role);
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

                }
                else { // Edited.

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
//        this.ur2edit = null;
//        mURIdEditText.setText("", TextView.BufferType.EDITABLE);
//        mURNameEditText.setText("", TextView.BufferType.EDITABLE);
//        mURRolesEditText.setText("", TextView.BufferType.EDITABLE);
//        mURIdEditText.setKeyListener(mURIdEditTextKeyListener);
    }

    public void editUser(User ur2edit) {
        this.ur2edit = ur2edit;
        if (ur2edit != null) {
            mURIdEditText.setText(ur2edit.getId(), TextView.BufferType.NORMAL);
            mURNameEditText.setText(ur2edit.getName(), TextView.BufferType.EDITABLE);
//            mURRolesEditText.setText(ur2edit.getRoles(), TextView.BufferType.EDITABLE);
            mURIdEditText.setKeyListener(null);
        }
    }
}