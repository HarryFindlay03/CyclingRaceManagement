package cycling;

import java.util.ArrayList;

public class Team implements java.io.Serializable {
    private String name;
    private String description;
    private int teamId;
    private static ArrayList<Integer> teamIds = new ArrayList<Integer>();
    private ArrayList<Rider> riders = new ArrayList<Rider>();

    public Team(String name, String description) {
        this.name = name;
        this.description = description;

        if(teamIds.size() == 0) {
            teamId = 0;
            teamIds.add(teamId);
        } else {
            teamId = teamIds.get(teamIds.size() - 1) + 1;
            teamIds.add(teamId);
        }
    }

    //PRIVATE METHODS



    //PUBLIC METHODS
    public void addRider(Rider rider) {
        riders.add(rider);
    }

    public void removeRider(Rider rider) {
        riders.remove(rider);
    }

    public ArrayList<Rider> getRiders() {
        return riders;
    }

    public int getTeamId() {
        return teamId;
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

    public static ArrayList<Integer> getTeamIds() {
        return teamIds;
    }

    public void addRiderToTeam(Rider rider) {
        riders.add(rider);
    }

    public String toString() {
        return "Team=[id=" + teamId + ", name=" + name + ", description=" + description + ", riders=" + riders + "]";
    }
}
