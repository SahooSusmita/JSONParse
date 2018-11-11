package com.yantriks.job.pubsub;

import com.google.api.services.pubsub.Pubsub;
import com.google.api.services.pubsub.model.AcknowledgeRequest;
import com.google.api.services.pubsub.model.ListSubscriptionsResponse;
import com.google.api.services.pubsub.model.PubsubMessage;
import com.google.api.services.pubsub.model.PullRequest;
import com.google.api.services.pubsub.model.PullResponse;
import com.google.api.services.pubsub.model.PushConfig;
import com.google.api.services.pubsub.model.ReceivedMessage;
import com.google.api.services.pubsub.model.Subscription;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 * Class SubscriptionMethods contains static methods for subscriptions.
 */
public final class SubscriptionMethods {

    /**
     * Prevents instantiation.
     */
    private SubscriptionMethods() {
    }

    
    /**
     * Keeps pulling messages from the given subscription.
     *
     * @param client Cloud Pub/Sub client.
     * @param args Arguments as an array of String.
     * @throws IOException when Cloud Pub/Sub API calls fail.
     */
  public static List<ActualMessage> pullMessages(final Pubsub client, final String[] args, int batchSize) throws IOException {

    List<ActualMessage> messageList = new ArrayList<>();
    String subscriptionName = PubsubUtils.getFullyQualifiedResourceName(
        PubsubUtils.ResourceType.SUBSCRIPTION, args[0], args[2]);
    PullRequest pullRequest = new PullRequest()
        .setReturnImmediately(false)
        .setMaxMessages(batchSize);

    PullResponse pullResponse;
    pullResponse = client.projects().subscriptions().pull(subscriptionName, pullRequest).execute();
    List<String> ackIds = new ArrayList<>(batchSize);
    List<ReceivedMessage> receivedMessages = pullResponse.getReceivedMessages();
    if (receivedMessages != null) {
      for (ReceivedMessage receivedMessage : receivedMessages) {
        PubsubMessage pubsubMessage = receivedMessage.getMessage();
        if (pubsubMessage != null && pubsubMessage.decodeData() != null) {
          
          String data = new String(pubsubMessage.decodeData(),"UTF-8");
          if(isJSONValid(data)) {
            ActualMessage msg = new ActualMessage();
            msg.setMessageId(pubsubMessage.getMessageId());
            msg.setMessage(data);
            System.out.println(msg);
            messageList.add(msg);
          } else {
            System.out.println("INVALID_MESSAGE:: " + new String(pubsubMessage.decodeData(),"UTF-8"));
          }
          
        }
        ackIds.add(receivedMessage.getAckId());
      }
      AcknowledgeRequest ackRequest = new AcknowledgeRequest();
      ackRequest.setAckIds(ackIds);
      client.projects().subscriptions()
          .acknowledge(subscriptionName, ackRequest)
          .execute();
    }
    return messageList;
  }
  
  public static boolean isJSONValid(String jsonInString) {
    Gson gson = new Gson();
    try {
        gson.fromJson(jsonInString, Object.class);
        return true;
    } catch(JsonSyntaxException ex) { 
        return false;
    }
}
}
