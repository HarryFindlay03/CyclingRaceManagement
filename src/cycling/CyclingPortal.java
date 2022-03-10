package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


/**
 * CyclingPortal is the implementation of the interface CyclingPortalInterface
 * 
 * @author Harry Findlay, Vihan Sharma
 *
 */
public class CyclingPortal implements CyclingPortalInterface {
	private ArrayList<Team> CyclingPortalTeams = new ArrayList<Team>();
	private ArrayList<Race> CyclingPortalRaces = new ArrayList<Race>();
	private ArrayList<Result> CyclingPortalResults = new ArrayList<Result>();
	//EXTRA PORTAL METHODS
	public ArrayList<Team> getCyclingPortalTeams() {
		return CyclingPortalTeams;
	}

	//INTERFACE METHODS
	@Override
	public int[] getRaceIds() {
		int[] tempArray = new int[CyclingPortalRaces.size()];
		for(int i = 0; i < tempArray.length; i++ ) {
			tempArray[i] = CyclingPortalRaces.get(i).getRaceId();
		}
		return tempArray;
	}

	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
		for(Race race: CyclingPortalRaces) {
			if(race.getName() == name) {
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
		//TODO check number of characters is not greater than system length

		Race newRace = new Race(name, description);
		CyclingPortalRaces.add(newRace);
		return newRace.getId();
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {

		for(Race race : CyclingPortalRaces) {
			if(race.getRaceId() == raceId) {
				return race.toString();
			}
		}
		throw new IDNotRecognisedException("ID not recognised in the system!");
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		boolean removed = false;
		for(Race race : CyclingPortalRaces) {
			if(race.getRaceId() == raceId) {
				removed = true;
				CyclingPortalRaces.remove(race);
			}
		}
		if (!removed) {
			throw new IDNotRecognisedException("ID not recognised in the system!");
		}
	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		for(Race race : CyclingPortalRaces) {
			if(race.getRaceId() == raceId) {
				return race.getNumOfStages();
			}
		}
		throw new IDNotRecognisedException("ID not recognised in the system!");
	}

	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {

		//IDNotRecognisedException, IllegalNameException, InvalidNameException
		boolean idExists = false;
		for (Race race : CyclingPortalRaces) {
			if(race.getRaceId() == raceId) {
				idExists = true;
				for(Stage stage: race.getStages()) {
					if(stage.getStageName() == stageName) {
						throw new IllegalNameException("Name already in the system!");
					}
					if(stage.getStageName() == null) {
						throw new InvalidNameException("Name is null!");
					}
					if(stage.getStageName().isEmpty()) {
						throw new InvalidNameException("Name has been left empty!");
					}
					//TODO: InvalidNameException character system limit check
					if(stage.getLength() < 5) {
						throw new InvalidLengthException("Length needs to be greater than 5 (kilometers)!");
					}
				}
			}
		}
		if (!idExists) {
			throw new IDNotRecognisedException("ID not recognised in the system!");
		}

		//Adding stage to race
		Stage stageToAdd = new Stage(stageName, description, startTime, type);

		for(Race race : CyclingPortalRaces) {
			if(race.getRaceId() == raceId) {
				race.addStageToRace(stageToAdd);
			}
		}
		return stageToAdd.getStageId();
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		int[] raceStages;
		for(Race race : CyclingPortalRaces) {
			if(race.getRaceId() == raceId) {
				raceStages = new int[race.getNumOfStages()];
				for(int i = 0; i < raceStages.length; i++) {
					raceStages[i] = race.getStages().get(i).getStageId();
				}
				return raceStages;
			}
		}

		//If return is not ran then ID has not been found in the system!
		throw new IDNotRecognisedException("ID not recognised in the system!");
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		//check through each race in the system and then check each stage in the race for the id given
		for(Race race : CyclingPortalRaces) {
			for(Stage stage : race.getStages()) {
				if(stage.getStageId() == stageId) {
					return stage.getLength();
				}
			}
		}
		throw new IDNotRecognisedException("ID not recognised in the system!");
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		//check isFound needed as no return
		boolean isFound = false;

		for(Race race : CyclingPortalRaces) {
			for(Stage stage : race.getStages()) {
				if(stage.getStageId() == stageId) {
					isFound = true;
					race.removeStage(stage);
				}
			}
		}

		if(!isFound) {
			throw new IDNotRecognisedException("ID not recognised in the system!");
		}
	}

	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, SegmentType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {

		for(Race race : CyclingPortalRaces) {
			for(Stage stage : race.getStages()) {
				if(stage.getStageId() == stageId) {
					//stageId is found -> Other checks can be done

					//If location for climb is set to be greater than the length of the stage
					if(location > stage.getLength()) {
						throw new InvalidLocationException("Location set outside the bounds of the stage!");
					}

					//TODO: StageStateExcepction -> need to work on the state of stages

					//InvalidStageType
					if(stage.getType() == StageType.TT) {
						throw new InvalidStageStateException("Cannot add climb to a TT!");
					}

					CategorizedClimb climbToAdd = new CategorizedClimb(type, location, averageGradient, length);
					stage.addCategorizedClimbToStage(climbToAdd);
					return climbToAdd.getSegmentId();

				}
			}
		}

		throw new IDNotRecognisedException("ID not recognised in the system!");
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		for(Race race : CyclingPortalRaces) {
			for(Stage stage : race.getStages()) {
				if(stage.getStageId() == stageId) {
					//stageId is found -> Other checks can be done

					//If location for sprint is set to be greater than the length of the stage
					if(location > stage.getLength()) {
						throw new InvalidLocationException("Location set outside the bounds of the stage!");
					}

					//TODO: StageStateExcepction -> need to work on the state of stages

					//InvalidStageType
					if(stage.getType() == StageType.TT) {
						throw new InvalidStageStateException("Cannot add climb to a TT!");
					}

					IntermediateSprint newSprint = new IntermediateSprint(SegmentType.SPRINT, location);
					stage.addIntermediateSprintToStage(newSprint);
					return newSprint.getSegmentId();

				}
			}
		}

		throw new IDNotRecognisedException("ID not recognised in the system!");
	}

	@Override
	public void removeSegment(int segmentId) throws IDNotRecognisedException, InvalidStageStateException {
		//TODO: Stage states

		boolean isFound = false;
		for(Race race : CyclingPortalRaces) {
			for(Stage stage : race.getStages()) {
				for(Segment segment : stage.getStageSegments()) {
					if(segment.getSegmentId() == segmentId) {
						//Segment has been found
						//Segment can now be removed
						isFound = true;
						stage.removeSegment(segment);
					}
				}
			}
		}

		if(!isFound) {
			//segmentId was not found
			throw new IDNotRecognisedException("ID not recognised in the system!");
		}

	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		// TODO Check this function works
		boolean isFound = false;

		for(Race race : CyclingPortalRaces) {
			for(Stage stage : race.getStages()) {
				if(stage.getStageId() == stageId) {
					if(stage.getStageState()) {
						//Stage is already waiting for results, cannot conclude something
						//that has already been concluded.
						throw new InvalidStageStateException("Stage not in the correct state!");
					}
					//stage has been found, do not need to throw IDNotRecognisedException
					isFound = true;
					//Setting stageState to 'waiting for results'
					stage.setStageState(true);
				}
			}
		}
		if(!isFound) {
			throw new IDNotRecognisedException("ID not recognised in the system!");
		}

	}

	@Override
	public int[] getStageSegments(int stageId) throws IDNotRecognisedException {
		int[] stageSegments;
		for(Race race : CyclingPortalRaces) {
			for(Stage stage : race.getStages()) {
				if(stage.getStageId() == stageId) {
					stageSegments = new int[stage.getStageSegments().size()];
					ArrayList<Segment> tempList = new ArrayList<Segment>();

					//sort the tempList based off of location in stage
					tempList.sort(Comparator.comparing(Segment::getLocation));

					//Placing the elements in tempList into the int[] array.
					for(int i = 0; i < tempList.size(); i++) {
						stageSegments[i] = tempList.get(i).getSegmentId();
					}

					return stageSegments;
				}
			}
		}

		throw new IDNotRecognisedException("ID not recognised in the system!");
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
		boolean isFound = false;
		for(Team team : CyclingPortalTeams) {
			for(Rider rider : team.getRiders()) {
				if(rider.getRiderId() == riderId) {
					isFound = true;
					team.removeRider(rider);
				}
			}
		}

		if(!isFound) {
			throw new IDNotRecognisedException("ID not recognised in the system!");
		}

	}

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointsException,
			InvalidStageStateException {
		// TODO InvalidCheckpointsException
			boolean isFoundStage = false;
			boolean isFoundRider = false;

			for(Race race: CyclingPortalRaces){
				for(Stage stage: race.getStages()){
					if(stage.getStageId()==stageId){
						isFoundStage= true;
						if(!stage.getStageState()) {
							throw new InvalidStageStateException("Stage under development!");
						}
						for(Team team: CyclingPortalTeams){
							for(Rider rider: team.getRiders()){
								if(rider.getRiderId()==riderId){
									isFoundRider = true;
									//both ids found
									for(Result result : CyclingPortalResults) {
										if(result.getRiderId() == riderId && result.getStageId() == stageId) {
											throw new DuplicatedResultException("Result already in system for rider!");
										}
									}
									Result result = new Result(stageId, riderId, checkpoints);
									CyclingPortalResults.add(result);
								}

							}
						}
					}
				}
			}

			if (!isFoundStage){
				throw new IDNotRecognisedException("Stage ID not recognised in the system!");
			}
			if(!isFoundRider) {
				throw new IDNotRecognisedException("Rider ID not recognised in the system!");
			}


	}

	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		//Finding result linked to riderId
		for(Result result : CyclingPortalResults) {
			if(result.getRiderId() == riderId && result.getStageId() == stageId) {
				//Find the stage and then get all the results for the riders in the stage and compare their finishing times
				for(Result allResult : CyclingPortalResults) {
					if(allResult.getStageId() == stageId) {
						//get all the results of the specific stage and compare them to the individual rider result
						int timeDelta = result.getFinishTime().compareTo(allResult.getFinishTime());
						//if timedifference of rider is less than one to rider infront then they have the same time
						if(timeDelta < 1) {
							result.setFinishTime(allResult.getFinishTime());
							//adjusted time
							return result.getFinishTime();
						} else {
							//unadjusted time
							return result.getFinishTime();
						}
					}
				}
			}
		}
		throw new IDNotRecognisedException("ID not recognised in the system!");
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
		boolean isFound = false;

		for(Race race : CyclingPortalRaces) {
			if(race.getName() == name) {
				isFound = true;
				CyclingPortalRaces.remove(race);
			}
		}

		if(!isFound) {
			throw new NameNotRecognisedException("Name not recognised in the system!");
		}

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
