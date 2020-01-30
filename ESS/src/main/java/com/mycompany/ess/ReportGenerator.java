/*
 *The Report Generator class was produced by Soloman Fitiwok, Michaela Laplante, 
Rozanne Cabrera, Abraham Muslimani, and Eric Harvey. The use is limited
to non commercial analisys of power system meterics. 
 */
package com.mycompany.ess;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

/**
 *The report generator class is used to generate the electrical load reports
 * from the data imported by the menu class. The class has methods to find the 
 * minium maximum and avenge values from the imported data. The report can be 
 * returned as a string or exported to a named CSV file using the exportReport method
 */
public class ReportGenerator {
    HashMap[] dataMaps;
    public ReportGenerator(ArrayList<File> dataFiles) throws FileNotFoundException{
        int count = 0;
        dataMaps = new HashMap[dataFiles.size()+1];
        for(File input:dataFiles){
            dataMaps[count] =  fileData(input);
            count++;
        }
        dataMaps[dataFiles.size()]= new HashMap<String,Integer>();
        for(int i=1; i<dataFiles.size();i++){
          System.out.println(i);
          dataMaps[dataFiles.size()] = fileData( dataMaps[dataFiles.size()] , dataFiles.get(i));  
        }
    }
    /**
     * The minimum method finds the minimum value in a hash map 
     * @param map
     * @return 
     */
     public static int minimum(HashMap<String,Integer> map) {
        Integer[] numbers = map.values().toArray(new Integer[0]);
        int minIdx = 0;
        for (int i = 1; i < numbers.length; i++) {
            if ((minIdx == -1) || (numbers[i] < numbers[minIdx]))
                minIdx = i;
      }
     return numbers[minIdx];
    }
  /**
   * The maximum method excepts a HashMap and finds the maximum
   * @param map
   * @return 
   */
   public static int maximum(HashMap<String,Integer> map) {
       Integer[] numbers = map.values().toArray(new Integer[0]);
       int maxIdx = 0;
       for (int i = 1; i < numbers.length; i++) {
           if ((maxIdx == -1) || (numbers[i] > numbers[maxIdx]))
                maxIdx = i;
       }
      return numbers[maxIdx];
    }
   /**
    * The average method accepts a HashMap and finds the average value of the 
    * values
    * @param map
    * @return 
    */
 public static double average(HashMap<String,Integer> map) {
    Integer[] vals = map.values().toArray(new Integer[0]);
    int sum =0;
    for(int i=0;i<vals.length;i++) {
         sum = sum+vals[i];
    }
      return (((double) sum) /vals.length );
  }
 /**
  * The fileData method is for parsing data from data files. The method returns 
  * a hash map the contains the data from the file passed to the method. 
  * @param fileName
  * @return
  * @throws FileNotFoundException 
  */
 public HashMap fileData(File fileName) throws FileNotFoundException{
    Scanner fileScan = new Scanner(fileName); 
        HashMap<String,Integer> map = new HashMap<>();
        fileScan.nextLine();
     while (fileScan.hasNext()) {
          String[] holdData = fileScan.nextLine().split(",");
          String num = holdData[2];
          int val = Integer.valueOf(holdData[3]);
          if (map.size() == 0)
   map.put(num,val);
   else {
   if (map.get(num) == null)
   map.put(num, val);
   else {
       val = map.get(num) +val;
    map.put(num, val);
	}
    }
     }
     return map;
 }
 /**
  * This version of the fileData method is for adding data to an existing HashMap
  * @param map
  * @param fileName
  * @return
  * @throws FileNotFoundException 
  */
 public HashMap fileData(HashMap<String,Integer> map, File fileName) throws FileNotFoundException{
     Scanner fileScan = new Scanner(fileName); 
          fileScan.nextLine();
     while (fileScan.hasNext()) {
          String[] holdData = fileScan.nextLine().split(",");
          String num = holdData[2];
          int val = Integer.valueOf(holdData[3]);
   if (map.get(num) == null)
   map.put(num, val);
   else {
    val = (int) map.get(num) +val;
    map.put(num, val);
    }
     }
     return map;
 }
 
 public String generateReport(){
     int count =0;
     DateFormat df = new SimpleDateFormat("dd/mm/yy hh:mm:ss");
     Date currentDate = new Date();
     String result = "Report generated, " + df.format(currentDate)+System.lineSeparator();
     result +="Generator name, KW min, KW max, KW Average";
     for(HashMap map: dataMaps){
         count++;
        result += System.lineSeparator();
        if(count != dataMaps.length)
            result += "Generator "+ String.valueOf(count)+", ";
        else
            result +="Total values, ";
        result += String.valueOf(minimum(map))+", ";
        result += String.valueOf(maximum(map))+", ";
        result += String.valueOf(average(map));
     }
     return result;
 }
}
 
