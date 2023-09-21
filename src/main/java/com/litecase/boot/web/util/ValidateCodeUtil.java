package com.litecase.boot.web.util;

import java.util.Random;

/**
 * 随机生成验证码工具类
 */
public class ValidateCodeUtil {
    public static Integer generateValidateCode(int length) {
        Integer code = null;

        if(length == 4) {
            code = new Random().nextInt(9999);

            if (code < 1000) {
                code = code + 100;
            }
        } else if (length == 6) {
            code = new Random().nextInt(999999);

            if ((code < 10000)) {
                code = code + 100000;
            }
        } else {
            throw new RuntimeException("只能生成4位或者6位数字验证码");
        }

        return code;
    }

    /**
     * 随机生成指定长度的字符串验证码
     *
     * @param length
     * @return
     */
    public static String generateValidateCode4String(int length) {
        Random random = new Random();

        String hash = Integer.toHexString(random.nextInt());

        String captureStr = hash.substring(0, length);

        return captureStr;
    }
}
