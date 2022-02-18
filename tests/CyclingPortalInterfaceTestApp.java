import cycling.*;

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
    public static void main(String[] args) {
        System.out.println("The system compiled and started the execution...");

        //cycling.MiniCyclingPortalInterface portal = new cycling.BadCyclingPortal();
//		cycling.CyclingPortalInterface portal = new cycling.BadCyclingPortal();

        MiniCyclingPortalInterface portal = new BadCyclingPortal();

        Race r = new Race("test", "testDescription");

        System.out.println(r);

        Race r2 = new Race("Tour de France", "The biggest race in the world!");

        System.out.println(r2);

		assert (portal.getRaceIds().length == 0)
				: "Initial CyclingPortal not empty as required or not returning an empty array.";

    }

}