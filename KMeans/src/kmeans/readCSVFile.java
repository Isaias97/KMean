/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmeans;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.String;

/**
 *
 * @author Aureli Isaias
 */
public class readCSVFile {
    public static void readDataFromCustomSeperator(String file) { 
        try { 
            // Create an object of file reader class with CSV file as a parameter. 
            FileReader filereader = new FileReader(file); 

            // create csvParser object with 
            // custom seperator semi-colon 
            CSVParser parser = new CSVParserBuilder().withSeparator(';').build(); 

            // create csvReader object with parameter 
            // filereader and parser 
            CSVReader csvReader = new CSVReaderBuilder(filereader) 
                                      .withCSVParser(parser) 
                                      .build(); 

            // Read all data at once 
            List<String[]> allData = csvReader.readAll(); 

            // Print Data. 
            for (String[] row : allData) { 
                for (String cell : row) { 
                    System.out.print(cell + "\t"); 
                } 
                System.out.println(); 
            } 
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
    }
    
    public static void readDataLineByLine(String file){ 
        try { 

            // Create an object of filereader 
            // class with CSV file as a parameter. 
            FileReader filereader = new FileReader(file); 

            // create csvReader object passing 
            // file reader as a parameter 
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                                      .withSkipLines(3).build(); 
            String[] nextRecord; 

            // we are going to read data line by line 
            while ((nextRecord = csvReader.readNext()) != null) { 
                for (String cell : nextRecord) { 
                    System.out.print(cell + "\t"); 
                } 
                System.out.println(); 
            } 
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
    
    public static void readAllDataAtOnce(String file) { 
        try { 
            // Create an object of file reader 
            // class with CSV file as a parameter. 
            FileReader filereader = new FileReader(file); 

            // create csvReader object and skip first Line 
            CSVReader csvReader = new CSVReaderBuilder(filereader) 
                                      .withSkipLines(3) 
                                      .build(); 
            List<String[]> allData = csvReader.readAll(); 

            // print Data 
            for (String[] row : allData) { 
                for (String cell : row) {
//                    String [row][cell] data = cell; 
                    System.out.print(cell + "\t"); 
                } 
                System.out.println(); 
            } 
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
    }
    
    public List<String[]> oneByOne(String file) throws Exception {
        List<String[]> list = new ArrayList<>();
        FileReader filereader = new FileReader(file); 
        CSVReader csvReader = new CSVReaderBuilder(filereader)
                                  .withSkipLines(3)
                                  .build();
        String[] line;
        while ((line = csvReader.readNext()) != null) {
            list.add(line);
        }
        filereader.close();
        csvReader.close();
        return list;
    }
}
