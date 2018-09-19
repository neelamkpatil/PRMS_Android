package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;

/**
 * Created by Peiyan on 2/9/18.
 */

public class MaintainScheduleScreen extends AppCompatActivity {
    // Tag for logging
    private static final String TAG = MaintainScheduleScreen.class.getName();

    // For displaying, ProgramName, Presenter, Producer should be TextView
    // Currently we are using EditText for testing convenience
    private TextView mPSIdEditText;
    private EditText mPSNameEditText;
    private EditText mPSDateEditText;
    private EditText mPSSttimeEditText;
    private EditText mPSDurationEditText;
    private EditText mPSPresenterEditText;
    private EditText mPSProducerEditText;
    private ProgramSlot ps2edit = null;
    private Button mPSNameSelectButton;
    private Button mPSPresenteSelectButton;
    private Button mPSProducerSelectButton;

//    KeyListener mPSNameEditTextKeyListener = null;
//    KeyListener mPSPresenterEditTextKeyListener = null;
//    KeyListener mPSProducerEditTextKeyListener = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        // Find all relevant views that we will need to read user input from
        // TextView id only for coding convenience, it will be delete when implementation finished
        mPSIdEditText = (TextView) findViewById(R.id.maintain_schedule_id_text_view);
        mPSNameEditText = (EditText) findViewById(R.id.maintain_schedule_program_name_text_view);
        mPSDateEditText = (EditText) findViewById(R.id.maintain_schedule_date_text_view);
        mPSSttimeEditText = (EditText) findViewById(R.id.maintain_schedule_sttime_text_view);
        mPSDurationEditText = (EditText) findViewById(R.id.maintain_schedule_duration_text_view);
        mPSPresenterEditText = (EditText) findViewById(R.id.maintain_schedule_presenter_text_view);
        mPSProducerEditText = (EditText) findViewById(R.id.maintain_schedule_producer_text_view);

        mPSNameSelectButton = (Button) findViewById(R.id.select_radio_program_button);
        mPSPresenteSelectButton = (Button) findViewById(R.id.select_presenter_button);
        mPSProducerSelectButton = (Button) findViewById(R.id.select_producer_button);

        mPSNameSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControlFactory.getReviewSelectProgramController().startUseCase();
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ControlFactory.getScheduleController().onDisplaySchedule(this);
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
        // If this is a new program slot, hide the "Delete" menu item.
        if (ps2edit == null) {
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
                if (ps2edit == null || ps2edit.getId() == null) { // Newly created.

                }
                else { // Edited.

                }
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                Log.v(TAG, "Deleting program slot " + ps2edit.getRadioProgramName() + "...");
                ControlFactory.getScheduleController().selectDeleteSchedule(ps2edit);
                return true;
            // Respond to a click on the "Cancel" menu option
            case R.id.action_cancel:
                Log.v(TAG, "Canceling creating/editing program slot...");
                ControlFactory.getScheduleController().selectCancelCreateEditSchedule();
                return true;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        Log.v(TAG, "Canceling creating/editing program slot...");
        ControlFactory.getScheduleController().selectCancelCreateEditSchedule();
    }

    public void createSchedule() {
        this.ps2edit = null;
        mPSIdEditText.setText("-1");
        mPSNameEditText.setText("", TextView.BufferType.EDITABLE);
        mPSDateEditText.setText("", TextView.BufferType.EDITABLE);
        mPSSttimeEditText.setText("", TextView.BufferType.EDITABLE);
        mPSDurationEditText.setText("", TextView.BufferType.EDITABLE);
        mPSPresenterEditText.setText("", TextView.BufferType.EDITABLE);
        mPSProducerEditText.setText("", TextView.BufferType.EDITABLE);

    }

    public void editSchedule(ProgramSlot ps2edit) {
        this.ps2edit = ps2edit;
        if (ps2edit != null) {
            mPSIdEditText.setText(ps2edit.getId());
            mPSNameEditText.setText(ps2edit.getRadioProgramName(), TextView.BufferType.EDITABLE);
            mPSDateEditText.setText(ps2edit.getProgramSlotDate(), TextView.BufferType.EDITABLE);
            mPSSttimeEditText.setText(ps2edit.getProgramSlotSttime(), TextView.BufferType.EDITABLE);
            mPSDurationEditText.setText(ps2edit.getProgramSlotDuration(), TextView.BufferType.EDITABLE);
            mPSPresenterEditText.setText(ps2edit.getProgramSlotPresenter(), TextView.BufferType.EDITABLE);
            mPSProducerEditText.setText(ps2edit.getProgramSlotProducer(), TextView.BufferType.EDITABLE);
        }
    }

    public void copySchedule(ProgramSlot ps2edit) {
        this.ps2edit = ps2edit;
        if (ps2edit != null) {
            mPSIdEditText.setText("-1");
            mPSNameEditText.setText(ps2edit.getRadioProgramName(), TextView.BufferType.NORMAL);
            mPSDateEditText.setText("", TextView.BufferType.EDITABLE);
            mPSSttimeEditText.setText("", TextView.BufferType.EDITABLE);
            mPSDurationEditText.setText(ps2edit.getProgramSlotDuration(), TextView.BufferType.EDITABLE);
            mPSPresenterEditText.setText(ps2edit.getProgramSlotPresenter(), TextView.BufferType.NORMAL);
            mPSProducerEditText.setText(ps2edit.getProgramSlotProducer(), TextView.BufferType.NORMAL);
            mPSNameEditText.setKeyListener(null);

        }
    }

}
