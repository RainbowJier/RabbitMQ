package com.example.rabbitmq.Topic;

import com.example.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author: RainbowJier
 * @Description: 👺🐉😎use symbol *
 * @Date: 2024/11/11 15:19
 * @Version: 1.0
 */

public class Recv_ {

    private final static String QUEUE_NAME = "*_queue";
    private final static String ROUTING_KEY = "log.*";
    private final static String EXCHANGE_NAME = "topic_exchange";

    public static void main(String[] argv) throws Exception {
        // connect to RabbitMQ server.
        Connection connection = ConnectionUtil.getConnection();

        // create a channel.
        Channel channel = connection.createChannel();

        // declare a queue and bind it to an exchange.
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // get messages from RabbitMQ server and consume them.
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // message id that is to identify a message, and confirm to be received.
                long deliveryTag = envelope.getDeliveryTag();

                // body of the message.
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println(" [x] received : " + msg + "!");

                // respond ack to RabbitMQ server.
                channel.basicAck(deliveryTag, false);
            }
        };

        // confirm message and respond to RabbitMQ server.
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}
