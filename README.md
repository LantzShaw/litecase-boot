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

**entity中为什么要设置serialVersionUID**

在 Java 中，`serialVersionUID` 是一个用于序列化和反序列化的版本控制标识符。它主要用于确保在反序列化过程中，反序列化的类版本与序列化时的类版本一致。

以下是 `serialVersionUID` 的一些主要作用和原因：

1. **版本控制**：当一个类被序列化后，它的状态被保存到一个字节流中。`serialVersionUID` 是一个唯一标识符，用于验证序列化的对象与当前的类定义是否兼容。如果类的定义发生了变化（例如添加了新字段或方法），而没有更新 `serialVersionUID`，则可能导致反序列化失败或抛出 `InvalidClassException`。

2. **确保兼容性**：通过显式声明 `serialVersionUID`，可以确保序列化和反序列化过程中类版本的一致性。这对于在不同版本的应用程序中传输对象非常重要。

3. **提高性能**：`serialVersionUID` 可以提高反序列化过程的性能，因为它允许 JVM 在反序列化时快速检查类的版本。

4. **避免默认生成**：如果类中没有声明 `serialVersionUID`，Java 序列化机制会根据类的结构自动生成一个默认的 `serialVersionUID`。这可能会因为类的细微变化（如编译器的版本）而不同，从而导致不必要的序列化问题。

在你的 Java Spring 应用中，通常在实现 `Serializable` 接口的类中，会设置 `private static final long serialVersionUID = 1L;`，这样可以确保当类的结构发生变化时，能够明确知道版本的兼容性。

举个例子：

```java
import java.io.Serializable;

public class MyClass implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private int age;
    
    // getters and setters
}
```

在这个例子中，`serialVersionUID` 是 `1L`，表示这是类的第一个版本。如果将来对 `MyClass` 进行更改（如添加字段），可以更新 `serialVersionUID` 的值，以指示类的版本已经变化。

**类似UserMapper.xml中，使用@Param("username") String username，使用@Param的意义**
```text
在 MyBatis 中，`@Param` 注解用于给 SQL 查询中的参数命名。当一个方法有多个参数或者需要为单个参数提供更明确的名称时，可以使用 `@Param` 注解。这个注解的作用是将 Java 方法的参数与 SQL 语句中的占位符进行绑定。

具体来说，`@Param("username") String username` 的意思是：

1. **命名参数**：在 SQL 语句中可以使用 `#{username}` 来引用传递的参数，而不需要依赖方法参数的名称。这在 SQL 语句中可以使得参数更加明确，尤其是当方法中有多个参数时。
   
2. **多参数的支持**：MyBatis 默认会根据方法参数的顺序匹配。如果方法有多个参数，MyBatis 需要知道如何将这些参数映射到 SQL 语句中。而通过 `@Param` 注解，可以明确地指定参数名称，以便在 SQL 语句中使用它。

### 示例说明

假设有一个 `findByUsername` 的方法，用于通过用户名查找用户。在 `UserMapper` 接口中，我们可以这样定义方法：

import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User findByUsername(@Param("username") String username);
}

`@Param("username")` 的作用是将传入的 `username` 参数绑定到 SQL 中的 `#{username}` 占位符上。这样在 XML 映射文件中可以通过 `#{username}` 来引用这个参数。

### 对应的 `UserMapper.xml`：

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-mapper-3.0.dtd">
<mapper namespace="com.example.mapper.UserMapper">

    <!-- 查询用户通过用户名 -->
    <select id="findByUsername" parameterType="String" resultType="com.example.entity.User">
        SELECT *
        FROM user
        WHERE username = #{username}
    </select>

</mapper>

### 作用场景

1. **多参数场景**：如果有多个参数，`@Param` 显得更加重要。例如：

   public interface UserMapper {
       User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
   }

   在对应的 SQL 中：

   <select id="findByUsernameAndPassword" resultType="com.example.entity.User">
       SELECT *
       FROM user
       WHERE username = #{username} AND password = #{password}
   </select>

   这样，MyBatis 就可以根据 `@Param` 注解，将方法的 `username` 和 `password` 参数正确映射到 SQL 语句的 `#{username}` 和 `#{password}` 中。

2. **避免参数名冲突**：如果方法参数的名字与 SQL 中的列名或关键词相同，`@Param` 可以避免冲突，确保参数正确映射。

总的来说，`@Param("...")` 的主要作用就是为 SQL 语句中的参数提供一个明确的名称，并确保多个参数能够正确地绑定到 SQL 语句中。
```
**可以不使用@Param可以吗**
```text
可以在 MyBatis 中不使用 `@Param` 注解，但这取决于具体的场景和需求。如果不使用 `@Param` 注解，MyBatis 默认会按照 **方法参数的顺序** 来处理 SQL 中的参数绑定。如果只有一个参数或者方法的参数数量较少且顺序明确，不使用 `@Param` 是可以的。

### 示例：不使用 `@Param` 的情况

#### 1. 单个参数

如果方法只接受一个参数，MyBatis 可以自动匹配参数，因此不需要 `@Param` 注解。例如：

public interface UserMapper {
    User findByUsername(String username);
}

对应的 XML 映射文件：

<select id="findByUsername" parameterType="String" resultType="com.example.entity.User">
    SELECT *
    FROM user
    WHERE username = #{username}
</select>

在这种情况下，MyBatis 会自动将方法的 `username` 参数映射到 SQL 语句中的 `#{username}` 占位符。

#### 2. 多个参数（不使用 `@Param`）

如果方法有多个参数而不使用 `@Param` 注解，MyBatis 会使用参数的默认名称 `arg0`、`arg1`、`arg2` 等来匹配 SQL 中的参数。此时，SQL 语句中的占位符必须使用这些默认名称。

例如：

public interface UserMapper {
    User findByUsernameAndPassword(String username, String password);
}

对应的 XML 映射文件：

<select id="findByUsernameAndPassword" resultType="com.example.entity.User">
    SELECT *
    FROM user
    WHERE username = #{arg0} AND password = #{arg1}
</select>

- `arg0` 代表方法的第一个参数 `username`。
- `arg1` 代表方法的第二个参数 `password`。

#### 3. 不推荐的情况

虽然不使用 `@Param` 可以工作，但在多个参数的场景下不推荐这样做，因为：

- **可读性差**：`arg0`、`arg1` 这样的命名方式不直观，难以维护和理解。
- **容易出错**：如果方法参数的顺序发生变化，SQL 语句中的参数绑定也需要随之调整，增加了出错的可能性。

### 使用 `@Param` 的优点

- **明确的参数命名**：可以通过名字直接引用参数，如 `#{username}` 和 `#{password}`，使 SQL 更易于理解和维护。
- **灵活性**：在复杂查询或者动态 SQL 语句中，`@Param` 可以提供更大的灵活性，避免依赖参数的顺序。

### 总结

- **可以不使用 `@Param`**，但仅限于参数较少的场景，且需要按照参数的顺序来引用（使用 `arg0`, `arg1` 等）。
- **推荐使用 `@Param`** 尤其是在有多个参数或方法签名复杂的情况下，这样能够提高代码的可读性和维护性。
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

**@PostConstruct注解的作用**

```text
参考文章: https://juejin.cn/post/7010017313625735198

@PostContruct是spring框架的注解，在方法上加该注解会在项目启动的时候执行该方法，也可以理解为在spring容器初始化的时候执行该方法。

 /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init()
    {
        List<Job> jobList = jobDao.selectJobAll();
        for (Job job : jobList)
        {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, job.getJobId());
            // 如果不存在，则创建
            if (cronTrigger == null)
            {
                ScheduleUtils.createScheduleJob(scheduler, job);
            }
            else
            {
                ScheduleUtils.updateScheduleJob(scheduler, job);
            }
        }
    }

上述代码表示在项目启动，Spring IOC容器初始化创建之后，Bean初始化之前和销毁之前，执行@PostConstruct注解的方法。一般用于一些项目初始化的设定。比如Spring IOC Container 初始化之后，用@PostConstruct注解Quartz的 CronTrigger 用于初始化定时器（向定时器中添加定时启动的JOB）。那么项目运行时就能自动的运行CronTrigger 中的job了。
```
***配置多模块包扫描*

```text
SpringBoot 2.3.12.RELEASE
META-INF/spring.factories

SprintBoot 2.7的新特性
META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports 配置了还需要在启动类所在的项目中通过dependency的方式引入，才有效

resources下的文件夹需要一个一个创建，不能通过 ‘.’ 的方式连续创建
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

## SQL

**常用sql**

```text
# 创建表 ``可以不要
DROP TABLE IF EXISTS t_scheduled;
CREATE TABLE `t_scheduled` (
`cron_id` VARCHAR(30) NOT NULL PRIMARY KEY,
`cron_name` VARCHAR(30) NULL,
`cron` VARCHAR(30) NOT NULL
);

# 查询
SELECT cron_id, cron_name, cron from t_scheduled;

# 分页查询
# select _column,_column from _table [where Clause] [limit N][offset M]
# limit N : 返回 N 条记录
# offset M : 跳过 M 条记录, 默认 M=0, 单独使用似乎不起作用
# limit N,M : 相当于 limit M offset N , 从第 N 条记录开始, 返回 M 条记录
# select * from _table limit (page_number-1)*lines_perpage, lines_perpage
# 或者
# select * from _table limit lines_perpage offset (page_number-1)*lines_perpage
SELECT * FROM t_scheduled LIMIT 10 OFFSET 0;


# 插入
INSERT INTO `t_scheduled` VALUES ('14', '定时器1', '0/10 * * * * ?');

# 批量插入
INSERT INTO t_scheduled VALUES ('90', '定时器90', '0/7 * * * * ?'), ('80', 'hello', '0/8 * * * * ?');

	
# 更新	
UPDATE `t_scheduled` SET cron_name='定时器修改' WHERE cron_id='14';

# 删除
DELETE FROM t_scheduled WHERE cron_id = '12';

# 批量删除
DELETE FROM t_scheduled WHERE cron_id in (90, 80, 2);


# 查询记录条数
SELECT COUNT(*) FROM t_scheduled;
SELECT COUNT(1) as id FROM t_scheduled;
SELECT COUNT(cron_id) as id FROM t_scheduled;
SELECT COUNT(cron_id) as id FROM t_scheduled WHERE cron_name='hello';
```

## Code
```text
参考文章: https://zhuanlan.zhihu.com/p/93310283#:~:text=SpringBoot%E6%95%B4%E5%90%88mybatis%E5%AE%9E%E7%8E%B0%E6%A0%B9%E6%8D%AEid%E7%9A%84%E6%89%B9%E9%87%8F%E5%88%A0%E9%99%A4%2C%E7%AE%80%E5%8D%95%E7%9A%84%E5%A2%9E%E5%88%A0%E6%94%B9%E6%9F%A5%E7%AD%89%20UserPo%20package%20com.qianhong.user.po%3B%20import%20java.io.Serializable%3B%20%2F%2A%2A%20%2A,nickName%3B%20%2F%2F%E5%A4%96%E9%94%AE%20%E7%94%A8%E6%88%B7%E5%9C%B0%E5%9D%80id%20private%20Integer%20addressId%3B%20%2F%2F%E7%9C%81%E7%95%A5getter%E5%92%8Csetter%20%7D
SpringBoot整合mybatis实现根据id的批量删除,简单的增删改查等

UserMapper.xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.qianhong.user.dao.UserDao">
  <resultMap id="BaseResultMap" type="com.qianhong.user.po.UserPo">
    <id column="id" jdbcType="INTEGER" property="id" />
    <result column="nick_name" jdbcType="VARCHAR" property="nickName" />
    <result column="address_id" jdbcType="INTEGER" property="addressId" />
  </resultMap>

  <sql id="Base_Column_List">
    id, nick_name, address_id
  </sql>
  <!--根据id查询用户-->
  <select id="selectByPrimaryKey" parameterType="java.lang.Integer" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List" />
    from user
    where id = #{id,jdbcType=INTEGER}
  </select>
  <!--根据id删除用户-->
  <delete id="deleteByPrimaryKey" parameterType="java.lang.Integer">
    delete from user
    where id = #{id,jdbcType=INTEGER}
  </delete>
  <!--添加用户-->
  <insert id="insert" parameterType="com.qianhong.user.bo.UserBo">
    insert into user (id, nick_name, address_id
      )
    values (#{id,jdbcType=INTEGER}, #{nickName,jdbcType=VARCHAR}, #{addressId,jdbcType=INTEGER}
      )
  </insert>
  <!--修改用户-->
  <update id="updateByPrimaryKeySelective" parameterType="com.qianhong.user.po.UserPo">
    update user
    <set>
      <if test="nickName != null">
        nick_name = #{nickName,jdbcType=VARCHAR},
      </if>
      <if test="addressId != null">
        address_id = #{addressId,jdbcType=INTEGER},
      </if>
    </set>
    where id = #{id,jdbcType=INTEGER}
  </update>

  <update id="updateByPrimaryKey" parameterType="com.qianhong.user.po.UserPo">
    update user
    set nick_name = #{nickName,jdbcType=VARCHAR},
      address_id = #{addressId,jdbcType=INTEGER}
    where id = #{id,jdbcType=INTEGER}
  </update>
  <!--批量添加用户-->
  <insert id="batchInsertUserPo">
    insert into user(id,nick_name,address_id)
    values
    <foreach collection="list" separator="," item="test">
      (
--         第一个test="test.id"是dao层传过来的参数，
        <if test="test.id !=null">
          id=#{test.id},
        </if>
        <if test="test.nickName != null">
          nick_name=#{test.nickName},
        </if>
        <if test="test.addressId != null">
          address_id=#{test.addressId}
        </if>
      )
    </foreach>

  </insert>
  <!--根据id批量删除用户-->
  <delete id="deleteUserAllById" parameterType="java.lang.String">
    delete from
    user  where id in
    <foreach item="id" collection="id" open="(" close=")" separator="," >
      #{id}
    </foreach>
  </delete>
</mapper>
```

BizDeviceMapper.java
```java
package cn.ltit.bigfish.daheng.core.device.mapper;

import cn.ltit.bigfish.daheng.core.device.model.dto.Gather;
import cn.ltit.bigfish.daheng.core.device.model.dto.GatherItem;
import cn.ltit.bigfish.daheng.core.device.model.entity.*;
import cn.ltit.engine.data.datascope.annotation.DataScope;
import cn.ltit.engine.data.core.mapper.ExtendMapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shaoqiang
 * @version 1.0
 * @description: TODO
 * @date 2023/3/4 15:59
 */
@DataScope
@Mapper
public interface BizDeviceMapper extends ExtendMapper<BizDevice> {


    IPage<BizDevice> selectExtPage(IPage<BizDevice> page, @Param("param") BizDevice param);

    List<Gather> getherOne(String organizationId);

    List<GatherItem> getherTwo(String organizationId);

    List<GatherItem> getherThree(String organizationId);

    List<GatherItem> getherFour(String organizationId);


    List<GatherItem> getherFive(String organizationId);


    Integer getOrgCount(@Param("param") Gather param);

    Integer getSensor(@Param("param") Gather param);

    Integer getGasTypeCount(@Param("param") Gather param,@Param("type") String type);

    Integer getMaintRecordCount(@Param("param") Gather param);

    Integer getRecordCount(@Param("param") Gather param);

    Integer getTraceCount(@Param("param") Gather param);

    Integer getChangeCount(@Param("param") Gather para,@Param("type") String type);

    Integer getOverdueCount(@Param("param") Gather para);

    List<String> getGasName(@Param("param") Gather param);

    List<String> getOrgName(@Param("param") Gather param);

    Integer getGasCount(@Param("param") Gather param,@Param("orgName") String orgName,@Param("gasName")String gasName);

    IPage<BizDevice> getAlarmList(IPage<BizDevice> page,@Param("param") Gather param);

    IPage<BizDevice> getMainList(IPage<BizDevice> page,@Param("param") Gather param);

    IPage<BizDevice> getTraceaList(IPage<BizDevice> page,@Param("param") Gather param);

    IPage<BizDevice> getTraceaItem(IPage<BizDevice> page,@Param("param") Gather param,@Param("id") String id);

    IPage<BizDevice> getGasTypeList(IPage<BizDevice> page,@Param("param") Gather param,@Param("type") String type);



    IPage<BizDevice> getOverdueItem(IPage<BizDevice> page,@Param("param") Gather param,@Param("organizationId") String organizationId);
}
```

BizDeviceMapper.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="cn.ltit.bigfish.daheng.core.device.mapper.BizDeviceMapper">
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="cn.ltit.bigfish.daheng.core.device.model.entity.BizDevice">
        <id column="id" property="id"/>
        <result column="create_by" property="createBy"/>
        <result column="create_time" property="createTime"/>
        <result column="update_by" property="updateBy"/>
        <result column="update_time" property="updateTime"/>
        <result column="status" property="status"/>
        <result column="reversion" property="reversion"/>
        <result column="is_reserved" property="reserved"/>
        <result column="ranking" property="ranking"/>
        <result column="device_name" property="deviceName"/>
        <result column="device_no" property="deviceNo"/>
        <result column="group_id" property="groupId"/>
        <result column="organization_id" property="organizationId"/>
        <result column="dtuip_create_date" property="dtuipCreateDate"/>
        <result column="dtuip_default_timescale" property="dtuipDefaultTimescale"/>
        <result column="dtuip_ioc_url" property="dtuipIocUrl"/>
        <result column="dtuip_is_alarms" property="dtuipIsAlarms"/>
        <result column="dtuip_is_delete" property="dtuipIsDelete"/>
        <result column="dtuip_is_line" property="dtuipIsLine"/>
        <result column="dtuip_lat" property="dtuipLat"/>
        <result column="dtuip_lng" property="dtuipLng"/>
        <result column="dtuip_linktype" property="dtuipLinktype"/>
        <result column="dtuip_user_id" property="dtuipUserId"/>
        <result column="dtuip_user_name" property="dtuipUserName"/>
        <result column="dtuip_id" property="dtuipId"/>
        <result column="dtuip_product_id" property="dtuipProductId"/>
        <result column="dtuip_product_type" property="dtuipProductType"/>
        <result column="dtuip_protocol_label" property="dtuipProtocolLabel"/>
        <result column="dtuip_remark" property="dtuipRemark"/>
        <result column="dtuip_time_zone" property="dtuipTimeZone"/>
        <result column="dtuip_group_id" property="dtuipGroupId"/>
    </resultMap>

    <!-- 通用查询映射结果 -->
    <resultMap id="ExtResultMap" type="cn.ltit.bigfish.daheng.core.device.model.entity.BizDevice">
        <id column="id" property="id"/>
        <result column="create_by" property="createBy"/>
        <result column="create_time" property="createTime"/>
        <result column="update_by" property="updateBy"/>
        <result column="update_time" property="updateTime"/>
        <result column="status" property="status"/>
        <result column="reversion" property="reversion"/>
        <result column="is_reserved" property="reserved"/>
        <result column="ranking" property="ranking"/>
        <result column="device_name" property="deviceName"/>
        <result column="device_no" property="deviceNo"/>
        <result column="group_id" property="groupId"/>
        <result column="organization_id" property="organizationId"/>
        <result column="dtuip_create_date" property="dtuipCreateDate"/>
        <result column="dtuip_default_timescale" property="dtuipDefaultTimescale"/>
        <result column="dtuip_ioc_url" property="dtuipIocUrl"/>
        <result column="dtuip_is_alarms" property="dtuipIsAlarms"/>
        <result column="dtuip_is_delete" property="dtuipIsDelete"/>
        <result column="dtuip_is_line" property="dtuipIsLine"/>
        <result column="dtuip_lat" property="dtuipLat"/>
        <result column="dtuip_lng" property="dtuipLng"/>
        <result column="dtuip_linktype" property="dtuipLinktype"/>
        <result column="dtuip_user_id" property="dtuipUserId"/>
        <result column="dtuip_user_name" property="dtuipUserName"/>
        <result column="dtuip_id" property="dtuipId"/>
        <result column="dtuip_product_id" property="dtuipProductId"/>
        <result column="dtuip_product_type" property="dtuipProductType"/>
        <result column="dtuip_protocol_label" property="dtuipProtocolLabel"/>
        <result column="dtuip_remark" property="dtuipRemark"/>
        <result column="dtuip_time_zone" property="dtuipTimeZone"/>
        <result column="dtuip_group_id" property="dtuipGroupId"/>
        <association property="bizDeviceGroup" column="group_id"
                     select="cn.ltit.bigfish.daheng.core.device.mapper.BizDeviceGroupMapper.selectById"/>
        <association property="sysOrganization" column="organization_id"
                     select="cn.ltit.engine.supplier.upms.core.organization.mapper.SysOrganizationMapper.selectById"/>
    </resultMap>

    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
        id
        ,
        create_by,
        create_time,
        update_by,
        update_time,
        status,
        reversion,
        is_reserved,
        ranking,
        device_name,
        device_no,
        group_id,
        organization_id,
        dtuip_create_date,
        dtuip_default_timescale,
        dtuip_ioc_url,
        dtuip_is_alarms,
        dtuip_is_delete,
        dtuip_is_line,
        dtuip_lat,
        dtuip_lng,
        dtuip_linktype,
        dtuip_user_id,
        dtuip_user_name,
        dtuip_id,
        dtuip_product_id,
        dtuip_product_type,
        dtuip_protocol_label,
        dtuip_remark,
        dtuip_time_zone,
        dtuip_group_id
    </sql>

    <sql id="Common_Column_List">
        t1
        .
        id
        ,
        t1.create_by,
        t1.create_time,
        t1.update_by,
        t1.update_time,
        t1.status,
        t1.reversion,
        t1.is_reserved,
        t1.ranking,
        t1.device_name,
        t1.device_no,
        t1.group_id,
        t1.organization_id,
        t1.dtuip_create_date,
        t1.dtuip_default_timescale,
        t1.dtuip_ioc_url,
        t1.dtuip_is_alarms,
        t1.dtuip_is_delete,
        t1.dtuip_is_line,
        t1.dtuip_lat,
        t1.dtuip_lng,
        t1.dtuip_linktype,
        t1.dtuip_user_id,
        t1.dtuip_user_name,
        t1.dtuip_id,
        t1.dtuip_product_id,
        t1.dtuip_product_type,
        t1.dtuip_protocol_label,
        t1.dtuip_remark,
        t1.dtuip_time_zone,
        t1.dtuip_group_id
    </sql>

    <select id="selectExtPage" resultMap="ExtResultMap">
        SELECT
        <include refid="Common_Column_List"/>
        FROM biz_device t1 LEFT JOIN sys_organization t2 on t1.organization_id=t2.id
        <where>
            <if test="param.groupId != null and param.groupId !=''">
                and t1.group_id =#{param.groupId}
            </if>
            <if test="param.orgName != null and param.orgName !=''">
                and t2.organization_name =#{param.orgName}
            </if>
            <if test="param.deviceName!=null and param.deviceName!=''">
                and t1.device_name like concat('%', #{param.deviceName}, '%')
            </if>
            <if test="param.deviceNo!=null and param.deviceNo!=''">
                and t1.device_no like concat('%', #{param.deviceNo}, '%')
            </if>
            <if test="param.dtuipIsAlarms != null">
                and t1.dtuip_is_alarms = #{param.dtuipIsAlarms}
            </if>
            <if test="param.dtuipIsLine != null">
                and t1.dtuip_is_line = #{param.dtuipIsLine}
            </if>
            <if test="param.status != null">
                and t1.status = #{param.status}
            </if>
            <if test="param.startDate != null">
                and DATE_FORMAT(t1.dtuip_create_date,'%Y-%m-%d') >=DATE_FORMAT(#{param.startDate},'%Y-%m-%d')
            </if>
            <if test="param.endDate != null">
                and DATE_FORMAT(t1.dtuip_create_date,'%Y-%m-%d') &lt;=DATE_FORMAT(#{param.eupLoadEndDate},'%Y-%m-%d')
            </if>
            <if test="param.organizationId != null and param.organizationId!=''">
                and t1.organization_id = #{param.organizationId}
            </if>
        </where>
        order by t1.dtuip_create_date desc
    </select>

    <select id="getherOne" resultType="cn.ltit.bigfish.daheng.core.device.model.dto.Gather">
        SELECT
        organization.organization_name orgName,
        count( organization.id ) deviceSum,
        organization.id organizationId
        FROM
        biz_device device
        INNER JOIN sys_organization organization ON device.organization_id = organization.id
        WHERE 1=1
        <if test="organizationId!=null and organizationId!=''">
            organization.id=#{organizationId}
        </if>
        GROUP BY organization.organization_name
    </select>
    <select id="getherTwo" resultType="cn.ltit.bigfish.daheng.core.device.model.dto.GatherItem">
        SELECT gas_name,
               gas_type,
               count(sensor.id) sensorSum
        FROM biz_device_sensor sensor
                 INNER JOIN biz_device device on sensor.device_id = device.id
        WHERE device.organization_id = #{organizationId}
        GROUP BY gas_name, gas_type
    </select>
    <select id="getherThree" resultType="cn.ltit.bigfish.daheng.core.device.model.dto.GatherItem">
        SELECT count(1) traceSum,
               sensor.sensor_name
        FROM biz_device device
                 INNER JOIN biz_device_sensor sensor ON sensor.device_id = device.id
                 INNER JOIN biz_device_sensor_trace trace ON sensor.id = trace.sensor_id
        WHERE device.organization_id = #{organizationId}
        GROUP BY sensor.id
    </select>
    <select id="getherFour" resultType="cn.ltit.bigfish.daheng.core.device.model.dto.GatherItem">
        SELECT count(1) alarmSum,
               sensor.sensor_name
        FROM biz_data_alarm alarm
                 INNER JOIN biz_device_sensor sensor on alarm.sensor_id = sensor.id
                 INNER JOIN biz_workflow_alarm_deal_record record on record.device_alarm_id = alarm.id
                 INNER JOIN biz_device device ON sensor.device_id = device.id
        WHERE device.organization_id = #{organizationId}
        GROUP BY sensor.id
    </select>
    <select id="getherFive" resultType="cn.ltit.bigfish.daheng.core.device.model.dto.GatherItem">
        SELECT count(1) maintSum,
               sensor.sensor_name
        FROM biz_workflow_device_maint_record record
                 INNER JOIN biz_workflow_device_maint_plan_sensor plan ON plan.plan_id = record.device_maint_id
            OR plan.plan_id = record.id
                 INNER JOIN biz_device_sensor sensor ON plan.sensor_id = sensor.id
                 INNER JOIN biz_device device ON sensor.device_id = device.id
        WHERE device.organization_id = #{organizationId}
        GROUP BY sensor.id
    </select>

    <select id="getOrgCount" resultType="java.lang.Integer">
        select COUNT(1)
        from (SELECT COUNT(1)
        FROM biz_device_sensor device
        INNER JOIN sys_organization organization ON device.organization_id = organization.id
        WHERE 1=1
        <if test="param.startDate != null">
            and DATE_FORMAT(organization.create_time,'%Y-%m-%d') >=DATE_FORMAT(#{param.startDate},'%Y-%m-%d')
        </if>
        <if test="param.endDate != null">
            and DATE_FORMAT(organization.create_time,'%Y-%m-%d') &lt;=DATE_FORMAT(#{param.endDate},'%Y-%m-%d')
        </if>
        GROUP BY organization.id) a
    </select>

    <select id="getSensor" resultType="java.lang.Integer">
        SELECT
        COUNT(1)
        FROM
        biz_device_sensor sensor
        INNER JOIN sys_organization sys ON sys.id = sensor.organization_id
        WHERE 1=1
        <if test="param.startDate != null">
            and DATE_FORMAT(sensor.create_time,'%Y-%m-%d') >=DATE_FORMAT(#{param.startDate},'%Y-%m-%d')
        </if>
        <if test="param.endDate != null">
            and DATE_FORMAT(sensor.create_time,'%Y-%m-%d') &lt;=DATE_FORMAT(#{param.endDate},'%Y-%m-%d')
        </if>
    </select>

    <select id="getGasTypeCount" resultType="java.lang.Integer">
        SELECT
        count(1)
        FROM
        biz_device_sensor
        WHERE gas_type=#{type}
        <if test="param.startDate != null">
            and DATE_FORMAT(create_time,'%Y-%m-%d') >=DATE_FORMAT(#{param.startDate},'%Y-%m-%d')
        </if>
        <if test="param.endDate != null">
            and DATE_FORMAT(create_time,'%Y-%m-%d') &lt;=DATE_FORMAT(#{param.endDate},'%Y-%m-%d')
        </if>

    </select>
    <select id="getMaintRecordCount" resultType="java.lang.Integer">
        SELECT
        count(1)
        FROM
        biz_device_sensor sensor
        INNER JOIN biz_workflow_device_maint_plan_sensor plan on sensor.id = plan.sensor_id
        WHERE 1=1
        <if test="param.startDate != null">
            and DATE_FORMAT(plan.create_time,'%Y-%m-%d') >=DATE_FORMAT(#{param.startDate},'%Y-%m-%d')
        </if>
        <if test="param.endDate != null">
            and DATE_FORMAT(plan.create_time,'%Y-%m-%d') &lt;=DATE_FORMAT(#{param.endDate},'%Y-%m-%d')
        </if>
    </select>
    <select id="getRecordCount" resultType="java.lang.Integer">
        SELECT
        count( 1 )
        FROM
        biz_device_sensor sensor
        INNER JOIN biz_data_alarm alarm ON sensor.id = alarm.sensor_id
        INNER JOIN biz_workflow_alarm_deal_record record on record.alarm_sn_no=alarm.alarm_sn_no
        WHERE
        1 =1
        <if test="param.startDate != null">
            and DATE_FORMAT(record.create_time,'%Y-%m-%d') >=DATE_FORMAT(#{param.startDate},'%Y-%m-%d')
        </if>
        <if test="param.endDate != null">
            and DATE_FORMAT(record.create_time,'%Y-%m-%d') &lt;=DATE_FORMAT(#{param.endDate},'%Y-%m-%d')
        </if>
    </select>
    <select id="getGasName" resultType="java.lang.String">
        SELECT
        gas_name
        FROM
        biz_device_sensor
        where gas_name is not null
        <if test="param.startDate != null">
            and DATE_FORMAT(create_time,'%Y-%m-%d') >=DATE_FORMAT(#{param.startDate},'%Y-%m-%d')
        </if>
        <if test="param.endDate != null">
            and DATE_FORMAT(create_time,'%Y-%m-%d') &lt;=DATE_FORMAT(#{param.endDate},'%Y-%m-%d')
        </if>
        GROUP BY
        gas_name
    </select>
    <select id="getOrgName" resultType="java.lang.String">
        SELECT
        organization.organization_name
        FROM
        biz_device_sensor sensor
        INNER JOIN sys_organization organization on sensor.organization_id = organization.id
        where 1=1
        <if test="param.startDate != null">
            and DATE_FORMAT(sensor.create_time,'%Y-%m-%d') >=DATE_FORMAT(#{param.startDate},'%Y-%m-%d')
        </if>
        <if test="param.endDate != null">
            and DATE_FORMAT(sensor.create_time,'%Y-%m-%d') &lt;=DATE_FORMAT(#{param.endDate},'%Y-%m-%d')
        </if>
        GROUP BY organization_name
    </select>
    <select id="getGasCount" resultType="java.lang.Integer">
        SELECT
        count(1)
        FROM
        biz_device_sensor sensor
        INNER JOIN sys_organization organization on sensor.organization_id = organization.id
        where sensor.gas_name =#{gasName}
        and organization.organization_name=#{orgName}
        <if test="param.startDate != null">
            and DATE_FORMAT(sensor.create_time,'%Y-%m-%d') >=DATE_FORMAT(#{param.startDate},'%Y-%m-%d')
        </if>
        <if test="param.endDate != null">
            and DATE_FORMAT(sensor.create_time,'%Y-%m-%d') &lt;=DATE_FORMAT(#{param.endDate},'%Y-%m-%d')
        </if>
    </select>

    <select id="getAlarmList" resultType="cn.ltit.bigfish.daheng.core.device.model.entity.BizDevice">
        SELECT
        record.id recordId,
        alarm.dtuip_trigger_date,
        sensor.location_no,
        region.region_name,
        record.event_status,
        sys.organization_name
        FROM
        biz_data_alarm alarm
        INNER JOIN biz_device_sensor sensor ON alarm.sensor_id = sensor.id
        INNER JOIN biz_workflow_alarm_deal_record record on record.alarm_sn_no=alarm.alarm_sn_no
        LEFT JOIN sys_organization sys ON sys.id = sensor.organization_id
        LEFT JOIN biz_install_region region ON region.id = sensor.region_id
        where 1=1

        <if test="param.id != null and param.id!=''">
            and alarm.organization_id = #{param.id}
        </if>
        <if test="param.startDate != null">
            and DATE_FORMAT(alarm.create_time,'%Y-%m-%d') >=DATE_FORMAT(#{param.startDate},'%Y-%m-%d')
        </if>
        <if test="param.endDate != null">
            and DATE_FORMAT(alarm.create_time,'%Y-%m-%d') &lt;=DATE_FORMAT(#{param.endDate},'%Y-%m-%d')
        </if>
    </select>
    <select id="getMainList" resultType="cn.ltit.bigfish.daheng.core.device.model.entity.BizDevice">
        SELECT
        record.id recordId,
        record.event_status,
        sensor.sensor_name,
        sensor.location_no,
        region.region_name,
        sys.organization_name,
        record.create_time
        FROM
        biz_device_sensor sensor
        INNER JOIN biz_workflow_device_maint_plan_sensor plan ON sensor.id = plan.sensor_id
        left JOIN biz_workflow_device_maint_record record ON record.id = plan.plan_id
        INNER JOIN biz_install_region region ON region.id = sensor.region_id
        INNER JOIN sys_organization sys on sys.id = sensor.organization_id
        where 1=1
        <if test="param.id != null and param.id!=''">
            and sensor.organization_id = #{param.id}
        </if>
        <if test="param.startDate != null">
            and DATE_FORMAT(record.create_time,'%Y-%m-%d') >=DATE_FORMAT(#{param.startDate},'%Y-%m-%d')
        </if>
        <if test="param.endDate != null">
            and DATE_FORMAT(record.create_time,'%Y-%m-%d') &lt;=DATE_FORMAT(#{param.endDate},'%Y-%m-%d')
        </if>
    </select>
    <select id="getTraceCount" resultType="java.lang.Integer">
        SELECT
        count(1)
        FROM
        biz_device_sensor_trace trace
        INNER JOIN biz_device_sensor sensor on sensor.id = trace.sensor_id
        INNER JOIN biz_device_group g on g.organization_id = sensor.organization_id
        INNER JOIN sys_organization s on s.id = g.organization_id
        WHERE 1=1
        <if test="param.endDate != null">
            and DATE_FORMAT(trace.actual_expiration_date,'%Y-%m-%d') &lt;=DATE_FORMAT(#{param.endDate},'%Y-%m-%d')
        </if>
    </select>
    <select id="getTraceaList" resultType="cn.ltit.bigfish.daheng.core.device.model.entity.BizDevice">
        SELECT
        sys.id organizationId,
        trace.id,
        sys.organization_name,
        count(1) as qty,
        max(datediff(now(),trace.actual_expiration_date)) maxDate
        FROM
        biz_device_sensor_trace trace
        INNER JOIN biz_device_sensor sensor on sensor.id = trace.sensor_id
        INNER JOIN biz_device device on device.id=sensor.device_id
        INNER JOIN sys_organization sys on sys.id=device.organization_id
        WHERE 1=1
        <if test="param.endDate != null">
            and DATE_FORMAT(trace.actual_expiration_date,'%Y-%m-%d') &lt;=DATE_FORMAT(#{param.endDate},'%Y-%m-%d')
        </if>
        GROUP BY sys.organization_name
        ORDER BY qty desc
    </select>
    <select id="getTraceaItem" resultType="cn.ltit.bigfish.daheng.core.device.model.entity.BizDevice">
        SELECT
        sensor.location_no,
        region.region_name,
        trace.id,
        sys.organization_name,
        datediff(now(),trace.actual_expiration_date) maxDate
        FROM
        biz_device_sensor_trace trace
        INNER JOIN biz_device_sensor sensor on sensor.id = trace.sensor_id
        INNER JOIN sys_organization sys on sys.id=sensor.organization_id
        left JOIN biz_install_region region on region.id = sensor.region_id
        WHERE 1=1
        <if test="id!=null and id !=''">
            and sys.id=#{id}
        </if>
        <if test="param.endDate != null">
            and DATE_FORMAT(trace.actual_expiration_date,'%Y-%m-%d') &lt;=DATE_FORMAT(#{param.endDate},'%Y-%m-%d')
        </if>
        ORDER BY maxDate desc
    </select>
    <select id="getChangeCount" resultType="java.lang.Integer">
        SELECT
        count(1)
        FROM
        biz_device_sensor_change t1
        INNER JOIN biz_device_sensor t2 ON t1.sensor_id = t2.id
        INNER JOIN sys_organization sys ON sys.id = t2.organization_id
        left JOIN biz_install_region region on region.id = t2.region_id
        WHERE 1=1 and t1.change_type=#{type}
        <if test="param.startDate != null">
            and DATE_FORMAT(t1.change_date,'%Y-%m-%d') >=DATE_FORMAT(#{param.startDate},'%Y-%m-%d')
        </if>
        <if test="param.endDate != null">
            and DATE_FORMAT(t1.change_date,'%Y-%m-%d') &lt;=DATE_FORMAT(#{param.endDate},'%Y-%m-%d')
        </if>
    </select>
    <select id="getOverdueCount" resultType="java.lang.Integer">
        SELECT
        count(1)
        FROM
        biz_device_sensor_trace trace
        INNER JOIN biz_device_sensor sensor ON trace.sensor_id = sensor.id
        INNER JOIN sys_organization sys ON sys.id = sensor.organization_id
        WHERE 1=1
        <if test="param.fig!=null and param.fig!='' and param.fig=='1'.toString()">
            <if test="param.startDate != null">
                and DATE_FORMAT(trace.actual_expiration_date,'%Y-%m-%d') >DATE_FORMAT(#{param.startDate},'%Y-%m-%d')
            </if>
        </if>
        <if test="param.fig!=null and param.fig!='' and param.fig=='2'.toString()">
            <if test="param.startDate != null">
                and DATE_FORMAT(trace.actual_expiration_date,'%Y-%m-%d') &lt;DATE_FORMAT(#{param.startDate},'%Y-%m-%d')
            </if>
        </if>
    </select>

    <select id="getGasTypeList" resultType="cn.ltit.bigfish.daheng.core.device.model.entity.BizDevice">
        SELECT
        t2.*,
        region.region_name,
        sys.organization_name
        FROM
        biz_device_sensor_change t1
        INNER JOIN biz_device_sensor t2 ON t1.sensor_id = t2.id
        INNER JOIN sys_organization sys ON sys.id = t2.organization_id
        left JOIN biz_install_region region on region.id = t2.region_id
        WHERE 1=1 and t1.change_type=#{type}
        <if test="param.id != null and param.id!=''">
            and t2.organization_id = #{param.id}
        </if>
        <if test="param.startDate != null">
            and DATE_FORMAT(t2.create_time,'%Y-%m-%d') >=DATE_FORMAT(#{param.startDate},'%Y-%m-%d')
        </if>
        <if test="param.endDate != null">
            and DATE_FORMAT(t2.create_time,'%Y-%m-%d') &lt;=DATE_FORMAT(#{param.endDate},'%Y-%m-%d')
        </if>
    </select>
</mapper>
```

## Questions

**为什么不推荐使用@Autowired注解，而推荐使用@Resource注解，或者使用构造函数+setter注入？**
在 Spring 框架中，`@Autowired` 注解广泛用于自动注入依赖，但在一些场景下并不推荐直接使用 `@Autowired`，尤其是在构造函数注入中。以下是一些不推荐使用 `@Autowired` 的原因，以及一些更好的替代方案。

### 1. **构造函数注入更安全、明确**

使用 `@Autowired` 注解进行字段注入会让类的依赖关系不够显式，尤其是在类的构造函数中没有明确传入依赖对象时，可能会导致代码的可测试性和可维护性降低。

#### 问题：
- **依赖隐藏**：依赖关系直接注入到类的字段中，依赖不通过构造函数传入，外部难以知道这个类依赖了哪些组件。
- **不利于测试**：在单元测试中，字段注入使得模拟对象或手动注入依赖变得困难，因为字段无法通过构造函数传入。
- **不适用于不可变对象**：字段注入后依赖的对象可能会在运行时被替换或改变，导致对象状态不再不可变。

#### 推荐方式：
使用 **构造函数注入** 更明确、更安全，特别是对于类的依赖关系，构造函数注入可以避免上面的问题。

```java
public class MyService {

    private final MyDependency myDependency;

    // 构造函数注入
    public MyService(MyDependency myDependency) {
        this.myDependency = myDependency;
    }
}
```

- **优点**：构造函数注入会让依赖关系更加明确，且不需要额外的注解，如 `@Autowired`。
- **强制依赖声明**：所有依赖项都通过构造函数传递，使得类的依赖一目了然，无法省略，避免隐式注入。

从 Spring 4.3 开始，如果只有一个构造函数，可以省略 `@Autowired`，Spring 会自动注入依赖。

### 2. **字段注入使代码不够灵活**

字段注入使得依赖的字段直接被注入，但在某些场景下，例如需要延迟初始化或重写依赖时，字段注入显得不够灵活。构造函数或 setter 注入则更灵活，可以提供更多控制。

#### 示例：字段注入（不推荐）
```java
public class MyService {

    @Autowired
    private MyDependency myDependency;

    // 不灵活，无法延迟或改变依赖注入的行为
}
```

#### 推荐方式：构造函数注入或 setter 注入
```java
public class MyService {

    private final MyDependency myDependency;

    public MyService(MyDependency myDependency) {
        this.myDependency = myDependency;
    }

    // 或者使用 setter 注入来控制依赖注入的时机
    @Autowired
    public void setMyDependency(MyDependency myDependency) {
        this.myDependency = myDependency;
    }
}
```

- **构造函数注入**：更加灵活，不会遇到循环依赖问题。
- **setter 注入**：允许依赖项在运行时修改或延迟注入，适合有时依赖不一定需要立刻注入的情况。

### 3. **代码的可测试性**

使用 `@Autowired` 注入字段时，测试类的依赖变得困难，因为无法在测试中轻松模拟或传递依赖项。构造函数注入可以通过测试框架如 JUnit 或 Mockito 轻松创建对象并传递 mock 依赖项。

#### 示例：构造函数注入提高测试性

```java
public class MyServiceTest {

    @Test
    public void testMyService() {
        MyDependency mockDependency = mock(MyDependency.class);
        MyService myService = new MyService(mockDependency);  // 手动注入依赖

        // 测试逻辑...
    }
}
```

构造函数注入让测试类变得简单，容易进行依赖的模拟和注入。

### 4. **避免循环依赖**

字段注入在某些情况下会导致 **循环依赖** 问题，比如两个类相互依赖时，Spring 容器可能无法正常解析。这种情况下，构造函数注入可以帮助更清晰地识别和解决循环依赖。

```java
public class A {
    @Autowired
    private B b;
}

public class B {
    @Autowired
    private A a;
}
```

- 如果使用字段注入，很容易产生循环依赖的错误。
- 使用构造函数注入时，Spring 会更容易识别和解决循环依赖问题，因为依赖项在构造时已经明确。

### 5. **不使用 `@Autowired` 的替代方案**

- **构造函数注入**：这是最推荐的方式，尤其是在使用 Spring 4.3 及以上版本时，Spring 会自动识别构造函数注入，无需 `@Autowired` 注解。

- **Setter 注入**：在某些情况下，如果依赖项不是必须的或者需要延迟注入，可以使用 `setter` 方法配合 `@Autowired` 注解。

- **配置类中手动注入**：在 `@Configuration` 类中，可以手动配置和注入依赖项。

#### 示例：手动注入依赖
```java
@Configuration
public class AppConfig {

    @Bean
    public MyService myService(MyDependency myDependency) {
        return new MyService(myDependency);
    }

    @Bean
    public MyDependency myDependency() {
        return new MyDependency();
    }
}
```

### 总结

- **字段注入**（使用 `@Autowired`）虽然简单，但会隐藏依赖关系，导致测试、维护和可读性的问题。
- **构造函数注入** 是更推荐的方式，尤其是当依赖明确且必需时，它能让依赖关系更加清晰，避免循环依赖，并提高代码的可测试性。
- **setter 注入** 可以用于需要延迟或可选的依赖注入场景。

因此，除非有特殊需求（如延迟初始化或可选依赖），构造函数注入是比 `@Autowired` 字段注入更好的选择。


**filter中如何注入util类**

在 Spring Boot 的 `Filter` 中，注入 `Util` 类或 Spring 管理的其他 Bean 可以通过以下几种方式实现。由于 `Filter` 本身并不是由 Spring 管理的组件，因此不能直接使用常规的 `@Autowired` 方式，但我们可以通过其他方法使其获得 Spring 容器中的 Bean。

### 1. 使用 `@Component` 注册 `Filter`
可以将 `Filter` 声明为一个 Spring 管理的 Bean，这样可以直接通过 `@Autowired` 注入依赖。示例如下：

#### 代码示例：

##### 1.1 创建 `JwtUtils` 工具类：
```java
@Component
public class JwtUtils {
    public String generateToken(String username) {
        // 假设这里是生成 JWT 的逻辑
        return "generated-jwt-token";
    }
}
```

##### 1.2 创建自定义 `Filter` 并注入 `JwtUtils`：
```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter implements Filter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = httpRequest.getHeader("Authorization");

        if (token != null && !token.isEmpty()) {
            // 使用注入的工具类进行处理
            String username = jwtUtils.generateToken("test-user");
            System.out.println("Generated Token: " + username);
        }

        chain.doFilter(request, response);
    }
}
```

##### 1.3 注册 `Filter`：
使用 `@Component` 将 `Filter` 注册到 Spring 容器，Spring 会自动管理 `Filter` 并将其应用到请求中。

### 2. 使用 `FilterRegistrationBean` 手动注册 `Filter`
如果你不想通过 `@Component` 自动扫描方式来注册 `Filter`，可以通过 `FilterRegistrationBean` 在配置类中手动注册 `Filter`，并将 `Util` 类注入。

#### 代码示例：

##### 2.1 自定义 `JwtAuthenticationFilter`：
```java
public class JwtAuthenticationFilter implements Filter {

    private final JwtUtils jwtUtils;

    // 通过构造函数注入 JwtUtils
    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = httpRequest.getHeader("Authorization");

        if (token != null && !token.isEmpty()) {
            // 使用注入的工具类
            String username = jwtUtils.generateToken("test-user");
            System.out.println("Generated Token: " + username);
        }

        chain.doFilter(request, response);
    }
}
```

##### 2.2 在配置类中注册 `Filter`：
```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthenticationFilter(JwtUtils jwtUtils) {
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtAuthenticationFilter(jwtUtils));
        registrationBean.addUrlPatterns("/api/*"); // 过滤特定路径
        return registrationBean;
    }
}
```

### 总结：
- **方式1**：使用 `@Component` 将 `Filter` 注册到 Spring 容器，并通过 `@Autowired` 注入工具类。
- **方式2**：使用 `FilterRegistrationBean` 手动注册 `Filter`，通过构造函数注入工具类。

这两种方式都能让 `Filter` 正常使用 Spring 管理的 Bean，例如 `JwtUtils` 等。