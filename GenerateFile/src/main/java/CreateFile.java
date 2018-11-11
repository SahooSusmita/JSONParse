package com.sterling.kafa;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

public class CreateFile implements Callable<String> {

	private String strFileName;
	private String strFileData;
	private String strFolderPath;

	public CreateFile(String strFileName, String strFileData, String strFolderPath) {
		this.strFileName = strFileName;
		this.strFileData = strFileData;
		this.strFolderPath = strFolderPath;
	}

	public String call() {

		generateTextInLocal(strFileName, strFileData, strFolderPath);
		return "Done";
	}

	private String generateTextInLocal(String strFileName2, String strFileData2, String strFolderPath2) {

		BufferedWriter writer = null;
		try {
			writer = Files.newBufferedWriter(Paths.get(strFolderPath2 + strFileName2));
			writer.write(strFileData2);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return strFileName2;

	}

}