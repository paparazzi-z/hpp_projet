package corona;

import java.util.LinkedList;
import java.util.List;

public class CalculateData {

    public List<String> top3;
    private List<String> datas;
    private int currentDate;

    public CalculateData() {
        top3 = new LinkedList<>();
        currentDate = 0;
    }

    public void setTop3(List<String> existedTop3) {
        top3 = existedTop3;
    }

    public void checkUpdate(String country , int personID , int score){

    }

    public void calculate(List<String> fr, List<String> it, List<String> sp) {
        // current pointer position of three list
        int posFr = 0;
        int posIt = 0;
        int posSp = 0;

        int totalLengthFr = (fr.size() + 1)/3;
        int totalLengthIt = (it.size() + 1)/3;
        int totalLengthSp = (sp.size() + 1)/3;

        boolean finish = false;

        while(!finish) {
            if (posFr < totalLengthFr && posIt < totalLengthIt && posSp < totalLengthSp) {
//                currentDate = Math.min()
            } else if(posFr == totalLengthFr && posIt < totalLengthIt && posSp < totalLengthSp) {

            } else if(posFr < totalLengthFr && posIt == totalLengthIt && posSp < totalLengthSp) {

            } else if(posFr < totalLengthFr && posIt < totalLengthIt && posSp == totalLengthSp) {

            } else if(posFr == totalLengthFr && posIt == totalLengthIt && posSp < totalLengthSp) {

            } else if(posFr == totalLengthFr && posIt < totalLengthIt && posSp == totalLengthSp) {

            } else if(posFr < totalLengthFr && posIt == totalLengthIt && posSp == totalLengthSp) {

            } else
                finish = true;
        }
    }
}
