package com.example.frame.config;

import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: RainbowJier
 * @Description: 👺🐉😎延迟队列配置类
 * @Date: 2024/11/12 9:40
 * @Version: 1.0
 */


@Data
@Configuration
public class TopicMQConfig {

    /**
     * message converter，message serializer
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Exchange.
     */
    private String registerEventExchange = "register.exchange";


    @Bean
    public TopicExchange registerEventExchange() {
        return new TopicExchange(registerEventExchange, true, false);
    }

    //===============email queue==================================
    private String registerEmailQueue = "register.email.queue";
    private String registerEmailBindingKey = "register.email.binding.key";
    private String registerEmailRoutingKey = "register.email.binding.key";

    @Bean
    public Queue registerEmailQueue() {
        return new Queue(registerEmailQueue, true, false, false);
    }

    @Bean
    public Binding registerEmailBinding() {
        return BindingBuilder.bind(registerEmailQueue())
                .to(registerEventExchange())
                .with(registerEmailBindingKey);
    }

    //===============sms queue==================================
    private String registerSmsQueue = "register.sms.queue";
    private String registerSmsBindingKey = "register.sms.binding.key";
    private String registerSmsRoutingKey = "register.sms.binding.key";

    @Bean
    public Queue registerSmsQueue() {
        return new Queue(registerSmsQueue, true, false, false);
    }

    @Bean
    public Binding registerSmsBinding() {
        return BindingBuilder.bind(registerSmsQueue())
                .to(registerEventExchange())
                .with(registerSmsBindingKey);
    }

}
