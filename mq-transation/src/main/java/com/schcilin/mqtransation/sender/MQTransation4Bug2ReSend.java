package com.schcilin.mqtransation.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.schcilin.mqtransation.constant.MQConstant;
import com.schcilin.mqtransation.coordinator.DBCoordinator;
import com.schcilin.mqtransation.pojo.RabbitMetaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: schcilin
 * @Date: 2019/5/24 11:32
 * @Content: MQ出现异常时，消息重发
 */

@Component
@Slf4j
public class MQTransation4Bug2ReSend {
    @Autowired
    private DBCoordinator dbCoordinator;
    @Autowired
    private MQTransationSender mqTransationSender;

    public void reSendMsg4Bug() throws Exception {
        List<RabbitMetaMessage> msgReady = this.dbCoordinator.getMsgReady();
        msgReady.stream().forEach(msg -> {
            Long resendValue = dbCoordinator.getResendValue(MQConstant.MQ_RESEND_COUNTER, msg.getMessageId());
            if (MQConstant.MAX_RETRY_COUNT < resendValue) {
                //发送警告信息
            }
            try {
                mqTransationSender.send(msg);
                dbCoordinator.incrResendKey(MQConstant.MQ_RESEND_COUNTER,msg.getMessageId());
            } catch (Exception e) {
                e.printStackTrace();
                log.error("从redis取出待发送消息向rabbitMQ重发消息失败");
            }

        });

    }
}
