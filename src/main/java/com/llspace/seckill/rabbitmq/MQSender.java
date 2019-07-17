package com.llspace.seckill.rabbitmq;

import com.llspace.seckill.entity.SeckillMessage;
import com.llspace.seckill.utils.JsonUtil;
import com.llspace.seckill.utils.SeckillConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * <p>@filename MQSender</p>
 * <p>
 * <p>@description 消息发送者</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/7/12 14:45
 **/
@Component
@Slf4j
public class MQSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * direct exchange方式
     *
     * @param message
     */
    public void sendSeckillMessage(SeckillMessage message) {
        String msg = JsonUtil.toJSONString(message);
        log.info("send message:"+msg);
        amqpTemplate.convertAndSend(SeckillConstant.QUEUE_NAME, msg);
    }
}
