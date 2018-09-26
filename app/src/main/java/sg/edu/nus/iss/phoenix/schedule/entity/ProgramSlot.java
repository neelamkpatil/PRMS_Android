package sg.edu.nus.iss.phoenix.schedule.entity;

/**
 * Created by Peiyan on 2/9/18.
 */

public class ProgramSlot {
    private String id;
    private String radioProgramName;
    private String programSlotDate;
    private String programSlotSttime;
    private String programSlotDuration;
    private String programSlotPresenter;
    private String programSlotProducer;

    public ProgramSlot(String id, String radioProgramName, String programSlotDate, String programSlotSttime,
                        String programSlotDuration, String programSlotPresenter, String programSlotProducer) {
        this.id = id;
        this.radioProgramName = radioProgramName;
        this.programSlotDate = programSlotDate;
        this.programSlotSttime = programSlotSttime;
        this.programSlotDuration = programSlotDuration;
        this.programSlotPresenter = programSlotPresenter;
        this.programSlotProducer = programSlotProducer;
    }

    public ProgramSlot(String radioProgramName, String programSlotDate, String programSlotSttime,
                       String programSlotDuration, String programSlotPresenter, String programSlotProducer) {
        this.radioProgramName = radioProgramName;
        this.programSlotDate = programSlotDate;
        this.programSlotSttime = programSlotSttime;
        this.programSlotDuration = programSlotDuration;
        this.programSlotPresenter = programSlotPresenter;
        this.programSlotProducer = programSlotProducer;
    }

    public ProgramSlot(String radioProgramName, String programSlotDuration, String programSlotDate, String programSlotSttime) {
        this.radioProgramName = radioProgramName;
        this.programSlotDuration = programSlotDuration;
        this.programSlotDate = programSlotDate;
        this.programSlotSttime = programSlotSttime;
    }

    public ProgramSlot() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRadioProgramName() {
        return radioProgramName;
    }

    public void setRadioProgramName(String radioProgramName) {
        this.radioProgramName = radioProgramName;
    }

    public String getProgramSlotDate() {
        return programSlotDate;
    }

    public void setProgramSlotDate(String programSlotDate) {
        this.programSlotDate = programSlotDate;
    }

    public String getProgramSlotSttime() {
        return programSlotSttime;
    }

    public void setProgramSlotSttime(String programSlotSttime) {
        this.programSlotSttime = programSlotSttime;
    }

    public String getProgramSlotDuration() {
        return programSlotDuration;
    }

    public void setProgramSlotDuration(String programSlotDuration) {
        this.programSlotDuration = programSlotDuration;
    }

    public String getProgramSlotPresenter() {
        return programSlotPresenter;
    }

    public void setProgramSlotPresenter(String programSlotPresenter) {
        this.programSlotPresenter = programSlotPresenter;
    }

    public String getProgramSlotProducer() {
        return programSlotProducer;
    }

    public void setProgramSlotProducer(String programSlotProducer) {
        this.programSlotProducer = programSlotProducer;
    }

    @Override
    public String toString() {
        return getId()+" "+getRadioProgramName()+" "+getProgramSlotDate();
    }
}
