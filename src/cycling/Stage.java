package cycling;


import java.time.LocalDateTime;
import java.util.ArrayList;

public class Stage {
    private String stageName;
    private String description;
    private double length;
    private LocalDateTime startTime;
    private StageType type;
    private int stageId;
    private static ArrayList<Integer> stageIds;
    private static ArrayList<Segment> segments;

    public Stage(String stageName, String description, LocalDateTime startTime, StageType type) {
        this.stageName = stageName;
        this.description = description;
        this.startTime = startTime;
        this.type = type;

        if(stageIds.size() == 0) {
            stageId = 0;
            stageIds.add(stageId);
        } else {
            stageId = stageIds.get(stageIds.size() - 1) + 1;
            stageIds.add(stageId);
        }
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

    public ArrayList<Segment> getStageSegments( int stageId ) { return segments; }

    public int addIntermediateSprintToStage(int stageId, double location) { return 0; }

    public void removeSegment(int segmentId) {
        segments.remove(Integer.valueOf(segmentId));
    }
}
