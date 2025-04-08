package com.example.frame.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class JsonData {
    /**
     * Code represents the status code.
     */
    private Integer code;
    /**
     * Response Data.
     */
    private Object data;
    /**
     * Description of the response.
     */
    private String msg;

    /**
     * Construct a new JsonData object with the specified code, data and message.
     */
    public <T> T getData(TypeReference<T> typeReference) {
        return JSON.parseObject(JSON.toJSONString(data), typeReference);
    }

    /**
     * Successful without parameters.
     */
    public static JsonData buildSuccess() {
        return new JsonData(0, null, null);
    }

    /**
     * Successful with response data.
     */

    public static JsonData buildSuccess(Object data) {
        return new JsonData(0, data, null);
    }

    public static JsonData buildSuccess(String msg) {
        return new JsonData(0, null, msg);
    }

    public static JsonData buildSuccess(Object data,String msg) {
        return new JsonData(0, data, msg);
    }

    /**
     * Error with description.
     */
    public static JsonData buildError(String msg) {
        return new JsonData(-1, null, msg);
    }

    /**
     * Custom status code and message.
     */
    public static JsonData buildCodeAndMsg(int code, String msg) {
        return new JsonData(code, null, msg);
    }
}