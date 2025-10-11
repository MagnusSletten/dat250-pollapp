package com.example.backend.MessageBrokers;

import org.springframework.stereotype.Component;

import com.example.backend.Model.Poll.Vote.Vote;
import com.example.backend.Model.Poll.Vote.VoteRequest;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Component
public class PollBroker {

    private static final String EXCHANGE_NAME = "poll";

    public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(System.getenv().getOrDefault("RABBITMQ_HOST", "rabbitmq"));
    factory.setPort(Integer.parseInt(System.getenv().getOrDefault("RABBITMQ_PORT", "5672")));
    try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel()) {

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        String routingKey = argv[0];
        String message = argv[1];

        channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
    }
    }

    public void sendVoteEvent(Integer pollId, VoteRequest vote) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    var host = System.getenv().getOrDefault("RABBITMQ_HOST", "rabbitmq");
    var port = Integer.parseInt(System.getenv().getOrDefault("RABBITMQ_PORT", "5672"));

    factory.setHost(System.getenv().getOrDefault("RABBITMQ_HOST", "rabbitmq"));
    factory.setPort(Integer.parseInt(System.getenv().getOrDefault("RABBITMQ_PORT", "5672")));
    try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel()) {

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        System.out.println("[PollBroker] connecting to " + host + ":" + port);
        String routingKey ="poll."+pollId.toString()+".vote.cast";
        String message = vote.toJson(); 

        channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");

        
    }
    }


}
