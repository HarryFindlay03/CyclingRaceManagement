package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;


/**
 * CyclingPortal is the implementation of the interface CyclingPortalInterface
 * 
 * @author Harry Findlay, Vihan Sharma
 *
 */
public class CyclingPortal implements CyclingPortalInterface {
	private ArrayList<Team> CyclingPortalTeams = new ArrayList<Team>();

	//EXTRA PORTAL METHODS
	public ArrayList<Team> getCyclingPortalTeams() {
		return CyclingPortalTeams;
	}

	//INTERFACE METHODS
	@Override
	public int[] getRaceIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, SegmentType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeSegment(int segmentId) throws IDNotRecognisedException, InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getStageSegments(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {

		//IllegalNameException
		for(Team team : CyclingPortalTeams) {
			if(team.getName() == name) {
				throw new IllegalNameException("Name already in the system!");
			}
		}

		//InvalidNameException
		if(name == null) {
			throw new InvalidNameException("Name is null!");
		}
		if(name.isEmpty()) {
			throw new InvalidNameException("Name has been left empty!");
		}
		//TODO remove this -> check updated JavaDoc on ELE.
		for(int i = 0; i < name.length(); i++) {
			if(Character.isWhitespace(name.charAt(i))) {
				throw new InvalidNameException("Name contains whitespace!");
			}
		}
		//TODO check number of characters is not greater than system length


		// Creates a new Team
		// Adds team to list of teams within the CyclingPortal
		// Returns the teamID of the new team

		Team newTeam = new Team(name, description);
		CyclingPortalTeams.add(newTeam);

		return newTeam.getTeamId();
	}

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		//Loop through all of the teams held in the system
		//Remove the team that it matched with the id
		boolean teamFound = false;
		for(Team team : CyclingPortalTeams) {
			if(team.getTeamId() == teamId) {
				teamFound = true;
				CyclingPortalTeams.remove(team);
			}
		}

		if(!teamFound) {
			throw new IDNotRecognisedException("ID not recognised in the system!");
		}

	}

	@Override
	public int[] getTeams() {
		int[] CyclingPortalTeamsArray = new int[CyclingPortalTeams.size()];

		for(int i = 0; i < CyclingPortalTeams.size(); i++) {
			CyclingPortalTeamsArray[i] = CyclingPortalTeams.get(i).getTeamId();
		}

		return CyclingPortalTeamsArray;
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		int[] teamRidersIds;

		for(Team team : CyclingPortalTeams) {
			if(team.getTeamId() == teamId) {
				teamRidersIds = new int[team.getRiders().size()];
				for(int i = 0; i < team.getRiders().size(); i++) {
					teamRidersIds[i] = team.getRiders().get(i).getRiderId();
				}
				return teamRidersIds;
			}
		}

		throw new IDNotRecognisedException("Given ID was not recognised in the system!");
	}

	@Override
	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {

		Rider newRider = new Rider(teamID, name, yearOfBirth);

		//Loops through all the teams until the teamId is found that
		//has been inputted for the rider, then it calls the addRider method
		//for that specific team.
		for(Team team : CyclingPortalTeams) {
			if(team.getTeamId() == teamID) {
				team.addRider(newRider);
			}
		}

		return newRider.getRiderId();
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointsException,
			InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eraseCyclingPortal() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

}
