package com.schcilin.mqtransation.provider;

import com.schcilin.mqtransation.anno.MQTransationMessageAnno;
import com.schcilin.mqtransation.constant.MQConstant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: schcilin
 * @Date: 2019/5/28 18:17
 * @Content:
 */

@RestController
public class MQTransationMessageController {
    @GetMapping(value = "test")
    @MQTransationMessageAnno(exchange = MQConstant.BIZ_EXCHANGE,bizName = MQConstant.BIZ_QUEUE,bindingKey = MQConstant.BIZ_ROUTINGKEY,dbCoordinator = MQConstant.RedisDBCoordinator)
    public void test(){
        System.out.println("哈哈哈");
    }
}
