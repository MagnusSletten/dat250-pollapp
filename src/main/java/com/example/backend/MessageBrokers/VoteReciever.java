package com.example.backend.MessageBrokers;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class VoteReciever {
    private static final String EXCHANGE_NAME = "poll";

    ConnectionFactory factory = new ConnectionFactory();
    
    public VoteReciever(MyListener callbackListener) throws Exception{
        
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare("polls", "topic");
    String queueName = channel.queueDeclare().getQueue();

    channel.queueBind(queueName, EXCHANGE_NAME, "poll.*.vote.cast");


    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), "UTF-8");
        System.out.println(" [x] Received '" +
        delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        callbackListener.onEvent(message);
    };
    channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
        
    }

  }


    

