#数据监控
http://127.0.0.1:8005/druid/index.html


1.用户发起获取token的请求。
2.通过web拦截器和我们自定义的过滤，看到是申请派发token的请求，一律放行。
3.当经过ClientCredentialsTokenEndpointFilter过滤器的时候，进行第三方客户端凭证验证。
4.执行第三方客户端凭证验证流程，验证通过继续放行，验证不通过响应客户端。
5.当经过TokenEndpointAuthenticationFilter过滤器的时候，进行用户凭证验证。
6.执行用户身份凭证验证流程，验证通过继续放行，验证不通过响应客户端。
7.进入到TokenEndpoint站点，执行postAccessToken方法
8.生成token方法返回客户端。
9.token派发完成
10.用户携带token访问资源（本文没有讲，携带token访问的时候也有一套验证token的流程，有时间再总结出来）

