package com.schcilin.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: schcilin
 * @Date: 2019/6/2 14:32
 * @Version 1.0
 * @des: 项目自定义网关filter
 */

/**
 * Zuul大部分功能都是通过过滤器来实现的。Zuul中定义了四种标准过滤器类型，这些过滤器类型对应于请求的典型生命周期。
 *
 * (1) PRE：这种过滤器在请求被路由之前调用。我们可利用这种过滤器实现身份验证、在集群中选择请求的微服务、记录调试信息等。
 *
 * (2) ROUTING：这种过滤器将请求路由到微服务。这种过滤器用于构建发送给微服务的请求，并使用Apache HttpClient或Netfilx Ribbon请求微服务。
 *
 * (3) POST：这种过滤器在路由到微服务以后执行。这种过滤器可用来为响应添加标准的HTTP Header、收集统计信息和指标、将响应从微服务发送给客户端等。
 *
 * (4) ERROR：在其他阶段发生错误时执行该过滤器。
 */
@Slf4j
public class ProjectZuulFilter extends ZuulFilter {
    /**
     * 设置过滤器类型
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**

     * 设置过滤级别,数值越小,级别越高
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否实行该过滤器,true代表执行
     * 这里可以设置限流的开关
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());
        String access_token = request.getParameter("access_token");
        if (StringUtils.isBlank(access_token)) {
            log.error("access_token is empty");
            //过滤请求是否向下级服务转发
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            //这里可以数据text/html或者application/json
            ctx.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        }
        return null;
    }
}
