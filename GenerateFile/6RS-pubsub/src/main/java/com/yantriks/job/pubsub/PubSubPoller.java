package com.yantriks.job.pubsub;

import java.io.IOException;
import org.apache.commons.cli.Options;
import com.google.api.services.pubsub.Pubsub;

public class PubSubPoller {
  
  private static Options options;
  
  static {
    options = new Options();
    options.addOption("l", "loop", false,
            "Loop forever for pulling when specified");

}

  public static void main(String[] argss) {
    
    String args[] = new String[3];
    System.setProperty("LOOP", "loop");
    int batchSize = 1000;
    
    try {
      Pubsub client = PubsubUtils.getClient();
      
      args[0] = "yantriks-202806";
      args[1] = "pull_messages";
      args[2] = "sub";
      
      SubscriptionMethods.pullMessages(client, args, batchSize);
    } catch (IOException e) {
      System.out.println("EXCEPTION... " + e.getMessage());
      e.printStackTrace();
    }

  }

}
