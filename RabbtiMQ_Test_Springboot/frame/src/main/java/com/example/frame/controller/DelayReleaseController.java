package com.example.frame.controller;

import com.example.frame.annotation.Systemlog;
import com.example.frame.controller.request.MailRequest;
import com.example.frame.controller.request.PhoneRequest;
import com.example.frame.controller.request.ProductRequest;
import com.example.frame.service.OrderService;
import com.example.frame.service.RegisterService;
import com.example.frame.util.CheckUtil;
import com.example.frame.util.JsonData;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/v1/product")
public class DelayReleaseController {

    @Resource
    private OrderService orderService;

    @PostMapping("confirmOrder")
    @ApiOperation(value = "提交订单，延迟队列-关闭订单")
    @Systemlog(description = "提交订单，延迟队列-关闭订单")
    public JsonData confirmOrder(@RequestBody ProductRequest productRequest) {
        return orderService.confirmOrder(productRequest);
    }

}
