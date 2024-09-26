package com.litecase.boot.demo;

import org.junit.jupiter.api.Test;

import java.io.InputStream;

public class PathTests {
    @Test
    void testGetPath() {
        // String path = EasyExcelTests.class.getResource("/").getPath();
        // String path = this.getClass().getResource("/").getPath();
        // 类路径中的资源路径是相对于src/main/resources或src/test/resources目录的，而不是相对于项目根目录的。

        // 获取路径
        String path = this.getClass().getClassLoader().getResource("test.html").getPath();

        // 获取输入流
        InputStream path2 = this.getClass().getClassLoader().getResourceAsStream("test.html");

        // 这里要带斜杠 /
        String path3 = this.getClass().getResource("/test.html").getPath();

        System.out.println("path1 = " + path);
        System.out.println("path2 = " + path2);
        System.out.println("path3 = " + path3);
    }
}
