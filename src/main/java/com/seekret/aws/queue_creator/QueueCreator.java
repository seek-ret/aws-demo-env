package com.seekret.aws.queue_creator;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueRequest;

import java.util.ArrayList;
import java.util.List;

public class QueueCreator {

  public static void main(final String[] args) {
    AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

    List<String> queues = new ArrayList<>();
    queues.add("global-topic");
    queues.add("my-topic");

    for (String queue : queues) {
      CreateQueueRequest create_request = new CreateQueueRequest(queue)
              .addAttributesEntry("DelaySeconds", "10")
              .addAttributesEntry("MessageRetentionPeriod", "86400");

      try {
        sqs.createQueue(create_request);
      } catch (AmazonSQSException e) {
        if (!e.getErrorCode().equals("QueueAlreadyExists")) {
          throw e;
        }
      }

      System.out.println("Created " + queue);
    }
  }
}
