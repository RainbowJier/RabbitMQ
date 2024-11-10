package com.example.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Description：TODO
 * @Author： RainbowJier
 * @Data： 2024/11/10 13:55
 */
public class ConnectionUtil {
    public static Connection getConnection() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("123.249.98.26");
        factory.setPort(5672);

        // 默认虚拟主机
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("guest");
        return factory.newConnection();
    }
}
