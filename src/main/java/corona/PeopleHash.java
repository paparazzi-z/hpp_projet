package corona;

import java.util.HashMap;

public class PeopleHash {
    private static HashMap<Integer, Person> peopleHash = new HashMap<>();

    public static void addPerson(Person p){
        peopleHash.put(p.getPerson_id(), p);
    }

    public static void removePerson(Person p){
        peopleHash.remove(p.getPerson_id(), p);
    }

    public static Person getPerson(int i){
        if (peopleHash.containsKey(i))
            return peopleHash.get(i);
        else
            return null;
    }
}