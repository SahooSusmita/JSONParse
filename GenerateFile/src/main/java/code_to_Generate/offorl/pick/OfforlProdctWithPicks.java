package com.offorl.pick;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Random;


public class OfforlProdctWithPicks {

	public static final Integer NUMBER_OF_PH_IN_TXT = 5000; // Number of OH required 500,1000,1500,2000,3000,4000,5000

	public static final String LOCAL_TXT_FOLDER_PATH = "C:\\app\\tmp\\";
	public static final String FILE_NAME = "OfforlProduct5000PH.txt";
	public static final String NEW_LINE = "\n";

	public static void main(String[] args) {

		try {
			Random rand = new Random();
			String path = LOCAL_TXT_FOLDER_PATH + FILE_NAME;

			File file = new File(path);
			boolean isCreated = false;
			if (file.exists()) {
				isCreated = true;
			} else {
				isCreated = file.createNewFile();
			}

			if (isCreated) {
				StringBuilder sb = new StringBuilder();
				
				for (int i = 0; i < NUMBER_OF_PH_IN_TXT; i++) {
					int SKU_NUMBER = rand.nextInt(9999999) + 1000000;
					createTextData(String.valueOf(SKU_NUMBER), sb);
				}
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

	private static String createTextData(String strSKUNumber, StringBuilder sb) {
		Random ran = new Random();
		// int numberOfPD = ran.nextInt(100) + 5;
		int numberOfPD = 10;
		sb.append("PH" + strSKUNumber+ "000001.000001.000001.000001.000BULLETIN,BOARD,SET,STEM       http://static.www.odcdn.com/pictures/us/od/sk/lg/221893_sk_lg.jpg                                   EACH                                                                   \r");
		sb.append(NEW_LINE);

		String strRandom = Integer.toString(ran.nextInt(999999999) + 10000000) + Integer.toString(ran.nextInt(999999)+100000);
		// If PD has to be limited then change the numberOfPD
		for (int i = 0; i <= numberOfPD; i++) {			
			System.out.println("Processing --> + " + i + " - PD - " + numberOfPD);
			sb.append("PD" + strSKUNumber + strRandom +"                                                                                                                                                                                                                         \r");
			sb.append(NEW_LINE);
		}

		return sb.toString();
	}

}
