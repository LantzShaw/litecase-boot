# Litecase Boot

## Records

**数据类型**
```shell
（1）基本数据类型，分为boolean、byte、int、char、long、short、double、float；
（2）引用数据类型 ，分为数组、类、接口。

每个基本数据类型提供了封装类

基本数据类型: boolean，char，byte，short，int，long，float，double
封装类类型：Boolean，Character，Byte，Short，Integer，Long，Float，Double


自动装箱/拆箱机制，使得二者可以相互转换
```

**为什么要用List list = new ArrayList(),而不用ArrayList alist = new ArrayList()呢?**
```shell
问题就在于List接口有多个实现类，现在你用的是ArrayList，也许哪一天你需要换成其它的实现类，如LinkedList或者Vector等等，这时你只要改变这一行就行了： List list = new LinkedList(); 其它使用了list地方的代码根本不需要改动。

假设你开始用ArrayList alist = new ArrayList(), 这下你有的改了，特别是如果你使用了ArrayList实现类特有的方法和属性。

这样的好处是为了代码的可维护性，可复用性，可扩展性以及灵活性，再者就是这符合了里氏代换原则和开闭原则。
```
**int和Integer有什么区别**

```shell
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
