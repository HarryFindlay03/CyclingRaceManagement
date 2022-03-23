package cycling;

import java.util.ArrayList;

public class Segment implements java.io.Serializable {
    private int segmentId;
    protected SegmentType type;
    protected double location;
    private static ArrayList<Integer> segmentIds = new ArrayList<Integer>();

    public Segment(SegmentType type, double location) {
        this.type = type;
        this.location = location;

        if (segmentIds.size() == 0) {
            segmentId = 0;
            segmentIds.add(segmentId);
        } else {
            segmentId = segmentIds.get(segmentIds.size() - 1) + 1;
            segmentIds.add(segmentId);
        }
    }

    public int getSegmentId() {
        return segmentId;
    }

    public double getLocation() { return location; }

    public void setLocation(double location) {
        this.location = location;
    }

    public SegmentType getType() {
        return type;
    }

    public void setType(SegmentType type) {
        this.type = type;
    }

}

