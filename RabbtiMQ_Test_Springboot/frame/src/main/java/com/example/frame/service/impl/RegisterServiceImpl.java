package com.example.frame.service.impl;

import com.example.frame.config.TopicMQConfig;
import com.example.frame.model.entity.EventMessage;
import com.example.frame.util.JsonData;
import com.example.frame.controller.request.MailRequest;
import com.example.frame.controller.request.PhoneRequest;
import com.example.frame.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Slf4j
@Service
public class RegisterServiceImpl implements RegisterService {
    @Resource
    private TopicMQConfig topicMQConfig;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public JsonData sendEmail(MailRequest mailRequest) {
        // 创建消息体
        EventMessage emailMessage = new EventMessage();
        emailMessage.setContent(mailRequest.getEmail())
                .setEventMessageType("EMAIL");

        // 发送消息
        rabbitTemplate.convertAndSend(
                topicMQConfig.getRegisterEventExchange(),
                topicMQConfig.getRegisterEmailRoutingKey(),
                emailMessage);

        return JsonData.buildSuccess();
    }

    @Override
    public JsonData sendSms(PhoneRequest phoneRequest) {
        EventMessage smsMessage = new EventMessage();
        smsMessage.setContent(phoneRequest.getPhone())
                .setEventMessageType("SMS");

        // 发送消息
        rabbitTemplate.convertAndSend(
                topicMQConfig.getRegisterEventExchange(),
                topicMQConfig.getRegisterSmsRoutingKey(),
                smsMessage);

        return JsonData.buildSuccess();
    }

    @Override
    public void handleRegisterEmailMessage(EventMessage eventMessage) {
        String content = eventMessage.getContent();
        log.info("发送邮箱验证码，邮箱地址：{}", content);
    }

    @Override
    public void handleRegisterSmsMessage(EventMessage eventMessage) {
        String content = eventMessage.getContent();
        log.info("发送手机验证码，手机号：{}", content);
    }
}
