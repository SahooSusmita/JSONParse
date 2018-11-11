package com.yantriks.job.pubsub;

import java.io.IOException;
import java.util.List;
import com.google.api.services.pubsub.Pubsub;
import com.google.api.services.pubsub.model.PublishRequest;
import com.google.api.services.pubsub.model.PublishResponse;
import com.google.api.services.pubsub.model.PubsubMessage;
import com.google.common.collect.ImmutableList;
import com.yantriks.job.utils.Constants;

public class PublishMessage {

  public static void main(String[] argss) {

    publish("TestMessage");

  }


  public static void publish(String message) {
    String args[] = new String[4];
    System.setProperty("LOOP", "loop");

    try {
      Pubsub client = PubsubUtils.getClient();

      // MYPROJ publish_message test hello
      // MYPROJ pull_messages sub

      args[0] = Constants.GOOGLE_PUBSUB_PORJECT_ID;
      args[1] = "publish_message";
      args[2] = Constants.GOOGLE_PUBSUB_TOPIC;
      args[3] = message;

      publishMessage(client, args);
    } catch (IOException e) {
      System.out.println("EXCEPTION... " + e.getMessage());
      e.printStackTrace();
    }
  }
  
  
  private static void publishMessage(final Pubsub client, final String[] args)
      throws IOException {
    String topic = PubsubUtils.getFullyQualifiedResourceName(
        PubsubUtils.ResourceType.TOPIC, args[0], args[2]);
    String message = args[3];
    PubsubMessage pubsubMessage = new PubsubMessage()
        .encodeData(message.getBytes("UTF-8"));
    List<PubsubMessage> messages = ImmutableList.of(pubsubMessage);
    PublishRequest publishRequest = new PublishRequest();
    publishRequest.setMessages(messages);
    PublishResponse publishResponse = client.projects().topics()
        .publish(topic, publishRequest)
        .execute();
    List<String> messageIds = publishResponse.getMessageIds();
    if (messageIds != null) {
      for (String messageId : messageIds) {
        System.out.println("Published with a message id: " + messageId);
      }
    }
  }

}
