/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatedashboard;


import java.io.*;
import java.util.Arrays;
import com.opencsv.CSVReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jesus
 */
public class CSVDemo {

    /**
     * @param args the command line arguments
     */
    public void readCSVFile(){
       
        
        String file = "docs\\user-tweets.csv";
        BufferedReader reader = null;
        String line = "";       
        
        
        
        try{
               reader = new BufferedReader(new FileReader(file));
               int lineCont = 0;
               
               while((line = reader.readLine()) != null){
                   String[] row = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                   
                   
                   for(String index: row){
                       System.out.printf("%-400s", index);
                       lineCont +=1; 
                   }
                   System.out.println();
               }
                System.out.println("lineas: "+((lineCont/4)-1));            
               
            }catch(Exception e){
                System.out.println(e);
            }
        
        finally{
            
            try {
            reader.close();
            
            }catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }   
    
    
//    
//    public static void main(String[] args) {
//        CSVDemo csvdemo = new CSVDemo();
//        csvdemo.readCSVFile();       
//    
//    }
}
