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