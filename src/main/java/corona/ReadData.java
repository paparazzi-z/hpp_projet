package corona;

import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.net.URL;

// Class for reading data from file. It manage InputStream and Scanner.
public class ReadData {

    private InputStream inputStream = null;
    private Scanner scanner = null;
    private String country;


    // Open a file with a URL. Create InputStream and Scanner.
    public void openFile(URL dir) {
        this.country = FilenameUtils.getBaseName(dir.getPath());
        try {
            inputStream = dir.openStream();
            scanner = new Scanner(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read next line
    public String readLine() {
        if (scanner.hasNext()) {
            return scanner.nextLine();
        }
        else {
            return "end";
        }
    }

    // Close the file when read is end.
    public void closeFile() {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                System.err.println("Cannot close file");
                e.printStackTrace();
            }
        }
        if (scanner != null) {
            scanner.close();
        }
    }

    // Parse line read from file to a Person. Only save useful information.
    public Person parseLine(String s){
        String[] line = s.split(","); // split each line with comma
        int person_id = Integer.parseInt(line[0]);
        int diagnosed_ts = Integer.parseInt(line[4].strip().split("\\.")[0]); // Delete white-space and numbers after the decimal point
        int contaminated_by;
        if (line[5].strip().equals("unknown"))
            contaminated_by = -1; // if the contaminated_by value is "unknown", set it to -1;
        else
            contaminated_by = Integer.parseInt(line[5].strip());

        return new Person(person_id, diagnosed_ts, contaminated_by, this.country);
    }


}


