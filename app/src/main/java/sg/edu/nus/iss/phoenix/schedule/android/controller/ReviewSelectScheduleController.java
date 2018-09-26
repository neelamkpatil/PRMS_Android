package sg.edu.nus.iss.phoenix.schedule.android.controller;

import android.content.Intent;
import android.util.Log;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.RetrieveSchedulesDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.ui.ReviewSelectScheduleScreen;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;

/**
 * Created by mia on 25/9/18.
 */

public class ReviewSelectScheduleController {
    // Tag for logging.
    private static final String TAG = ReviewSelectScheduleController.class.getName();

    private ReviewSelectScheduleScreen reviewSelectScheduleScreen;
    private ProgramSlot psSelected = null;

    public void startUseCase() {
        psSelected = null;
        Intent intent = new Intent(MainController.getApp(), ReviewSelectScheduleScreen.class);
        MainController.displayScreen(intent);
    }

    public void onDisplay(ReviewSelectScheduleScreen reviewSelectScheduleScreen) {
        this.reviewSelectScheduleScreen = reviewSelectScheduleScreen;
        new RetrieveSchedulesDelegate(this).execute("all");
    }

    public void schedulesRetrieved(List<ProgramSlot> programSlots) {
        reviewSelectScheduleScreen.showSchedules(programSlots);
    }

    public void selectSchedule(ProgramSlot programSlot) {
        psSelected = programSlot;
        Log.v(TAG, "Selected program slot: " + programSlot.getRadioProgramName() + ".");
        // To call the base use case controller with the selected radio program.
        // At present, call the MainController instead.
        ControlFactory.getMainController().selectedSchedule(psSelected);
    }

    public void selectCancel() {
        psSelected = null;
        Log.v(TAG, "Cancelled the selection of program slot.");
        // To call the base use case controller;
        // At present, call the MainController instead.
        ControlFactory.getMainController().selectedSchedule(psSelected);

    }
}
