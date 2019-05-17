package com.ocean.cloudcms.controller;

import com.ocean.cloudcms.dao.BrokerMessageLogMapper;
import com.ocean.cloudcms.feginClients.UserFeignClient;
import com.ocean.cloudcms.thread.StatsDemo;
import com.ocean.cloudcms.utils.SensitiveWordInit;
import com.ocean.cloudcms.utils.SensitivewordEngine;
import com.ocean.cloudcms.vo.UserVo;
import com.ocean.cloudcommon.utils.ApiResponse;
import com.ocean.cloudcommon.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @Author: chenhy
 * @Date: 2019/3/4 17:20
 * @Version 1.0
 */
@RestController
@Api(tags = "测试API")
public class TestController {
    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;
    @Autowired
    private UserFeignClient userFeignClient;

    @ApiOperation(value = "测试线程")
    @GetMapping("/test/addThread/")
    public R test() throws InterruptedException {
        StatsDemo.addThread();
        //StatsDemo.addThread();
        System.out.println("线程执行完毕");
        return R.ok();
    }
    @ApiOperation(value = "测试敏感词")
    @GetMapping("/test/testKeyWord/")
    public R testKeyWord(String word) throws InterruptedException {
        long a = System.currentTimeMillis();
        if(SensitivewordEngine.sensitiveWordMap==null){

            List list = brokerMessageLogMapper.getKeyWord();
            SensitiveWordInit sensitiveWordInit = new SensitiveWordInit();
            SensitivewordEngine.sensitiveWordMap = sensitiveWordInit.initKeyWord(list);
        }
        System.out.println(a);
        Set<String> result = SensitivewordEngine.getSensitiveWord(word,SensitivewordEngine.minMatchTYpe);
        System.out.println(System.currentTimeMillis()-a);
        System.out.println(result);


        return R.ok().put("data",result)
                .put("time",System.currentTimeMillis()-a);
    }

    @GetMapping("/test/getUserFromFeignClient")
    public ApiResponse testFeignClient(long id){
        UserVo userVo = userFeignClient.getUser(id);
        return ApiResponse.ok(userVo);
    }
}
