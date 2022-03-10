package cycling;

import java.time.LocalTime;
import java.util.ArrayList;

public class Result {
    private int stageId;
    private int riderId;
    private ArrayList<LocalTime> checkpoints;

    public Result(int stageId, int riderId, LocalTime... checkpoints) {
        this.stageId = stageId;
        this.riderId = riderId;
        for(LocalTime check : checkpoints) {
            this.checkpoints.add(check);
        }
    }

    public int getRiderId() {
        return riderId;
    }

    public ArrayList<LocalTime> getCheckpoints() {
        return checkpoints;
    }

    public int getStageId() {
        return stageId;
    }

    public LocalTime getFinishTime() {
        return checkpoints.get(checkpoints.size() - 1);
    }
}
