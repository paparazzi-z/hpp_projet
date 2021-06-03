import corona.*;

public class Main {

    public static void main(String[] args){

        // Choose whether using multithreading
        boolean multi = true;
        // Choose the folder going to use. "data" or "test"
        String folder = "data";
        // Choose the size of the data.
        String size = "20";
        // Choose to use which countries' data. "Fr", "It", "Sp", "FrIt", "FrSp", "ItSp" or "FrItSp"
        String country = "FrItSp";

        CoronaVirus coronaVirus = new CoronaVirus(folder, size, country);

        System.out.println("Begin reading data...");
        long startTime = System.nanoTime();

        if (multi)
            coronaVirus.methodMultiThread();
        else
            coronaVirus.methodNaive();

        long endTime = System.nanoTime();
        System.out.println("Analyse done!");
        System.out.println("Time used: " + (endTime-startTime)/1000000000.0 + " seconds");

    }


}
