package com.yantriks.job.cnbnap.northbound;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.io.FileUtils;
import com.yantriks.job.pubsub.PublishMessage;
import com.yantriks.job.utils.Constants;

public class CnbnapNorthboundTest {
  
  public static void main(String[] args) {
    
    getFilesList();

  }
  
  @SuppressWarnings("unchecked")
  private static void getFilesList() {
    try {
      
      File dir = new File(Constants.JSON_FOLDER_PATH_POSITIVE);
      String[] extensions = new String[] { "txt", "json" };
      System.out.println("Reading files from dir..." + dir.getCanonicalPath());
      
      List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
      for (File file : files) {
          System.out.println("File Name: " + file.getCanonicalPath());
          String message = FileUtils.readFileToString(file);
          System.out.println("Read in: " + message.trim());
          publishMessage(message);
      }
      
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private static void publishMessage(String message) {
    PublishMessage.publish(message.trim());
  }

}
