import cycling.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CyclingPortalTestMainMethod {
    public static void main(String[] args) {
        CyclingPortalInterface portal = new CyclingPortal();
        try {
            portal.createRace("testRace", "testDesc");
            portal.createRace("testRace1", "testDesc");
            portal.addStageToRace(0, "stage1", "stage1Desc", 130, LocalDateTime.parse("2022-03-07T10:15:30"), StageType.MEDIUM_MOUNTAIN);
            portal.addStageToRace(0, "stage2", "stage1Desc", 160, LocalDateTime.parse("2022-03-08T10:15:30"), StageType.MEDIUM_MOUNTAIN);
            portal.addStageToRace(1, "stageName", "stage1Desc", 130, LocalDateTime.parse("2022-03-07T10:15:30"), StageType.MEDIUM_MOUNTAIN);
            portal.addStageToRace(1, "stagePoo", "stage1Desc", 130, LocalDateTime.parse("2022-03-07T10:15:30"), StageType.MEDIUM_MOUNTAIN);

            portal.addCategorizedClimbToStage(0, 45d, SegmentType.C2, 5.4, 4.5);
            portal.addIntermediateSprintToStage(0, 60);

            portal.addCategorizedClimbToStage(1, 55d, SegmentType.C2, 5.4, 4.5);
            portal.addIntermediateSprintToStage(1, 70);
//            System.out.println(portal.addCategorizedClimbToStage(2, 100d, SegmentType.C2, 5.4, 5d));
//            System.out.println(portal.addIntermediateSprintToStage(2, 86d));
//            System.out.println(portal.addIntermediateSprintToStage(2, 90d));

            System.out.println(portal.viewRaceDetails(0));


            portal.createTeam("testTeam1", "testDesc");
            portal.createTeam("testTeam2", "testDesc1");
            portal.createRider(0, "Harry Findlay", 2003);
            portal.createRider(0, "Vihan Sharma", 2003);
            portal.createRider(0, "Jonathan Hargreaves", 2003);
//            for(int elem: portal.getTeamRiders(0)) {
//                System.out.println(elem);
//            }
//            portal.removeRider(1);
//            for(int elem: portal.getTeamRiders(0)) {
//                System.out.println(elem);
//            }
//            for(Integer elem : portal.getTeamRiders(1)) {
//                System.out.printf("Id:%x\n", elem);
//            }

            portal.concludeStagePreparation(0);
            portal.concludeStagePreparation(1);

            portal.registerRiderResultsInStage(0, 0, LocalTime.parse("10:15:30"), LocalTime.parse("10:45:16.98"), LocalTime.parse("11:18:20"), LocalTime.parse("14:30:05.66"));
            portal.registerRiderResultsInStage(0, 1, LocalTime.parse("10:15:30"), LocalTime.parse("10:46:16.99"), LocalTime.parse("11:20:20"), LocalTime.parse("14:35:05.69"));
            portal.registerRiderResultsInStage(0, 2, LocalTime.parse("10:15:30"), LocalTime.parse("10:45:19.97"), LocalTime.parse("11:20:18"), LocalTime.parse("14:31:24"));

            portal.registerRiderResultsInStage(1, 0, LocalTime.parse("10:15:30"), LocalTime.parse("10:46:16.02"), LocalTime.parse("11:18:20"), LocalTime.parse("14:30:05"));
            portal.registerRiderResultsInStage(1, 1, LocalTime.parse("10:15:30"), LocalTime.parse("10:46:16.01"), LocalTime.parse("11:19:20"), LocalTime.parse("14:29:04"));
            portal.registerRiderResultsInStage(1, 2, LocalTime.parse("10:15:30"), LocalTime.parse("10:46:19.76"), LocalTime.parse("11:20:16"), LocalTime.parse("14:31:24"));

            System.out.println("Rider  ID 0");
            for (LocalTime elem : portal.getRiderResultsInStage(0, 0)) {
                System.out.println(elem);
            }

            System.out.println("Rider  ID 1");
            for (LocalTime elem : portal.getRiderResultsInStage(0, 1)) {
                System.out.println(elem);
            }

            System.out.println("Adjusted time!");
            System.out.println(portal.getRiderAdjustedElapsedTimeInStage(0, 1));


            System.out.println("Ranking Adjusted times!");

            for (LocalTime elem : portal.getRankedAdjustedElapsedTimesInStage(0)) {
                System.out.println(elem);
            }

            System.out.println("GC RANK ID:");

            for (Integer elem : portal.getRidersGeneralClassificationRank(0)) {
                System.out.println(elem);
            }

            System.out.println("GC RANK RACE:");
            for (LocalTime elem : portal.getGeneralClassificationTimesInRace(0)) {
                System.out.println(elem);
            }

//            System.out.println("getRiderRankInStage");
//            for(Integer elem : portal.getRidersRankInStage(0)) {
//                System.out.printf("Id:%x\n", elem);
//            }

            System.out.println("POINTS in stage 0:");
            for (Integer elem : portal.getRidersPointsInStage(0)) {
                System.out.println(elem);
            }
//
//            System.out.println("POINTS in stage 1:");
//            for(Integer elem : portal.getRidersPointsInStage(1)) {
//                System.out.println(elem);
//            }
//
//
//            System.out.println("TOTAL POINTS RIDER ID ORDER:");
//            for(Integer elem : portal.getRidersPointClassificationRank(0)) {
//                System.out.println(elem);
//            }
//
//            System.out.println("TOTAL POINTS IN RACE:");
//            for(Integer elem : portal.getRidersPointsInRace(0)) {
//                System.out.println(elem);
//            }
//
//            System.out.println("MOUNTAIN POINTS IN STAGE 0:");
//            for(Integer elem : portal.getRidersMountainPointsInStage(0)) {
//                System.out.println(elem);
//            }
//
//            System.out.println("MOUNTAIN POINTS IN STAGE 1:");
//            for(Integer elem : portal.getRidersMountainPointsInStage(1)) {
//                System.out.println(elem);
//            }
//
//            System.out.println("TOTAL MOUNTAIN POINTS IN RACE ID:");
//            for(Integer elem : portal.getRidersMountainPointClassificationRank(0)) {
//                System.out.println(elem);
//            }
//
//            System.out.println("TOTAL MOUNTAIN POINTS IN RACE:");
//            for(Integer elem : portal.getRidersMountainPointsInRace(0)) {
//                System.out.println(elem);
//            }

            portal.saveCyclingPortal("CyclingPortalSave.ser");
            portal.eraseCyclingPortal();
            portal.loadCyclingPortal("CyclingPortalSave.ser");
            for (Integer elem : portal.getRidersGeneralClassificationRank(0)) {
                System.out.println(elem);
            }
//
//            System.out.println("MEGA");
//            megaTest();


        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void megaTest() throws IOException,NameNotRecognisedException, ClassNotFoundException, IllegalNameException, DuplicatedResultException, InvalidCheckpointsException, InvalidNameException, IDNotRecognisedException,
            InvalidLengthException, InvalidStageStateException, InvalidLocationException, InvalidStageTypeException {
        CyclingPortalInterface portal = new CyclingPortal();

        portal.createRace("testRace" , "this is to test");

        // Initialise first stage
        portal.addStageToRace(0, "testStage1", "this is a test" , 20, LocalDateTime.now() , StageType.MEDIUM_MOUNTAIN);
        portal.addCategorizedClimbToStage(0, 4.0, SegmentType.C1, 5.0, 1.0);
        portal.addCategorizedClimbToStage(0, 6.0, SegmentType.C2, 5.0, 1.0);
        portal.addIntermediateSprintToStage(0, 7.0);
        portal.addCategorizedClimbToStage(0, 9.0, SegmentType.HC, 5.0, 1.0);
        portal.addIntermediateSprintToStage(0, 15.0);


        // Initialise second stage
        portal.addStageToRace(0, "testStage2", "this is a test" , 10, LocalDateTime.now() , StageType.FLAT);
        portal.addIntermediateSprintToStage(1, 4);
        portal.addCategorizedClimbToStage(1, 6.0, SegmentType.C2, 5.0, 1.0);
        portal.addIntermediateSprintToStage(1, 7);
        portal.addCategorizedClimbToStage(1, 8.0, SegmentType.C1, 5.0, 1.0);

        System.out.println(portal.viewRaceDetails(0));

        portal.createTeam("TestTeam", "TestDesc");
        portal.createRider(0, "TestRider", 2000);
        portal.createRider(0, "TestRider2", 2000);
        portal.createRider(0, "TestRider3", 2000);
        portal.createRider(0, "TestRider4", 2000);

        portal.concludeStagePreparation(0);
        portal.concludeStagePreparation(1);

        // Results for first stage
        portal.registerRiderResultsInStage(0, 0, LocalTime.of(12, 00, 00), LocalTime.of(12, 5, 00), LocalTime.of(12, 8, 00), LocalTime.of(12, 15, 00),LocalTime.of(12, 20, 00), LocalTime.of(12, 30, 00), LocalTime.of(12, 35, 00));
        portal.registerRiderResultsInStage(0, 1, LocalTime.of(12, 00, 00), LocalTime.of(12, 7, 00), LocalTime.of(12, 9, 00), LocalTime.of(12, 13, 00),LocalTime.of(12, 25, 00), LocalTime.of(12, 27, 00), LocalTime.of(12, 36, 1, 5000000));
        portal.registerRiderResultsInStage(0, 2, LocalTime.of(12, 00, 00), LocalTime.of(12, 10, 00), LocalTime.of(12, 11, 00), LocalTime.of(12, 16, 00),LocalTime.of(12, 19, 00), LocalTime.of(12, 28, 00), LocalTime.of(12, 36, 02, 1000000));
        portal.registerRiderResultsInStage(0, 3, LocalTime.of(12, 00, 00), LocalTime.of(12, 4, 00), LocalTime.of(12, 6, 00), LocalTime.of(12, 11, 00), LocalTime.of(12, 21, 00), LocalTime.of(12, 31, 00), LocalTime.of(12, 32, 00));

        // Results for second stage
        portal.registerRiderResultsInStage(1, 0, LocalTime.of(13, 00, 00), LocalTime.of(13, 5, 00), LocalTime.of(13, 6, 00), LocalTime.of(13, 10, 00), LocalTime.of(13, 14, 00), LocalTime.of(13, 20, 00));
        portal.registerRiderResultsInStage(1, 1, LocalTime.of(13, 00, 00), LocalTime.of(13, 4, 00), LocalTime.of(13, 7, 00), LocalTime.of(13, 11, 00), LocalTime.of(13, 13, 00), LocalTime.of(13, 21, 00));
        portal.registerRiderResultsInStage(1, 2, LocalTime.of(13, 00, 00), LocalTime.of(13, 6, 00), LocalTime.of(13, 8, 00), LocalTime.of(13, 9, 00), LocalTime.of(13, 15, 00), LocalTime.of(13, 19, 00));

        int[] rankedRiders = portal.getRidersRankInStage(0);

        for(int rider : rankedRiders) {
            System.out.println(rider);
        }
        LocalTime[] adjusted = portal.getRankedAdjustedElapsedTimesInStage(0);
        for(LocalTime point : adjusted) {
            System.out.println(point.toString());
        }

        int[] points = portal.getRidersPointsInStage(0);
        for (int point : points) {
            System.out.println(point);
        }

        int[] rankedPoints = portal.getRidersMountainPointsInStage(0);

        for (int point: rankedPoints) {
            System.out.println(point);
        }

    }

}
