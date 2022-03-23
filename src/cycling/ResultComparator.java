package cycling;

import java.time.LocalTime;
import java.util.Comparator;

/**
 * Sort results based off of their total elapsed time
 */
public class ResultComparator implements Comparator<Result> {
    @Override
    public int compare(Result r1, Result r2) {
        int result = Result.getElapsedTime(r1.getCheckpoints().get(0), r1.getFinishTime()).compareTo(Result.getElapsedTime(r2.getCheckpoints().get(0), r2.getFinishTime()));
        return result;
    }
}
