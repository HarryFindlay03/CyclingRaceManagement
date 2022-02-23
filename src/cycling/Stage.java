package cycling;


import java.time.LocalDateTime;
import java.util.ArrayList;

public class Stage {

    public enum StageType {
        FLAT,
        HIGH_MOUNTAIN,
        MEDIUM_MOUNTAIN,
        TT
    }

    private String stageName;
    private String description;
    private double length;
    private LocalDateTime startTime;
    private StageType type;
    private int stageId;
    private static ArrayList<Integer> stageIds;
    //TODO: private static ArrayList<Segment> segments;

    public Stage(String stageName, String description) {
        this.stageName = stageName;
        this.description = description;

        if(stageIds.size() == 0) {
            stageId = 0;
            stageIds.add(stageId);
        } else {
            stageId = stageIds.get(stageIds.size() - 1) + 1;
            stageIds.add(stageId);
        }
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


}
