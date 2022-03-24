package cycling;

/**
 * A child class of Segment that abstractly represents an intermediate sprint
 * as it does not add any more functionality to Segment but differentiates from
 * the Segment class itself.
 * @author Harry Findlay, Vihan Sharma
 */
public class IntermediateSprint extends Segment {
    public IntermediateSprint(SegmentType type, double location) {
        super(type, location);
    }
}
