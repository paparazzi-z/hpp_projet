package corona;

import java.util.Vector;

public class MultiThreads implements Runnable{

    private String country;
    private String dir;
    public Vector<String> currentData = new Vector<>(4);

    public MultiThreads (String c, String dir) {
        this.country = c;
        this.dir = dir;
    }

    @Override
    public void run(){
//        ReadData data = new ReadData(dir, country);
//        System.out.println(currentData.size());
//        data.openFile();
////        do{
//            currentData = data.readLine();
//            System.out.println(currentData);
//            System.out.println(currentData.size());
////        }while(!data.finished);

    }

}
