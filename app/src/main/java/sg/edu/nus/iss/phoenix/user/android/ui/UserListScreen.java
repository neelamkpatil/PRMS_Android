package sg.edu.nus.iss.phoenix.user.android.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.user.entity.User;

/**
 * Created by wangzuxiu on 17/9/18.
 */

public class UserListScreen extends AppCompatActivity {
    private ListView mListView;
    private UserAdapter mURAdapter;
    private User selectedUR = null;
    private static final String TAG = UserListScreen.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        // mRPNameEditText = (EditText) findViewById(R.id.maintain_program_name_text_view);
        // mRPDescEditText = (EditText) findViewById(R.id.maintain_program_desc_text_view);
        // mDurationEditText = (EditText) findViewById(R.id.maintain_program_duration_text_view);

        ArrayList<User> users = new ArrayList<User>();
        mURAdapter = new UserAdapter(this, users);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ControlFactory.getUserController().selectCreateUser();
            }
        });

        mListView = (ListView) findViewById(R.id.radio_ur_list);
        mListView.setAdapter(mURAdapter);

        // Setup the item selection listener
        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                // Log.v(TAG, "Radio program at position " + position + " selected.");
                User ur = (User) adapterView.getItemAtPosition(position);
                // Log.v(TAG, "Radio program name is " + rp.getRadioProgramName());
                selectedUR = ur;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // your stuff
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListView.setSelection(0);

        ControlFactory.getUserController().onDisplayUserList(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        // For UserListScreen, hide the "copy" menu item
        MenuItem menuItem = menu.findItem(R.id.action_copy);
        menuItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "View" menu option
            case R.id.action_view:
                if (selectedUR == null) {
                    // Prompt for the selection of a radio program.
                    Toast.makeText(this, "Select a user first! Use arrow keys on emulator", Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "There is no selected user.");
                } else {
                    Log.v(TAG, "Viewing user: " + selectedUR.getName() + "...");
                    ControlFactory.getUserController().selectEditUser(selectedUR);
                }
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        ControlFactory.getUserController().maintainedUser();
    }

    public void showUsers(List<User> users) {
        mURAdapter.clear();
        for (int i = 0; i < users.size(); i++) {
            mURAdapter.add(users.get(i));
        }
    }
}
