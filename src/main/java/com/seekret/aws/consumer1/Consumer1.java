package com.seekret.aws.consumer1;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Consumer1 {

  public static void main(final String[] args) {
//    System.out.println("PID: " + ProcessHandle.current().pid());
    AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

    String queueUrl1 = sqs.getQueueUrl("global-topic").getQueueUrl();
    String queueUrl2 = sqs.getQueueUrl("my-topic").getQueueUrl();

    while (true) {
      List<Message> messages;
      List<Message> messages2;
      do {
        messages = sqs.receiveMessage(queueUrl1).getMessages();
        for (Message m : messages) {
          System.out.println(m.getBody());
          sqs.deleteMessage(queueUrl1, m.getReceiptHandle());
        }
        messages2 = sqs.receiveMessage(queueUrl2).getMessages();
        for (Message m : messages2) {
          System.out.println(m.getBody());
          sqs.deleteMessage(queueUrl2, m.getReceiptHandle());
        }
      } while (!messages.isEmpty() && !messages2.isEmpty());

      try {
        TimeUnit.SECONDS.sleep(5);
      } catch (InterruptedException e) {
        // Empty on purpose.
      }
    }
  }

}
