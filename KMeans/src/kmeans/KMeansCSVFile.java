/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmeans;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

/**
 *
 * @author Aureli Isaias
 */
public class KMeansCSVFile {
    public static void main(String[] args) throws FileNotFoundException, Exception {        
        String file = "iris.csv";
        readCSVFile.readAllDataAtOnce(file);
    }
}
