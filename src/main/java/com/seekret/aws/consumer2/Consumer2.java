package com.seekret.aws.consumer2;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Consumer2 {

  public static void main(final String[] args) {
    AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

    String queueUrl1 = sqs.getQueueUrl("global-topic").getQueueUrl();

    while (true) {
      List<Message> messages;
      do {
        messages = sqs.receiveMessage(queueUrl1).getMessages();
        for (Message m : messages) {
          System.out.println(m.getBody());
          sqs.deleteMessage(queueUrl1, m.getReceiptHandle());
        }
      } while (!messages.isEmpty());

      try {
        TimeUnit.SECONDS.sleep(4);
      } catch (InterruptedException e) {
        // Empty on purpose.
      }
    }
  }

}
