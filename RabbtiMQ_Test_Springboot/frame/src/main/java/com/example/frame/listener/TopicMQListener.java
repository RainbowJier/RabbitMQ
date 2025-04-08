package com.example.frame.listener;

import com.example.frame.model.entity.EventMessage;
import com.example.frame.service.RegisterService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: RainbowJier
 * @Description: 👺🐉😎C 端队列监听器（消费者）
 * @Date: 2024/11/12 10:02
 * @Version: 1.0
 */
@Slf4j
@Component
@RabbitListener(queuesToDeclare = {
        @Queue("register.sms.queue"),
        @Queue("register.email.queue")
})
public class TopicMQListener {
    @Autowired
    private RegisterService registerService;

    @RabbitHandler
    public void emailHandler(EventMessage eventMessage, Message message, Channel channel) {
        log.info("==========【注册邮箱/手机号监听器】-Start==========");

        try {
            String eventType = eventMessage.getEventMessageType();
            if("EMAIL".equals(eventType)){
                registerService.handleRegisterEmailMessage(eventMessage);
            }else if("SMS".equals(eventType)){
                registerService.handleRegisterSmsMessage(eventMessage);
            }
        } catch (Exception e) {
            log.error("【注册邮箱/手机号监听器】消费异常：{}", e.getMessage());
        }
        log.info("==========【注册邮箱/手机号监听器】-End==========");
    }
}
