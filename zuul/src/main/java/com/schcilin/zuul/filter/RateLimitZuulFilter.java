package com.schcilin.zuul.filter;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: schcilin
 * @Date: 2019/6/2 14:32
 * @Version 1.0
 * @des: zuul级别的限流filter
 */

/**
 * Zuul大部分功能都是通过过滤器来实现的。Zuul中定义了四种标准过滤器类型，这些过滤器类型对应于请求的典型生命周期。
 * <p>
 * (1) PRE：这种过滤器在请求被路由之前调用。我们可利用这种过滤器实现身份验证、在集群中选择请求的微服务、记录调试信息等。
 * <p>
 * (2) ROUTING：这种过滤器将请求路由到微服务。这种过滤器用于构建发送给微服务的请求，并使用Apache HttpClient或Netfilx Ribbon请求微服务。
 * <p>
 * (3) POST：这种过滤器在路由到微服务以后执行。这种过滤器可用来为响应添加标准的HTTP Header、收集统计信息和指标、将响应从微服务发送给客户端等。
 * <p>
 * (4) ERROR：在其他阶段发生错误时执行该过滤器。
 */
@Slf4j
public class RateLimitZuulFilter extends ZuulFilter {

    @Autowired
    private SystemPublicMetrics systemPublicMetrics;

    Map<String, RateLimiter> map = Maps.newConcurrentMap();

    /**
     * 设置过滤器类型
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }
    /**
     * 参考该类PreDecorationFilter
     *
     * @return
     * @Deprecated public static final int FILTER_ORDER = PRE_DECORATION_FILTER_ORDER为5;
     * 所以这里的限流级别必须大于5,否则RequestContext.getCurrentContext()里拿不到serviceId等数据。
     * 设置过滤级别,数值越小,级别越高
     */
    @Override
    public int filterOrder() {
        return 6;
    }

    /**
     * 是否实行该过滤器,true代表执行
     * 这里可以设置限流的开关
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
       //限流开关
        systemPublicMetrics.


        return false;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletResponse response = ctx.getResponse();
        String mapKey = null;
        //对于service格式的路由，走RibbonRoutingFilter路由微服务的serviceId
        String serviceID = (String) ctx.get("your serviceID");
        if (StringUtils.isNotBlank(serviceID)) {
            mapKey = serviceID;
            map.putIfAbsent(mapKey, RateLimiter.create(1000.0));
        }
        //如果不是RibbonRoutingFilter路由,默认是URL格式
        else {
            //对于URl格式的,走的是simpleHostRoutingFilter
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            HttpServletResponse response = ctx.getResponse();
            String mapKey = null;
            //对于service格式的路由，走RibbonRoutingFilter路由微服务的serviceId
            String serviceID = (String) ctx.get("your serviceID");
            if (StringUtils.isNotBlank(serviceID)) {
                mapKey = serviceID;
                map.putIfAbsent(mapKey, RateLimiter.create(1000.0));
            }
            //如果不是RibbonRoutingFilter路由,默认是URL格式
            else {
                //对于URl格式的,走的是simpleHostRoutingFilter
                URL routeHost = ctx.getRouteHost();
                if (ObjectUtil.isNotNull(routeHost)) {
                    mapKey = routeHost.toString();
                    map.putIfAbsent(mapKey, RateLimiter.create(2000.0));
                }

            }
            RateLimiter rateLimiter = map.get(mapKey);
            //没有获取到令牌
            if (!rateLimiter.tryAcquire()) {
                HttpStatus tooManyRequests = HttpStatus.TOO_MANY_REQUESTS;
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.setStatus(tooManyRequests.value());
                //不向微服务转发
                ctx.setSendZuulResponse(false);
                throw new ZuulException(tooManyRequests.getReasonPhrase(), tooManyRequests.value(), tooManyRequests.getReasonPhrase());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
