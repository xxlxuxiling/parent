package com.schcilin.common_server.util.consistentHash.util;

/**
 * @Author: schcilin
 * @Date: 2019/4/14 15:15
 * @Version 1.0
 * @des: 一致性hash算法工具类
 */
public class FNV1_32_HASH {

    public static int getHash(String str)
     {
                final int p = 16777619;
                 int hash = (int)2166136261L;
                 for (int i = 0; i < str.length(); i++)
                         hash = (hash ^ str.charAt(i)) * p;
                 hash += hash << 13;
                 hash ^= hash >> 7;
                hash += hash << 3;
                hash ^= hash >> 17;
                hash += hash << 5;

                // 如果算出来的值为负数则取其绝对值
                if (hash < 0)
                         hash = Math.abs(hash);
                 return hash;
            }
}
