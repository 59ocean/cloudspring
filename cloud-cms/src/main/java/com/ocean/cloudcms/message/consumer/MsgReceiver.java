package com.ocean.cloudcms.message.consumer;

import com.ocean.cloudcms.message.MqMessage;
import com.ocean.cloudcms.message.service.Mqservice;
import com.ocean.cloudcms.utils.SpringUtil;
import com.rabbitmq.client.Channel;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

@Component
public class MsgReceiver {
    @Autowired
    private Mqservice mqservice;

    //配置监听的哪一个队列，同时在没有queue和exchange的情况下会去创建并建立绑定关系
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "order-queue",durable = "true"),
            exchange = @Exchange(name="order-exchange",durable = "true",type = "topic"),
            key = "order.*"
        )
    )
    @RabbitHandler//如果有消息过来，在消费的时候调用这个方法
    public void onOrderMessage(@Payload MqMessage message, @Headers Map<String,Object> headers, Channel channel) throws IOException {
        //消费者操作
      /*  System.out.println("---------收到消息，开始消费---------");
        System.out.println("订单ID：");*/
        String serviceName = message.getServiceName();
        String serviceMethod = message.getServiceMethod();
        try {
          /*  System.out.println("==========receiver1========");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("==========receiver2==========");*/
            Class service = Class.forName(serviceName);
            //从spring容器中拿bean，the bean is 单例

            Object object = SpringUtil.getBean(service);
            Method method = service.getMethod(serviceMethod,Map.class);
            method.invoke(object,message.getParams());
            //new Mqservice().test(message.getParams());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        /**
         * Delivery Tag 用来标识信道中投递的消息。RabbitMQ 推送消息给 Consumer 时，会附带一个 Delivery Tag，
         * 以便 Consumer 可以在消息确认时告诉 RabbitMQ 到底是哪条消息被确认了。
         * RabbitMQ 保证在每个信道中，每条消息的 Delivery Tag 从 1 开始递增。
         */
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        /**
         *  multiple 取值为 false 时，表示通知 RabbitMQ 当前消息被确认
         *  如果为 true，则额外将比第一个参数指定的 delivery tag 小的消息一并确认
         */
        boolean multiple = false;

        //ACK,确认一条消息已经被消费
        channel.basicAck(deliveryTag,multiple);
    }
}
