package com.litecase.boot.web.util;

public class FileUtil {
    public static String getPath() {
//        String path = FileUtil.class.getClassLoader().getResource("").getPath();
        String path = FileUtil.class.getResource("/").getPath();

        return path;
    }
}
