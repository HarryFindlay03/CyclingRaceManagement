package cycling;

import java.util.ArrayList;

public class Rider {
    private String name;
    private int yearOfBirth;
    private int riderId;

    private static ArrayList<Integer> riderIds = new ArrayList<Integer>();

    public Rider(String name, int yearOfBirth) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;

        if(riderIds.size() == 0) {
            riderId = 0;
            riderIds.add(riderId);
        } else {
            riderId = riderIds.get(riderIds.size() - 1) + 1;
            riderIds.add(riderId);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
