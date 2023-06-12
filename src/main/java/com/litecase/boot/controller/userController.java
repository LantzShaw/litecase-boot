package com.litecase.boot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/user")
public class userController {
    @GetMapping("/list")
    public String getUsers() {
        ArrayList<String> userList = new ArrayList<String>();

        String[] tempArr = {"Lantz"};

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
