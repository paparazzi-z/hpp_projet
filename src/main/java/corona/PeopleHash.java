package corona;

import java.util.HashMap;

// A HashMap to store persons still in calculation procedure. Key is person_id of the Person and Value is Person.
public class PeopleHash {
    private static HashMap<Integer, Person> peopleHash = new HashMap<>();

    // Add person in to HashMap
    public static void addPerson(Person p){
        peopleHash.put(p.getPerson_id(), p);
    }

    // Remove person from HashMap
    public static void removePerson(Person p){
        peopleHash.remove(p.getPerson_id(), p);
    }

    // Get person from Hashmap
    public static Person getPerson(int i){
        if (peopleHash.containsKey(i))
            return peopleHash.get(i);
        else
            return null;
    }
}
