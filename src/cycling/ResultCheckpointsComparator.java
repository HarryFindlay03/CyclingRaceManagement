package cycling;

import java.util.Comparator;

/**
 * Sort results based off of a position in the checkpoints array
 */
public class ResultCheckpointsComparator implements Comparator<Result> {
    int pos;

    /**
     * Constructor that sets where the position in the checkpoints array the comparator should look
     * @param pos The position within the checkpoints array where the comparator should sort at.
     */
    public ResultCheckpointsComparator(int pos) {
        this.pos = pos;
    }

    @Override
    public int compare(Result r1, Result r2) {
        return r1.getCheckpoints().get(pos).compareTo(r2.getCheckpoints().get(pos));
    }
}
