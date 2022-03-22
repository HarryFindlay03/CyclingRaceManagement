package cycling;

import java.util.Comparator;

public class RaceResultMountainPointsComparator implements Comparator<RaceResult> {
    @Override
    public int compare(RaceResult r1, RaceResult r2) {
        int result;

        if(r1.getTotalMountainPoints() > r2.getTotalMountainPoints()) {
            result = 1;
        } else if (r1.getTotalMountainPoints() == r2.getTotalMountainPoints()) {
            result = 0;
        } else {
            result = -1;
        }

        //biggest to smallest
        return -1 * result;
    }
}