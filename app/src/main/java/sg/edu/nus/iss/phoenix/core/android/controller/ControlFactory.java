package sg.edu.nus.iss.phoenix.core.android.controller;

import sg.edu.nus.iss.phoenix.authenticate.android.controller.LoginController;
import sg.edu.nus.iss.phoenix.radioprogram.android.controller.ProgramController;
import sg.edu.nus.iss.phoenix.radioprogram.android.controller.ReviewSelectProgramController;
import sg.edu.nus.iss.phoenix.schedule.android.controller.ReviewSelectScheduleController;
import sg.edu.nus.iss.phoenix.user.android.controller.ReviewSelectUserController;
import sg.edu.nus.iss.phoenix.user.android.controller.UserController;
import sg.edu.nus.iss.phoenix.schedule.android.controller.ScheduleController;

public class ControlFactory {
    private static MainController mainController = null;
    private static LoginController loginController = null;
    private static ProgramController programController = null;
    private static ReviewSelectProgramController reviewSelectProgramController = null;
    private static UserController userController=null;
    private static ReviewSelectUserController reviewSelectUserController=null;
	private static ScheduleController scheduleController = null;
	private static ReviewSelectScheduleController reviewSelectScheduleController = null;
	
    public static MainController getMainController() {
        if (mainController == null) mainController = new MainController();
        return mainController;
    }

    public static LoginController getLoginController() {
        if (loginController == null) loginController = new LoginController();
        return loginController;
    }

    public static ProgramController getProgramController() {
        if (programController == null) programController = new ProgramController();
        return programController;
    }

    public static ReviewSelectScheduleController getReviewSelectScheduleController() {
        if (reviewSelectScheduleController == null) reviewSelectScheduleController = new ReviewSelectScheduleController();
        return reviewSelectScheduleController;
    }

    public static ReviewSelectProgramController getReviewSelectProgramController() {
        if (reviewSelectProgramController == null) reviewSelectProgramController = new ReviewSelectProgramController();
        return reviewSelectProgramController;
    }

    public static ReviewSelectUserController getReviewSelectUserController() {
        if (reviewSelectUserController == null) reviewSelectUserController = new ReviewSelectUserController();
        return reviewSelectUserController;
    }

    public static UserController getUserController() {
        if (userController == null) userController = new UserController();
        return userController;
    }
	
	public static ScheduleController getScheduleController() {
        if (scheduleController == null) scheduleController = new ScheduleController();
        return scheduleController;
    }
}
