package com.example.backend.MessageBrokers;

import com.rabbitmq.client.DeliverCallback;

import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Component
public class PollReciever  {

  private static final String EXCHANGE_NAME = "poll";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(System.getenv().getOrDefault("RABBITMQ_HOST", "rabbitmq"));
factory.setPort(Integer.parseInt(System.getenv().getOrDefault("RABBITMQ_PORT", "5672")));;
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE_NAME, "topic");
    String queueName = channel.queueDeclare().getQueue();

    if (argv.length < 1) {
        System.err.println("Usage: ReceiveLogsTopic [binding_key]...");
        System.exit(1);
    }

    for (String bindingKey : argv) {
        channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
    }

    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), "UTF-8");
        System.out.println(" [x] Received '" +
            delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
    };
    channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
  }


    public void voteReciever(MyListener callbackListener) {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(System.getenv().getOrDefault("RABBITMQ_HOST", "rabbitmq"));
    factory.setPort(Integer.parseInt(System.getenv().getOrDefault("RABBITMQ_PORT", "5672")));;
    try {
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    

    channel.exchangeDeclare(EXCHANGE_NAME, "topic");
    String queueName = channel.queueDeclare().getQueue();

    channel.queueBind(queueName, EXCHANGE_NAME, EXCHANGE_NAME+".*.vote.cast");
    
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), "UTF-8");
        System.out.println(" [x] Received '" +
        delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        callbackListener.onEvent(message);
    };
    channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
    catch(Exception e){
      System.out.println(e);
    }


  }


    
    
}
