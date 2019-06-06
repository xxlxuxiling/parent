package com.schcilin.basicInfo.controller;


import com.schcilin.basicInfo.entity.TUser;
import com.schcilin.basicInfo.service.TUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author schcilin
 * @since 2019-01-02
 */
@RestController("/baseUser")
public class TUserContrroller {
    @Autowired
    private TUserService tUserService;
    @GetMapping(value = "/add")
    public void  addUser() throws Exception{
        TUser tUser = new TUser();
        tUser.setId(String.valueOf(Math.random()));
        tUser.setUserName("你猜猜");
        tUserService.save(tUser);
    }

}

