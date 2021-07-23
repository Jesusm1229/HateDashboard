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

        private static List<String> resultIdList;
        private static List<String> resultTweetList;        
        private static List<String> resultUserList; 
        private static List<String> resultLocationStringList; 
        
                
        public static List<String> tweetsCount;
                        
        public Spark(){
            executeSpark();
        }
    
        //Función para contar duplicados en lista
        public static void countDuplicate(List<Row> list){
            tweetsCount = new ArrayList<String> ();
            
            float totalP = totalPercentage(list) ;
            
            Set<Row> uniqueSet = new HashSet<Row>(list);
            for (Row temp : uniqueSet) {                
                tweetsCount.add(temp + "∥" + Collections.frequency(list, temp) + "∥"+ (Collections.frequency(list, temp)/totalP*100));
            }
        
        }
        
        //Función para especificar el porcentaje con respecto a las "veces de aparición"
        public static float totalPercentage(List<Row> list){
            
            Set<Row> uniqueSet = new HashSet<Row>(list);
            float totalPer = 0;

                for (Row temp : uniqueSet) {               
                     totalPer = totalPer + Collections.frequency(list, temp);
                }
                return totalPer;
        }
        
        
        
        
        public  List<String> getTweetsCount(){
                return tweetsCount;        
        }
        
         public  List<String> getresultIdList(){
                return resultIdList;     
        }
        
         public  List<String> getresultTweetList(){
                return resultTweetList;     
        }
         
          public List<String> getresultUserList(){
                return resultUserList;        
        }
          
          public  List<String> getresultLocationStringList(){
                return resultLocationStringList;      
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
		
                
                //Dataset completo
                Dataset<Row> result = sc.sqlContext().sql("SELECT userTweets.id, text, location, username FROM userTweets INNER JOIN offensiveFounded ON userTweets.id = offensiveFounded.id");
		List<Row> resultList = result.collectAsList();
                
                
                //Dataset Id
                Dataset<Row> resultId = sc.sqlContext().sql("SELECT userTweets.id FROM userTweets INNER JOIN offensiveFounded ON userTweets.id = offensiveFounded.id");
                //List<Row> resultIdTextList = resultIdText.collectAsList();
                resultIdList = resultId.as(Encoders.STRING()).collectAsList();
                
                //Dataset Text
                Dataset<Row> resultText = sc.sqlContext().sql("SELECT text FROM userTweets INNER JOIN offensiveFounded ON userTweets.id = offensiveFounded.id");
                //List<Row> resultIdTextList = resultIdText.collectAsList();
                resultTweetList = resultText.as(Encoders.STRING()).collectAsList();
                

                //Dataset Location
                Dataset<Row> resultLocation = sc.sqlContext().sql("SELECT location FROM userTweets INNER JOIN offensiveFounded ON userTweets.id = offensiveFounded.id");
		List<Row> resultLocationList = resultLocation.collectAsList(); 
                resultLocationStringList = resultLocation.as(Encoders.STRING()).collectAsList();
                
                 //Dataset Username
                Dataset<Row> resultUser = sc.sqlContext().sql("SELECT username FROM userTweets INNER JOIN offensiveFounded ON userTweets.id = offensiveFounded.id");
                //List<Row> resultUserList = resultUser.collectAsList();                
                resultUserList = resultUser.as(Encoders.STRING()).collectAsList();
                

                //Se cuenta duplicados en lista de localización               
                countDuplicate(resultLocationList);
                                
         
		//Realizamos los procedimientos necesarios para almacenar los datos en un nuevo archivo csv. 
                //Este archivo se considera respaldo físico.
		csvWriter = new CSV_Writer(resultList);
		csvWriter.createFile();
	}

}