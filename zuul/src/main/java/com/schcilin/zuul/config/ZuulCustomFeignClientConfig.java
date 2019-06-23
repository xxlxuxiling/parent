package com.schcilin.zuul.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @Author: schcilin
 * @Date: 2019/4/22 13:40
 * @Content: Feign配置, 使用FeignClient进行服务间调用，传递headers信息
 */
@Component
@Slf4j
public class ZuulCustomFeignClientConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String access_token = request.getParameter("access_token");
        Map<String, Collection<String>> queries = requestTemplate.queries();
        Map<String, Collection<String>> newMaP = Maps.newLinkedHashMap();
        newMaP.putAll(queries);
        Collection<String> param = Lists.newArrayList();
        param.add(access_token);
        newMaP.put("access_token", param);
        Class<? extends RequestTemplate> clz = requestTemplate.getClass();
        try {
            Field field = clz.getDeclaredField("queries");
            //必须设置可见，将private->public
            field.setAccessible(true);
            field.set(requestTemplate, newMaP);
            if (log.isDebugEnabled()) {
                log.debug("requestTemplate" + requestTemplate);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        requestTemplate.header("user_token", getHeaders(request).get("user_token"));
        requestTemplate.header("currentUserIp", request.getHeader("currentUserIp"));
        //请求源:feign
        requestTemplate.header("request_source", "feign");


    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                String value = request.getHeader(key);
                map.put(key, value);
            }
        }

        Map<String,Object> hashMap = new HashMap();
        return map;

    }

    /**
     * 记录feign请求的日志级别
     *
     * @return
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        //这里记录所有，根据实际情况选择合适的日志level
        return Logger.Level.FULL;
    }
}
