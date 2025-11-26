package com.example.backend.MessageBrokers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Component;

import com.example.backend.Model.Vote.VoteDTO;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

@Component
public class PollBroker implements PollMessageBroker {

   private final ConnectionFactory factory;
   private static final String EXCHANGE_NAME = "polls";

    public PollBroker() {
        String host = System.getenv().getOrDefault("RABBITMQ_HOST", "rabbitmq");
        int port = Integer.parseInt(System.getenv().getOrDefault("RABBITMQ_PORT", "5672"));

        factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);

        System.out.println("[PollBroker] configured for " + host + ":" + port);
    }

    public void sendVote(Integer pollId, VoteDTO vote) throws IOException, TimeoutException {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC, true);

            String routingKey = "poll." + pollId + ".vote.cast";
            String message    = vote.toJson();
            channel.basicPublish(
                    EXCHANGE_NAME,
                    routingKey,
                    null,
                    message.getBytes(StandardCharsets.UTF_8)
            );

            System.out.println("[PollBroker] sent '" + routingKey + "':'" + message + "'");
        }
    }

    public void recieve(Listener callbackListener) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(System.getenv().getOrDefault("RABBITMQ_HOST", "rabbitmq"));
        factory.setPort(Integer.parseInt(System.getenv().getOrDefault("RABBITMQ_PORT", "5672")));
        ;
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            String queueName = channel.queueDeclare().getQueue();

            channel.queueBind(queueName, EXCHANGE_NAME, EXCHANGE_NAME + ".*.vote.cast");

            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
                callbackListener.onEvent(message);
            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}