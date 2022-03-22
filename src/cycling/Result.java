package cycling;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Result keeps track of results within a certain stage
 */
public class Result {
    private int raceId;
    private int stageId;
    private int riderId;
    private int resultId;

    private int points = 0;
    private int mountainPoints = 0;

    private static ArrayList<Integer> resultIds = new ArrayList<Integer>();

    private ArrayList<LocalTime> checkpoints = new ArrayList<LocalTime>();

    public Result(int raceId, int stageId, int riderId, LocalTime... checkpoints) {
        this.raceId = raceId;
        this.stageId = stageId;
        this.riderId = riderId;

        for(LocalTime check : checkpoints) {
            this.checkpoints.add(check);
        }

        if(resultIds.size() == 0) {
            resultId = 0;
            resultIds.add(resultId);
        } else {
            resultId = resultIds.get(resultIds.size() - 1) + 1;
            resultIds.add(resultId);
        }
    }

    public int getRaceId() {
        return raceId;
    }

    public int getResultId() {
        return resultId;
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

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int getMountainPoints() {
        return mountainPoints;
    }

    public void addMountainPoints(int mountainPoints) {
        this.mountainPoints += mountainPoints;
    }

    /**
     * Static method to compute the elapsed time between two inputted LocalTime objects
     * @param startTime time the stage starts
     * @param finishTime time of the finish time
     * @return The elapsed time taken for the rider in seconds
     */
    public static LocalTime getElapsedTime(LocalTime startTime, LocalTime finishTime) {
        int hours = (int) ChronoUnit.HOURS.between(startTime, finishTime);
        int minutes = (int) ChronoUnit.MINUTES.between(startTime, finishTime) % 60;
        int seconds = (int) ChronoUnit.SECONDS.between(startTime, finishTime) % 60;
        //int nanoSeconds = (int) ChronoUnit.NANOS.between(startTime, finishTime) % 1000000000;
        return LocalTime.of(hours, minutes, seconds);
    }

}
