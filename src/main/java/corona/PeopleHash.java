package corona;

import java.util.HashMap;

public class PeopleHash {
    private static HashMap<Integer, Person> peopleHash = new HashMap<>();

    public static void addPerson(Person p){
        peopleHash.put(p.getID(), p);
    }

    public static void removePerson(Person p){
        peopleHash.remove(p.getID(), p);
    }

    public static Person getPerson(int i){
        return peopleHash.get(i);
    }
}
