package cycling;

import java.util.Comparator;

public class AdjustedRaceResultComparator implements Comparator<RaceResult> {
    @Override
    public int compare(RaceResult r1, RaceResult r2) {
        int result = r1.getTotalElapsedTime().compareTo(r2.getTotalElapsedTime());
        return result;
    }
}
