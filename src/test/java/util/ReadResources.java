package util;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.NoSuchFileException;

public class ReadResources {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadResources.class);


    public String readFile(String url) throws NoSuchFileException {


        ClassLoader classLoader = this.getClass().getClassLoader();

        InputStream inputStream = classLoader.getResourceAsStream(url);

        Preconditions.checkNotNull(inputStream, "Resource file not found.");

        return changeToString(inputStream);

    }

    private String changeToString(InputStream inputStream) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    LOGGER.error("ERROR MESSAGE", e);
                }
            }
        }
        return sb.toString();
    }
}

