package com.seekret.aws.producer2;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import java.util.concurrent.TimeUnit;

public class Producer2 {

  public static void main(final String[] args) {
    AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

    String queueUrl = sqs.getQueueUrl("global-topic").getQueueUrl();
    SendMessageRequest send_msg_request = new SendMessageRequest()
            .withQueueUrl(queueUrl)
            .withMessageBody("another message world");

    while (true) {
      sqs.sendMessage(send_msg_request);
      System.out.println("Sent message");
      try {
        TimeUnit.SECONDS.sleep(13);
      } catch (InterruptedException e) {
        // Empty on purpose.
      }
    }
  }

}
