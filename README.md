# swagger-start

swagger-spring-boot-starter


## 使用
```
        <dependency>
            <groupId>com.github.jinse95</groupId>
            <artifactId>swagger-spring-boot-starter</artifactId>
            <version>1.0.0</version>
        </dependency>
```
### 注解
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
## 访问路径
http://项目地址/doc.html

## PS
`1. 若同时配置了单分组与多分组，则单分组失效`  
`2. 使用swagger-bootstrap-ui替换的原本的文档ui`

## 版本更新

### 1.0.0
初始版本

### 1.0.1
去除不规范注释即maven依赖