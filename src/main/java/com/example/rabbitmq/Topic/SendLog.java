package com.example.rabbitmq.Topic;

import com.example.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @Author: RainbowJier
 * @Description: 👺🐉😎
 * @Date: 2024/11/11 15:19
 * @Version: 1.0
 */
@Slf4j
public class SendLog {
    private final static String EXCHANGE_NAME = "topic_exchange";

    public static void main(String[] argv) throws Exception {
        String[] routingKeys = {"log.info.register","log.info.login", "log.error", "log.warn.login"};
        for (String routingKey : routingKeys) {

            // connect to RabbitMQ server.
            Connection connection = ConnectionUtil.getConnection();

            // create a channel.
            Channel channel = connection.createChannel();

            // declare an fanout exchange.
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            try {

                String message = "This is a " + routingKey + " message";

                // publish a message to the queue.
                channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [SendRegister] Send '" + message + "'");
            } catch (Exception e) {
                log.error(e.getMessage());
            } finally {
                channel.close();
                connection.close();
            }
        }
    }
}