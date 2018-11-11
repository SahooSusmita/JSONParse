package com.yantriks.job.utils;

public class Constants {
  
  public static final Integer NUMBER_OF_THREADS_FOR_PUBSUB_PERFORMANCE = 10;
  public static final Integer NUMBER_OF_MESSAGES_FOR_PUBSUB_PERFORMANCE = 100;
  public static final String JSON_FOLDER_PATH_POSITIVE = "D:/6RS/TEST/cnbnap_northbound_positive";
  public static final String JSON_FOLDER_PATH_NEGATIVE = "D:/6RS/TEST/cnbnap_northbound_negative";
  public static final Integer DELAY_IN_SENDING_MESSAGES = 1000;
  
  // Google pubSub Settings
  public static final String GOOGLE_PUBSUB_PORJECT_ID = "yantriks-202806";
  public static final String GOOGLE_PUBSUB_TOPIC = "test";
  public static final String GOOGLE_PUBSUB_SUBSCRIPTION = "sub";
  
  // CSV to SFTP
  public static final Integer NUMBER_OF_THREADS_FOR_CSV = 10;
  public static final Integer NUMBER_OF_ROWS_IN_CSV = 1000;
  public static final Integer NUMBER_OF_CSV_FILES = 1;
  public static final Integer DELAY_IN_CREATING_FILES = 1000;
  public static final String LOCAL_CSV_FOLDER_PATH = "D:/6rs_files/";
  public static final String SFTP_FILE_NAME = "pickWave";
  
  // SFTP Settings
  public static final String SFTP_HOST = "sftp-srsyan.6river.org";
  public static final String SFTP_USERNAME = "srsyan";
  public static final String SFTP_PASSWORD = "bbdaI9XKSrNGrR2qoeAt";
  public static final String SFTP_FOLDER_LOCATION = "/southbound/incoming/";
  
  

}
