package cycling;

import java.util.Comparator;

/**
 * Sort results based off of a position in the checkpoints array
 */
public class ResultCheckpointsComparator implements Comparator<Result> {
    int pos;
    public ResultCheckpointsComparator(int pos) {
        this.pos = pos;
    }

    @Override
    public int compare(Result r1, Result r2) {
        return r1.getCheckpoints().get(pos).compareTo(r2.getCheckpoints().get(pos));
    }
}
