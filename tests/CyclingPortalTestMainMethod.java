import cycling.CyclingPortal;
import cycling.CyclingPortalInterface;
import cycling.Race;
import cycling.StageType;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class CyclingPortalTestMainMethod {
    public static void main(String[] args) {
        CyclingPortalInterface portal = new CyclingPortal();
        try {
            System.out.println(portal.createTeam("testTeam1", "testDesc"));
            portal.createRider(0, "Harry Findlay", 2003);
            portal.createRider(0, "Vihan Sharma", 2003);
            portal.createRace("testRace", "testDesc");
            System.out.println(Race.getCyclingPortalRaces());
            System.out.println(portal.viewRaceDetails(0));
//            portal.addStageToRace(0, "stage1", "stage1Desc", 130, LocalDateTime.parse("2022-03-07T10:15:30"), StageType.MEDIUM_MOUNTAIN);
//            portal.registerRiderResultsInStage(0, 0, LocalTime.parse("10:30:15"), LocalTime.parse("10:45:16"), LocalTime.parse("11:18:20"), LocalTime.parse("14:30:02"));
//            portal.registerRiderResultsInStage(0, 1, LocalTime.parse("10:30:15"), LocalTime.parse("10:46:16"), LocalTime.parse("11:20:20"), LocalTime.parse("14:29:04"));
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
