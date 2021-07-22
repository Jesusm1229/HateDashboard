package hatedashboard;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import org.apache.spark.sql.Row;

public class CSV_Writer {
	
	private List<Row> dataToWrite;
	private FileWriter csvW;
	
	public CSV_Writer(List<Row> dataToWrite) {
		this.dataToWrite = dataToWrite;
		try {
			csvW = new FileWriter("result.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addHeader() {
		try {
			csvW.append("Id");
			csvW.append(",");
			csvW.append("Text");
			csvW.append(",");
			csvW.append("Location");
			csvW.append(",");
			csvW.append("Username");
			csvW.append("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void createFile() {
		
		this.addHeader();
		
		try {
			
			for (int i=0; i<dataToWrite.size(); i++) {
				csvW.append(dataToWrite.get(i).toString().replaceAll("\\[", "").replaceAll("\\]",""));
				csvW.append("\n");
			}
			csvW.flush();
			csvW.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
