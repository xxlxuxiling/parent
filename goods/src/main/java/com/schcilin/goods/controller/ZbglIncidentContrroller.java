package com.schcilin.goods.controller;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.schcilin.goods.service.ZbglIncidentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 重保零报告事件|sjxf|zbgl 前端控制器
 * </p>
 *
 * @author garve
 * @since 2018-12-26
 */
@RestController
@RequestMapping("/int")
@Slf4j
public class ZbglIncidentContrroller {

    @Autowired
    private ZbglIncidentService zbglIncidentService;
    public String processHystrix_Get() {
        return "微服务不可用";
    }
    // //一旦调用服务方法失败抛出“错误信息后，自动调用用@HystrixCommand标注好的fallbackMethod调用类中的指定方法”
    @HystrixCommand(fallbackMethod = "processHystrix_Get")
    @GetMapping(value = "/demo", produces = "application/json")
    public String demo() {

        log.info("大厦到拉萨回到拉萨好点了");
        return "";
    }

}

