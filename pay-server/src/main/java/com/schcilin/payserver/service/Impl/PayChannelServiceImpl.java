package com.schcilin.payserver.service.Impl;

import com.schcilin.payserver.entity.PayChannel;
import com.schcilin.payserver.mapper.PayChannelMapper;
import com.schcilin.payserver.service.PayChannelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author schcilin
 * @since 2019-06-23
 */
@Service
public class PayChannelServiceImpl extends ServiceImpl<PayChannelMapper, PayChannel> implements PayChannelService {
    @Override
    public void test() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Reflections reflections = new Reflections("com.schcilin.payserver.service.Impl");
        Set<Class<?>> annotatedWith = reflections.getTypesAnnotatedWith(Service.class);
        for(Class clzz:annotatedWith){
            String canonicalName = clzz.getCanonicalName();
            Class<?> aClass = Class.forName(canonicalName);
            PayChannelServiceImpl o = (PayChannelServiceImpl) aClass.newInstance();
            Field[] declaredFields = o.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
            }
            Class<?> superclass = this.getClass().getSuperclass();
            Field[] supFields = superclass.getDeclaredFields();
            for (Field supField : supFields) {
                supField.setAccessible(true);
                supField.set(supField.getName(),supField.getType());
            }


            System.out.println(o.baseMapper);
        }




    }
}
