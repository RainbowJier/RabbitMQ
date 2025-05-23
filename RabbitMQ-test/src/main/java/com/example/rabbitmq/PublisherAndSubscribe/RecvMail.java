package com.example.rabbitmq.PublisherAndSubscribe;

import com.example.rabbitmq.config.ConnectionConfig;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author: RainbowJier
 * @Description: 👺🐉😎
 * @Date: 2024/11/11 15:19
 * @Version: 1.0
 */

public class RecvMail {

    private final static String QUEUE_NAME = RabbitMQConfig.MAIL_QUEUE_NAME;
    private final static String EXCHANGE_NAME = RabbitMQConfig.EXCHANGE_NAME;

    public static void main(String[] argv) throws Exception {
        // connect to RabbitMQ server.
        Connection connection = ConnectionConfig.getConnection();

        // create a channel.
        Channel channel = connection.createChannel();

        // declare a queue and bind it to the specified exchange.
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // get messages from RabbitMQ server and consume them.
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // message id that is to identify a message, and confirm to be received.
                long deliveryTag = envelope.getDeliveryTag();

                // body of the message.
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println(" [Mail Server] received : " + msg + "!");

                // respond ack to RabbitMQ server.
                channel.basicAck(deliveryTag, false);
            }
        };

        // confirm message and respond to RabbitMQ server.
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}
