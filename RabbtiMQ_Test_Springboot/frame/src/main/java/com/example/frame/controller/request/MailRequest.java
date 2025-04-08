package com.example.frame.controller.request;


import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailRequest {

    @ApiModelProperty("邮箱")
    private String email;
}
