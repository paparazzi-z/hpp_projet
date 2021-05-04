package corona;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadData {

    private String filePath;
    public List<String> data;

    public ReadData( String s) {
        this.filePath = s;
    }

    public String getPath() {
        return this.filePath;
    }

    public void fetchData() {
        String csvFile = this.filePath;
        CSVReader reader = null;
        data = new ArrayList<String>();

        try {
            reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            while ((line = reader.readNext()) != null) {
                int tmp = Integer.valueOf(line[4].strip().split("\\.")[0]);
                line[4] = Integer.toString(tmp);
                System.out.println(line[0] + ", " + line[1] + ", " + line[2] + ", " + line[3] + ", " + line[4] + ", " + line[5] + ", " + line[6]);
                for (int j = 0 ; j < line.length ; j ++)
                    data.add(line[j]);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

    }
}


