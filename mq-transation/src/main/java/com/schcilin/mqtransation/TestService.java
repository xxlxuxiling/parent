package com.schcilin.mqtransation;/**
 * @Author: schcilin
 * @Date: 2019/5/28 19:23
 * @Content:
 */

import com.schcilin.mqtransation.anno.MQTransationMessageAnno;
import com.schcilin.mqtransation.constant.MQConstant;
import org.springframework.stereotype.Service;

/**
 * @version v1.0
 * @Author schcilin
 * @Description //TODO $
 * @Date $ $
 **/
@Service
public class TestService implements Test {
    @Override
    @MQTransationMessageAnno(exchange =MQConstant.BIZ_EXCHANGE,bizName = MQConstant.BIZ_QUEUE,bindingKey = MQConstant.BIZ_ROUTINGKEY,dbCoordinator = MQConstant.RedisDBCoordinator)
    public void test(){
        System.out.println("哈哈哈");
    }
    @MQTransationMessageAnno(exchange ="xxl.exchange",bizName ="xxl.queue",bindingKey = "xxl.rout",dbCoordinator = MQConstant.RedisDBCoordinator)
    @Override
    public void testXxl() {
        System.out.println("xxl");
    }
}
