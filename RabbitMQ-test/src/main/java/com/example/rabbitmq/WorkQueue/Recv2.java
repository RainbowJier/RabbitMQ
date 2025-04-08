package com.example.rabbitmq.WorkQueue;

import com.example.rabbitmq.config.ConnectionConfig;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Description：Consumer2
 * @Author： RainbowJier
 * @Data： 2024/11/10 13:53
 */

public class Recv2 {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        // connect to RabbitMQ server.
        Connection connection = ConnectionConfig.getConnection();

        // create a channel.
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

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