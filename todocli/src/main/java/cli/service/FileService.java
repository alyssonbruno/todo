package cli.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import cli.domain.DataToFile;

public class FileService {

    private String filename;

    public FileService(String filename) {
        this.filename = filename;
    }

    public <T extends DataToFile> void saveToFile(List<T> datas) { //fix: está apagando o conteudo do arquivo
        try (
            BufferedWriter writer = new BufferedWriter(
                new FileWriter(this.filename)
            )
        ) {
            for (T data : datas) {
                writer.write(data.toLine());
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public <T> List<T> readFromFile(Function<String, T> converter) {
        List<T> result = new ArrayList<>();
        try (
            BufferedReader reader = new BufferedReader(
                new FileReader(this.filename)
            )
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.add(converter.apply(line));
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return result;
    }
}
