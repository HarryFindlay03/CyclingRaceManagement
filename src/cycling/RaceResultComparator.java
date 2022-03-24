package cycling;

import java.time.LocalTime;
import java.util.Comparator;

public class RaceResultComparator implements Comparator<RaceResult> {
    @Override
    public int compare(RaceResult r1, RaceResult r2) {
        int result = r1.getTotalElapsedTime().compareTo(r2.getTotalElapsedTime());
        return result;
    }
}
