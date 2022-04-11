package com.seekret.aws.queue_deleter;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueRequest;

import java.util.ArrayList;
import java.util.List;

public class QueueDeleter {

  public static void main(final String[] args) {
    AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

    List<String> queues = new ArrayList<>();
    queues.add("global-topic");
    queues.add("my-topic");

    for (String queue : queues) {
      try {
        sqs.deleteQueue(queue);
      } catch (AmazonSQSException e) {
        if (!e.getErrorCode().equals("QueueAlreadyExists")) {
          throw e;
        }
      }

      System.out.println("Deleted " + queue);
    }
  }
}
