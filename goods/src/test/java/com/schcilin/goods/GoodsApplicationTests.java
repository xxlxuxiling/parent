package com.schcilin.goods;

import com.schcilin.goods.entity.TGoods;
import com.schcilin.goods.service.TGoodsService;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsApplicationTests {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Autowired
    private TGoodsService tGoodsService;


    @Test
    public void testDemo() {
        int consumer = 1;
        final CountDownLatch downLatch = new CountDownLatch(consumer);
        for (int i = 0; i <consumer ; i++) {
           new Thread(()->{
               TGoods tGoods = new TGoods();
               tGoods.setId(String.valueOf(Math.random()));
               tGoods.setGoodName("ssss");
               tGoodsService.insertModel(tGoods,"1");
               downLatch.countDown();
           }).start();
        }
        try {
            downLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void dataSourceEncryptor() {
        String root = stringEncryptor.encrypt("root");
        System.out.println(root);
    }
}

