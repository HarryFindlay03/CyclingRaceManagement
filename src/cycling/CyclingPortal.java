package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;


//TODOs
//getRankInStage
//getAdjustedRankInStage
//


/**
 * CyclingPortal is the implementation of the interface CyclingPortalInterface
 * 
 * @author Harry Findlay, Vihan Sharma
 *
 */
public class CyclingPortal implements CyclingPortalInterface {
	//CONSTANT POINTS ARRAYS
	private final int[] flatPoints = new int[]{50, 30, 20, 18, 16, 14, 12, 10, 8, 7, 6, 5, 4, 3, 2};
	private final int[] mediumMountainPoints = new int[]{30, 25, 22, 19, 17, 15, 13, 11, 9, 7, 6, 5, 4, 3, 2};
	private final int[] highMountainPoints = new int[]{20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
	private final int[] ttPoints = new int[]{20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
	private final int[] intSprintPoints = new int[]{20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

	//INTERFACE METHODS
	@Override
	public int[] getRaceIds() {
		int[] tempArray = new int[Race.getCyclingPortalRaces().size()];
		for(int i = 0; i < tempArray.length; i++ ) {
			tempArray[i] = Race.getCyclingPortalRaces().get(i).getRaceId();
		}
		return tempArray;
	}

	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
		for(Race race: Race.getCyclingPortalRaces()) {
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
		Race.addRace(newRace);
		return newRace.getId();
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {

		for(Race race : Race.getCyclingPortalRaces()) {
			if(race.getRaceId() == raceId) {
				return race.toString();
			}
		}
		throw new IDNotRecognisedException("ID not recognised in the system!");
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		boolean removed = false;
		for (Race race : Race.getCyclingPortalRaces()) {
			if (race.getRaceId() == raceId) {
				Race.removeRace(race);
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
		// TODO Auto-generated method stub
		for(Race race : Race.getCyclingPortalRaces()) {
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
		for (Race race : Race.getCyclingPortalRaces()) {
			if(race.getRaceId() == raceId) {
				for(Stage stage: race.getStages()) {
					if(stage.getStageName() == stageName) {
						throw new IllegalNameException("Name already in the system!");
					}
				}
				Stage stageToAdd = new Stage(stageName, description, length, startTime, type);
				race.addStageToRace(stageToAdd);
				return stageToAdd.getStageId();
			}
		}
		throw new IDNotRecognisedException("ID not recognised in the system!");
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		int[] raceStages;
		for(Race race : Race.getCyclingPortalRaces()) {
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
		for(Race race : Race.getCyclingPortalRaces()) {
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

		for(Race race : Race.getCyclingPortalRaces()) {
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

		for(Race race : Race.getCyclingPortalRaces()) {
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
						throw new InvalidStageStateException("Cannot add climb to a TT!");
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
		for(Race race : Race.getCyclingPortalRaces()) {
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
		for(Race race : Race.getCyclingPortalRaces()) {
			for(Stage stage : race.getStages()) {
				for(Segment segment : stage.getStageSegments()) {
					if(segment.getSegmentId() == segmentId) {
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
		// TODO Check this function works
		boolean isFound = false;

		for(Race race : Race.getCyclingPortalRaces()) {
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
		for(Race race : Race.getCyclingPortalRaces()) {
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
		for(Team team : Team.getCyclingPortalTeams()) {
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
		Team.addTeam(newTeam);
		return newTeam.getTeamId();
	}

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		//Loop through all of the teams held in the system
		//Remove the team that it matched with the id
		boolean teamFound = false;
		for(Team team : Team.getCyclingPortalTeams()) {
			if(team.getTeamId() == teamId) {
				Team.removeTeam(team);
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
		int[] CyclingPortalTeamsArray = new int[Team.getCyclingPortalTeams().size()];

		for(int i = 0; i < Team.getCyclingPortalTeams().size(); i++) {
			CyclingPortalTeamsArray[i] = Team.getCyclingPortalTeams().get(i).getTeamId();
		}

		return CyclingPortalTeamsArray;
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		int[] teamRidersIds;

		for(Team team : Team.getCyclingPortalTeams()) {
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
			throw new IllegalArgumentException("Argument may be illegal or inappropriate ;)");
		}
		if(name.isEmpty()) {
			throw new IllegalArgumentException("Argument may be illegal or inappropriate ;)");
		}
		if(name.length() > 30) {
			throw new IllegalArgumentException("Argument may be illegal or inappropriate ;)");
		}

		//Loops through all the teams until the teamId is found that
		//has been inputted for the rider, then it calls the addRider method
		//for that specific team.
		for(Team team : Team.getCyclingPortalTeams()) {
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
		for(Team team : Team.getCyclingPortalTeams()) {
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

	}

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointsException,
			InvalidStageStateException {
		// TODO InvalidCheckpointsException
			boolean isFoundStage = false;
			boolean isFoundRider = false;

			for(Race race: Race.getCyclingPortalRaces()){
				for(Stage stage: race.getStages()){
					if(stage.getStageId()==stageId){
						isFoundStage= true;
						if(!stage.getStageState()) {
							throw new InvalidStageStateException("Stage under development!");
						}
						for(Team team: Team.getCyclingPortalTeams()){
							for(Rider rider: team.getRiders()){
								if(rider.getRiderId()==riderId){
									isFoundRider = true;
									//both ids found
									for(Result result : Result.getCyclingPortalResults()) {
										if(result.getRiderId() == riderId && result.getStageId() == stageId) {
											throw new DuplicatedResultException("Result already in system for rider!");
										}
									}
									Result result = new Result(stageId, riderId, checkpoints);
									Result.addResult(result);
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
		for(Result result : Result.getCyclingPortalResults()) {
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
		//TODO -> do we need to work with nanoseconds?
		//Checking the stage type, if stagetype is TT then do not adjust the time
		StageType type = null;
		for(Race race : Race.getCyclingPortalRaces()) {
			for(Stage stage : race.getStages()) {
				type = stage.getType();
			}
		}

		//Finding result linked to riderId
		for(Result result : Result.getCyclingPortalResults()) {
			if(result.getRiderId() == riderId && result.getStageId() == stageId) {
				//Find the stage and then get all the results for the riders in the stage and compare their finishing times
				for(Result allResult : Result.getCyclingPortalResults()) {
					if(allResult.getStageId() == stageId && allResult.getRiderId() != riderId) {
						if(type == null) {
							throw new IDNotRecognisedException("ID not recognised in the system!");
						}
						if(type == StageType.TT) {
							//Return elapsed time
							return Result.getElapsedTime(result.getCheckpoints().get(0), result.getFinishTime());
						}
						//Stage type is not a TT and a stage has been found
						LocalTime riderFinish = result.getFinishTime();
						LocalTime otherFinish = allResult.getFinishTime();

						if(Result.getElapsedTime(otherFinish, riderFinish).getSecond() < 1) {
							//update the finish time in the stage for the rider
							//result.setFinishTime(allResult.getFinishTime());
							System.out.println("Changing elapsed time");
							return Result.getElapsedTime(allResult.getCheckpoints().get(0), allResult.getFinishTime());
						}
						//Gap was greater than (or equal to) a second.
						return Result.getElapsedTime(result.getCheckpoints().get(0), result.getFinishTime());
					}
				}
			}
		}
		throw new IDNotRecognisedException("ID not recognised in the system!");
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		boolean isFound = false;
		for(Result result : Result.getCyclingPortalResults()) {
			if(result.getRiderId() == riderId && result.getStageId() == stageId) {
				isFound = true;
				Result.removeResult(result);
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
		for(Result result : Result.getCyclingPortalResults()) {
			if(result.getStageId() == stageId) {
				isFound = true;
				resultsInStage.add(result);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		int[] returnArr;

		for(Race race : Race.getCyclingPortalRaces()) {
			for(Stage stage : race.getStages()) {
				if(stage.getStageId() == stageId) {
					//Get the orderedList of results in the stage (like getRidersRankInStage but we want the result objects)
					ArrayList<Result> resultsInStage = new ArrayList<Result>();
					for(Result result : Result.getCyclingPortalResults()) {
						if(result.getStageId() == stageId) {
							resultsInStage.add(result);
						}
					}

					ArrayList<Result> endSortedResults = new ArrayList<>(resultsInStage);
					Comparator<Result> comparator = new ResultComparator();
					endSortedResults.sort(comparator);

					//Depending on the stage type assign the points differently
					if(stage.getType() == StageType.FlAT) {
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

		for(Race race : Race.getCyclingPortalRaces()) {
			for(Stage stage : race.getStages()) {
				//Get the orderedList of results in the stage (like getRidersRankInStage but we want the result objects)
				ArrayList<Result> resultsInStage = new ArrayList<Result>();
				for(Result result : Result.getCyclingPortalResults()) {
					if(result.getStageId() == stageId) {
						resultsInStage.add(result);
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

		for(Race race : Race.getCyclingPortalRaces()) {
			if(race.getName() == name) {
				isFound = true;
				Race.getCyclingPortalRaces().remove(race);
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
