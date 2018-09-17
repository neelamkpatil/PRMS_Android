package sg.edu.nus.iss.phoenix.schedule.android.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.RetrieveSchedulesDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.ui.MaintainScheduleScreen;
import sg.edu.nus.iss.phoenix.schedule.android.ui.ScheduleListScreen;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;

/**
 * Created by Peiyan on 2/9/18.
 */

public class ScheduleController {
    // Tag for logging.
    private static final String TAG = ScheduleController.class.getName();

    private ScheduleListScreen scheduleListScreen;
    private MaintainScheduleScreen maintainScheduleScreen;
    private ProgramSlot ps2edit = null;

    public void startUseCase() {
        ps2edit = null;
        Intent intent = new Intent(MainController.getApp(), ScheduleListScreen.class);
        MainController.displayScreen(intent);
    }

    public void onDisplayScheduleList(ScheduleListScreen scheduleListScreen) {
        Log.e("ScheduleController", "onDisplayScheduleList");
        this.scheduleListScreen = scheduleListScreen;
        new RetrieveSchedulesDelegate(this).execute("all");
    }

    public void schedulesRetrieved(List<ProgramSlot> programSlots) {
        scheduleListScreen.showSchedules(programSlots);
    }

    public void selectCreateSchedule() {
        ps2edit = null;
        Intent intent = new Intent(MainController.getApp(), MaintainScheduleScreen.class);
        MainController.displayScreen(intent);
    }

    public void selectEditSchedule(ProgramSlot programSlot) {
        ps2edit = programSlot;
        Log.d(TAG, "Editing program slot: " + programSlot.getRadioProgramName() + " "
                + programSlot.getProgramSlotDate() + " " + programSlot.getProgramSlotSttime() + "...");

        Intent intent = new Intent(MainController.getApp(), MaintainScheduleScreen.class);
        Bundle b = new Bundle();
        b.putString("Id", programSlot.getId());
        b.putString("Rpname", programSlot.getRadioProgramName());
        b.putString("Date", programSlot.getProgramSlotDate());
        b.putString("Sttime", programSlot.getProgramSlotSttime());
        b.putString("Duration", programSlot.getProgramSlotDuration());
        b.putString("Presenter", programSlot.getProgramSlotPresenter());
        b.putString("Producer", programSlot.getProgramSlotProducer());
        intent.putExtras(b);

        MainController.displayScreen(intent);
    }

    public void selectCopySchedule(ProgramSlot programSlot) {
        ps2edit = programSlot;
        Log.d(TAG, "Copying program slot 2: " + "id: " + programSlot.getId() + " "
                + programSlot.getRadioProgramName() + " "
                + programSlot.getProgramSlotDate() + " "
                + programSlot.getProgramSlotSttime() + " "
                + programSlot.getProgramSlotPresenter() + "...");

        Intent intent = new Intent(MainController.getApp(), MaintainScheduleScreen.class);
        Bundle b = new Bundle();
        b.putString("Rpname", programSlot.getRadioProgramName());
        b.putString("Duration", programSlot.getProgramSlotDuration());
        b.putString("Presenter", programSlot.getProgramSlotPresenter());
        b.putString("Producer", programSlot.getProgramSlotProducer());
        intent.putExtras(b);

        MainController.displayScreen(intent);
    }

    public void onDisplaySchedule(MaintainScheduleScreen maintainScheduleScreen) {
        this.maintainScheduleScreen = maintainScheduleScreen;
        if (ps2edit == null)
            maintainScheduleScreen.createSchedule();
        else if (ps2edit.getId() == null) {
            maintainScheduleScreen.copySchedule(ps2edit);
        }
        else {
            maintainScheduleScreen.editSchedule(ps2edit);
        }
    }

    public void selectUpdateSchedule(ProgramSlot ps) {

    }

    public void selectDeleteSchedule(String id) {

    }

    public void scheduleDeleted(boolean success) {
        // Go back to ScheduleList screen with refreshed schedules.
        startUseCase();
    }

    public void scheduleUpdated(boolean success) {
        // Go back to ScheduleList screen with refreshed schedules.
        startUseCase();
    }

    public void selectCreateSchedule(ProgramSlot ps) {

    }

    public void scheduleCreated(boolean success) {
        // Go back to ScheduleList screen with refreshed schedules.
        startUseCase();
    }

    public void selectCancelCreateEditSchedule() {
        // Go back to ScheduleList screen with refreshed programs.
        startUseCase();
    }

    public void maintainedSchedule() {
        ControlFactory.getMainController().maintainedSchedule();
    }

    public void selectedProgram(RadioProgram rpSelected, ProgramSlot ps) {
        ps.setRadioProgramName(rpSelected.getRadioProgramName());
        selectEditSchedule(ps);
    }
}
