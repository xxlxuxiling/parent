package com.schcilin.common_server.util.consistentHash.util;

import java.security.MessageDigest;

/**
 * @Author: schcilin
 * @Date: 2019/7/31 9:46
 * @Content: MD5加密工具
 */


public class MD5 {
    public static String encode(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            str = buf.toString();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return str;
    }
}
