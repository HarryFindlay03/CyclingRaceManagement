package cycling;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * RaceResult stores a rider result within a whole race
 * i.e.: total elapsed time; total points; total mountain points
 */
public class RaceResult {
    private int raceId;
    private int riderId;

    private LocalTime totalElapsedTime = LocalTime.of(0, 0, 0);
    private int totalPoints = 0;
    private int totalMountainPoints = 0;

    public RaceResult(int raceId, int riderId) {
        this.raceId = raceId;
        this.riderId = riderId;
    }

    //INSTANCE METHODS
    public int getRaceId() {
        return raceId;
    }

    public int getRiderId() {
        return riderId;
    }

    public LocalTime getTotalElapsedTime() {
        return totalElapsedTime;
    }

    public void addToElapsedTime(LocalTime localTime) {
        LocalTime t = totalElapsedTime.plusHours(localTime.getHour()).plusMinutes(localTime.getMinute()).plusSeconds(localTime.getSecond());
        totalElapsedTime = t;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void addToTotalPoints(int points) {
        this.totalPoints += points;
    }

    public int getTotalMountainPoints() {
        return totalMountainPoints;
    }

    public void addToTotalMountainPoints(int moutainPoints) {
        this.totalMountainPoints += moutainPoints;
    }
}
