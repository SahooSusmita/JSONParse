package com.yantriks.job.csvtosftp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class CsvWorker implements Callable<String> {
  
 private static String SFTP_HOST = "35.225.125.123";
  private static String SFTP_USERNAME = "sftpuser";
  private static String SFTP_PASSWORD = "pas5w0rd";
  private static String SFTP_FOLDER_LOCATION = "/tmp/KK/southbound/incoming/";
  
  
  /*public static final String SFTP_HOST = "sftp-srsyan.6river.org";
  public static final String SFTP_USERNAME = "srsyan";
  public static final String SFTP_PASSWORD = "bbdaI9XKSrNGrR2qoeAt";
  public static final String SFTP_FOLDER_LOCATION = "/southbound/incoming/";*/
  
  private String fileName;
  
  private String folderPath;
  
  private List<CSVColumns> csvColumnsList;
  
  public CsvWorker(String s){
      this.fileName = s;
  }
  
  public CsvWorker(List<CSVColumns> col, String folderPath, String fileName){
    this.csvColumnsList = col;
    this.folderPath = folderPath;
    this.fileName = fileName;
}

  @Override
  public String call() throws Exception {
    generateCSV(csvColumnsList, fileName, folderPath);
    return "SUCCESS";
  }
  
  public static String generateCSV(List<CSVColumns> csvList, String fileName, String folderPath) {
    CSVPrinter csvPrinter = null;
    try {
        
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(folderPath + fileName));

            csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                      .withHeader("messageType", "groupType", "groupID", "pickID", "containerID", "containerType", "sourceLocation", "destinationLocation", 
                          "unitOfMeasureQuantity", "unitOfMeasure", "eachQuantity", "productID", "lotID", "identifiers", "name", "description", "image", "length", "width", 
                          "height", "weight", "dimensionUnitOfMeasure", "weightUnitOfMeasure", "expectedShippingDate", "data"));
            
            for(CSVColumns csv : csvList) {
              csvPrinter.printRecord(csv.getMessageType(), csv.getGroupType(), csv.getGroupID(), csv.getPickID(), csv.getContainerID(), 
                  csv.getContainerType(), csv.getSourceLocation(), csv.getDestinationLocation(), csv.getUnitOfMeasureQuantity(), 
                  csv.getUnitOfMeasure(), csv.getEachQuantity(), csv.getProductID(), csv.getLotID(), csv.getIdentifiers(), csv.getName(),
                  csv.getDescription(), csv.getImage(), csv.getLength(), csv.getWidth(), csv.getHeight(), csv.getWeight(), csv.getDimensionUnitOfMeasure(),
                  csv.getWeightUnitOfMeasure(), csv.getExpectedShippingDate(), csv.getData());
            }
            
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
      try {
        csvPrinter.flush();
        csvPrinter.close();
        sftpUpload(folderPath, fileName);
      } catch (IOException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    }
    return fileName;
}
  
  public static void sftpUpload(String folderPath, String fileName) throws Exception {
    
    JSch jsch = new JSch();
          Session session = null;
          try {
              session = jsch.getSession(SFTP_USERNAME, SFTP_HOST, 22);
              session.setConfig("StrictHostKeyChecking", "no");
              session.setPassword(SFTP_PASSWORD);
              session.connect();
              
              Channel channel = session.openChannel("sftp");
              channel.connect();
              ChannelSftp sftpChannel = (ChannelSftp) channel;
              sftpChannel.put(folderPath+fileName, SFTP_FOLDER_LOCATION);  
              sftpChannel.exit();
              session.disconnect();
          } catch (JSchException e) {
              e.printStackTrace();  
          } catch (SftpException e) {
              e.printStackTrace();
          }
   
   }

}
