package cycling;

import java.util.ArrayList;

/**
 * Race class that holds all of the information about a race
 *@author Harry Findlay, Vihan Sharma
 */

public class Race {
    private String name;
    private String description;
    private int Id;
    //private Arraylist<> results;
    private static ArrayList<Integer> raceIds = new ArrayList<Integer>();
    //private ArrayList<Stage> stages;
    private ArrayList<Integer> teamsInRace;

    public Race(String name, String description) {
        this.name = name;
        this.description = description;

        if(raceIds.size() == 0) {
            Id = 1;
            raceIds.add(1);
        } else {
            int tempRaceId = raceIds.get(raceIds.size() - 1);
            Id = tempRaceId + 1;
            raceIds.add(Id);
        }
    }

    @Override
    public String toString() {
        String str = String.format("Race[name=%s, description=%s, Id=%x]", name, description, Id);
        return str;
    }

    public String getName() {
        return name;
    }

    public int getRaceId() {
        return Id;
    }
}