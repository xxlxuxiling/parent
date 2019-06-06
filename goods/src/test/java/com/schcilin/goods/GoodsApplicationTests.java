package com.schcilin.goods;

import com.schcilin.goods.entity.TGoods;
import com.schcilin.goods.service.TGoodsService;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsApplicationTests {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Autowired
    private TGoodsService tGoodsService;


    @Test
    public void testDemo() {
        TGoods tGoods = new TGoods();
        tGoods.setId(String.valueOf(Math.random()));
        tGoods.setGoodName("ssss");
        tGoodsService.insertModel(tGoods);

    }

    @Test
    public void dataSourceEncryptor() {
        String root = stringEncryptor.encrypt("root");
        System.out.println(root);
    }
}

