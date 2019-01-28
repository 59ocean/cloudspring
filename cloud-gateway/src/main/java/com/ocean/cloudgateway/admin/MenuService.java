package com.ocean.cloudgateway.admin;


import com.ocean.cloudgateway.MenuDTO;
import com.ocean.cloudgateway.intercepter.FeignIntercepter;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Headers("Content-Type:application/json")
@FeignClient(name = "api-admin", configuration = FeignIntercepter.class)
public interface MenuService {
    @GetMapping("/menu/userMenus")
    List<MenuDTO> userMenus();
}
