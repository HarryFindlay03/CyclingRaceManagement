package cycling;

import java.util.Comparator;

public class RaceResultPointsComparator implements Comparator<RaceResult> {
    @Override
    public int compare(RaceResult r1, RaceResult r2) {
        int result;

        if(r1.getTotalPoints() > r2.getTotalPoints()) {
            result = 1;
        } else if(r1.getTotalPoints() == r2.getTotalPoints()) {
            result = 0;
        } else {
            result = -1;
        }

        //biggest to smallest
        return -1 * result;
    }
}
