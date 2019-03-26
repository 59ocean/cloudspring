package com.ocean.cloudcms.message.producter;

import com.alibaba.fastjson.JSON;
import com.ocean.cloudcms.constant.Constants;
import com.ocean.cloudcms.dao.BrokerMessageLogMapper;
import com.ocean.cloudcms.entity.BrokerMessageLog;
import com.ocean.cloudcms.message.MqMessage;
import com.ocean.cloudcms.utils.DateUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RabbitMsgSenderTwo {
    //自动注入RabbitTemplate模板类
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;



    //发送消息方法调用: 构建自定义对象消息
    public void sendMsg(MqMessage message) throws Exception {
        // 通过实现 ConfirmCallback 接口，消息发送到 Broker 后触发回调，确认消息是否到达 Broker 服务器，也就是只确认是否正确到达 Exchange 中
        // 插入消息记录表数据
        BrokerMessageLog brokerMessageLog = new BrokerMessageLog();
        // 消息唯一ID
        brokerMessageLog.setId((int)(Math.random()*1000));
        brokerMessageLog.setMessageId(message.getMessageId());
        // 保存消息整体 转为JSON 格式存储入库
        brokerMessageLog.setMessage(JSON.toJSONString(message));
        // 设置消息状态为0 表示发送中
        brokerMessageLog.setStatus("0");
        // 设置消息未确认超时时间窗口为 一分钟
        brokerMessageLog.setNextRetry(DateUtils.addMinutes(new Date(), Constants.MSG_TIMEOUT));
        brokerMessageLog.setCreateTime(new Date());
        brokerMessageLog.setUpdateTime(new Date());
        brokerMessageLogMapper.insertSelective(brokerMessageLog);
        //rabbitTemplate.setConfirmCallback(confirmCallback);
        //消息唯一ID
        CorrelationData correlationData = new CorrelationData((String) message.getMessageId());
        //rabbitTemplate.convertAndSend("order-exchange", "order.ABC", message, correlationData);
        rabbitTemplate.convertAndSend("order-queue2",message);
    }
}
