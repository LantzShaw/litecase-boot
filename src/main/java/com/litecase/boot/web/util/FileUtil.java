package com.litecase.boot.web.util;

public class FileUtil {
    public static String getPath() {
        String path = FileUtil.class.getClassLoader().getResource("").getPath();

        return path;
    }

//    public static String getPath() {
//        return FileUtil.class.getResource("/").getPath();
//    }
}
