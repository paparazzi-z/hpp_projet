package corona;

public class MultiThreads {

    public class ReadThread implements Runnable{

        private String name;

        public ReadThread (String name) {
            this.name = name;
        }

        @Override
        public void run(){
            ReadData dataFr = new ReadData("G:/HPP/HPP_projet/data/20/France.csv", "France");
            ReadData dataIt = new ReadData("G:/HPP/HPP_projet/data/20/Italy.csv", "Italy");
            ReadData dataSp = new ReadData("G:/HPP/HPP_projet/data/20/Spain.csv", "Spain");
        }
    }

    public class CalculateThread implements Runnable{

        private String name;

        public CalculateThread (String name) {
            this.name = name;
        }

        @Override
        public void run(){

        }
    }
}
