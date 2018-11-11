package com.yantriks.job.cnbnap.northbound;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.io.FileUtils;
import com.yantriks.job.pubsub.PublishMessage;
import com.yantriks.job.utils.Constants;

public class CnbnapNorthboundPerformanceTest {
  
  public static void main(String[] args) {
    
    getFilesList();

  }
  
  @SuppressWarnings("unchecked")
  private static void getFilesList() {
    try {
      
      ExecutorService executor = Executors.newFixedThreadPool(Constants.NUMBER_OF_THREADS_FOR_PUBSUB_PERFORMANCE);
      
      File dir = new File(Constants.JSON_FOLDER_PATH_POSITIVE);
      String[] extensions = new String[] { "txt", "json" };
      System.out.println("Reading files from dir..." + dir.getCanonicalPath());
      
      List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
      for (int i = 0; i < Constants.NUMBER_OF_MESSAGES_FOR_PUBSUB_PERFORMANCE; i++) {
        
        System.out.println("Sending pubsub count -> " + i);
        
        for (File file : files) {
            System.out.println("File Name: " + file.getCanonicalPath());
            String message = FileUtils.readFileToString(file);
            System.out.println("Read in: " + message.trim());
            
            
            executor.submit(new Callable<String>() {
              public String call() {
                publishMessage(message);
                return null;
              }
            });
        }
        
        try {
          Thread.sleep(Constants.DELAY_IN_SENDING_MESSAGES);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      executor.shutdown();
      
      while (!executor.isTerminated()) {
      }
      System.out.println("Finished all threads. Total pubsub messages published are  " + Constants.NUMBER_OF_MESSAGES_FOR_PUBSUB_PERFORMANCE);
      
      
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private static void publishMessage(String message) {
    PublishMessage.publish(message.trim());
  }

}
