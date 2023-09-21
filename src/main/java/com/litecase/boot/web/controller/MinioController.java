//package com.litecase.boot.web.controller;
//
//import com.litecase.boot.web.config.MinioClientConfig;
//import com.litecase.boot.web.util.MinioUtil;
//import io.minio.MinioClient;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/minio")
//public class MinioController extends BaseController {
//    MinioUtil minioUtil = new MinioUtil();
//
//    /**
//     * 上传文件
//     * @param file
//     * @return
//     */
//    @PostMapping("/uploadFile")
//    public AjaxResult uploadFile(@RequestBody MultipartFile file) {
//        MinioClient minioClient = MinioClientConfig.getMinioClient();
//        if (minioClient == null) {
//            return AjaxResult.error("连接MinIO服务器失败", null);
//        }
//        ResultEntity<Map<String, Object>> result = minioUtil.minioUpload(file, "", "data-enpower");
//        if (result.getCode() == 0) {
//            return AjaxResult.success("上传成功");
//        } else {
//            return AjaxResult.error("上传错误！");
//        }
//    }
//
//    /**
//     * 获取文件预览地址
//     * @param fileName
//     * @return
//     */
//    @RequestMapping("/getRedFile")
//    public AjaxResult getRedFile(@RequestBody String fileName) {
//        MinioClient minioClient = MinioClientConfig.getMinioClient();
//        if (minioClient == null) {
//            return AjaxResult.error("连接MinIO服务器失败", null);
//        }
//        String url = minioUtil.getPreviewFileUrl("data-enpower",fileName);
//        return AjaxResult.success(url);
//    }
//
//    /**
//     * 下载文件
//     * @param fileName
//     * @param response
//     * @return
//     */
//    @RequestMapping("/downloadFile")
//    public String downloadFile(@RequestParam String fileName, HttpServletResponse response) {
//        MinioClient minioClient = MinioClientConfig.getMinioClient();
//        if (minioClient == null) {
//            return "连接MinIO服务器失败";
//        }
//        return minioUtil.downloadFile("data-enpower",fileName,response) != null ? "下载成功" : "下载失败";
//    }
//
//    /**
//     * 删除文件
//     *
//     * @param fileName 文件路径
//     * @return
//     */
//    @PostMapping("/deleteFile")
//    public String deleteFile(String fileName) {
//        MinioClient minioClient = MinioClientConfig.getMinioClient();
//        if (minioClient == null) {
//            return "连接MinIO服务器失败";
//        }
//        boolean flag = minioUtil.deleteFile("data-enpower",fileName);
//        return flag == true ? "删除成功" : "删除失败";
//    }
//
//}
