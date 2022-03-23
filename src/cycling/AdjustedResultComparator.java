package cycling;

import java.util.Comparator;

public class AdjustedResultComparator implements Comparator<Result> {
    @Override
    public int compare(Result r1, Result r2) {
        int result = r1.getResultElapsedTime().compareTo(r2.getResultElapsedTime());
        return result;
    }
}
