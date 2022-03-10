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
    private static ArrayList<Integer> raceIds = new ArrayList<Integer>();

    private static ArrayList<Race> CyclingPortalRaces = new ArrayList<Race>();

    private ArrayList<Stage> stages;

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
        int numOfStages = stages.size();

        int totalLength = 0;
        for(Stage stage: stages) {
            totalLength += stage.getLength();
        }

        String str = String.format("Race[name=%s, description=%s, Id=%x, numOfStages=%x, totalLength=%x]", name, description, Id, numOfStages, totalLength);
        return str;
    }

    public static ArrayList<Race> getCyclingPortalRaces() {
        return CyclingPortalRaces;
    }

    public static void addRace(Race race) {
        CyclingPortalRaces.add(race);
    }

    public static void removeRace(Race race) {
        CyclingPortalRaces.remove(race);
    }

    public int getNumOfStages() {return stages.size();}

    public ArrayList<Stage> getStages() {return stages;}

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

    public void addStageToRace(Stage stage) {
        stages.add(stage);
    }

    public void removeStage(Stage stage) {
        stages.remove(stage);
    }
}