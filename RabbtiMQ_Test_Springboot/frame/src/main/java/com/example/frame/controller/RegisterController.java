package com.example.frame.controller;

import com.example.frame.aop.annotation.SysLogAnno;
import com.example.frame.util.CheckUtil;
import com.example.frame.util.JsonData;
import com.example.frame.controller.request.MailRequest;
import com.example.frame.controller.request.PhoneRequest;
import com.example.frame.service.RegisterService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/v1/account/register")
public class RegisterController {

    @Resource
    private RegisterService registerService;

    @PostMapping("Mail")
    @ApiOperation(value = "发送邮箱验证码")
    @SysLogAnno(description = "发送邮箱验证码")
    public JsonData sendEmail(@RequestBody MailRequest mailRequest) {
        boolean isEmail = CheckUtil.isEmail(mailRequest.getEmail());
        if(!isEmail){
            return JsonData.buildError("邮箱格式不正确");
        }

        return registerService.sendEmail(mailRequest);
    }

    @PostMapping("Sms")
    @ApiOperation(value = "发送手机验证码")
    @SysLogAnno(description = "发送手机验证码")
    public JsonData sendSms(@RequestBody PhoneRequest phoneRequest) {
        boolean isPhone = CheckUtil.isPhone(phoneRequest.getPhone());
        if(!isPhone){
            return JsonData.buildError("手机号格式不正确");
        }

        return registerService.sendSms(phoneRequest);
    }


}
