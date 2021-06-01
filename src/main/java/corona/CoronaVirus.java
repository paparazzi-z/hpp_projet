package corona;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CoronaVirus {

    private static final List<URL> dirs = new ArrayList<>();

    private static URL dirFr;
    private static URL dirIt;
    private static URL dirSp;

    public static void initDirs(String s){

        dirFr = CoronaVirus.class.getResource("/data/" + s + "/France.csv");
        dirIt = CoronaVirus.class.getResource("/data/" + s + "/Italy.csv");
        dirSp = CoronaVirus.class.getResource("/data/" + s + "/Spain.csv");

        dirs.add(dirFr);
        dirs.add(dirSp);
        dirs.add(dirIt);
    }

    public static void methodNaive(){
        List<ReadData> readDataList = new ArrayList<>();

        for(URL dirCountry : dirs){
            ReadData readData = new ReadData();
            readDataList.add(readData);
            readData.openFile(dirCountry);
        }

        for (ReadData readData : readDataList) {
            String read_string = readData.readLine();
            System.out.println(read_string);
        }
    }

    public static void methodMultiThread(){
//        MultiThreads readFr = new MultiThreads("France", "G:/HPP/HPP_projet/data/20/France.csv");
//        MultiThreads readIt = new MultiThreads("Italy", "G:/HPP/HPP_projet/data/20/Italy.csv");
//        MultiThreads readSp = new MultiThreads("Spain", "G:/HPP/HPP_projet/data/20/Spain.csv");
//
//        Thread threadFr = new Thread(readFr);
//        Thread threadIt = new Thread(readIt);
//        Thread threadSp = new Thread(readSp);
//
//        threadFr.start();
//        threadIt.start();
//        threadSp.start();

//        threadFr.join();
//        threadIt.join();
//        threadSp.join();
    }


}
