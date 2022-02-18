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
    //TODO: private Arraylist<> results;
    private static ArrayList<Integer> raceIds = new ArrayList<Integer>();
    //TODO: private ArrayList<Stage> stages;
    private ArrayList<Integer> teamsInRace;

    public Race(String name, String description) {
        this.name = name;
        this.description = description;

        if(raceIds.size() == 0) {
            Id = 1;
            raceIds.add(Id);
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

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return Id;
    }

    public int getRaceId() {
        return Id;
    }
}