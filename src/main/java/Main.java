import corona.*;

public class Main {

    public static void main(String[] args){

        // Choose whether using multithreading
        boolean multi = false;
        // Choose the size of the data. 20, 5000 or 1000000
        String size = "20";

        CoronaVirus.initDirs(size);

        if (multi)
            CoronaVirus.methodMultiThread();
        else
            CoronaVirus.methodNaive();



    }


}
