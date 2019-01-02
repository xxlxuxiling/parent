package com.schcilin.goods.service.impl;

import com.schcilin.goods.entity.ZbglIncident;
import com.schcilin.goods.mapper.ZbglIncidentMapper;
import com.schcilin.goods.service.ZbglIncidentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 重保零报告事件|sjxf|zbgl 服务实现类
 * </p>
 *
 * @author garve
 * @since 2018-12-26
 */
@Service
public class ZbglIncidentServiceImpl extends ServiceImpl<ZbglIncidentMapper, ZbglIncident> implements ZbglIncidentService {
}
