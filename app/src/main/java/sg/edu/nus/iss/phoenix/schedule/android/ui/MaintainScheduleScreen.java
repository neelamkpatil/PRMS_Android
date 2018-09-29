package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Toast;

import java.text.SimpleDateFormat;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.schedule.android.controller.ScheduleController;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.user.android.controller.ReviewSelectUserController;
import sg.edu.nus.iss.phoenix.user.android.ui.ReviewSelectUserScreen;

/**
 * Created by Peiyan on 2/9/18.
 */

public class MaintainScheduleScreen extends AppCompatActivity {
    // Tag for logging
    private static final String TAG = MaintainScheduleScreen.class.getName();

    private TextView mPSIdEditText;
    private EditText mPSNameEditText;
    private EditText mPSDateEditText;
    private EditText mPSSttimeEditText;
    private EditText mPSDurationEditText;
    private EditText mPSPresenterEditText;
    private EditText mPSProducerEditText;
    private ProgramSlot tmpPS = null;
    private Button mPSNameSelectButton;
    private Button mPSPresenteSelectButton;
    private Button mPSProducerSelectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

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
                saveCurrentProgramSlot();
                ControlFactory.getScheduleController().setTmpProgramSlot(tmpPS);
                ControlFactory.getReviewSelectProgramController().startUseCase();
            }
        });
        mPSPresenteSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCurrentProgramSlot();
                ControlFactory.getScheduleController().setTmpProgramSlot(tmpPS);
                ControlFactory.getReviewSelectUserController().startUseCase("presenter");
            }
        });
        mPSProducerSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCurrentProgramSlot();
                ControlFactory.getScheduleController().setTmpProgramSlot(tmpPS);
                ControlFactory.getReviewSelectUserController().startUseCase("producer");
            }
        });
    }

    public void saveCurrentProgramSlot(){

        tmpPS=getProgramSlot();
        System.out.println("In post create");
        if(mPSIdEditText.getText().toString()!=null){
            tmpPS.setId(mPSIdEditText.getText().toString());
            System.out.println("ID"+mPSIdEditText.getText().toString());
        }

        if(!mPSNameEditText.getText().toString().equals("")){
            System.out.println("name"+mPSNameEditText.getText().toString());
            tmpPS.setRadioProgramName(mPSNameEditText.getText().toString());

        }
        if(!mPSDateEditText.getText().toString().equals("")){
            tmpPS.setProgramSlotDate(mPSDateEditText.getText().toString());
            System.out.println("test"+mPSDateEditText.getText().toString());
        }
        if (!mPSSttimeEditText.getText().toString().equals("")) {
            tmpPS.setProgramSlotSttime(mPSSttimeEditText.getText().toString());
            System.out.println("time"+mPSSttimeEditText.getText().toString());
        }
        if(!mPSDurationEditText.getText().toString().equals("")){
            tmpPS.setProgramSlotDuration(mPSDurationEditText.getText().toString());
            System.out.println("duration"+mPSDurationEditText.getText().toString());
        }
        if(!mPSPresenterEditText.getText().toString().equals("")){
            tmpPS.setProgramSlotPresenter(mPSPresenterEditText.getText().toString());
        }
        if(!mPSProducerEditText.getText().toString().equals("")){
            tmpPS.setProgramSlotProducer(mPSProducerEditText.getText().toString());
        }

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
        if (tmpPS == null || tmpPS.getId() == null || tmpPS.getId().equals("-1")) {
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
                System.out.println("tmps"+tmpPS);
                if (validateFormat()) {
                    if (tmpPS == null || tmpPS.getId() == null || tmpPS.getId().equalsIgnoreCase("-1")) { // Newly created.
                        Log.v(TAG, "Saving schedule " + mPSNameEditText.getText().toString() + "...");
                        ProgramSlot ps = new ProgramSlot(mPSIdEditText.getText().toString(),mPSNameEditText.getText().toString(),
                                mPSDateEditText.getText().toString(),mPSSttimeEditText.getText().toString(), mPSDurationEditText.getText().toString(),mPSPresenterEditText.getText().toString(),mPSProducerEditText.getText().toString());
                        ControlFactory.getScheduleController().selectCreateSchedule(ps);
                        System.out.println("pssave:"+ps);
                    }
                    else { // Edited.
                        if (tmpPS != null ) { // edited
                            Log.v(TAG, "Saving schedule " + mPSNameEditText.getText().toString() + "...");
                            ProgramSlot ps = new ProgramSlot(mPSIdEditText.getText().toString(), mPSNameEditText.getText().toString(),
                                    mPSDateEditText.getText().toString(), mPSSttimeEditText.getText().toString(), mPSDurationEditText.getText().toString(), mPSPresenterEditText.getText().toString(), mPSProducerEditText.getText().toString());
                            ControlFactory.getScheduleController().selectUpdateSchedule(ps);
                            System.out.println("pssave:" + ps);
                        }
                    }
                }

                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                Log.v(TAG, "Deleting program slot " + tmpPS.getRadioProgramName() + "...");
                ControlFactory.getScheduleController().selectDeleteSchedule(tmpPS);
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
        this.tmpPS = null;
        mPSIdEditText.setText("-1");
        mPSNameEditText.setText("", TextView.BufferType.NORMAL);
        mPSDateEditText.setText("", TextView.BufferType.EDITABLE);
        mPSSttimeEditText.setText("", TextView.BufferType.EDITABLE);
        mPSDurationEditText.setText("", TextView.BufferType.EDITABLE);
        mPSPresenterEditText.setText("", TextView.BufferType.NORMAL);
        mPSProducerEditText.setText("", TextView.BufferType.NORMAL);

    }

    public void editSchedule(ProgramSlot ps2edit) {
        this.tmpPS = ps2edit;
        if (ps2edit != null) {
            mPSIdEditText.setText(ps2edit.getId());
            mPSNameEditText.setText(ps2edit.getRadioProgramName(), TextView.BufferType.NORMAL);
            mPSDateEditText.setText(ps2edit.getProgramSlotDate(), TextView.BufferType.EDITABLE);
            mPSSttimeEditText.setText(ps2edit.getProgramSlotSttime(), TextView.BufferType.EDITABLE);
            mPSDurationEditText.setText(ps2edit.getProgramSlotDuration(), TextView.BufferType.EDITABLE);
            mPSPresenterEditText.setText(ps2edit.getProgramSlotPresenter(), TextView.BufferType.NORMAL);
            mPSProducerEditText.setText(ps2edit.getProgramSlotProducer(), TextView.BufferType.NORMAL);
        }
    }

    public void copySchedule(ProgramSlot ps2edit) {
        this.tmpPS = ps2edit;
        if (ps2edit != null) {
            mPSIdEditText.setText("-1");
            mPSNameEditText.setText(ps2edit.getRadioProgramName(), TextView.BufferType.NORMAL);
            mPSDateEditText.setText("", TextView.BufferType.EDITABLE);
            mPSSttimeEditText.setText("", TextView.BufferType.EDITABLE);
            mPSDurationEditText.setText(ps2edit.getProgramSlotDuration(), TextView.BufferType.EDITABLE);
            mPSPresenterEditText.setText(ps2edit.getProgramSlotPresenter(), TextView.BufferType.NORMAL);
            mPSProducerEditText.setText(ps2edit.getProgramSlotProducer(), TextView.BufferType.NORMAL);
            mPSNameSelectButton.setClickable(false);
            mPSNameSelectButton.setBackgroundColor(Color.parseColor("#D3D3D3"));
            mPSPresenteSelectButton.setClickable(false);
            mPSPresenteSelectButton.setBackgroundColor(Color.parseColor("#D3D3D3"));
            mPSProducerSelectButton.setClickable(false);
            mPSProducerSelectButton.setBackgroundColor(Color.parseColor("#D3D3D3"));

        }
    }

    public ProgramSlot getProgramSlot() {
        return new ProgramSlot();
    }

    private boolean validateFormat() {
        boolean flag = true;
        if (mPSNameEditText.getText().toString().equals("")){
            mPSNameEditText.setError("Please select a radio program!");
            Toast.makeText(this, "Please select a radio program!", Toast.LENGTH_LONG).show();
            flag = false;
        }
        if (mPSDateEditText.getText().toString().equals("")){
            mPSDateEditText.setError("Please input a date!");
            flag = false;
        } else if (!isValidDate(mPSDateEditText.getText().toString())) {
            mPSDateEditText.setError("Please input a valid future date!");
            flag = false;
        }
        if (mPSSttimeEditText.getText().toString().equals("")){
            mPSSttimeEditText.setError("Please input a time!");
            flag = false;
        } else if (!isValidTime(mPSSttimeEditText.getText().toString())) {
            mPSSttimeEditText.setError("Please input a valid time!");
            flag = false;
        }
        if (mPSDurationEditText.getText().toString().equals("")){
            mPSDurationEditText.setError("Please input a duration!");
            flag = false;
        } else if (!isValidTime(mPSDurationEditText.getText().toString())) {
            mPSDurationEditText.setError("Please input a valid duration!");
            flag = false;
        }
        if (mPSPresenterEditText.getText().toString().equals("")){
            mPSPresenterEditText.setError("Please select a presenter!");
            flag = false;
        }
        if (mPSProducerEditText.getText().toString().equals("")){
            mPSProducerEditText.setError("Please select a producer!");
            flag = false;
        }
        return flag;
    }


    protected boolean isValidDate(String date) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dd = fmt.parse(date);
            java.util.Date now = new java.util.Date();
            Log.d(TAG, "date: " + dd.toString() + " now " + now.toString());
            if (dd.after(now)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isValidTime(String time) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("hh:mm:ss");
            java.util.Date tt = fmt.parse(time);
            Log.d(TAG, "time: " + tt.toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void warning(String action) {
        if (action.equals("create")) {
            Toast.makeText(this, "Program slot cannot be created due to time overlapping!", Toast.LENGTH_LONG).show();
        } else if (action.equals("update")) {
            Toast.makeText(this, "Program slot cannot be updated due to time overlapping!", Toast.LENGTH_LONG).show();
        }

    }
}
