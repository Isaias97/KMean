/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmeans;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author Aureli Isaias
 */
public class readExcelIris {
    private String inputFile;
    static String [][] data = new String[150][4];
    private static final int NUM_CLUSTERS = 2;    // Total clusters.
    private static final int TOTAL_DATA = 7;      // Total data points.
    
    private static double SAMPLES[][] = new double[150][4];
    
    private static ArrayList<Data> dataSet = new ArrayList<Data>();
    private static ArrayList<Centroid> centroids = new ArrayList<Centroid>();
    
    private static void initialize()
    {
        System.out.println("Centroids initialized at:");
        centroids.add(new Centroid(5.7, 2.5, 5.0, 2.0)); // lowest set.
        centroids.add(new Centroid(7.2, 3.6, 6.1, 2.5)); // highest set.
        System.out.println("     (" + centroids.get(0).X() + ", " + centroids.get(0).Y() + 
                                    ", "+centroids.get(0).Z() + ", " + centroids.get(0).W()+")");
        System.out.println("     (" + centroids.get(1).X() + ", " + centroids.get(1).Y() +
                                  ", "+centroids.get(1).Z()+", "+centroids.get(1).W()+")");
        System.out.print("\n");
    }
    
    private static void kMeanCluster()
    {
        final double bigNumber = Math.pow(10, 10);    // some big number that's sure to be larger than our data range.
        double minimum = bigNumber;                   // The minimum value to beat. 
        double distance = 0.0;                        // The current minimum value.
        int sampleNumber = 0;
        int cluster = 0;
        boolean isStillMoving = true;
        Data newData = null;
        
        // Add in new data, one at a time, recalculating centroids with each new one. 
        while(dataSet.size() < TOTAL_DATA)
        {
            newData = new Data(SAMPLES[sampleNumber][0], SAMPLES[sampleNumber][1]
            , SAMPLES[sampleNumber][2], SAMPLES[sampleNumber][3]);
            dataSet.add(newData);
            minimum = bigNumber;
            for(int i = 0; i < NUM_CLUSTERS; i++)
            {
                distance = dist(newData, centroids.get(i));
                if(distance < minimum){
                    minimum = distance;
                    cluster = i;
                }
            }
            newData.cluster(cluster);
            
            // calculate new centroids.
            for(int i = 0; i < NUM_CLUSTERS; i++)
            {
                int totalX = 0;
                int totalY = 0;
                int totalZ = 0;
                int totalW = 0;
                int totalInCluster = 0;
                for(int j = 0; j < dataSet.size(); j++)
                {
                    if(dataSet.get(j).cluster() == i){
                        totalX += dataSet.get(j).X();
                        totalY += dataSet.get(j).Y();
                        totalZ += dataSet.get(j).Z();
                        totalW += dataSet.get(j).W();
                        totalInCluster++;
                    }
                }
                if(totalInCluster > 0){
                    centroids.get(i).X(totalX / totalInCluster);
                    centroids.get(i).Y(totalY / totalInCluster);
                    centroids.get(i).Z(totalZ / totalInCluster);
                    centroids.get(i).W(totalW / totalInCluster);                    
                }
            }
            sampleNumber++;
        }
        
        // Now, keep shifting centroids until equilibrium occurs.
        while(isStillMoving)
        {
            // calculate new centroids.
            for(int i = 0; i < NUM_CLUSTERS; i++)
            {
                int totalX = 0;
                int totalY = 0;
                int totalZ = 0;
                int totalW = 0;
                int totalInCluster = 0;
                for(int j = 0; j < dataSet.size(); j++)
                {
                    if(dataSet.get(j).cluster() == i){
                        totalX += dataSet.get(j).X();
                        totalY += dataSet.get(j).Y();
                        totalZ += dataSet.get(j).Z();
                        totalW += dataSet.get(j).W();
                        totalInCluster++;
                    }
                }
                if(totalInCluster > 0){
                    centroids.get(i).X(totalX / totalInCluster);
                    centroids.get(i).Y(totalY / totalInCluster);
                    centroids.get(i).Z(totalZ / totalInCluster);
                    centroids.get(i).W(totalW / totalInCluster);  
                }
            }
            
            // Assign all data to the new centroids
            isStillMoving = false;
            
            for(int i = 0; i < dataSet.size(); i++)
            {
                Data tempData = dataSet.get(i);
                minimum = bigNumber;
                for(int j = 0; j < NUM_CLUSTERS; j++)
                {
                    distance = dist(tempData, centroids.get(j));
                    if(distance < minimum){
                        minimum = distance;
                        cluster = j;
                    }
                }
                tempData.cluster(cluster);
                if(tempData.cluster() != cluster){
                    tempData.cluster(cluster);
                    isStillMoving = true;
                }
            }
        }
    }
    
    /**
     * // Calculate Euclidean distance.
     * @param d - Data object.
     * @param c - Centroid object.
     * @return - double value.
     */
    private static double dist(Data d, Centroid c)
    {
        return Math.sqrt(Math.pow((c.Y() - d.Y()), 2) + Math.pow((c.X() - d.X()), 2)
        + Math.pow((c.Z() - d.Z()), 2) + Math.pow((c.W() - d.W()), 2));
    }
    
    private static class Data
    {
        private double mX = 0;
        private double mY = 0;
        private double mZ = 0;
        private double mW = 0;
        private int mCluster = 0;
        
        public Data()
        {
            return;
        }
        
        public Data(double x, double y, double z, double w)
        {
            this.X(x);
            this.Y(y);
            this.Z(z);
            this.W(w);
            return;
        }
        
        public void X(double x)
        {
            this.mX = x;
            return;
        }
        
        public double X()
        {
            return this.mX;
        }
        
        public void Y(double y)
        {
            this.mY = y;
            return;
        }
        
        public double Y()
        {
            return this.mY;
        }
        
        public void Z(double z)
        {
            this.mZ = z;
            return;
        }
        
        public double Z()
        {
            return this.mZ;
        }
        
        public void W(double w)
        {
            this.mW = w;
            return;
        }
        
        public double W()
        {
            return this.mW;
        }
        
        public void cluster(int clusterNumber)
        {
            this.mCluster = clusterNumber;
            return;
        }
        
        public int cluster()
        {
            return this.mCluster;
        }
    }
    
    private static class Centroid
    {
        private double mX = 0.0;
        private double mY = 0.0;
        private double mZ = 0.0;
        private double mW = 0.0;
        
        public Centroid()
        {
            return;
        }
        
        public Centroid(double newX, double newY, double newZ, double newW)
        {
            this.mX = newX;
            this.mY = newY;
            this.mZ = newZ;
            this.mW = newW;
            return;
        }
        
        public void X(double newX)
        {
            this.mX = newX;
            return;
        }
        
        public double X()
        {
            return this.mX;
        }
        
        public void Y(double newY)
        {
            this.mY = newY;
            return;
        }
        
        public double Y()
        {
            return this.mY;
        }
        
        public void Z(double newZ)
        {
            this.mZ = newZ;
            return;
        }
        
        public double Z()
        {
            return this.mZ;
        }
        
        public void W(double newW)
        {
            this.mW = newW;
            return;
        }
        
        public double W()
        {
            return this.mW;
        }
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public void read() throws IOException  {
        File inputWorkbook = new File(inputFile);
        Workbook w;
        try {
            w = Workbook.getWorkbook(inputWorkbook);
            // Get the first sheet
            Sheet sheet = w.getSheet(1);
            // Loop over first 10 column and lines

            for (int j = 0; j < sheet.getColumns(); j++) {
                for (int i = 0; i < sheet.getRows(); i++) {
                    Cell cell = sheet.getCell(j, i);
                    data[i][j] = cell.getContents();
                }
            }
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        readExcelIris test = new readExcelIris();
        test.setInputFile("G:/NetBeansProjects/KMean/KMeans/iris.xls");
        test.read();
        
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                SAMPLES[i][j] = Double.valueOf(data[i][j]);
            }
        }
        
        initialize();
        kMeanCluster();
        
        // Print out clustering results.
        for(int i = 0; i < NUM_CLUSTERS; i++)
        {
            System.out.println("Cluster " + i + " includes:");
            for(int j = 0; j < TOTAL_DATA; j++)
            {
                if(dataSet.get(j).cluster() == i){
                    System.out.println("     (" + dataSet.get(j).X() + ", " + dataSet.get(j).Y() + ","
                            + dataSet.get(j).Z()+","+dataSet.get(j).W()+")");
                }
            } // j
            System.out.println();
        } // i
        
        // Print out centroid results.
        System.out.println("Centroids finalized at:");
        for(int i = 0; i < NUM_CLUSTERS; i++)
        {
            System.out.println("     (" + centroids.get(i).X() + ", " + centroids.get(i).Y()
                                        + ", "+centroids.get(i).Z()+","+centroids.get(i).W()+")");
        }
        System.out.print("\n");
        return;
    }
}
