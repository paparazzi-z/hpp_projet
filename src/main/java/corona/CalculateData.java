package corona;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CalculateData {

    private Person personGetInfected;
    private List<Person> roots;
    private List<InfectChain> chains;
    private InfectChain[] top3;
    private String top3Output;

    private int currentDate;

    public CalculateData() {

        this.top3 = new InfectChain[3];
        this.roots = new ArrayList<>();
        this.chains = new ArrayList<>();
        currentDate = 0;
    }

    public void addNewPerson(Person p){
        this.personGetInfected = p;
        this.currentDate = p.getDiagnosed_ts();
        PeopleHash.addPerson(p);
    }

    public void createNewChain(){
        InfectChain newChain = new InfectChain(this.personGetInfected);
        chains.add(newChain);
        roots.add(this.personGetInfected);
    }

    public Person findRoot(Person p){
        if (p.getContaminated_by() == -1){
            return p;
        }
        else{
            if (PeopleHash.getPerson(p.getContaminated_by()) != null){
                return findRoot(PeopleHash.getPerson(p.getContaminated_by()));
            }
            else
                return p;
        }
    }


    // Calculate chains
    public void calculate(Person p) {
        addNewPerson(p);

        if (!chains.isEmpty()){
            // Update weights in each chain
            for (InfectChain chain:chains){
                chain.updateChainWeight(this.currentDate);
            }

            // Remove chain whose weight is 0
            removeChain();
        }


        Person root = findRoot(this.personGetInfected);

        if(root.equals(this.personGetInfected)){
            createNewChain();
        }
        else{
            int i = roots.indexOf(root);
            InfectChain currentChain = chains.get(i);
            currentChain.addPerson(this.personGetInfected);
        }

        //Sort chains and roots
        sortChain();
        calculateTop3();

    }

    // Remove chain. And remove persons in the chain out of hash map. Also remove root in List roots.
    public void removeChain(){
        Iterator<InfectChain> iterator = chains.iterator();
        while (iterator.hasNext()){
            InfectChain chain = iterator.next();
            if(chain.getWeight() == 0){
                for (Person person:chain.getMembers()){
                    PeopleHash.removePerson(person);
                }
                roots.remove(chain.getRoot());
                iterator.remove();
            }
        }
    }

    // Sort chains and roots
    public void sortChain(){
        Collections.sort(chains);
        for (int index = 0 ; index < chains.size() ; index ++){
            Person p = chains.get(index).getRoot();
            roots.set(index, p);
        }
    }

    public void calculateTop3() {
        top3Output = "";
        if(chains.size() > 2){
            for (int i = 0 ; i < 3 ; i++){
                top3[i] = chains.get(i);
                top3Output += top3[i].getCountry() + ", " + top3[i].getRoot().getPerson_id() + ", " + top3[i].getWeight() + ", ";
            }
        }
        else {
            for (int i = 0 ; i < chains.size() ; i++){
                top3[i] = chains.get(i);
                top3Output += top3[i].getCountry() + ", " + top3[i].getRoot().getPerson_id() + ", " + top3[i].getWeight() + ", ";
            }
        }
        top3Output = top3Output.substring(0,top3Output.length()-2);
        System.out.println(top3Output);
    }


    // Obtain TOP3 chains
    public String getTop3() {
        top3Output = "";
        if(chains.size() > 2){
            for (int i = 0 ; i < 3 ; i++){
                top3[i] = chains.get(i);
                top3Output += top3[i].getCountry() + ", " + top3[i].getRoot().getPerson_id() + ", " + top3[i].getWeight() + ", ";
            }
        }
        else {
            for (int i = 0 ; i < chains.size() ; i++){
                top3[i] = chains.get(i);
                top3Output += top3[i].getCountry() + ", " + top3[i].getRoot().getPerson_id() + ", " + top3[i].getWeight() + ", ";
            }
        }
        top3Output = top3Output.substring(0,top3Output.length()-2);
        System.out.println(top3Output);
        return top3Output;
    }

    public String getTop3Output() {
        return top3Output;
    }
}
