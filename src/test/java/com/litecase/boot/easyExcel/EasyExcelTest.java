package com.litecase.boot.easyExcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.fastjson2.JSON;
import com.litecase.boot.web.model.DemoData;
import com.litecase.boot.web.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;

@Slf4j
public class EasyExcelTest {
    @Test
    public void simpleRead() {
//        String fileName = FileUtil.getPath() + "static" + File.separator + "excel" + File.separator + "demo.xlsx";
//        String fileName = FileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        String fileName = FileUtil.getPath() + File.separator + "demo.xlsx";
//        String fileName = "/Users/Documents/" + "demo" + File.separator + "demo.xlsx";

//        InputStream inputStream = FileUtil.class.getResourceAsStream("static/excel/demo.xlsx");

        System.out.println("fileName = " + fileName);
//        System.out.println("inputStream = " + inputStream);

        EasyExcel.read(fileName, DemoData.class, new PageReadListener<DemoData>(demoData -> {
            for (DemoData data : demoData) {
                log.info("读取数据: {}", JSON.toJSONString(data));
            }
        })).sheet().doRead();
    }
}
