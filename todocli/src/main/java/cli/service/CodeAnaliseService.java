package cli.service;

import java.io.BufferedReader;
import java.io.IOException;

import cli.domain.FileFormat;

public class CodeAnaliseService {

    /** Receive a stream of information from PIPE then add one taks
     *  for each line from it. If --file-format is text (default)
     *  all line will be include, if is code only line with "todo:"
     *  will be added.
     * @author Alysson
     * @version 2024-11
     */
    public void fromBufferedReader(
        BufferedReader reader,
        FileFormat format,
        String[] tags
    ) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            if (format != null && format.equals(FileFormat.CODE)) {
                for (String tag : tags) {
                    String lowerCaseLine = line.toLowerCase();
                    String lowerCaseTag = tag.toLowerCase();
                    if (lowerCaseLine.contains(lowerCaseTag + ":")) {
                        int index = lowerCaseLine.indexOf(lowerCaseTag + ":");
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
