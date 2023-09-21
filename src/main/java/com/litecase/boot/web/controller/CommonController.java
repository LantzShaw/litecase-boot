package com.litecase.boot.web.controller;


import com.litecase.boot.web.common.R;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${litecase.path}")
    private  String basePath;

    @PostMapping("/upload")
    public R<String> uploadFile(MultipartFile file) {
        log.info("文件上传");

        String originalFilename = file.getOriginalFilename();

        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        final String uniqueFilename =UUID.randomUUID().toString() + suffix;

        File dir = new File(basePath + uniqueFilename);

        if(dir.isDirectory()) {
            dir.mkdirs();
        }

        try {
            file.transferTo(dir);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return R.success("文件上传成功!");
    }

    @GetMapping("/download")
    public void download(String fileName, HttpServletResponse response) {
        try {
            FileInputStream fileInputStream = new FileInputStream(basePath + fileName);

            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("application/octet-stream");

            int len = 0;

            byte[] bytes = new byte[1024];

            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }

            fileInputStream.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
