package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class CodeAnaliseService {

    /** Receive a stream of information from PIPE then add one taks
     *  for each line from it. If --file-format is text (default)
     *  all line will be include, if is code only line with "todo:"
     *  will be added.
     * @author Alysson
     * @version 2024-11
     */
    public static void fromBufferedReader(
        BufferedReader reader,
        String format,
        ArrayList<String> tags
    ) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            if (format.equals("code")) {
                for (String tag : tags) {
                    if (line.contains(tag + ":")) {
                        int index = line.indexOf(tag + ":");
                        System.out.printf(
                            "add %s\n",
                            line.substring(index + tag.length() + 1).trim()
                        );
                    }
                }
            } else {
                System.out.printf("add %s\n", line);
            }
        }
    }
}
