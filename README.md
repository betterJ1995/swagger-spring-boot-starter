# swagger-start

swagger-spring-boot-starter

## 注解
@EnableSwaggerJ

## 单分组接口文档
```
swagger:
  j:
    single:
      title: J的接口文档标题
      description: J的starter创建的默认接口文档描述
      version: 1.1版本
      group-name: default分组
      # 支持以逗号分隔配置多个包
      base-package: cn.j.sbdemo.sys.api1,cn.j.sbdemo.sys.api2
      license: 许可证
      license-url: 许可证链接
      terms-of-service-url: 服务条款URL
```
## 多分组接口文档
```
swagger:
  j:
    docket[0]:
      #属性与单分组相同
    docket[1]:
      #属性与单分组相同
```

`PS: 若同时配置了单分组与多分组，则单分组失效`