package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import domain.DataToFile;


public class FileService {
    private String filename;

    public FileService(String filename){
        this.filename = filename;
    }

    public void saveToFile(List<DataToFile> datas){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.filename))) {
               for (DataToFile data : datas) {
                   writer.write(data.toLine());
               }
           } catch (IOException e) {
               System.err.println("Error: " + e.getMessage());
           }
       }
    public <T> List<T> readFromFile(Function<String, T> converter){
        List<T> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(this.filename))) {
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


