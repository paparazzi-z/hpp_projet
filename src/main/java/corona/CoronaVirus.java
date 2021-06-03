package corona;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

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

    public CoronaVirus(String folder, String s, String c){
        size = Integer.parseInt(s);

        dirFr = CoronaVirus.class.getResource("/" + folder + "/" + s + "/France.csv");
        dirIt = CoronaVirus.class.getResource("/" + folder + "/" + s + "/Italy.csv");
        dirSp = CoronaVirus.class.getResource("/" + folder + "/" + s + "/Spain.csv");

        if(c.contains("Fr"))
            dirs.add(dirFr);
        if(c.contains("It"))
            dirs.add(dirIt);
        if(c.contains("Sp"))
            dirs.add(dirSp);

    }

    public void methodNaive(){
        try{
            List<ReadData> readDataList = new ArrayList<>();

            for(URL dirCountry : dirs){
                ReadData readData = new ReadData();
                readDataList.add(readData);
                readData.openFile(dirCountry);
            }

            peopleList = new ArrayList<>();

            for (ReadData readData : readDataList) {
                String read_string = readData.readLine();

//            System.out.println(read_string);

                if(!read_string.equals("end")){
                    current_person = readData.parseLine(read_string);
                    peopleList.add(current_person);
                }
                else{
                    readData.closeFile();
                }
            }

            while (!readDataList.isEmpty()){
                priority_person = peopleList.get(0);
                for(Person person : peopleList){
                    if (person.getPerson_id() < priority_person.getPerson_id())
                        priority_person = person;
                }
                priority_person.setWeight(10);

                // Add person to calculation procedure
                calculateData.calculate(priority_person);

                output = calculateData.getTop3();
                System.out.println(output);

                int index = peopleList.indexOf(priority_person);
                String read_string = readDataList.get(index).readLine();
                if(!read_string.equals("end")){
                    current_person = readDataList.get(index).parseLine(read_string);
                    peopleList.set(index,current_person);
                }
                else{
                    readDataList.remove(index);
                    peopleList.remove(index);
                }
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    public void methodMultiThread(){
        List<ReadData> readDataList = new ArrayList<>();

            for(URL dirCountry : dirs){
                ReadData readData = new ReadData();
                readDataList.add(readData);
                readData.openFile(dirCountry);
            }

            peopleList = new ArrayList<>();

            for (ReadData readData : readDataList) {
                String read_string = readData.readLine();


                if(!read_string.equals("end")){
                    current_person = readData.parseLine(read_string);
                    peopleList.add(current_person);
                }
                else{
                    readData.closeFile();
                }
            }
        BlockingQueue<Person> blockingQueueRead = new LinkedBlockingDeque<>(100);

        readMultiThread readMultiThread = new readMultiThread(blockingQueueRead, peopleList, readDataList);
        calculateMultiThread calculateMultiThread = new calculateMultiThread(blockingQueueRead, calculateData);

        readThread = new Thread(readMultiThread);
        calculateThread = new Thread(calculateMultiThread);

        calculateThread.setPriority(10);

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


    public String getOutput() {
        return output;
    }
}

class readMultiThread implements Runnable{
    BlockingQueue<Person> blockingQueueRead;
    List<Person> peopleList;
    List<ReadData> readDataList;

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

    public void read() {
        while (!readDataList.isEmpty()) {
            Person priority_person = peopleList.get(0);
            for (Person person : peopleList) {
                if (person.getPerson_id() < priority_person.getPerson_id())
                    priority_person = person;
            }
            priority_person.setWeight(10);

            try {
                blockingQueueRead.put(priority_person);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(blockingQueueRead);

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

class calculateMultiThread implements Runnable{
    BlockingQueue<Person> blockingQueueRead;
    CalculateData calculateData;
    String output;

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
//        }
    }

    public void calculate(){
        while (CoronaVirus.readThread.isAlive()||!blockingQueueRead.isEmpty()){
            if(!blockingQueueRead.isEmpty())
            // Add person to calculation procedure
            try {
                calculateData.calculate(blockingQueueRead.take());
                output = calculateData.getTop3();
                System.out.println(output);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
