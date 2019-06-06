package com.schcilin.mqtransation.sender;

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

    public void reSendMsg4Bug(String msgId) throws Exception {
        RabbitMetaMessage msgReady = this.dbCoordinator.getMetaMsg(msgId);
        Long resendValue = dbCoordinator.getResendValue(MQConstant.MQ_PROVIDER_RETRY_COUNT_KEY, msgReady.getMessageId());
        //发送警告信息,同时向数据库存储信息，人工干预，好苦逼
        if (MQConstant.MAX_RETRY_COUNT < resendValue) {
            try {
                //TODO 只发送警告信息,比如说短信通知
                log.error("发送警告消息，告警{}次", resendValue);
                //同时删除count_key
                this.dbCoordinator.deleteResendKey(MQConstant.MQ_PROVIDER_RETRY_COUNT_KEY, msgId);
                log.error("从redis取出ready消息{}，同时删除重发的统计{}次", msgId, resendValue);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {
                this.mqTransationSender.send(msgReady);

            } catch (Exception e) {
                e.printStackTrace();
                log.error("从redis取出ready状态消息向rabbitMQ重发消息失败,重发{}次", resendValue);
                if (MQConstant.MAX_RETRY_COUNT >= resendValue) {
                    reSendMsg4Bug(msgId);
                } else {
                    //抛到下一级处理
                    throw e;
                }
            }
        }
    }


    public List<RabbitMetaMessage> reSendMsg4DataBase() throws Exception {
        //TODO 查询出redis中ready状态发送RabbitMQ失败得信息，处理是向rabbitMQ再次发送消息，此时还有可能遇到bug
        return this.dbCoordinator.getMsgReady();


    }
}
