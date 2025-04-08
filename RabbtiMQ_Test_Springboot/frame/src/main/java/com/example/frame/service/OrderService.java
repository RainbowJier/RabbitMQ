package com.example.frame.service;


import com.example.frame.controller.request.MailRequest;
import com.example.frame.controller.request.PhoneRequest;
import com.example.frame.controller.request.ProductRequest;
import com.example.frame.model.entity.EventMessage;
import com.example.frame.util.JsonData;

public interface OrderService {
    /**
     * 提交订单，延迟队列-关闭订单
     */
    JsonData confirmOrder(ProductRequest productRequest);

    /**
     * 支付超时，关闭订单
     */
    void handleCloseOrder(EventMessage eventMessage);
}
