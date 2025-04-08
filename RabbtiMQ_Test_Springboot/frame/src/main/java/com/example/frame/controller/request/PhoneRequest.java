package com.example.frame.controller.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneRequest {
    @ApiModelProperty(value = "手机号")
    private String phone;
}
