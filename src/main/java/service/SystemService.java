package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import org.springframework.stereotype.Service;

@Service
public class SystemService {

    /** Receive a stream of information from PIPE then add one taks
     *  for each line from it. If --file-format is text (default)
     *  all line will be include, if is code only line with "todo:"
     *  will be added.
     * @author Alysson
     * @version 2024-11
     */
    public BufferedReader readFromFile(File file) throws IOException {
        BufferedReader pipeReader;

        if (file != null) {
            pipeReader = Files.newBufferedReader(file.toPath());
        } else {
            pipeReader = new BufferedReader(new InputStreamReader(System.in));
        }
        return pipeReader;
    }
}
