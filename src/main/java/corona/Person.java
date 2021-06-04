package corona;

import java.util.Objects;

// Class Person to store information of each person
public class Person implements Comparable<Person> {
    private int person_id;
    private int diagnosed_ts;
    private int contaminated_by;
    private String country;
    private int weight;

    // Constructor
    public Person(int p, int d, int c, String co){
        this.person_id = p;
        this.diagnosed_ts = d;
        this.contaminated_by = c;
        this.country = co;
    }

    // Update weight of the person by using the currentTime which is the diagnosed_ts of newest person in calculation procedure.
    public void updatePersonWeight(int currentTime){
        int period = currentTime - this.diagnosed_ts;

        if (period <= 604800) {
            weight = 10;
        } else if (period <= 1209600) {
            weight = 4;
        }
        else {
            weight = 0;
        }
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public int getDiagnosed_ts() {
        return diagnosed_ts;
    }

    public void setDiagnosed_ts(int diagnosed_ts) {
        this.diagnosed_ts = diagnosed_ts;
    }

    public int getContaminated_by() {
        return contaminated_by;
    }

    public void setContaminated_by(int contaminated_by) {
        this.contaminated_by = contaminated_by;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry(){
        return this.country;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight(){
        return this.weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return getPerson_id() == person.getPerson_id() && getDiagnosed_ts() == person.getDiagnosed_ts() && getContaminated_by() == person.getContaminated_by() && getWeight() == person.getWeight() && getCountry().equals(person.getCountry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPerson_id(), getDiagnosed_ts(), getContaminated_by(), getCountry(), getWeight());
    }

    @Override
    public int compareTo(Person p){
        return Integer.compare(this.getPerson_id(),p.getPerson_id());
    }
}
