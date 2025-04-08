package com.example.rabbitmq.WorkQueue;

import com.example.rabbitmq.config.ConnectionConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;


/**
 * @Description：Producer
 * @Author： RainbowJier
 * @Data： 2024/11/10 13:53
 */

@Slf4j
public class Send {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        // connect to RabbitMQ server.
        Connection connection = ConnectionConfig.getConnection();

        // create a channel.
        Channel channel = connection.createChannel();

        // use try-with-resources to ensure the connection and channel are closed.
        try {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            for (int i = 0; i < 50; i++) {
                String message = "Task : " + i;

                // publish a message to the queue.
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + message + "'");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            channel.close();
            connection.close();
        }
    }
}