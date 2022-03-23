package cycling;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.text.FieldPosition;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

/**
 * CyclingPortal is the implementation of the interface CyclingPortalInterface
 * 
 * @author Harry Findlay, Vihan Sharma
 *
 */
public class CyclingPortal implements CyclingPortalInterface {
	//RACE, TEAM & RESULTS LIST
	private ArrayList<Race> CyclingPortalRaces = new ArrayList<Race>();
	private ArrayList<Team> CyclingPortalTeams = new ArrayList<Team>();
	private ArrayList<Result> CyclingPortalResults = new ArrayList<Result>();

	//CONSTANT POINTS ARRAYS
	private final int[] flatPoints = new int[]{50, 30, 20, 18, 16, 14, 12, 10, 8, 7, 6, 5, 4, 3, 2};
	private final int[] mediumMountainPoints = new int[]{30, 25, 22, 19, 17, 15, 13, 11, 9, 7, 6, 5, 4, 3, 2};
	private final int[] highMountainPoints = new int[]{20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
	private final int[] ttPoints = new int[]{20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
	private final int[] intSprintPoints = new int[]{20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

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
			System.out.println(race.getName());
			if(race.getName().equals(name)) {
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
		if(name.length() > 30) {
			throw new InvalidNameException("Name is too long!");
		}
		for(int i = 0; i < name.length(); i++) {
			if(Character.isWhitespace(name.charAt(i))) {
				throw new InvalidNameException("Name contains whitespace!");
			}
		}
		Race newRace = new Race(name, description);
		CyclingPortalRaces.add(newRace);
		return newRace.getRaceId();
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
		for (Race race : CyclingPortalRaces) {
			if (race.getRaceId() == raceId) {
				CyclingPortalRaces.remove(race);
				removed = true;
				//once removed break out of loop
				break;
			}
		}
		if (!removed) {
			throw new IDNotRecognisedException("ID not recognised in the system!");
		}
	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
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

		if(stageName == null) {
			throw new InvalidNameException("Name is null!");
		}
		if(stageName.isEmpty()) {
			throw new InvalidNameException("Name has been left empty!");
		}
		if(stageName.length() > 30) {
			throw new InvalidNameException("Name is too long!");
		}
		if(length < 5) {
			throw new InvalidLengthException("Length needs to be greater than 5 (kilometers)!");
		}

		//IllegalNameException
		for (Race race : CyclingPortalRaces) {
			for(Stage stage: race.getStages()) {
				if(stage.getStageName().equals(stageName)) {
					throw new IllegalNameException("Name already in the system!");
				}
			}
			if(race.getRaceId() == raceId) {
				Stage stageToAdd = new Stage(raceId, stageName, description, length, startTime, type);
				race.addStageToRace(stageToAdd);
				return stageToAdd.getStageId();
			}
		}

		throw new IDNotRecognisedException("ID not recognised in the system!");
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
					break;
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
					if(location > stage.getLength() || location < 0) {
						throw new InvalidLocationException("Location set outside the bounds of the stage!");
					}

					if(stage.getStageState()) {
						throw new InvalidStageStateException("Stage already concluded!");
					}

					//InvalidStageType
					if(stage.getType() == StageType.TT) {
						throw new InvalidStageTypeException("Cannot add climb to a TT!");
					}
					if(location+length > stage.getLength()) {
						throw new InvalidLocationException("Segment finishes outside the bounds of the stage!");
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
					if(location > stage.getLength() || location < 0) {
						throw new InvalidLocationException("Location set outside the bounds of the stage!");
					}

					if(stage.getStageState()) {
						throw new InvalidStageStateException("Stage is already concluded!");
					}

					//InvalidStageType
					if(stage.getType() == StageType.TT) {
						throw new InvalidStageTypeException("Cannot add climb to a TT!");
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
		boolean isFound = false;

		for(Race race : CyclingPortalRaces) {
			for(Stage stage : race.getStages()) {
				for(Segment segment : stage.getStageSegments()) {
					if(segment.getSegmentId() == segmentId) {
						if(stage.getStageState()) {
							throw new InvalidStageStateException("Stage already concluded!");
						}
						//Segment has been found
						//Segment can now be removed
						isFound = true;
						stage.removeSegment(segment);
						break;
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
					ArrayList<Segment> tempList = stage.getStageSegments();

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
			if(team.getName().equals(name)) {
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
		for(int i = 0; i < name.length(); i++) {
			if(Character.isWhitespace(name.charAt(i))) {
				throw new InvalidNameException("Name contains whitespace!");
			}
		}
		if(name.length() > 30) {
			throw new InvalidNameException("Name is too long!");
		}



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
				CyclingPortalTeams.remove(team);
				teamFound = true;
				break;
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

		if(name == null) {
			throw new IllegalArgumentException("Argument may be illegal or inappropriate!");
		}
		if(yearOfBirth < 1900) {
			throw new IllegalArgumentException("Argument may be illegal or inappropriate!");
		}

		//Loops through all the teams until the teamId is found that
		//has been inputted for the rider, then it calls the addRider method
		//for that specific team.
		for(Team team : CyclingPortalTeams) {
			if(team.getTeamId() == teamID) {
				Rider newRider = new Rider(teamID, name, yearOfBirth);
				team.addRider(newRider);
				return newRider.getRiderId();
			}
		}

		throw new IDNotRecognisedException("ID not recognised in the system!");
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		boolean isFound = false;
		//removing rider from team
		for(Team team : CyclingPortalTeams) {
			for(Rider rider : team.getRiders()) {
				if(rider.getRiderId() == riderId) {
					team.removeRider(rider);
					isFound = true;
					break;
				}
			}
		}

		if(!isFound) {
			throw new IDNotRecognisedException("ID not recognised in the system!");
		}

		//removing rider from results
		for(Result result : CyclingPortalResults) {
			if(result.getRiderId() == riderId) {
				CyclingPortalResults.remove(result);
				break;
			}
		}
	}

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointsException,
			InvalidStageStateException {

		//Invalid Checkpoints exception
		for(int i = 0; i < checkpoints.length; i++) {
			LocalTime low = checkpoints[i];
			for(int j = i; j < checkpoints.length; j++) {
				if(low.compareTo(checkpoints[j]) > 0) {
					throw new InvalidCheckpointsException("Checkpoints not in chronological order!");
				}
			}
		}

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
								Result result = new Result(stage.getRaceId(), stageId, riderId, checkpoints);
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
		LocalTime[] resultArray;
		for(Result result : CyclingPortalResults) {
			if (result.getRiderId() == riderId && result.getStageId() == stageId) {
				//+1 to make space for elapsed time
				resultArray = new LocalTime[result.getCheckpoints().size()+1];
				for (int i = 0; i < result.getCheckpoints().size(); i++) {
					resultArray[i] = result.getCheckpoints().get(i);
				}
				//adding elapsed time to end of array
				resultArray[resultArray.length - 1] = Result.getElapsedTime(result.getCheckpoints().get(0), result.getFinishTime());
				return resultArray;
			}
		}
		throw new IDNotRecognisedException("Id not recognised in the system!");

	}

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		//Checking the stage type, if stagetype is TT then do not adjust the time
		StageType type = null;
		for(Race race : CyclingPortalRaces) {
			for(Stage stage : race.getStages()) {
				type = stage.getType();
			}
		}
		if(type == null) {
			throw new IDNotRecognisedException("ID not recognised in the system!");
		}

		ArrayList<Result> resultsInStage = new ArrayList<Result>();
		for(Result result : CyclingPortalResults) {
			if(result.getStageId() == stageId) {
				resultsInStage.add(new Result(result.getRaceId(), result.getStageId(), result.getRiderId(), result.getCheckpoints().toArray(new LocalTime[0])));
			}
		}

		ArrayList<Result> sorted = new ArrayList<>(resultsInStage);
		Comparator<Result> comparator = new ResultComparator();
		sorted.sort(comparator);

		if(type == StageType.TT) {
			for(Result result : sorted) {
				if(result.getStageId() == stageId && result.getRiderId() == riderId) {
					return Result.getElapsedTime(result.getCheckpoints().get(0), result.getFinishTime());
				}
			}
		}

		for(int i = 0; i < sorted.size()-1; i++) {
			LocalTime l1 = sorted.get(i).getFinishTime();
			LocalTime l2 = sorted.get(i+1).getFinishTime();

			if(ChronoUnit.SECONDS.between(l1, l2) < 1) {
				sorted.get(i+1).setFinishTime(l1);
			}
		}

		for(Result result : sorted) {
			if(result.getStageId() == stageId && result.getRiderId() == riderId) {
				return Result.getElapsedTime(result.getCheckpoints().get(0), result.getFinishTime());
			}
		}

		throw new IDNotRecognisedException("ID not recognised in the system!");

	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		boolean isFound = false;
		for(Result result : CyclingPortalResults) {
			if(result.getRiderId() == riderId && result.getStageId() == stageId) {
				isFound = true;
				CyclingPortalResults.remove(result);
				break;
			}
		}

		if(!isFound) {
			throw new IDNotRecognisedException("ID not recongised in the system!");
		}
	}

	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		int[] riderRank;
		ArrayList<Result> resultsInStage = new ArrayList<Result>();
		boolean isFound = false;
		//get all the results in a stage linked with the stageId
		for(Result result : CyclingPortalResults) {
			if(result.getStageId() == stageId) {
				isFound = true;
				resultsInStage.add(new Result(result.getRaceId(), result.getStageId(), result.getRiderId(), result.getCheckpoints().toArray(new LocalTime[0])));
			}
		}

		//If there are no results in the stage, then the stage id does not match
		if(!isFound) {
			throw new IDNotRecognisedException("ID not recognised in the system!");
		}

		//sort all the results
		ArrayList<Result> sorted = new ArrayList<>(resultsInStage);
		Comparator<Result> comparator = new ResultComparator();
		sorted.sort(comparator);

		riderRank = new int[sorted.size()];
		//place them into the return array
		for(int i = 0; i < sorted.size(); i++) {
			riderRank[i] = sorted.get(i).getRiderId();
		}

		return riderRank;
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		ArrayList<Rider> riders = new ArrayList<Rider>();

		LocalTime[] returnArr;
		for(Team team: CyclingPortalTeams){
			for(Rider rider : team.getRiders()){
				riders.add(rider);
			}
		}

		ArrayList<Result> resultsInStage = new ArrayList<Result>();
		for (Result result : CyclingPortalResults) {
			if (result.getStageId() == stageId) {
				Result tempResult = new Result(result.getRaceId(), result.getStageId(), result.getRiderId(), result.getCheckpoints().toArray(new LocalTime[0]));
				resultsInStage.add(tempResult);
			}
		}
		if(resultsInStage.size() == 0){
			return new LocalTime[0];
		}

		for(Race race : CyclingPortalRaces) {
			for (Stage stage : race.getStages()) {
				if (stage.getStageId() == stageId) {

					//Get the orderedList of results in the stage (like getRidersRankInStage but we want the result objects)
					for(Rider rider : riders){
						for(Result result: resultsInStage){
							if(result.getRiderId()==rider.getRiderId()){
								result.setResultElapsedTime(getRiderAdjustedElapsedTimeInStage(stageId, result.getRiderId()));
							}
						}
					}

					ArrayList<Result> endSortedResults = new ArrayList<>(resultsInStage);
					Comparator<Result> comparator = new AdjustedResultComparator();
					endSortedResults.sort(comparator);

					returnArr = new LocalTime[endSortedResults.size()];
					for(int i = 0; i<endSortedResults.size(); i++){
						returnArr[i] = endSortedResults.get(i).getResultElapsedTime();
					}

					return returnArr;
				}
			}
		}

		throw new IDNotRecognisedException("Id not recognized in the system!");
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		int[] returnArr;

		for(Race race : CyclingPortalRaces) {
			for(Stage stage : race.getStages()) {
				if(stage.getStageId() == stageId) {
					//Get the orderedList of results in the stage (like getRidersRankInStage but we want the result objects)
					ArrayList<Result> resultsInStage = new ArrayList<Result>();
					for(Result result : CyclingPortalResults) {
						if(result.getStageId() == stageId) {
							Result tempResult = new Result(result.getRaceId(), result.getStageId(), result.getRiderId(), result.getCheckpoints().toArray(new LocalTime[0]));
							resultsInStage.add(tempResult);
						}
					}

					ArrayList<Result> endSortedResults = new ArrayList<>(resultsInStage);
					Comparator<Result> comparator = new ResultComparator();
					endSortedResults.sort(comparator);

					//Depending on the stage type assign the points differently
					if(stage.getType() == StageType.FLAT) {
						for(int i = 0; i < endSortedResults.size(); i++) {
							if(i < 15) {
								endSortedResults.get(i).addPoints(flatPoints[i]);
							} else {
								endSortedResults.get(i).addPoints(0);
							}
						}
					}
					if(stage.getType() == StageType.MEDIUM_MOUNTAIN) {
						for(int i = 0; i < endSortedResults.size(); i++) {
							if(i < 15) {
								endSortedResults.get(i).addPoints(mediumMountainPoints[i]);
							} else {
								endSortedResults.get(i).addPoints(0);
							}
						}
					}
					if(stage.getType() == StageType.HIGH_MOUNTAIN) {
						for(int i = 0; i < endSortedResults.size(); i++) {
							if(i < 15) {
								endSortedResults.get(i).addPoints(highMountainPoints[i]);
							} else {
								endSortedResults.get(i).addPoints(0);
							}
						}
					}
					if(stage.getType() == StageType.TT) {
						for(int i = 0; i < endSortedResults.size(); i++) {
							if(i < 15) {
								endSortedResults.get(i).addPoints(ttPoints[i]);
							} else {
								endSortedResults.get(i).addPoints(0);
							}
						}
					}

					//Working out sprint position points

					//find where the intSprints are
					//Find the position where the sprints are
					ArrayList<Segment> segmentsInStage = stage.getStageSegments();
					ArrayList<Integer> sprintIndexes = new ArrayList<Integer>();
					for(int i = 0; i < segmentsInStage.size(); i++) {
						if(segmentsInStage.get(i).getType() == SegmentType.SPRINT) {
							//i+1 to ignore the startTime in the checkpoints list
							sprintIndexes.add(i+1);
						}
					}

					for(int j = 0; j < sprintIndexes.size(); j++) {
						Comparator<Result> checkpointsComparator = new ResultCheckpointsComparator(sprintIndexes.get(j));
						ArrayList<Result> sorted = new ArrayList<>(resultsInStage);
						sorted.sort(checkpointsComparator);

						for(int x = 0; x < sorted.size(); x++) {
							if(x < 15) {
								sorted.get(x).addPoints(intSprintPoints[x]);
							} else {
								sorted.get(x).addPoints(0);
							}
						}
					}

					//adding points into returnArr
					returnArr = new int[endSortedResults.size()];
					for(int z = 0; z < endSortedResults.size(); z++) {
						returnArr[z] = endSortedResults.get(z).getPoints();
					}

					return returnArr;
				}
			}
		}
		throw new IDNotRecognisedException("ID not recognised in the system!");
	}

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		int[] returnArr;

		int[] c4Points = new int[]{1};
		int[] c3Points = new int[]{2, 1};
		int[] c2Points = new int[]{5, 3, 2, 1};
		int[] c1Points = new int[]{10, 8, 6, 4, 2, 1};
		int[] hcPoints = new int[]{20, 15, 12, 10, 8, 6, 4, 2};

		for(Race race : CyclingPortalRaces) {
			for(Stage stage : race.getStages()) {
				//Get the orderedList of results in the stage (like getRidersRankInStage but we want the result objects)
				ArrayList<Result> resultsInStage = new ArrayList<Result>();
				for(Result result : CyclingPortalResults) {
					if(result.getStageId() == stageId) {
						Result tempResult = new Result(result.getRaceId(), result.getStageId(), result.getRiderId(), result.getCheckpoints().toArray(new LocalTime[0]));
						resultsInStage.add(tempResult);
					}
				}

				ArrayList<Result> endSortedResults = new ArrayList<>(resultsInStage);
				Comparator<Result> comparator = new ResultComparator();
				endSortedResults.sort(comparator);

				//find where the Climbs are
				ArrayList<Segment> segmentsInStage = stage.getStageSegments();
				ArrayList<Integer> climbIndexes = new ArrayList<Integer>();

				for(int i = 0; i < segmentsInStage.size(); i++) {
					if(segmentsInStage.get(i).getType() != SegmentType.SPRINT) {
						//i+1 to ignore start time in checkpoints
						climbIndexes.add(i+1);
					}
				}

				for(int j = 0; j < climbIndexes.size(); j++) {
					//-1 as startTime is not considered a segment
					int index = climbIndexes.get(j) - 1;
					Comparator<Result> checkpointsComparator = new ResultCheckpointsComparator(climbIndexes.get(j));
					ArrayList<Result> sorted = new ArrayList<>(resultsInStage);
					sorted.sort(checkpointsComparator);

					if(segmentsInStage.get(index).getType() == SegmentType.C4) {
						for(int i = 0; i < sorted.size(); i++) {
							if(i == 0) {
								sorted.get(i).addMountainPoints(c4Points[i]);
							} else {
								sorted.get(i).addMountainPoints(0);
							}
						}
					}
					if(segmentsInStage.get(index).getType() == SegmentType.C3) {
						for(int i = 0; i < sorted.size(); i++) {
							if(i < 2) {
								sorted.get(i).addMountainPoints(c3Points[i]);
							} else {
								sorted.get(i).addMountainPoints(0);
							}
						}
					}
					if(segmentsInStage.get(index).getType() == SegmentType.C2) {
						for(int i = 0; i < sorted.size(); i++) {
							if(i < 4) {
								sorted.get(i).addMountainPoints(c2Points[i]);
							} else {
								sorted.get(i).addMountainPoints(0);
							}
						}
					}
					if(segmentsInStage.get(index).getType() == SegmentType.C1) {
						for(int i = 0; i < sorted.size(); i++) {
							if(i < 6) {
								sorted.get(i).addMountainPoints(c1Points[i]);
							} else {
								sorted.get(i).addMountainPoints(0);
							}
						}
					}
					if(segmentsInStage.get(index).getType() == SegmentType.HC) {
						for(int i = 0; i < sorted.size(); i++) {
							if(i < 8) {
								sorted.get(i).addMountainPoints(hcPoints[i]);
							} else {
								sorted.get(i).addMountainPoints(0);
							}
						}
					}
				}

				//adding points into returnArr
				returnArr = new int[endSortedResults.size()];
				for(int z = 0; z < endSortedResults.size(); z++) {
					returnArr[z] = endSortedResults.get(z).getMountainPoints();
				}

				return returnArr;
			}
		}

		throw new IDNotRecognisedException("ID not recognised in the system!");
	}

	@Override
	public void eraseCyclingPortal() {
		CyclingPortalRaces.clear();
		CyclingPortalTeams.clear();
		CyclingPortalResults.clear();
	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		Save saveObject = new Save(CyclingPortalRaces, CyclingPortalTeams, CyclingPortalResults);
		try {
			FileOutputStream file = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(saveObject);

			out.close();
			file.close();

			System.out.println("Object has been serialized");

		}
		catch (IOException e) {
			throw new IOException("IO exception found");
		}
	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		try
		{
			// Reading the object from a file
			FileInputStream file = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(file);

			// Method for deserialization of object
			Save saveObject = (Save)in.readObject();

			CyclingPortalRaces = saveObject.getCyclingPortalRaces();
			CyclingPortalTeams = saveObject.getCyclingPortalTeams();
			CyclingPortalResults = saveObject.getCyclingPortalResults();

			in.close();
			file.close();

			System.out.println("Object has been deserialized ");
		}
		catch(IOException ex)
		{
			throw new IOException("IOException was caught!");

		}
		catch(ClassNotFoundException ex)
		{
			throw new ClassNotFoundException("Class Not Found! ");
		}
	}

	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {
		boolean isFound = false;

		for(Race race : CyclingPortalRaces) {
			if(race.getName().equals(name)) {
				isFound = true;
				CyclingPortalRaces.remove(race);
				break;
			}
		}

		if(!isFound) {
			throw new NameNotRecognisedException("Name not recognised in the system!");
		}

	}

	@Override
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
		LocalTime[] returnArr;

		//For every rider create a new RaceResult object
		ArrayList<RaceResult> raceResults = new ArrayList<RaceResult>();
		for(Team team : CyclingPortalTeams) {
			for(Rider rider : team.getRiders()) {
				raceResults.add(new RaceResult(raceId, rider.getRiderId()));
			}
		}

		for(Race race : CyclingPortalRaces) {
			if(race.getRaceId() == raceId) {
				for(Stage stage : race.getStages()) {
					//ridersRankInStage
					int[] ridersRankInStage = getRidersRankInStage(stage.getStageId());
					LocalTime[] ridersAdjustedElapsedTimes = getRankedAdjustedElapsedTimesInStage(stage.getStageId());

					for(int i = 0; i < ridersAdjustedElapsedTimes.length; i++) {
						for(RaceResult raceResult : raceResults) {
							if(raceResult.getRiderId() == ridersRankInStage[i]) {
								raceResult.addToElapsedTime(ridersAdjustedElapsedTimes[i]);
							}
						}
					}
				}

				if(raceResults.size() == 0) {
					return new LocalTime[0];
				}

				//sort the raceResults and get the riderIds linked with them
				ArrayList<RaceResult> sorted = new ArrayList<>(raceResults);
				Comparator<RaceResult> comparator = new AdjustedRaceResultComparator();
				sorted.sort(comparator);

				returnArr = new LocalTime[sorted.size()];
				for(int z = 0; z < sorted.size(); z++) {
					returnArr[z] = sorted.get(z).getTotalElapsedTime();
				}

				return returnArr;
			}
		}

		throw new IDNotRecognisedException("ID not recognised in the system!");
	}

	@Override
	public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
		int[] returnArr;

		//For every rider create a new RaceResult object
		ArrayList<RaceResult> raceResults = new ArrayList<RaceResult>();
		for(Team team : CyclingPortalTeams) {
			for(Rider rider : team.getRiders()) {
				raceResults.add(new RaceResult(raceId, rider.getRiderId()));
			}
		}

		for(Race race : CyclingPortalRaces) {
			if(race.getRaceId() == raceId) {
				for(Stage stage : race.getStages()) {
					//ridersRankInStage
					int[] ridersRankInStage = getRidersRankInStage(stage.getStageId());
					int[] ridersPointsInStage = getRidersPointsInStage(stage.getStageId());

					for(int i = 0; i < ridersRankInStage.length; i++) {
						for(RaceResult raceResult : raceResults) {
							if(raceResult.getRiderId() == ridersRankInStage[i]) {
								raceResult.addToTotalPoints(ridersPointsInStage[i]);
							}
						}
					}

				}

				//sort the raceResults and get the riderIds linked with them
				ArrayList<RaceResult> sorted = new ArrayList<>(raceResults);
				Comparator<RaceResult> comparator = new RaceResultPointsComparator();
				sorted.sort(comparator);

				//adding points into returnArr
				returnArr = new int[sorted.size()];
				for(int z = 0; z < sorted.size(); z++) {
					returnArr[z] = sorted.get(z).getTotalPoints();
				}

				return returnArr;
			}
		}

		throw new IDNotRecognisedException("ID not recognised in the system!");
	}

	@Override
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
		int[] returnArr;

		ArrayList<RaceResult> raceResults = new ArrayList<RaceResult>();
		for(Team team : CyclingPortalTeams) {
			for(Rider rider : team.getRiders()) {
				raceResults.add(new RaceResult(raceId, rider.getRiderId()));
			}
		}

		for(Race race : CyclingPortalRaces) {
			if(race.getRaceId() == raceId) {
				for(Stage stage : race.getStages()) {
					//ridersRankInStage
					int[] ridersRankInStage = getRidersRankInStage(stage.getStageId());
					int[] ridersMountainPointsInStage = getRidersMountainPointsInStage(stage.getStageId());

					for(int i = 0; i < ridersRankInStage.length; i++) {
						for(RaceResult raceResult : raceResults) {
							if(raceResult.getRiderId() == ridersRankInStage[i]) {
								raceResult.addToTotalMountainPoints(ridersMountainPointsInStage[i]);
							}
						}
					}

				}

				//sort the raceResults and get the riderIds linked with them
				ArrayList<RaceResult> sorted = new ArrayList<>(raceResults);
				Comparator<RaceResult> comparator = new RaceResultMountainPointsComparator();
				sorted.sort(comparator);

				//adding points into returnArr
				returnArr = new int[sorted.size()];
				for(int z = 0; z < sorted.size(); z++) {
					returnArr[z] = sorted.get(z).getTotalMountainPoints();
				}

				return returnArr;
			}
		}

		throw new IDNotRecognisedException("ID not recognised in the system!");
	}

	@Override
	public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
		int[] returnArr;

		//For every rider create a new RaceResult object
		ArrayList<RaceResult> raceResults = new ArrayList<RaceResult>();
		for(Team team : CyclingPortalTeams) {
			for(Rider rider : team.getRiders()) {
				raceResults.add(new RaceResult(raceId, rider.getRiderId()));
			}
		}

		for(Race race : CyclingPortalRaces) {
			if(race.getRaceId() == raceId) {
				for(Stage stage : race.getStages()) {
					//ridersRankInStage
					int[] ridersRankInStage = getRidersRankInStage(stage.getStageId());
					LocalTime[] ridersAdjustedElapsedTimes = getRankedAdjustedElapsedTimesInStage(stage.getStageId());

					for(int i = 0; i < ridersAdjustedElapsedTimes.length; i++) {
						for(RaceResult raceResult : raceResults) {
							if(raceResult.getRiderId() == ridersRankInStage[i]) {
								raceResult.addToElapsedTime(ridersAdjustedElapsedTimes[i]);
							}
						}
					}
				}

				if(raceResults.size() == 0) {
					return new int[0];
				}

				//sort the raceResults and get the riderIds linked with them
				ArrayList<RaceResult> sorted = new ArrayList<>(raceResults);
				Comparator<RaceResult> comparator = new AdjustedRaceResultComparator();
				sorted.sort(comparator);

				returnArr = new int[sorted.size()];
				for(int z = 0; z < sorted.size(); z++) {
					returnArr[z] = sorted.get(z).getRiderId();
				}

				return returnArr;
			}
		}

		throw new IDNotRecognisedException("ID not recognised in the system!");

	}

	@Override
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
		int[] returnArr;

		//For every rider create a new RaceResult object
		ArrayList<RaceResult> raceResults = new ArrayList<RaceResult>();
		for(Team team : CyclingPortalTeams) {
			for(Rider rider : team.getRiders()) {
				raceResults.add(new RaceResult(raceId, rider.getRiderId()));
			}
		}

		for(Race race : CyclingPortalRaces) {
			if(race.getRaceId() == raceId) {
				for(Stage stage : race.getStages()) {
					//ridersRankInStage
					int[] ridersRankInStage = getRidersRankInStage(stage.getStageId());
					int[] ridersPointsInStage = getRidersPointsInStage(stage.getStageId());

					for(int i = 0; i < ridersRankInStage.length; i++) {
						for(RaceResult raceResult : raceResults) {
							if(raceResult.getRiderId() == ridersRankInStage[i]) {
								raceResult.addToTotalPoints(ridersPointsInStage[i]);
							}
						}
					}
				}

				if(raceResults.size() == 0) {
					return new int[0];
				}

				//sort the raceResults and get the riderIds linked with them
				ArrayList<RaceResult> sorted = new ArrayList<>(raceResults);
				Comparator<RaceResult> comparator = new RaceResultPointsComparator();
				sorted.sort(comparator);

				//adding points into returnArr
				returnArr = new int[sorted.size()];
				for(int z = 0; z < sorted.size(); z++) {
					returnArr[z] = sorted.get(z).getRiderId();
				}

				return returnArr;
			}
		}

		throw new IDNotRecognisedException("ID not recognised in the system!");

	}

	@Override
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
		int[] returnArr;

		ArrayList<RaceResult> raceResults = new ArrayList<RaceResult>();
		for(Team team : CyclingPortalTeams) {
			for(Rider rider : team.getRiders()) {
				raceResults.add(new RaceResult(raceId, rider.getRiderId()));
			}
		}

		for(Race race : CyclingPortalRaces) {
			if(race.getRaceId() == raceId) {
				for(Stage stage : race.getStages()) {
					//ridersRankInStage
					int[] ridersRankInStage = getRidersRankInStage(stage.getStageId());
					int[] ridersMountainPointsInStage = getRidersMountainPointsInStage(stage.getStageId());

					for(int i = 0; i < ridersRankInStage.length; i++) {
						for(RaceResult raceResult : raceResults) {
							if(raceResult.getRiderId() == ridersRankInStage[i]) {
								raceResult.addToTotalMountainPoints(ridersMountainPointsInStage[i]);
							}
						}
					}
				}

				if(raceResults.size() == 0) {
					return new int[0];
				}

				//sort the raceResults and get the riderIds linked with them
				ArrayList<RaceResult> sorted = new ArrayList<>(raceResults);
				Comparator<RaceResult> comparator = new RaceResultMountainPointsComparator();
				sorted.sort(comparator);

				//adding points into returnArr
				returnArr = new int[sorted.size()];
				for(int z = 0; z < sorted.size(); z++) {
					returnArr[z] = sorted.get(z).getRiderId();
				}

				return returnArr;
			}
		}

		throw new IDNotRecognisedException("ID not recognised in the system!");
	}

}
