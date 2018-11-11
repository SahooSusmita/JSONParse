package com.yantriks.job.pubsub;

public class ActualMessage {
  
  private String messageId;
  private String message;
  
  public String getMessageId() {
    return messageId;
  }
  public void setMessageId(String messageId) {
    this.messageId = messageId;
  }
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
  @Override
  public String toString() {
    return "RECEIVED Message [messageId=" + messageId + ", message=" + message + "]";
  }
}
