package com.ocean.cloudcms.message.consumer;

import com.ocean.cloudcms.message.MqMessage;
import com.ocean.cloudcms.utils.SpringUtil;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

@Component
@RabbitListener(queues = "fanout-queue")
public class MsgReceiverTwo {
    //配置监听的哪一个队列，同时在没有queue和exchange的情况下会去创建并建立绑定关系

    @RabbitHandler//如果有消息过来，在消费的时候调用这个方法
    public void onOrderMessage(@Payload MqMessage message, @Headers Map<String,Object> headers, Channel channel) throws IOException {
        //消费者操作
        System.out.println("---------收到消息，开始消费222222222---------");


        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);


        boolean multiple = false;

        //ACK,确认一条消息已经被消费
        channel.basicAck(deliveryTag,multiple);
    }
}
