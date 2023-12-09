package com.litecase.boot.web.constant.enums;

public enum GenderEnum {
    FEMALE("2", "女"),
    MALE("1", "男");

    /**
     * 性别编码
     */
    private String genderCode;

    /**
     * 性别名称
     */
    private String genderName;

    GenderEnum(String genderCode, String genderName) {
        this.genderName = genderName;
        this.genderCode = genderCode;
    }


    public String getGenderCode() {
        return genderCode;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderCode(String genderCode) {
        this.genderCode = genderCode;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    /**
     * 根据code获取枚举 - 用于switch-case中
     *
     * @param genderCode
     * @return
     */
    public static GenderEnum getGenderEnumByCode(String genderCode) {
        for (GenderEnum genderEnum : GenderEnum.values()) {
            if (genderEnum.getGenderCode().equals(genderCode)) {
                return genderEnum;
            }
        }
        return null;
    }

    /**
     * 根据name获取code
     *
     * @param genderName
     * @return
     */
    public static String getGenderCodeByName(String genderName) {
        String genderCode = "genderCode is null";

        for (GenderEnum genderEnum : GenderEnum.values()) {
            if (genderEnum.getGenderName().equals(genderName)) {
                genderCode = genderEnum.getGenderCode();
                break;
            }
        }

        return genderCode;
    }

    /**
     * 根据code获取name
     *
     * @param genderCode
     * @return
     */
    public static String getGenderNameByCode(String genderCode) {
        String genderName = "genderName is null";

        for (GenderEnum genderEnum : GenderEnum.values()) {
            if (genderEnum.getGenderCode().equals(genderCode)) {
                genderName = genderEnum.getGenderName();
                break;
            }
        }

        return  genderName;
    }

    @Override
    public String toString() {
        return "GenderEnum{" +
                "genderCode='" + genderCode + '\'' +
                ", genderName='" + genderName + '\'' +
                '}';
    }
}


// NOTE: 枚举应用
//public class EnumMain {
//
//    public static void main(String[] args){
//        //循环带自定义方法的枚举
//        SexEnum[] sexEnums = SexEnum.values();
//        for (SexEnum sexEnum : sexEnums) {
//            System.out.println("sexCode:"+sexEnum.getSexCode());
//            System.out.println("sexName:"+sexEnum.getSexName());
//            System.out.println("sexCode:sexName="+sexEnum.toString());
//        }
//
//        //根据sexCode获取sexName
//        String sexName = SexEnum.getSexNameByCode("women");
//        System.out.println("根据sexCode获取sexName：" + sexName);
//
//        //根据sexName获取sexCode
//        String sexCode = SexEnum.getSexCodeByName("男");
//        System.out.println("根据sexName获取sexCode：" + sexCode);
//
//        //通过传入的sexCode使用switch
//        testSexEnumSwitch("women");
//
//    }
//    /**
//     * 实际项目中，基本上都是传sexCode的，所以这里也根据传入的sexCode，使用switch方法
//     * @param sexCode
//     */
//    private static void testSexEnumSwitch(String sexCode){
//        //自定义getEnumByCode方法，通过sexCode获取SexEnum
//        SexEnum sexEnum = SexEnum.getEnumByCode(sexCode);
//        switch (sexEnum){
//            case MAN:
//                System.out.println(sexEnum.toString());
//                break;
//            case WOMEN:
//                System.out.println(sexEnum.toString());
//                break;
//            default:
//                System.out.println("other");
//        }
//    }
//}

//public enum SexEnum {
//    MAN("man","男"),
//    WOMEN("women","女");
//
//    private String sexCode;
//
//    private String sexName;
//
//    /**
//     * 自定义构造函数，以完成枚举对sexCode、sexName赋值
//     * @param sexCode
//     * @param sexName
//     */
//    SexEnum(String sexCode,String sexName){
//        this.sexCode = sexCode;
//        this.sexName = sexName;
//    }
//
//    /**
//     * 获取sexCode
//     * @return
//     */
//    public String getSexCode() {
//        return sexCode;
//    }
//
//    /**
//     * 获取sexName
//     * @return
//     */
//    public String getSexName() {
//        return sexName;
//    }
//
//    /**
//     * 项目中经常会根据code，转换成对应的name
//     * 所以这里自定义方法，根据sexCode获取sexName
//     * 通过循环enum来实现
//     * @param sexCode
//     * @return
//     */
//    public static String getSexNameByCode(String sexCode){
//        String sexName = "sexCode不存在";
//        SexEnum[] sexEnums = SexEnum.values();
//        for (SexEnum sexEnum : sexEnums) {
//            if(sexEnum.getSexCode().equals(sexCode)){
//                sexName =  sexEnum.getSexName();
//                break;
//            }
//        }
//        return sexName;
//    }
//
//    /**
//     * 项目中也有根据name，转换成对应的code
//     * 所以这里自定义方法，根据sexName获取sexCode
//     * 通过循环enum来实现
//     * @param sexName
//     * @return
//     */
//    public static String getSexCodeByName(String sexName){
//        String sexCode = "sexName不存在";
//        SexEnum[] sexEnums = SexEnum.values();
//        for (SexEnum sexEnum : sexEnums) {
//            if(sexEnum.getSexName().equals(sexName)){
//                sexCode =  sexEnum.getSexCode();
//                break;
//            }
//        }
//        return sexCode;
//    }
//
//    /**
//     * 根据sexCode获取SexEnum，在switch中使用
//     * 通过循环enum来实现
//     * @param sexCode
//     * @return
//     */
//    public static SexEnum getEnumByCode(String sexCode){
//        SexEnum[] sexEnums = SexEnum.values();
//        for (SexEnum sexEnum : sexEnums) {
//            if(sexEnum.getSexCode().equals(sexCode)){
//                return sexEnum;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 重写toString方法
//     * @return
//     */
//    @Override
//    public String toString() {
//        return this.sexCode + ":" + this.sexName;
//    }
//}