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

    //===============traffic reduction delay queue===================================


    /**
     * delayer time 1 minute, unit: milliseconds
     */
//    private Integer ttl = 1000 * 60 * 1;
//
    // delay exchange --> delay queue --> release.queue
//    private String trafficReleaseDelayRoutingKey = "traffic.release.delay.routing.key";
//    private String trafficReleaseDelayQueue = "traffic.release.delay.queue";
//
//    @Bean
//    public Queue trafficReleaseDelayQueue() {
//        Map<String, Object> args = new HashMap<>(3);
//        args.put("x-message-ttl", ttl);
//        args.put("x-dead-letter-exchange", trafficEventExchange);
//        args.put("x-dead-letter-routing-key", trafficReleaseRoutingKey);
//
//        return new Queue(trafficReleaseDelayQueue, true, false, false, args);
//    }
//
//    @Bean
//    public Binding trafficReleaseDelayBinding() {
//        return BindingBuilder.bind(trafficReleaseDelayQueue()).to(trafficEventExchange())
//                .with(trafficReleaseDelayRoutingKey);
//    }
//
//    private String trafficReleaseRoutingKey = "traffic.release.routing.key";
//    private String trafficReleaseQueue = "traffic.release.queue";
//
//    @Bean
//    public Queue trafficReleaseQueue() {
//        return new Queue(trafficReleaseQueue, true, false, false);
//    }
//
//    @Bean
//    public Binding trafficReleaseBinding() {
//        return BindingBuilder.bind(trafficReleaseQueue()).to(trafficEventExchange())
//                .with(trafficReleaseRoutingKey);
//    }


}
