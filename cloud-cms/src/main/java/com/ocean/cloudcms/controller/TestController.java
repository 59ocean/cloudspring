package com.ocean.cloudcms.controller;

import com.ocean.cloudcms.thread.StatsDemo;
import com.ocean.cloudcommon.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: chenhy
 * @Date: 2019/3/4 17:20
 * @Version 1.0
 */
@RestController
@Api(tags = "测试API")
public class TestController {

    @ApiOperation(value = "测试线程")
    @GetMapping("/test/addThread/")
    public R test() throws InterruptedException {
        StatsDemo.addThread();
        //StatsDemo.addThread();
        System.out.println("线程执行完毕");
        return R.ok();
    }
}
