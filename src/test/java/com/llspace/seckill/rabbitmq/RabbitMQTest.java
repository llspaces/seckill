package com.llspace.seckill.rabbitmq;

import com.llspace.seckill.SeckillApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>@filename RabbitMQTest</p>
 * <p>
 * <p>@description rabbitmq测试</p>
 *
 * RabbitMQ常用的Exchange有四种：fanout、direct、topic、headers
 * Exchange概念:交换机，根据路由键转发消息到绑定的队列
 *
 * @author llspace
 * @version 1.0
 * @since 2019/7/12 15:35
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SeckillApplication.class})
public class RabbitMQTest {

    @Autowired
    private MQSender mqSender;

    @Test
    public void direct(){
        //mqSender.send("hello rabbitmq !");
    }

    @Test
    public void topic(){
        //mqSender.sendTopic("hello rabbitmq !");
    }

}
