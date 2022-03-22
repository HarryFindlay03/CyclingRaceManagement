package cycling;

import java.util.Comparator;

public class RaceResultMountainPointsComparator implements Comparator<RaceResult> {
    @Override
    public int compare(RaceResult r1, RaceResult r2) {
        int result;

        if(r1.getTotalMountainPoints() > r2.getTotalMountainPoints()) {
            result = 1;
        } else {
            result = 0;
        }

        return result;
    }
}