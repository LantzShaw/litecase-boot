# Question Record

1. `Bad credentials`密码错误，查看断点调试，findByUsername中密码有没有查出来

```text
1. 因为在mapper中的findByUsername中，密码没有设置的查询列中，导致没有匹配出来
```

2. 关于spring security新版的配置，[参考代码](https://github.com/bezkoder/spring-boot-spring-security-jwt-authentication)

```text
https://github.com/bezkoder/spring-boot-login-example
https://github.com/18061495586/Spring-Security-Demo
```