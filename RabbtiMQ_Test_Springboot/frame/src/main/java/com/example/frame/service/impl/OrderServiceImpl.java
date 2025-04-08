package com.example.frame.service.impl;

import com.example.frame.config.OrderMQConfig;
import com.example.frame.config.TopicMQConfig;
import com.example.frame.controller.request.MailRequest;
import com.example.frame.controller.request.PhoneRequest;
import com.example.frame.controller.request.ProductRequest;
import com.example.frame.model.entity.EventMessage;
import com.example.frame.service.OrderService;
import com.example.frame.service.RegisterService;
import com.example.frame.util.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMQConfig orderMQConfig;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public JsonData confirmOrder(ProductRequest productRequest) {
        // 创建消息体
        EventMessage orderMessage = new EventMessage();
        orderMessage
                .setBizId("1")    // 订单 id
                .setContent(productRequest.getProductName());

        // 发送消息
        rabbitTemplate.convertAndSend(
                orderMQConfig.getOrderDelayEventExchange(),
                orderMQConfig.getOrderDelayRoutingKey(),
                orderMessage);

        return JsonData.buildSuccess();
    }

    @Override
    public void handleCloseOrder(EventMessage eventMessage) {
        String content = eventMessage.getContent();
        log.info("订单支付超市，关闭订单：{}", content);
    }

}
