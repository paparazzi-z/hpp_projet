package corona;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


 /*
Principle class of the project. You can choose methodNaive or methodMultiThread in main function. It control the
global flow of the project.
 */
public class CoronaVirus {

    private final List<URL> dirs = new ArrayList<>();

    private int size;

    private URL dirFr;
    private URL dirIt;
    private URL dirSp;

    private List<Person> peopleList;
    private Person current_person;
    private Person priority_person;
    private final CalculateData calculateData = new CalculateData();
    private String output;

    static Thread readThread;
    static Thread calculateThread;

    // Constructor
    public CoronaVirus(String folder, String s, String c){
        size = Integer.parseInt(s);

        // Get URL of each file
        dirFr = CoronaVirus.class.getResource("/" + folder + "/" + s + "/France.csv");
        dirIt = CoronaVirus.class.getResource("/" + folder + "/" + s + "/Italy.csv");
        dirSp = CoronaVirus.class.getResource("/" + folder + "/" + s + "/Spain.csv");

        // Add URL(s) to list
        if(c.contains("Fr"))
            dirs.add(dirFr);
        if(c.contains("It"))
            dirs.add(dirIt);
        if(c.contains("Sp"))
            dirs.add(dirSp);

    }

    // Naive method(without multithreading)
    public void methodNaive(){
        try{
            // Create InputStream and Scanner for each file which are written in class ReadData. Add them to a list.
            List<ReadData> readDataList = new ArrayList<>();

            for(URL dirCountry : dirs){
                ReadData readData = new ReadData();
                readDataList.add(readData);
                readData.openFile(dirCountry);
            }

            // Create a list to store data (as a Person) which is read from file(s). Store 3 Person max.
            peopleList = new ArrayList<>();

            // Read first line from file(s)
            Iterator<ReadData> iterator = readDataList.iterator();
            while (iterator.hasNext()){
                ReadData readData = iterator.next();
                String read_string = readData.readLine();

                if(!read_string.equals("end")){
                    current_person = readData.parseLine(read_string);
                    peopleList.add(current_person);
                }
                else{
                    readData.closeFile();
                    iterator.remove();
                }
            }

            // Continually read next line in file(s) until there is no data to read.
            if (readDataList.isEmpty()){
                System.out.println("NO DATA!!!");
                this.output = "NO DATA!!!";
            }
            else{
                while (!readDataList.isEmpty()){
//                    Collections.sort(peopleList);
                    // Set the person with smallest id as priority_person which will be calculated.
                    priority_person = peopleList.get(0);
                    for(Person person : peopleList){
                        if (person.getPerson_id() < priority_person.getPerson_id())
                            priority_person = person;
                    }
                    priority_person.setWeight(10);

                    // Add person to calculation procedure where we are going to create and calculate infect chains.
                    calculateData.calculate(priority_person);

//                output = calculateData.getTop3();
//                System.out.println(output);

                    // Read next line of the file which the former priority_person belongs to.
                    int index = peopleList.indexOf(priority_person);
                    String read_string = readDataList.get(index).readLine();
                    if(!read_string.equals("end")){
                        current_person = readDataList.get(index).parseLine(read_string);
                        peopleList.set(index,current_person);
                    }
                    else{
                        readDataList.get(index).closeFile();
                        readDataList.remove(index);
                        peopleList.remove(index);
                    }
                }

                // Sort all chains in order and get TOP3 chains.
                calculateData.sortChain();
                output = calculateData.getTop3();
                System.out.println(output);
            }


        } catch (NullPointerException e){
            e.printStackTrace();
        }


    }

    // Method using multithreading
    public void methodMultiThread(){
        // Create InputStream and Scanner for each file which are written in class ReadData. Add them to a list.
        List<ReadData> readDataList = new ArrayList<>();

        for(URL dirCountry : dirs){
            ReadData readData = new ReadData();
            readDataList.add(readData);
            readData.openFile(dirCountry);
        }

        // Create a list to store data (as a Person) which is read from file(s). Store 3 Person max.
        peopleList = new ArrayList<>();

        // Read first line from file(s)
        Iterator<ReadData> iterator = readDataList.iterator();
        while (iterator.hasNext()){
            ReadData readData = iterator.next();
            String read_string = readData.readLine();

            if(!read_string.equals("end")){
                current_person = readData.parseLine(read_string);
                peopleList.add(current_person);
            }
            else{
                readData.closeFile();
                iterator.remove();
            }
        }

        // Continually read next line in file(s) until there is no data to read.
        if (readDataList.isEmpty()){
            System.out.println("NO DATA!!!");
            this.output = "NO DATA!!!";
        }
        else {
            // Create a BlockingQueue to store the data (as a Person) read from file.
            BlockingQueue<Person> blockingQueueRead = new LinkedBlockingDeque<>(20);

            // Create two instances of readMultiThread and calculateMultiThread
            readMultiThread readMultiThread = new readMultiThread(blockingQueueRead, peopleList, readDataList);
            calculateMultiThread calculateMultiThread = new calculateMultiThread(blockingQueueRead, calculateData);

            // Create two threads for reading and calculating
            readThread = new Thread(readMultiThread);
            calculateThread = new Thread(calculateMultiThread);

            // Set priority
            readThread.setPriority(1);
            calculateThread.setPriority(10);

            // Start threads
            readThread.start();
            calculateThread.start();

            try{
                readThread.join();
                calculateThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            output = calculateData.getTop3Output();
//        System.out.println(output);
        }


    }


    public String getOutput() {
        return output;
    }
}

// Class for reading data which implements interface Runnable to realize multithreading
class readMultiThread implements Runnable{
    BlockingQueue<Person> blockingQueueRead;
    List<Person> peopleList;
    List<ReadData> readDataList;

    // Constructor
    public readMultiThread(BlockingQueue<Person> blockingQueueRead, List<Person> peopleList, List<ReadData> readDataList) {
        this.blockingQueueRead = blockingQueueRead;
        this.peopleList = peopleList;
        this.readDataList = readDataList;
    }

    @Override
    public void run(){
//        synchronized (blockingQueueRead) {
            read();
//        }
    }

    public synchronized void read() {
        while (!readDataList.isEmpty()) {
            // Set the person with smallest id as priority_person which will be calculated.
            Person priority_person = peopleList.get(0);
            for (Person person : peopleList) {
                if (person.getPerson_id() < priority_person.getPerson_id())
                    priority_person = person;
            }
            priority_person.setWeight(10);

            // Put priority_person in to BlockingQueue
            try {
                blockingQueueRead.put(priority_person);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println(blockingQueueRead);

            // Read next line of the file which the former priority_person belongs to.
            int index = peopleList.indexOf(priority_person);
            String read_string = readDataList.get(index).readLine();
            if(!read_string.equals("end")){
                Person current_person = readDataList.get(index).parseLine(read_string);
                peopleList.set(index,current_person);
            }
            else{
                readDataList.remove(index);
                peopleList.remove(index);
            }
        }
    }
}

// Class for calculating data which implements interface Runnable to realize multithreading
class calculateMultiThread implements Runnable{
    BlockingQueue<Person> blockingQueueRead;
    CalculateData calculateData;
    String output;

    // Constructor
    public calculateMultiThread(BlockingQueue<Person> blockingQueueRead, CalculateData calculateData) {
        this.blockingQueueRead = blockingQueueRead;
        this.calculateData = calculateData;
    }

    @Override
    public void run(){
//        try {
//            Thread.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        while(!blockingQueueRead.isEmpty())
//        synchronized (blockingQueueRead) {

        calculate();

        // Sort all chains in order and get TOP3 chains.
        calculateData.sortChain();
        output = calculateData.getTop3();
        System.out.println(output);
//        }
    }

    public synchronized void calculate(){
        while (CoronaVirus.readThread.isAlive()||!blockingQueueRead.isEmpty()){
            if(!blockingQueueRead.isEmpty())
                // Add Person which is taken from BlockingQueue to calculation procedure
                try {
                    calculateData.calculate(blockingQueueRead.take());
//                    output = calculateData.getTop3();
//                    System.out.println(output);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        }
    }
}

//class calculateMultiThread implements Runnable{
//    BlockingQueue<Person> blockingQueueRead;
//    Person personGetInfected;
//    List<Person> roots;
//    List<InfectChain> chains;
//    InfectChain[] top3;
//    String top3Output;
//    int currentDate;
//
//    public calculateMultiThread(BlockingQueue<Person> blockingQueueRead) {
//        this.blockingQueueRead = blockingQueueRead;
//        this.top3 = new InfectChain[3];
//        this.roots = new ArrayList<>();
//        this.chains = new ArrayList<>();
//        currentDate = 0;
//    }
//
//    @Override
//    public void run(){
////        try {
////            Thread.sleep(1);
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
////        while(!blockingQueueRead.isEmpty())
////        synchronized (blockingQueueRead) {
//            calculate();
////        }
//    }
//
//    public void calculate(){
//        while (CoronaVirus.readThread.isAlive()||!blockingQueueRead.isEmpty()){
//            if(!blockingQueueRead.isEmpty())
//            // Add person to calculation procedure
//            try {
//                // add new person
//                this.personGetInfected = blockingQueueRead.take();
//                this.currentDate = personGetInfected.getDiagnosed_ts();
//                PeopleHash.addPerson(personGetInfected);
//
//                if (!chains.isEmpty()){
//                    // Update weights in each chain
//                    for (InfectChain chain:chains){
//                        chain.updateChainWeight(this.currentDate);
//                    }
//
//                    // Remove chain whose weight is 0
//                    Iterator<InfectChain> iterator = chains.iterator();
//                    while (iterator.hasNext()){
//                        InfectChain chain = iterator.next();
//                        if(chain.getWeight() == 0){
//                            for (Person person:chain.getMembers()){
//                                PeopleHash.removePerson(person);
//                            }
//                            roots.remove(chain.getRoot());
//                            iterator.remove();
//                        }
//                    }
//                }
//
//                Person root = findRoot(this.personGetInfected);
//
//                if(root.equals(this.personGetInfected)){
//                    InfectChain newChain = new InfectChain(this.personGetInfected);
//                    chains.add(newChain);
//                    roots.add(this.personGetInfected);
//                }
//                else{
//                    int i = roots.indexOf(root);
//                    InfectChain currentChain = chains.get(i);
//                    currentChain.addPerson(this.personGetInfected);
//                }
//
//                // Sort chain
//                Collections.sort(chains);
//                for (int index = 0 ; index < chains.size() ; index ++){
//                    Person p = chains.get(index).getRoot();
//                    roots.set(index, p);
//                }
//
//                top3Output = "";
//                if(chains.size() > 2){
//                    for (int i = 0 ; i < 3 ; i++){
//                        top3[i] = chains.get(i);
//                        top3Output += top3[i].getCountry() + ", " + top3[i].getRoot().getPerson_id() + ", " + top3[i].getWeight() + ", ";
//                    }
//                }
//                else {
//                    for (int i = 0 ; i < chains.size() ; i++){
//                        top3[i] = chains.get(i);
//                        top3Output += top3[i].getCountry() + ", " + top3[i].getRoot().getPerson_id() + ", " + top3[i].getWeight() + ", ";
//                    }
//                }
//                top3Output = top3Output.substring(0,top3Output.length()-2);
//
//                System.out.println(top3Output);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
//
//    public Person findRoot(Person p){
//        if (p.getContaminated_by() == -1){
//            return p;
//        }
//        else{
//            if (PeopleHash.getPerson(p.getContaminated_by()) != null){
//                return findRoot(PeopleHash.getPerson(p.getContaminated_by()));
//            }
//            else
//                return p;
//        }
//    }
//}
