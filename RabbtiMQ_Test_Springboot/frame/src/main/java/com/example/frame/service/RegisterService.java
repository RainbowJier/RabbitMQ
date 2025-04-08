package com.example.frame.service;


import com.example.frame.model.entity.EventMessage;
import com.example.frame.util.JsonData;
import com.example.frame.controller.request.MailRequest;
import com.example.frame.controller.request.PhoneRequest;

public interface RegisterService {
    JsonData sendEmail(MailRequest mailRequest);

    JsonData sendSms(PhoneRequest phoneRequest);

    void handleRegisterEmailMessage(EventMessage eventMessage);

    void handleRegisterSmsMessage(EventMessage eventMessage);
}
