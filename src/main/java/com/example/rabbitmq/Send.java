package com.example.rabbitmq;

import com.example.rabbitmq.util.ConnectionUtil;
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
        Connection connection = ConnectionUtil.getConnection();

        // create a channel.
        Channel channel = connection.createChannel();

        // use try-with-resources to ensure the connection and channel are closed.
        try {
            /**
             * Declare a queue.
             * param1: queue name
             * param2: durable: 是否持久化，true表示持久化，false表示非持久化
             * param3: exclusive: 是否独占，true表示独占，false表示共享
             * param4: autoDelete: 是否自动删除，true表示自动删除，false表示不自动删除
             * param5: arguments: 队列的其他参数，可以设置队列的最大长度、消息TTL等属性，如果不需要可以设置为 null。
             */
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            String message = "Hello World!";
            /**
             * exchange: 交换机名称，为空则使用默认交换机
             * routingKey: 路由键，为空则使用队列名称
             * properties: 消息属性，可以设置消息的持久化、延迟、优先级等属性，如果不需要可以设置为 null。
             * body: 消息内容
             */
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));

            System.out.println(" [x] Sent '" + message + "'");
        }catch (Exception e){
            log.error(e.getMessage());
        }finally {
            channel.close();
            connection.close();
        }
    }
}