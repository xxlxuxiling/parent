package com.schcilin.tcctransactions.web.controller.unify.api;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * Author: schicilin
 * Date: 2019/4/12 15:55
 * Content:
 */
public class Test {
    @Getter
    @Setter
    public static class A{
        private String id;
        private String name;
    }
    @Getter
    @Setter
    public static class B{
        private String id;
        private String name;
        private String aid;
    }
    public static void main(String[] args) {
        List<String> aList = Lists.newArrayList("222");
        aList.stream().forEach(s->System.out.println(s));

    }
}
