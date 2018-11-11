package com.dillards.json;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sterling.kafa.CreateFile;

public class GenerateJSONForDillardsNBDocInduct {
	public static final Integer NUMBER_OF_THREADS = 5;
	public static final Integer NUMBER_OF_FILES = 1;
	public static final Integer NUMBER_OF_PICS=6000;
	public static final Integer DELAY_IN_CREATING_FILES = 500;
	public static final String LOCAL_FOLDER_PATH = "C:/app/tmp/";
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public static void main(String[] args) {

		ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

		IntStream.range(0, NUMBER_OF_FILES).parallel().forEach(iCount -> {
			String strFileName = "DILMAUNBDOCINDUCT" + iCount + ".txt";
			String strJson = generateJSONPick(NUMBER_OF_PICS);
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

	private static String generateJSONPick(int iPicks) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();
		ArrayNode picksArray = mapper.createArrayNode();

		Random rand = new Random();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		String ts = df.format(new Date());

		rootNode.put("messageType", "pickTaskPicked");
		rootNode.put("timestamp", ts);
		rootNode.put("groupType", "orderPick");
		rootNode.put("groupID", Integer.toString(rand.nextInt(99999999) + 10000000));
		ObjectNode oContainerNode = mapper.createObjectNode();
		oContainerNode.put("containerType", "LG");
		oContainerNode.put("containerID", Integer.toString(rand.nextInt(99999999) + 10000000)
				+ Integer.toString(rand.nextInt(99999999) + 10000000) + Integer.toString(rand.nextInt(9999) + 1000));

		rootNode.putPOJO("Containers", oContainerNode);

		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String tsZ = df1.format(new Date());

		ObjectNode oInduct = mapper.createObjectNode();
		oInduct.put("startedAt", tsZ);
		oInduct.put("completedAt", tsZ);
		oInduct.put("userID", Integer.toString(rand.nextInt(999999999) + 100000000));
		oInduct.put("deviceID", "chuck");

		rootNode.putPOJO("induct", oInduct);
		for (int i = 0; i < iPicks; i++) {

			ObjectNode oPickObj = mapper.createObjectNode();

			String strPickID = randomAlphaNumeric(10);
			oPickObj.put("pickID", strPickID);
			oPickObj.put("eachQuantity", 5);
			oPickObj.put("sourceLocation", Integer.toString(rand.nextInt(9999999) + 1000000));

			ObjectNode oProduct = mapper.createObjectNode();
			oProduct.put("productID", Integer.toString(rand.nextInt(9999999) + 0000001));
			oProduct.put("unitOfMeasure", "EA");
			oProduct.put("unitOfMeasureQuantity", rand.nextInt(99) + 1);
			oPickObj.putPOJO("product", oProduct);

			ArrayNode scannedArray = mapper.createArrayNode();

			oPickObj.putPOJO("scanned", scannedArray);
			scannedArray.add(Integer.toString(rand.nextInt(999999999) + 100000000));
/*			
			ArrayNode reasonArray = mapper.createArrayNode();
			oPickObj.putPOJO("reason", reasonArray);
			
			reasonArray.add("DAMAGED_PRODUCT");
*/
			ObjectNode oData = mapper.createObjectNode();
			oData.put("WHSE", Integer.toString(rand.nextInt(9999) + 1000));
			oData.put("PICKING_SEQUENCE", Integer.toString(rand.nextInt(99999999) + 10000000)
					+ Integer.toString(rand.nextInt(99999999) + 10000000) + Integer.toString(rand.nextInt(99) + 10));
			oData.put("PRIORITY", Integer.toString(rand.nextInt(3) + 1));
			oData.put("CATEGORY", "S");
			oData.put("DETAIL_LINES", "5");
			oData.put("DETAIL_QTY", "27");
			String strUPC = randomAlphaNumeric(9);
			oData.put("UPC", strUPC);
			oData.put("DEPT", Integer.toString(rand.nextInt(999) + 100));
			oData.put("MIC", Integer.toString(rand.nextInt(999) + 100));
			oData.put("STYLE", "CSD");
			oData.put("COLOR", "RED");
			oData.put("SIZE", "M");

			oPickObj.putPOJO("data", oData);

			picksArray.add(oPickObj);
		}
		rootNode.putPOJO("picks", picksArray);
		return rootNode.toString();

	}

	private static String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

}
