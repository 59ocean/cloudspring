package com.ocean.cloudgateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthSignatureFilter implements GlobalFilter, Ordered {

    /**
     * 拦截请求，获取authToken，并校验
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getQueryParams().getFirst("authToken");
       /* if (null==token  || token.isEmpty()) {
            //当请求不携带Token或者token为空时，直接设置请求状态码为401，返回
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }*/
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -400;
    }
}
