package com.dillards.json;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sterling.kafa.CreateFile;

public class GenerateJSONForDillardsSB {
	public static final Integer NUMBER_OF_THREADS = 5;
	public static final Integer NUMBER_OF_FILES = 1;
	public static final Integer DELAY_IN_CREATING_FILES = 500;
	public static final Integer NUMBER_OF_PICS=6000;
	public static final String LOCAL_FOLDER_PATH = "C:/app/tmp/";

	public static void main(String[] args) {

		ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
		IntStream.range(0, NUMBER_OF_FILES).parallel().forEach(iCount -> {
		String strFileName = "DILMAUSB" + iCount + ".txt";
		String strJson = generateJSONSB(NUMBER_OF_PICS);
		executor.submit(new CreateFile(strFileName, strJson, LOCAL_FOLDER_PATH));
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		});
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		System.out.println("FIle Completed");
	}

	private static String generateJSONSB(int iCount) {

		Random rand = new Random();
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();
		String strSourceLocation = null;
		String strProductId = null;
		String strSize = null;
		String strDept = null;
		String strMic = null;
		String strEachQty = null;
		String strStart = "\u0002";
		String strEnd = "\u0003";
		StringBuilder strPickwave = null;
		String strMessage = null;
		String strFinalIdentifiers = null;
		String strNew = null;

		String strClient = "Dillards";
		String strsixrs = "6RS";
		String strDoc = "DOCUMENT";
		String strMS = "MS" + Integer.toString(rand.nextInt(9999) + 1000);
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSSSSSXXX");
		String strDate = df1.format(new Date());

		String strWareHouse = "WHSE000" + Integer.toString(rand.nextInt(1) + 9);
		String strDOCValue = "Doc00120001";
		String strS = "S";
		String strPickingSequence = Integer.toString(rand.nextInt(99999999) + 10000000)
				+ Integer.toString(rand.nextInt(99999999) + 10000000);
		String strPriority = Integer.toString(rand.nextInt(9) + 1);
		String strCategory = "R";
		String strContainerType = "T1";
		String strDetailLines = Integer.toString(rand.nextInt(99) + 1);
		String strDetailQty = Integer.toString(rand.nextInt(99) + 01);

		StringBuilder stSbr = new StringBuilder();

		List<String> lList = Arrays.asList(strClient, strsixrs, strDoc, strMS, strDate, strWareHouse, strDOCValue, strS,
				strPickingSequence, strPriority, strCategory, strContainerType, strDetailLines, strDetailQty);

		for (String string : lList) {
			stSbr.append(string);
			stSbr.append('^');
		}

		strPickwave = new StringBuilder();

		System.out.println(iCount);
		for (int i = 0; i < iCount; i++) {

			if (i > 0) {
				strPickwave.append("|");
			}
			strSourceLocation = "00A00" + Integer.toString(rand.nextInt(9) + 1) + ":";
			strProductId = Integer.toString(rand.nextInt(9999999) + 1000000) + ":";
			String strRandom = "001000211100000" + ":";

			
			String strIdentifier1 = Integer.toString(rand.nextInt(9999999) + 1000000) + ",";
			String strIdentifier2 = Integer.toString(rand.nextInt(99999999) + 10000000)
					+ Integer.toString(rand.nextInt(9999999) + 1000000);
			
			

			strFinalIdentifiers = strIdentifier1 + strIdentifier2 + ":";
			strEachQty = Integer.toString(rand.nextInt(9) + 1) + ":";
			strDept = "0" + Integer.toString(rand.nextInt(999) + 100) + ":";
			strMic = "0" + Integer.toString(rand.nextInt(99) + 10) + ":";
			strSize = Integer.toString(rand.nextInt(9) + 1) + ":";
			String strStyle = "abc" + ":";
			String strColour = "green" + ":";
			String strProductName = "INDUSTRIAL GLOVES BLUE 1 DZ" ;

			strNew = strSourceLocation + strProductId + strRandom + strFinalIdentifiers + strEachQty + strDept + strMic
					+ strSize + strSize + strStyle + strStyle + strColour + strProductName;

			strPickwave.append(strNew);
		}

		strMessage = strStart + stSbr.toString() + strPickwave + strEnd;

		rootNode.put("message", strMessage);
		rootNode.put("other?", "other?Value");

		return rootNode.toString();
	}

}
