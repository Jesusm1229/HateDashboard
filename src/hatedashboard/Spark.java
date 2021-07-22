/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatedashboard;

import java.util.*;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class Spark {
    
        public static ArrayList<String> listOfKeysAux;
        public static ArrayList<Long> listOfValuesAux;
        
        public static List<Row> resultList;
        public static List<Row> resultLocationList;
        
        public static List<String> duplicateList;
        
        public static ArrayList<String> tweetsCount;
        
        public static Map<String, Long> resultMapAux = new HashMap<>();
                
        public Spark(){
            executeSpark();
        }
    
        public static void countDuplicate(List<Row> list){
            tweetsCount = new ArrayList<String> ();
            
            float totalP = totalPercentage(list) ;
            
            Set<Row> uniqueSet = new HashSet<Row>(list);
            for (Row temp : uniqueSet) {
                //System.out.println(temp + ": " + Collections.frequency(list, temp));
                tweetsCount.add(temp + "++" + Collections.frequency(list, temp) + ","+ (Collections.frequency(list, temp)/totalP*100));
            }
        
        }
        
        public static float totalPercentage(List<Row> list){
            
        Set<Row> uniqueSet = new HashSet<Row>(list);
        float totalPer = 0;
        
            for (Row temp : uniqueSet) {
                //System.out.println(temp + ": " + Collections.frequency(list, temp));
                 totalPer = totalPer + Collections.frequency(list, temp);
            }
            return totalPer;
        }
        
        
        public static <String> Map<String, Long> countByClassicalLoop(List<String> inputList) {
                Map<String, Long> resultMap = new HashMap<>();
                for (String element : inputList) {
                    if (resultMap.containsKey(element)) {
                        resultMap.put(element, resultMap.get(element) + 1L);
                    } else {
                        resultMap.put(element, 1L);
                    }
                }           
         
            getkeySet(resultMap);
            
            getValues(resultMap);
                        
            //resultMapAux = resultMap; 
            
            return resultMap;
        }
        
        public static <String> ArrayList<String> getkeySet(Map<String, Long> inputMap){
        
            Set<String> keySet = inputMap.keySet();
            // Creating an ArrayList of keys
            // by passing the keySet
            ArrayList<String> listOfKeys = new ArrayList<String>(keySet);

            // Getting Collection of values from HashMap
            Collection<Long> values = inputMap.values();

            // Creating an ArrayList of values
            ArrayList<Long> listOfValues  = new ArrayList<>(values);

            //listOfKeysAux = listOfKeys;
            
            System.out.println("Los Keys del Map son: " + listOfKeys);
                        
            return listOfKeys;
        }
        
        public static <String> ArrayList<Long> getValues(Map<String, Long> inputMap){
        
            Set<String> keySet = inputMap.keySet();
            // Creating an ArrayList of keys
            // by passing the keySet
            ArrayList<String> listOfKeys
                = new ArrayList<String>(keySet);

            // Getting Collection of values from HashMap
            Collection<Long> values = inputMap.values();

            // Creating an ArrayList of values
            ArrayList<Long> listOfValues
                = new ArrayList<>(values);

            listOfValuesAux = listOfValues;
            
            System.out.println("Los valores del Map son: " + listOfValues);
            
            return listOfValues;
        }
        
        public static ArrayList<String> getTweetsCount(){
            return tweetsCount;        
        }
        
        public static ArrayList<String> getlistOfKeysAux(){        
            return listOfKeysAux;
        }
        
        public static ArrayList<Long> getlistOfValuessAux(){        
            return listOfValuesAux;
        }       
        
        
        
        

	public static boolean findOffensiveWord(String searchSentence, List<Row> offensiveWords) {
		for(int i=0; i< offensiveWords.size(); i++) {
			Pattern patternWord = Pattern.compile(offensiveWords.get(i).toString().replaceAll("[^a-zA-Z ]", ""), Pattern.CASE_INSENSITIVE);
		    Matcher sentenceForSearch = patternWord.matcher(searchSentence);
		    boolean founded = sentenceForSearch.find();
		    if(founded)
		    	return true;
		}
		return false;
	}
        
  

	public static void executeSpark() {
		//Declaracion de Variables
		SparkSession sc = SparkSession.builder().appName("OffensiveTweetFinder").master("local").getOrCreate();
		Dataset<Row> user_TweetsTable = sc.sqlContext().read().format("com.databricks.spark.csv").option("delimiter", ",").load("C:/Prueba/user-tweets.csv").toDF("id", "text", "location", "username");
		Dataset<Row> offensiveWordsTable = sc.sqlContext().read().format("com.databricks.spark.csv").option("delimiter", ",").load("C:/Prueba/BDD.csv").toDF("id", "word");
		user_TweetsTable.createOrReplaceTempView("userTweets");
		offensiveWordsTable.createOrReplaceTempView("offensiveWords");
		List<String> indexesOff = new ArrayList<String>();
		CSV_Writer csvWriter;
		
		//Consultas en SQL
		Dataset<Row> tweetsText = sc.sqlContext().sql("SELECT text FROM userTweets");
		Dataset<Row> offensiveText = sc.sqlContext().sql("SELECT word FROM offensiveWords");
		
		//Creacion de lista con los datos
		List<Row> tweetsList = tweetsText.collectAsList();
		List<Row> oWordsList = offensiveText.collectAsList();
		
		//Iterar entre tweets e identificar los ofensivos
		for(int i=0; i<tweetsList.size(); i++) {
			String singleTweet = tweetsList.get(i).toString();
			String sentence = singleTweet.replaceAll("[^a-zA-Z ]", "").toLowerCase();
			if(findOffensiveWord(sentence, oWordsList)) {
				indexesOff.add(""+i);
			}
		}
		
		//Creamos un nuevo Dataset (tabla) con los datos de los indices de los tweets identificados como ofensivos
		Dataset<Row> offensiveFounded = sc.sqlContext().createDataset(indexesOff, Encoders.STRING()).toDF("id");
		offensiveFounded.createOrReplaceTempView("offensiveFounded");
		
		//Se realiza el JOIN entre los Datasets (tablas) userTweets y offensiveFounded, y el resultado se almacena en un nuevo Dataset
		
                //Dataset Location
                Dataset<Row> resultLocation = sc.sqlContext().sql("SELECT location FROM userTweets INNER JOIN offensiveFounded ON userTweets.id = offensiveFounded.id");
		
                //Dataset completo
                Dataset<Row> result = sc.sqlContext().sql("SELECT userTweets.id, text, location, username FROM userTweets INNER JOIN offensiveFounded ON userTweets.id = offensiveFounded.id");
		
                
		//Se muestra el contenido del Dataset resultante
		result.show(2505, false);
		
		//Almacenamos la tabla del resultado como un ArrayList
                
                //Resultado completo
		resultList = result.collectAsList();
		
                //Resultado Location
                resultLocationList = resultLocation.collectAsList(); 
                
                countDuplicate(resultLocationList);
                
                
                
//                //System.out.println("FIJATE ACÁAA"+getTweetsCount());
//                 for (String str : getTweetsCount())
//	      { 		      
//	           System.out.println(str); 		
//	      }
                
                //Se cuentan las iteraciones de cada ubicación
                //System.out.println(countByClassicalLoop(resultList));
                
                //resultMapAux = countByClassicalLoop(resultList);
                
                //System.out.println("ACAAAAAAAA"+getlistOfValuessAux());
                // System.out.println("Sin función"+listOfKeysAux);
                //locationList(countByClassicalLoop(resultList));
                
		//Realizamos los procedimientos necesarios para almacenar los datos en un nuevo archivo csv
		csvWriter = new CSV_Writer(resultList);
		csvWriter.createFile();
	}

}