package code_to_Generate.offorl.pick;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

public class OfforlPickwaveWith10kPicks {
	  
	  public static final Integer NUMBER_OF_OH_IN_TXT = 4500; // Number of OH required 500,1000,1500,2000,3000,4000,5000
	  public static final Integer BATCH_NUMBER = 55555;
	  public static final String LOCAL_TXT_FOLDER_PATH = "C:\\app\\tmp\\";
	  public static final String FILE_NAME = "OfforlPickwave4500OH.txt";
	  public static final String NEW_LINE = "\n";

	  public static void main(String[] args) {
	    
	    try {
	      String path = LOCAL_TXT_FOLDER_PATH+FILE_NAME;
	      Random ran = new Random();
	      
	      File file = new File(path);
	      boolean isCreated = false;
	      if(file.exists()) {
	        isCreated = true;
	      } else {
	        isCreated = file.createNewFile();
	      }
	      
	      if(isCreated) {
	        StringBuilder sb = new StringBuilder();
	        sb.append("BH"+BATCH_NUMBER+"START                                                                                                                                                                         \r");
	        sb.append(NEW_LINE);
	        for(int i=0;i<NUMBER_OF_OH_IN_TXT;i++) {
	          createTextData(String.valueOf(BATCH_NUMBER), sb);
	        }
	        sb.append("BT"+BATCH_NUMBER+"END                                                                                                                                                                           ");
	        generateTxtFile(path, sb.toString());
	      }
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }
	  
	  public static void generateTxtFile(String filePath, String data) {

	    try {
	      Files.write(Paths.get(filePath), data.getBytes(), StandardOpenOption.APPEND);
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }
	  
	  
	private static String createTextData(String batchNumber, StringBuilder sb) {
	  Random ran = new Random();
	  //int numberOfPD = ran.nextInt(100) + 5;
	  int numberOfOD = 10;
	  
	  int length = 7;
	  boolean useLetters = false;
	  boolean useNumbers = true;
	    
	    
	    sb.append("OH"+batchNumber+"19128163200129344901REGCCA051302005000 19128163200188293449010513000000000                              99FALSE0010011FALSE11111       1ORDER   1        015.000012.900009.130\r");
	    sb.append(NEW_LINE);
	    
	    // If PD has to be limited then change the numberOfPD
	    for(int i=0;i<=numberOfOD;i++) {
	      String productId = RandomStringUtils.random(length, useLetters, useNumbers);
	//    System.out.println("Processing --> + " + i + " - OD - " + numberOfOD);
	      sb.append("OD"+batchNumber+"1912816320012934490112UU022156   00020001"+productId+"                                                                                                                              \r");
	      sb.append(NEW_LINE);
	    }
	    System.out.println("completed");
	    return sb.toString();
	  }

	}
