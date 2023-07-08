package com.litecase.boot.web.config;

// SpringBoot MultipartFile设置指定文件上传大小
// 方法1、application.properties配置mutipart.max-file-size或者max-request-size（yml一样，只是格式有变化）
// 方法2、编写配置类,并通过@Bean标签来加入到IOC容器中管理，注意当前类上需要加注解@Configuration，不然扫不到就不会起作用了

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;


// @Configuration
public class FileUploadConfiuration {
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件大小200mb
        factory.setMaxFileSize(DataSize.ofMegabytes(200L));
        //设置总上传数据大小1GB
        factory.setMaxRequestSize(DataSize.ofGigabytes(1L));

        return factory.createMultipartConfig();
    }
}
