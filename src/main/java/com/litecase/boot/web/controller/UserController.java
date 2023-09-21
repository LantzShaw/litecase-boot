package com.litecase.boot.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.litecase.boot.web.common.R;
import com.litecase.boot.web.model.entity.User;
import com.litecase.boot.web.service.UserService;
import com.litecase.boot.web.util.SMSUtil;
import com.litecase.boot.web.util.ValidateCodeUtil;
import jakarta.servlet.http.HttpSession;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    // 获取application.yml文件配置 并设置默认值（application.yml文件的配置优先级更高），默认值可有可无
    @Value("${spring.servlet.multipart.max-file-size:110KB}")
    private String FILE_LIMIT_SIZE;

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {

        String phoneNumber = map.get("phoneNumber").toString();

        String code = map.get("code").toString();

        String codeInSession = (String) session.getAttribute(phoneNumber);

        if(codeInSession != null && codeInSession.equals(code)) {
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(User::getPhoneNumber, phoneNumber);

            User user = userService.getOne(lambdaQueryWrapper);

            if(user == null) {
                user = new User();
                user.setPhoneNumber(phoneNumber);
                user.setStatus(1);
                userService.save(user);
            }

            return R.success(user);
        }

       return R.error("登录失败");
    }

    @PostMapping("/send-message")
    public R<String> sendMessage(@RequestBody  User user, HttpSession session) {
        // 获取手机验证码
        String phone = user.getPhoneNumber();

        if(StringUtils.isNoneEmpty(user.getPhoneNumber())) {
            // 生成随机验证码
            String code = ValidateCodeUtil.generateValidateCode(4).toString();

            // 调用阿里云API完成短信发送
            SMSUtil.sendMessage("litecase", "", phone, code);

            session.setAttribute(phone, code);

            return R.success("手机验证码短信发送成功");
        }

        return R.error("短信发送失败");

    }


    @GetMapping("/update/{id}/{name}") // http://localhost:9000/user/update/12/132
    public void updateUser(@PathVariable("id") Integer id, @PathVariable("name") String name) {
        System.out.println("id: " + id + "name" + name);

        // userService.save();
    }

    @DeleteMapping("/delete/{id}")
    public void removeUserById(@PathVariable("id") Integer id) {

    }

    /**
     * 单文件上传
     *
     * @param file
     * @return String
     */
    // NOTE: 这个注解应该可有可无
    // @ResponseBody
    @PostMapping(value = "/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }

        // 文件保存目录
        String savePath = "D:/uploads/";
        // 源文件名
        String originalFilename = file.getOriginalFilename();
        // 扩展名
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 唯一文件名
        String uniqueSuffix = UUID.randomUUID() + ext;
        // 构建上传路径
        File dest = new File(savePath + uniqueSuffix);

        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }

        try {
            file.transferTo(dest);
            return "上传成功!";
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "上传失败";
    }

    /**
     * 多文件上传
     *
     * @param files
     * @return String
     */
    @PostMapping("/uploads")
    public String uploadFiles(@RequestParam("files") MultipartFile[] files) {
        System.out.println(FILE_LIMIT_SIZE);
        // 获取项目运行的绝对路径
        // final String savedPath = System.getProperty("user.dir");
        // final String savedPath = "D:\\uploads\\"; 也可以 "D:/uploads/"
        final String savedPath = "D:\\uploads\\";

        if (files.length == 0) return "上传的文件不能为空，请重新上传！";

        for (MultipartFile file : files) {
            System.out.println("文件大小" + file.getSize());

            // TODO: 如何全局处理文件上传异常，例如：文件大小、文件类型、文件数量, 并返回异常信息
            // 参考文章: https://blog.csdn.net/daweozai/article/details/103575718
            // if (file.getSize() > 1) return "上传文件太大，无法上传！";


            String originalFilename = file.getOriginalFilename();
            String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            // FIXME: 这里为什么单引号也可以，但是没有得到想要的数据
            String uniqueSuffix = new Date().getTime() + "-" + Math.round(Math.random() * 1e9) + ext;

            File dest = new File(savedPath);

            if (!dest.exists()) {
                dest.mkdirs();
            }

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(savedPath + uniqueSuffix);
                fileOutputStream.write(file.getBytes());
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e) {
                System.out.println("----------" + e.getMessage());

                e.printStackTrace();
            }
        }

        return "上传成功!";
    }

    /**
     * 文件下载
     *
     * @param id
     * @return String
     */
    @GetMapping("/download/{id}")
    public String downloadFile(@PathVariable("id") String id) {


        return "下载";

    }

    //    @ResponseBody
//    @PostMapping("/upload")
//    public String uploadFile(@RequestBody MultipartFile file) {
//
//        System.out.println(file.getContentType());
//
//
//        return "---file name:" + file.getOriginalFilename() +
//                "---file type:" + file.getContentType() +
//                "---file size:" + file.getSize();
//
//    }

    @GetMapping("/user2") // http://localhost:9000/user/user2?name=123&age=12
    public void getUserList(@RequestParam("name") String name, @RequestParam("age") Integer age) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        System.out.println(name + " " + age);

        stopWatch.stop();

        System.out.println("最终耗时" + stopWatch.getTotalTimeMillis());
    }

//    /**
//     * 使用set去重
//     *
//     * @param testList
//     */
//    private static void useSetDistinct(List<String> testList) {
//        System.out.println("HashSet.add 开始去重，条数：" + testList.size());
//        List<String> testListDistinctResult = new ArrayList<>(new HashSet(testList));
//        System.out.println("HashSet.add 去重完毕，条数：" + testListDistinctResult.size());
//    }

    @PostMapping("/create")
    public void createUser(@RequestBody User user, @RequestHeader String token, @RequestHeader String uuid) {
//        默认是application/json
//        {
//            id: "123",
//            name: "Lantz",
//            age: 12
//        }

//       Headers中传参
//      token: asdasdfasd
//      uuid: 123j4oi12jo43j

    }

    @GetMapping("/list")
    public String getUsers() {
        ArrayList<String> userList = new ArrayList<String>();

        String[] tempArr = {"Lantz"};
// NOTE: 推荐使用这种方式
        List<Integer> numberList = new ArrayList<Integer>();
        numberList.add(12);

//        使用上面的定义方式
        ArrayList<Integer> numberList_2 = new ArrayList<>();

        for (int i = 0; i < numberList_2.size(); i++) {
            System.out.println(numberList_2.get(i));
        }

        for (int num : numberList_2) {
            System.out.println(num);
        }

        for (String user : userList) {
            System.out.println(user);
        }

        List<Map<String, Object>> person = new ArrayList<Map<String, Object>>();

        Map<String, String> map = new HashMap<String, String>();

        map.put("name", "Lantz");
        map.put("gender", "male");

        Map<String, Object> map1 = new HashMap<String, Object>();

        map1.put("name", "Janne");

        map1.put("gender", "female");
        map1.put("age", 19);

        map1.get("age");

//        person.add(map);
        person.add(map1);

//        List<String> Arr = ["Lantz", "Janne"];


        userList.add("Lantz");

        return "Lantz Shaw";
    }
}
