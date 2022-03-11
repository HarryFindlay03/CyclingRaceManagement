package cycling;

public class CategorizedClimb extends Segment{
    private double averageGradient;
    private double length;

    public CategorizedClimb(SegmentType type, double location, double averageGradient, double length) {
        super(type, location);
        this.averageGradient = averageGradient;
        this.length = length;
    }

    public double getAverageGradient() { return averageGradient; }

    public void setAverageGradient(double averageGradient) {
        this.averageGradient = averageGradient;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

}
