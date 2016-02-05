import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Gregory on 2/4/2016.
 */
public class ReadFile {

    private String path;

    public ReadFile(String path) {
        this.path = path;
    }

    public String[] OpenFile(int numLines) throws IOException {
        FileReader fr = new FileReader(path);
        BufferedReader textReader = new BufferedReader(fr);

        String[] textData = new String[numLines];

        for(int i = 0; i < numLines; i++) {
            textData[i] = textReader.readLine();
        }

        textReader.close();
        return textData;
    }
}
