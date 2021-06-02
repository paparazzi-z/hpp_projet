package corona;

import java.util.ArrayList;
import java.util.List;

public class InfectChain implements Comparable<InfectChain> {

    private Person root;
    private List<Person> members;
    private Person end;
    private int weight;
    private String country;

    public InfectChain(Person r){
        this.root = r;
        this.members = new ArrayList<>();
        members.add(r);
        this.end = r;
        this.weight = r.getWeight();
        this.country = r.getCountry();
    }

    public void addPerson(Person p){
        this.members.add(p);
        this.end = p;
        this.weight += p.getWeight();
    }

    public void updateChainWeight(int currentTime){
        this.weight = 0;
        for (Person member:members){
            member.updatePersonWeight(currentTime);
            this.weight += member.getWeight();
        }
    }


    @Override
    public int compareTo(InfectChain c){
        if (this.weight != c.weight){
            return Integer.compare(c.weight, this.weight);
        }
        else{
            return Integer.compare(this.root.getDiagnosed_ts(), c.root.getDiagnosed_ts());
        }
    }


    public Person getRoot() {
        return root;
    }

    public void setRoot(Person root) {
        this.root = root;
    }

    public List<Person> getMembers() {
        return members;
    }

    public void setMembers(List<Person> members) {
        this.members = members;
    }

    public Person getEnd() {
        return end;
    }

    public void setEnd(Person end) {
        this.end = end;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
