package com.ocean.cloudcms.controller;

import com.ocean.cloudcms.message.producter.RabbitMsgSender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: chenhy
 * @Date: 2019/2/18 15:40
 * @Version 1.0
 */
@RestController
@RequestMapping("/v1/cms/msg")
@Api(tags = "消息API")
public class MsgController {
    @Autowired
    private  RabbitMsgSender rabbitMsgSender;

    @GetMapping("/sendMsg")
    @ApiOperation(value = "发送消息")
    public void sendMessage(){
        Map<String,Object> data = new HashMap<>();
        data.put("id",1);
        data.put("messageId","abcdn123");
        try {
            rabbitMsgSender.sendOrder(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
