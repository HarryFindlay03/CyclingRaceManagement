package cycling;


import javax.xml.stream.Location;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Stage class that holds all of the information about a stage
 *  @author Harry Findlay, Vihan Sharma
 */
public class Stage implements java.io.Serializable {
    private String stageName;
    private String description;
    private double length;
    private LocalDateTime startTime;
    private StageType type;

    private int raceId;
    private int stageId;

    //stageState: false -> nothing, true -> 'waiting for results'
    private boolean stageState;

    private static int counter = 0;

    private ArrayList<Segment> segments = new ArrayList<Segment>();

    public Stage(int raceId, String stageName, String description, double length, LocalDateTime startTime, StageType type) {
        this.raceId = raceId;
        this.stageName = stageName;
        this.description = description;
        this.length = length;
        this.startTime = startTime;
        this.type = type;
        stageState = false;

        stageId = counter;
        counter++;
    }

    public int getRaceId() {
        return raceId;
    }

    public int getStageId() {
        return stageId;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime() {
        this.startTime = startTime;
    }

    public StageType getType() {
        return type;
    }

    public void setType(StageType type) {
        this.type = type;
    }

    public ArrayList<Segment> getStageSegments() { return segments; }

    public boolean getStageState() {
        return stageState;
    }

    public void setStageState(boolean stageState) {
        this.stageState = stageState;
    }

    public void addIntermediateSprintToStage(IntermediateSprint intermediateSprint) {
        segments.add(intermediateSprint);
    }

    public void addCategorizedClimbToStage(CategorizedClimb categorizedClimb) {
        segments.add(categorizedClimb);
    }

    public void removeSegment(Segment segment) {
        segments.remove(segment);
    }

}
