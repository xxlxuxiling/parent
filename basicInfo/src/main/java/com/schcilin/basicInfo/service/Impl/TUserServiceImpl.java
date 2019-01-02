package com.schcilin.basicInfo.service.Impl;

import com.schcilin.basicInfo.entity.TUser;
import com.schcilin.basicInfo.mapper.TUserMapper;
import com.schcilin.basicInfo.service.TUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author schcilin
 * @since 2019-01-02
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements TUserService {

}
