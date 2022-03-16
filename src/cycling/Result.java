package cycling;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Result {
    private int stageId;
    private int riderId;
    private ArrayList<LocalTime> checkpoints;
    private static ArrayList<Result> CyclingPortalResults = new ArrayList<Result>();

    public Result(int stageId, int riderId, LocalTime... checkpoints) {
        this.stageId = stageId;
        this.riderId = riderId;
        for(LocalTime check : checkpoints) {
            this.checkpoints.add(check);
        }
    }
    public static void addResult(Result result ) {
        CyclingPortalResults.add(result);
    }
    public static void removeResult(Result result) {
        CyclingPortalResults.remove(result);
    }
    public static ArrayList<Result> getCyclingPortalResults() {
        return CyclingPortalResults;
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

    public void setFinishTime(LocalTime finishTime) {
        checkpoints.remove(checkpoints.size() - 1);
        checkpoints.add(finishTime);
    }

    public LocalTime getElapsedTime(LocalTime startTime, LocalTime finishTime) {

        LocalTime ElapsedTime;
        long seconds = ChronoUnit.HOURS.between(finishTime, startTime);
        ElapsedTime = LocalTime.ofSecondOfDay(seconds);
        return ElapsedTime;
    }

}
