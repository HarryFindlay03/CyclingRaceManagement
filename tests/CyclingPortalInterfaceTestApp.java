import cycling.*;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;


/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the CyclingPortalInterface interface -- note you
 * will want to increase these checks, and run it on your CyclingPortal class
 * (not the BadCyclingPortal class).
 *
 *
 * @author Diogo Pacheco
 * @version 1.0
 */
public class CyclingPortalInterfaceTestApp {

    /**
     * Test method.
     *
     * @param args not used
     */
//    public static void main(String[] args) {
//        System.out.println("The system compiled and started the execution...");
//
//        //cycling.MiniCyclingPortalInterface portal = new cycling.BadCyclingPortal();
////		cycling.CyclingPortalInterface portal = new cycling.BadCyclingPortal();
//
//
//
////		assert (portal.getRaceIds().length == 0)
////				: "Initial CyclingPortal not empty as required or not returning an empty array.";
//
//        try {
//            CyclingPortalInterface portal = new CyclingPortal();
//
//            portal.createTeam("testTeam1", "testDescription1");
//            portal.createTeam("testTeam2", "testDescription2");
//
//            portal.createRider(1, "Harry Findlay", 2003);
//            portal.createRider(0, "Vihan Sharma", 2003);
//            portal.createRider(0, "Jonathan Hargreaves", 2002);
//            portal.createRider(1, "Maddie Hansford", 2002);
//
//            System.out.println("Team 0");
//            for(int Id : portal.getTeamRiders(0)) {
//                System.out.printf("Rider Id: %x\n", Id);
//            }
//
//            System.out.println("Team 1");
//            for(int Id : portal.getTeamRiders(1)) {
//                System.out.printf("Rider Id: %x\n", Id);
//            }
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//    }
    CyclingPortalInterface portal;

    @BeforeEach
    void initPortal() {
        portal = new CyclingPortal();
    }

    //assertThrows

    @Nested
    class CyclingPortalTests {
        @ParameterizedTest
        @CsvSource({"test, testDescription", "Ineos Grenadiers, The best grand tour team", "TeStTeaM, F*un£nyStylinh"})
        void testCreateTeam(String name, String description) {
            try {
                Object x = portal.createTeam(name, description);
                assertTrue(x instanceof Integer);
            } catch (Exception e) {
                System.out.println("Error:" + e);
            }
        }

    }




}