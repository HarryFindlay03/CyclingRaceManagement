package cycling;

import java.util.Comparator;

public class RaceResultPointsComparator implements Comparator<RaceResult> {
    @Override
    public int compare(RaceResult r1, RaceResult r2) {
        int result;

        if(r1.getTotalPoints() > r2.getTotalPoints()) {
            result = 1;
        } else {
            result = 0;
        }

        return result;
    }
}
