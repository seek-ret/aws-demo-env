package com.seekret.aws.producer1;
import java.util.concurrent.TimeUnit;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.*;

public class Producer1 {

  public static void main(final String[] args) {
    AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

    String queueUrl1 = sqs.getQueueUrl("global-topic").getQueueUrl();
    SendMessageRequest send_msg_request1 = new SendMessageRequest()
            .withQueueUrl(queueUrl1)
            .withMessageBody("hello world");

    String queueUrl2 = sqs.getQueueUrl("my-topic").getQueueUrl();
    SendMessageRequest send_msg_request2 = new SendMessageRequest()
            .withQueueUrl(queueUrl2)
            .withMessageBody("hola");

    while (true) {
      sqs.sendMessage(send_msg_request1);
      sqs.sendMessage(send_msg_request2);
      System.out.println("Sent message");
      try {
        TimeUnit.SECONDS.sleep(8);
      } catch (InterruptedException e) {
        // Empty on purpose.
      }
    }
  }

}
