package com.yantriks.job.csvtosftp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.yantriks.job.utils.Constants;

public class SouthBoundCreateFilesInSFTP {
  
  public static void main(String[] args) {
    ExecutorService executor = Executors.newFixedThreadPool(Constants.NUMBER_OF_THREADS_FOR_CSV);
    
    for (int i = 0; i < Constants.NUMBER_OF_CSV_FILES; i++) {
      
      String fileName = Constants.SFTP_FILE_NAME+i+".csv";
      List<CSVColumns> createCSV = createCSV();
      executor.submit(new CsvWorker(createCSV, Constants.LOCAL_CSV_FOLDER_PATH, fileName));
      System.out.println("Finished --> " + fileName);
      
      try {
        Thread.sleep(Constants.DELAY_IN_CREATING_FILES);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    
    executor.shutdown();
    
    while (!executor.isTerminated()) {
    }
    System.out.println("Finished all threads. Total CSV files created is " + Constants.NUMBER_OF_CSV_FILES);
  }

  private static List<CSVColumns> createCSV() {
    List<CSVColumns> csvList = new ArrayList<>();

    for(int j=0; j < Constants.NUMBER_OF_ROWS_IN_CSV; j++) {
      CSVColumns csvColumns = CSVColumns.builder().messageType("pickWave").groupType("batchPick").groupID(""+j)
          .pickID("FG6PWQFYTX").containerID("").containerType("SMTOTE|MEDTOTE|LGTOTE").sourceLocation("XTWPNN2YQM").destinationLocation("LLWPNN2YQM")
          .unitOfMeasureQuantity("12").unitOfMeasure("DZ").eachQuantity("10").productID("Y44XSSZC94").lotID("")
          .identifiers("000006612|000006613").name("INDUSTRIAL GLOVES YELLOW 1 DZ").description("INDUSTRIAL WORK GLOVES YELLOW 1 DOZEN").image("https://example.com/Y44XSSZC94.png")
          .length("13"+j).width("4"+j).height("5"+j).weight("7"+j)
          .dimensionUnitOfMeasure("2").weightUnitOfMeasure("1"+j).expectedShippingDate(String.valueOf(new Date()))
          .data("ThisKey=This Value|ThatKey=That Value")
          .build();
      
      csvList.add(csvColumns);
    }
    return csvList;
  }

}
