package com.cnbnap.jsonnb;

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

public class GenerateJSONForCNBNAPAccept {
	
	
	public static final Integer NUMBER_OF_THREADS = 5;
	public static final Integer NUMBER_OF_FILES = 1;
	public static final Integer DELAY_IN_CREATING_FILES = 500;
	public static final Integer NUMBER_OF_PICS=6000;
	public static final String LOCAL_FOLDER_PATH = "C:/app/tmp/";
	public static final String SFTP_FILE_NAME = "pickWave";
	public static final String NEW_LINE = "\n";
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public static void main(String[] args) {

		ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

		IntStream.range(0, NUMBER_OF_FILES).parallel().forEach(iCount -> {
			String strFileName = "CNBNAPACCEPT" + iCount + ".txt";
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

		rootNode.put("timestamp", ts);
		rootNode.put("messageType", "containerAccepted");
		rootNode.put("groupType", "orderPick");
		rootNode.put("groupID", randomAlphaNumeric(11));
		ObjectNode oContainerNode = mapper.createObjectNode();
		
		String strContainerId = randomAlphaNumeric(11);
		String strContainerType = randomAlphaNumeric(7);
		oContainerNode.put("containerID", strContainerId);
		oContainerNode.put("containerType", strContainerType);
		rootNode.putPOJO("container", oContainerNode);

		for (int i = 0; i < iPicks; i++) {

			ObjectNode oPickObj = mapper.createObjectNode();

			String strPickID = randomAlphaNumeric(11);
			oPickObj.put("pickID", strPickID);
			oPickObj.put("eachQuantity", 6);
			oPickObj.put("sourceLocation", randomAlphaNumeric(11));

			ObjectNode oProduct = mapper.createObjectNode();
			oProduct.put("productID", randomAlphaNumeric(11));
			oProduct.put("unitOfMeasure", "EA");
			oProduct.put("unitOfMeasureQuantity", rand.nextInt(99) + 1);
			oPickObj.putPOJO("product", oProduct);
			
			ObjectNode oData = mapper.createObjectNode();
			oData.put("Key1", "Test1Value");
			oData.put("Key2", "Test2");
			oData.put("Key12", "Test1Value");
			oData.put("Key3", "Test3");

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
