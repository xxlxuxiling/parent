package com.schcilin.redismodule;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * @Auther:
 * @Date: 2018/11/21 21:00
 * @Description:
 */
public class

BloomFilterTest {
    private static final int capacity = 1000000;
    private static final int key = 999998;
    private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), capacity);

    static {
        for (int i = 0; i < capacity; i++) {
            bloomFilter.put(i);

        }
    }

    public static void main(String[] args) {
        //返回计算机最精确的时间，单位微妙
        long start = System.nanoTime();
        if (bloomFilter.mightContain(key)) {
            System.out.println("成功过滤到的值为=" + key);
        }
        long end = System.nanoTime();
        System.out.println(end - start);
        int sum = 0;
        for (int i = 20000 + capacity; i < 300000 + capacity; i++) {
            if (bloomFilter.mightContain(i)) {
                sum += 1;
            }
        }
        System.out.println("错误率为=" + sum);

    }
}
