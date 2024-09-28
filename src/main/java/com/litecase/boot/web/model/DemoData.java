package com.litecase.boot.web.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class DemoData {
    private String name;
    private Integer age;
    private String gender;
}

//@Getter
//@Setter
//@EqualsAndHashCode
//public class DemoData {
//    private String string;
//    private Date date;
//    private Double doubleData;
//}
