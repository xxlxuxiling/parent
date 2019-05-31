package com.schcilin.mqtransation.artificial;

import com.google.common.collect.Lists;
import com.schcilin.mqtransation.constant.MQConstant;
import com.schcilin.mqtransation.coordinator.DBCoordinator;
import com.schcilin.mqtransation.pojo.RabbitMetaMessage;
import com.schcilin.mqtransation.sender.MQTransation4Bug2ReSend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: schcilin
 * @Date: 2019/5/31 17:29
 * @Content: 人工干预重发redis中ready的信息
 */

@RestController
@Slf4j
public class MQTransation4Bug2ReSendController {
    @Autowired
    private MQTransation4Bug2ReSend mqTransation4Bug2ReSend;
    @Autowired
    private DBCoordinator dbCoordinator;

    /**
     * 待发送信息列表
     *
     * @return
     */
    @GetMapping("/dbList")
    public List<RabbitMetaMessage> getReSendMsg4DataBaseList() {
        List<RabbitMetaMessage> dbList = Lists.newArrayList();
        try {
            dbList = this.mqTransation4Bug2ReSend.reSendMsg4DataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbList;
    }

    /**
     * 人工干预重新发送消息
     *
     * @param rabbitMetaMessage
     */
    @PostMapping("/reSend")
    public void reSendMsg4DataBaseList(RabbitMetaMessage rabbitMetaMessage) {
        try {
            Long increment = dbCoordinator.incrResendKey(MQConstant.MQ_PROVIDER_RETRY_COUNT_KEY, rabbitMetaMessage.getMessageId());
            log.info("人工干预成功生产消息ID->{}，同时添加redis中的统计{}次", rabbitMetaMessage.getMessageId(), increment);
            mqTransation4Bug2ReSend.reSendMsg4Bug(rabbitMetaMessage.getMessageId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
