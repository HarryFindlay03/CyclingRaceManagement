package cycling;

import java.util.ArrayList;

/**
 * Rider class that holds all of the information about a rider
 *  @author Harry Findlay, Vihan Sharma
 */

public class Rider implements java.io.Serializable {
    private String name;
    private int yearOfBirth;
    private int riderId;
    private int teamId;

    private static ArrayList<Integer> riderIds = new ArrayList<Integer>();

    public Rider(int teamId, String name, int yearOfBirth) {
        this.teamId = teamId;
        this.name = name;
        this.yearOfBirth = yearOfBirth;

        if(riderIds.size() == 0) {
            riderId = 0;
            riderIds.add(riderId);
        } else {
            riderId = riderIds.get(riderIds.size() - 1) + 1;
            riderIds.add(riderId);
        }
    }

    public int getTeamId() {
        return teamId;
    }

    public int getRiderId() {
        return riderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String toString() {
        return "Rider[teamId=" + teamId + "riderId=" + riderId + ", name=" + name + ", yearOfBirth=" + yearOfBirth + "]";
    }
}
