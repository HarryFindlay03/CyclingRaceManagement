package cycling;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Race class that holds all of the information about a race
 *@author Harry Findlay, Vihan Sharma
 */

public class Race implements java.io.Serializable{
    private String name;
    private String description;
    private int raceId;

    private static int counter = 0;

    private ArrayList<Stage> stages = new ArrayList<Stage>();

    public Race(String name, String description) {
        this.name = name;
        this.description = description;

        raceId = counter;
        counter++;
    }

    @Override
    public String toString() {
        int numOfStages = stages.size();

        double totalLength = 0d;

        for(Stage stage : stages) {
            totalLength += stage.getLength();
        }

        String str = String.format("Race[name=%s, description=%s, Id=%x, numOfStages=%x, totalLength=%f]", name, description, raceId, numOfStages, totalLength);
        assert(str.length() > 0);
        return str;
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

    public int getRaceId() {
        return raceId;
    }

    public void addStageToRace(Stage stage) {
        stages.add(stage);
    }

    public void removeStage(Stage stage) {
        stages.remove(stage);
    }
}