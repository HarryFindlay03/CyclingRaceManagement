package cycling;

import java.util.ArrayList;

public class Save implements java.io.Serializable{
    private ArrayList<Race> CyclingPortalRaces = new ArrayList<Race>();
    private ArrayList<Team> CyclingPortalTeams = new ArrayList<Team>();
    private ArrayList<Result> CyclingPortalResults = new ArrayList<Result>();

    public Save(ArrayList<Race> CyclingPortalRaces, ArrayList<Team> CyclingPortalTeams, ArrayList<Result> CyclingPortalResults){
        this.CyclingPortalRaces = CyclingPortalRaces;
        this.CyclingPortalTeams = CyclingPortalTeams;
        this.CyclingPortalResults= CyclingPortalResults;
    }

    public ArrayList<Race> getCyclingPortalRaces() {
        return CyclingPortalRaces;
    }

    public ArrayList<Team> getCyclingPortalTeams() {
        return CyclingPortalTeams;
    }

    public ArrayList<Result> getCyclingPortalResults() {
        return CyclingPortalResults;
    }
}
