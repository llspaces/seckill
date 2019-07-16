package com.llspace.seckill.rabbitmq;

import com.llspace.seckill.utils.SeckillConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>@filename MQConfig</p>
 * <p>
 * <p>@description rabbitmq配置类</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/7/12 14:47
 **/
@Configuration
public class MQConfig {

    /**
     * direct exchange
     * @return
     */
    @Bean
    public Queue queue(){
        return new Queue(SeckillConstant.QUEUE_NAME, true);
    }

}
