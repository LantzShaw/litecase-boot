# Litecase Boot

## Records

**数据类型**

```text
（1）基本数据类型，分为boolean、byte、int、char、long、short、double、float；
（2）引用数据类型 ，分为数组、类、接口。

每个基本数据类型提供了封装类

基本数据类型: boolean，char，byte，short，int，long，float，double
封装类类型：Boolean，Character，Byte，Short，Integer，Long，Float，Double


自动装箱/拆箱机制，使得二者可以相互转换
```

**为什么要用List list = new ArrayList(),而不用ArrayList alist = new ArrayList()呢?**

```text
问题就在于List接口有多个实现类，现在你用的是ArrayList，也许哪一天你需要换成其它的实现类，如LinkedList或者Vector等等，这时你只要改变这一行就行了： List list = new LinkedList(); 其它使用了list地方的代码根本不需要改动。

假设你开始用ArrayList alist = new ArrayList(), 这下你有的改了，特别是如果你使用了ArrayList实现类特有的方法和属性。

这样的好处是为了代码的可维护性，可复用性，可扩展性以及灵活性，再者就是这符合了里氏代换原则和开闭原则。
```

**int和Integer有什么区别**

```text
Integer是int的包装类；int是基本数据类型；
Integer变量必须实例化后才能使用；int变量不需要；
Integer实际是对象的引用，指向此new的Integer对象；int是直接存储数据值 ；
Integer的默认值是null；int的默认值是0。


1. 由于Integer变量实际上是对一个Integer对象的引用，所以两个通过new生成的Integer变量永远是不相等的（因为new生成的是两个对象，其内存地址不同）
2. Integer变量和int变量比较时，只要两个变量的值是向等的，则结果为true（因为包装类Integer和基本数据类型int比较时，java会自动拆包装为int，然后进行比较，实际上就变为两个int变量的比较）
3. 非new生成的Integer变量和new Integer()生成的变量比较时，结果为false。因为非new生成的Integer变量指向的是静态常量池中cache数组中存储的指向了堆中的Integer对象，而new Integer()生成的变量指向堆中新建的对象，两者在内存中的对象引用（地址）不同。
4. 对于两个非new生成的Integer对象，进行比较时，如果两个变量的值在区间-128到127之间，则比较结果为true，如果两个变量的值不在此区间，则比较结果为false
# 参考文章: https://blog.csdn.net/chenliguan/article/details/53888018
```

**application.properties和application.yml文件的区别**

```text
一般上来说，当我们创建一个SpringBoot项目时，IDE会默认帮我们创建一个application.properties配置文件。有些朋友习惯把.properties文件改成.yml文件

区别：
1.内容格式比较：
.properties文件，通过.来连接，通过=来赋值，结构上，没有分层的感觉，但比较直接。
.yml文件，通过：来分层，结构上，有比较明显的层次感，最后key赋值的：后需要留一个空格

2.执行顺序
如果工程中同时存在application.properties文件和 application.yml文件，yml文件会先加载，而后加载的properties文件会覆盖yml文件。所以建议工程中，只使用其中一种类型的文件即可
```

**运行test代码，提示: Java HotSpot(TM) 64-Bit Server VM warning: Sharing is only supported for boot loader classes
because bootstrap classpath has been appended**

```text
打开设置，在搜索框内输入：async，然后去掉以下勾选即可。
```

**什么是Bean**

```text
简单笼统的说就是一个类，一个可复用的类。
javaBean在MVC设计模型中是model，又称模型层，在一般的程序中，我们称它为数据层，就是用来设置数据的属性和一些行为，然后我会提供获取属性和设置属性的get/set方法JavaBean是一种JAVA语言写成的可重用组件。为写成JavaBean，类必须是具体的和公共的，并且具有无参数的构造器。JavaBean
通过提供符合一致性设计模式的公共方法将内部域暴露成员属性。众所周知，属性名称符合这种模式，其他Java
类可以通过自身机制发现和操作这些JavaBean 属性。　
用户可以使用JavaBean将功能、处理、值、数据库访问和其他任何可以用java代码创造的对象进行打包，并且其他的开发者可以通过内部的JSP页面、Servlet、其他JavaBean、applet程序或者应用来使用这些对象。用户可以认为JavaBean提供了一种随时随地的复制和粘贴的功能，而不用关心任何改变。
　　JavaBean可分为两种：一种是有用户界面（UI，User
Interface）的JavaBean；还有一种是没有用户界面，主要负责处理事务（如数据运算，操纵数据库）的JavaBean。JSP通常访问的是后一种JavaBean。
```

**数据库连接**

```text
需要安装的依赖:
spring-boot-starter-data-jdbc -> 自动帮我们引入了数据源、JDBC与事务相关jar包
mysql-connector-java  -> 数据库连接

现在的连接驱动： com.mysql.cj.jdbc.Driver 之前是: com.mysql.jdbc.Driver

参考文章: https://blog.csdn.net/dlhjw1412/article/details/118886601?spm=1001.2101.3001.6650.2&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-2-118886601-blog-124501825.235%5Ev38%5Epc_relevant_yljh&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-2-118886601-blog-124501825.235%5Ev38%5Epc_relevant_yljh&utm_relevant_index=5
```

**IDEA打开后提示：Lombok Requires Annotation Processing: Do you want to enable annotation processors?**

```text
这个问题的具体愿意是因为项目中引用lombok的jar包编译无法通过

在网上搜索发现大多数修改方法都是下面这个方法
依次点击 File——Setting——Build.Execution.Deployment——Compiler——Annotation—— Processors ，勾选窗口上的 Enable Annotation Processing(启用批注处理)=> 应用-确定即可

参考文章: https://blog.csdn.net/weixin_45869725/article/details/114384026
```

**Jackson日期转换少一天**

```text
用LocalDateTime 配合GMT+8时区，可以解决

参考文章: https://www.cnblogs.com/yuan-/p/11703280.html
```

**idea新建mapper xml模板文件**

```text
这个文件是模板文件，需要在idea设置中添加（Settings -> Editor -> File and Code Template)

然后添加新的模板 文件后缀是xml 内容如下
<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
"http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="">
    
</mapper>

参考文章: https://blog.csdn.net/qq_48455576/article/details/113838269
```

**springboot整合mybatis使用xml实现sql语句的配置,在insert之后返回自增id**

```text
参考文章: https://blog.csdn.net/weixin_43944305/article/details/106300945
```

**serialVersionUID 是干什么的？**

```text
序列化运行时将一个版本号（称为serialVersionUID）与每个可序列化类相关联，该版本号在反序列化期间用于验证序列化对象的发送方和接收方是否为该对象加载了与序列化兼容的类。

强烈建议所有可序列化类显式声明serialVersionUID值，因为默认的 serialVersionUID 计算对类详细信息高度敏感，这些详细信息可能因编译器实现而异，因此在反序列化过程中可能会导致意外的InvalidClassExceptions。

因此，为了保证在不同的java编译器实现中SerialVersionId值是一致的，可序列化类必须声明一个显式的SerialVersionId值。还强烈建议显式 serialVersionUID 声明尽可能使用 private 修饰符，因为此类声明仅适用于立即声明的类——serialVersionUID字段不可用作继承成员。

参考文章: https://zhuanlan.zhihu.com/p/347246506
```

**@NotNull、@NotBlank与@NotEmpty的区别**

```text
@NotNull：不能为null，但可以为empty(""," “,” ") ，一般用在基本数据类型的非空校验上，而且被其标注的字段可以使用 @size/@Max/@Min对字段数值进行大小的控制，例如Integer、BigDecimal、String等
@NotBlank: 只应用于char值可读序列，即只用于String，且不能为null
@NotEmpty：不能为null，而且长度必须大于0(" “,” ")，一般用在集合类上面


@NotNull 一般用于Integer 、 bigDecimal、String

@NotBlank 一般用于Sting，且不能为null

@NotEmpty 一般用于集合类中
```

**default 方法**

```text
Default方法是指，在接口内部包含了一些默认的方法实现（也就是接口中可以包含方法体，这打破了Java之前版本对接口的语法限制），从而使得接口在进行扩展的时候，不会破坏与接口相关的实现类代码

interface InterfaceA{
    void doFirstWork();
    default void doSecondWork(){
        System.out.println("doSecondWork");
    }
}
 
class InterfaceImpl implements InterfaceA{
    @Override
    public void doFirstWork() {
        System.out.println("doFirstWork");
    }
}
 
public class Test {
    public static void main(String[] args) {
        InterfaceImpl obj = new InterfaceImpl();
        obj.doFirstWork();
        obj.doSecondWork();
    }
}
输出：
doFirstWork
doSecondWork
```

**@Autowired、@Resource、@Reference 与@Inject注解的区别**

```text
1.@Autowired
org.springframework.beans.factory.annotation.Autowired
SpringBoot项目中常用。简单来说就是引入由Spring容器管理的bean。

 // 若是使用@Autowired // FruitServiceImpl 需要添加类似@Component, @Service, @Repository, @Controller or @Indexed注解，不然会报错


2.@Resource
javax.annotation.Resource
作用相当于@Autowired，只不过@Autowired是byType自动注入，而@Resource默认byName自动注入。

3.@Reference
@Reference是dubbo的注解，也是注入，他一般注入的是分布式的远程服务的对象，需要dubbo配置使用。

简单来说他们的区别：
@Reference注入的是分布式中的远程服务对象，@Resource和@Autowired注入的是本地spring容器中的对象。

参考文章: https://blog.csdn.net/u014662858/article/details/84262544
```

**Serializable的理解**

```text
使用场景: 本地存储、网络传输

ObjectOutputStream

凡是离开内存的信息都需要进行序列化，所以java中要实现对象IO读写操作都必须实现Serializable接口，否则报错
```

**@Mapper与@Repository的区别**

```text
1. @Mapper注解写在每个Dao接口层的接口类上，@MapperScan注解写在SpringBoot的启动类上。

2. 当我们的一个项目中存在多个Dao层接口的时候，此时我们需要对每个接口类都写上@Mapper注解，非常的麻烦，此时可以使用@MapperScan注解来解决这个问题。让这个接口进行一次性的注入，不需要在写@Mapper注解

3. @Mapper注解相当于是@Reponsitory注解和@MapperScan注解的和，会自动的进行配置加载。

4. @MapperScan注解多个包，@Mapper只能把当前接口类进行动态代理。

在实际开发中，如何使用@Mapper、@MapperSacn、@Reponsitory注解？？？

在SpringBoot的启动类上给定@MapperSacn注解。此时Dao层可以省略@Mapper注解，当让@Reponsitory注解可写可不写，最好还是写上。
当使用@Mapper注解的时候，可以省略@MapperSacn以及@Reponsitory。

建议：

以后在使用的时候，在启动类上给定@MapperScan("Dao层接口所在的包路径")。在Dao层上不写@Mapper注解，写上@Reponsitory即可。

有时候会在xml文件中写sql，有时候会直接在mapper接口，例如：UserMapper中通过@Select、@Insert、@Delete、@Update去写SQL,
前者可用于复杂的sql，并且利于维护管理，后者可用于书写一些简单的sql

UserMapper 会映射到 UserMapper.xml
通过@Param注解传递参数
mapper接口
public User selectUser(@Param("userName") String name,@Param("password") String pwd);

xml文件
selectUser 相当于 <select id="selectUser"></select>中的id 两者一定要对应上
通过#{userName} 方式访问

注意点：

当使用了@Param注解来声明参数的时候，SQL语句取值使用#{}，${}取值都可以。
当不使用@Param注解声明参数的时候，必须使用的是#{}来取参数。使用${}方式取值会报错。
不使用@Param注解时，参数只能有一个，可以是一个基本的数据也可以是一个JavaBean。如果是JavaBean最好还是声明一个@Param注解。

链接：https://juejin.cn/post/7159004945230856200
```

**打包**

```text
因为springboot内置了tomcat，所以springboot项目打包成jar可以免去tomcat的配置，如果是打包成war包，则还需要配置tomcat。

打包后在target目录下有两个jar文件，其中有一个jar包后缀为.original，这是Maven标准打包插件打的jar包，它只包含我们自己的Class，不包含依赖，
而后缀为.jar的是SpringBoot打包插件创建的包含依赖的jar，可以直接运行。
```

**常用技术栈**

```text
1. Xxl-Job 分布式任务调度
2. Knife4j  接口文档
3. Hutool、Lombok  工具类
4. Alibaba EasyExcel Excel框架
5. Jackson 序列化框架
6. Redis 缓存数据库 
7. Lock4j 分布式锁 
8. Validation 校验框架
9. SpringMessage 国际化
10. Thymeleaf、Velocity、FreeMarker模板引擎 
```

## Questions

