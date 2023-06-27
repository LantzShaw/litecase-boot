package com.litecase.boot.web.controller;

import com.litecase.boot.web.model.entity.User;
import com.litecase.boot.web.service.UserService;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;


    @GetMapping("/update/{id}/{name}") // http://localhost:9000/user/update/12/132
    public void updateUser(@PathVariable("id") Integer id, @PathVariable("name") String name) {
        System.out.println("id: " + id + "name" + name);

//        userService.save();
    }

    @DeleteMapping("/delete/{id}")
    public void removeUserById(@PathVariable("id") Integer id) {

    }

    @ResponseBody // NOTE: 这个应该可有可无
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {

        System.out.println(file.getContentType());


        return "---file name:" + file.getOriginalFilename() +
                "---file type:" + file.getContentType() +
                "---file size:" + file.getSize();
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
