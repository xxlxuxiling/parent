package com.schcilin.zuul.authenticator;

import org.springframework.http.HttpRequest;

/**
 * @Author: schcilin
 * @Date: 2019/5/20 14:55
 * @Content: 异步线程获取
 */

public class AsyncRequestHolder {
    private static InheritableThreadLocal<HttpRequest> holder = new InheritableThreadLocal<>();

    public static void set(HttpRequest request) {
        holder.set(request);
    }

    public static HttpRequest get() {
        return holder.get();
    }

    public static void clear() {
        holder.remove();
    }
}
