package sg.edu.nus.iss.phoenix.schedule.android.controller;

import android.content.Intent;
import android.util.Log;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.CreateScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.DeleteScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.RetrieveSchedulesDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.UpdateScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.ui.MaintainScheduleScreen;
import sg.edu.nus.iss.phoenix.schedule.android.ui.ScheduleListScreen;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.user.entity.User;

/**
 * Created by Peiyan on 29/9/18.
 * Updated by Neelam-Create,Update use case 25/09/208
 * Peiyan-Copy use schedule use case
 */


public class ScheduleController {
    // Tag for logging.
    private static final String TAG = ScheduleController.class.getName();

    private ScheduleListScreen scheduleListScreen;
    private MaintainScheduleScreen maintainScheduleScreen;
    private ProgramSlot ps2edit = null;

    /**
     * Inflates the ScheduleListScreen for browsing schedules.
     */
    public void startUseCase() {
        ps2edit = null;
        Intent intent = new Intent(MainController.getApp(), ScheduleListScreen.class);
        MainController.displayScreen(intent);
    }

    /**
     * Inflates ScheduleListScreen with all the existing schedules.
     * This method will call asynTack RetrieveSchedulesDelegate to retrieve all the schedules.
     *
     * @param scheduleListScreen This parameter contains the UI instance to be loaded.
     * @see RetrieveSchedulesDelegate
     */
    public void onDisplayScheduleList(ScheduleListScreen scheduleListScreen) {
        this.scheduleListScreen = scheduleListScreen;
        new RetrieveSchedulesDelegate(this).execute("all");
    }

    /**
     * After retrieved all the schedules, display ScheduleListScreen with all the existing schedules.
     *
     * @param programSlots This parameter contains the ProgramSlot objects to be loaded.
     */
    public void schedulesRetrieved(List<ProgramSlot> programSlots) {
        scheduleListScreen.showSchedules(programSlots);
    }

    /**
     * Inflates MaintainScheduleScreen with a null object.
     * Provide the functionality to user to create a new schedule.
     */
    public void selectCreateSchedule() {
        ps2edit = null;
        Intent intent = new Intent(MainController.getApp(), MaintainScheduleScreen.class);
        MainController.displayScreen(intent);
    }

    /**
     * Inflates MaintainScheduleScreen with an ProgramSlot object of an existing ProgramSlot.
     * All the attributes of this ProgramSlot will be passed.
     * Provide the functionality to user to edit an existing schedule.
     * @param programSlot The existing ProgramSlot object
     */
    public void selectEditSchedule(ProgramSlot programSlot) {
        ps2edit=programSlot;
        Intent intent = new Intent(MainController.getApp(), MaintainScheduleScreen.class);
        MainController.displayScreen(intent);
    }

    /**
     * Inflates MaintainScheduleScreen with an ProgramSlot object of an existing ProgramSlot.
     * Only RadioProgramName, Duration, Presenter and Producer of the ProgramSlot will be passed.
     * Provide the functionality to user to create a new schedule by reusing pre-defined attributes.
     * @param programSlot The existing ProgramSlot object
     */
    public void selectCopySchedule(ProgramSlot programSlot) {
        ps2edit = programSlot;
        Intent intent = new Intent(MainController.getApp(), MaintainScheduleScreen.class);
        MainController.displayScreen(intent);
    }

    /**
     * Display MaintainScheduleScreen with an specified ProgramSlot object.
     * According to attributes of this ProgramSlot, it will call the create/copy/edit method of UI instance.
     * @param maintainScheduleScreen This parameter contains the UI instance to be loaded
     */
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

    /**
     * Call asyncTask UpdateScheduleDelegate to Update a specified schedule.
     * @param ps The existing ProgramSlot object
     * @see UpdateScheduleDelegate
     */
    public void selectUpdateSchedule(ProgramSlot ps) {
        new UpdateScheduleDelegate(this).execute(ps);

    }

    /**
     * Call asyncTask DeleteScheduleDelegate to delete a specified schedule having this id.
     * @param ps The existing ProgramSlot object
     * @see DeleteScheduleDelegate
     */
    public void selectDeleteSchedule(ProgramSlot ps) {
        new DeleteScheduleDelegate(this).execute(ps.getId());

    }

    /**
     * When the schedule deleted, StartUseCase.
     * @param success The result of the ProgramSlot deletion
     */
    public void scheduleDeleted(boolean success) {
        // Go back to ScheduleList screen with refreshed schedules.
        startUseCase();
    }

    /**
     * When the schedule updated, according to return,
     * startUseCase or call the warning method of maintainScheduleScreen.
     * @param success The result of the ProgramSlot deletion
     * @see MaintainScheduleScreen
     */
    public void scheduleUpdated(boolean success) {
        // Go back to ScheduleList screen with refreshed schedules.
        if (success) {
            startUseCase();
        } else {
            maintainScheduleScreen.warning("update");
        }
    }

    /**
     * Call asyncTask CreateScheduleDelegate to create a new schedule with specified attributes.
     * @param ps The new ProgramSlot object
     * @see CreateScheduleDelegate
     */
    public void selectCreateSchedule(ProgramSlot ps) {
        new CreateScheduleDelegate(this).execute(ps);

    }

    /**
     * When the schedule updated, according to return,
     * startUseCase or call the warning method of maintainScheduleScreen.
     * @param success The result of the ProgramSlot creation
     * @see MaintainScheduleScreen
     */
    public void scheduleCreated(boolean success) {
        // Go back to ScheduleList screen with refreshed schedules.
        if (success) {
            startUseCase();
        } else {
            maintainScheduleScreen.warning("create");
        }

    }

    /**
     * Select Cancel on MaintainScheduleScreen, startUseCase.
     */
    public void selectCancelCreateEditSchedule() {
        // Go back to ScheduleList screen with refreshed programs.
        startUseCase();
    }

    /**
     * After finished maintaining the schedules, call the MainController
     */
    public void maintainedSchedule() {
        ControlFactory.getMainController().maintainedSchedule();
    }

    /**
     * After selected a specified radio program, update the ps2edit
     * and selectEditSchedule with the ps2edit object
     * @param rpSelected The selected Radio ProgramSlot
     */
    public void selectedProgram(RadioProgram rpSelected) {
        if (rpSelected != null) {
            ps2edit.setRadioProgramName(rpSelected.getRadioProgramName());
        }
        selectEditSchedule(ps2edit);
    }

    /**
     * After selected a specified radio program, update the ps2edit
     * and selectEditSchedule with the ps2edit object
     * @param user The selected User
     * @param userType The type of selected User
     */
    public void selectedUser(User user,String userType) {

        if (user != null) {
            if(userType.equalsIgnoreCase("presenter")){
                ps2edit.setProgramSlotPresenter(user.getId());
            }else if (userType.equalsIgnoreCase("producer")){
                ps2edit.setProgramSlotProducer(user.getId());
            }
        }
        selectEditSchedule(ps2edit);

    }

    /**
     * Save the tmpProgramSlot into ps2edit
     */
    public void setTmpProgramSlot(ProgramSlot tmpProgramSlot) {
        ps2edit = tmpProgramSlot;
    }

}
