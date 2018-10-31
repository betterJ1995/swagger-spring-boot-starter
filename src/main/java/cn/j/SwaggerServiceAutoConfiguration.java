package cn.j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Map;
import java.util.Set;

/**
 * @Author J
 * @Date 2018/10/30 16:43
 * @Description
 **/
@Configuration
@ConditionalOnClass(Docket.class)
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(prefix = "swagger.doc", value = "enabled", matchIfMissing = true)
public class SwaggerServiceAutoConfiguration implements BeanFactoryAware {

    private final static Logger logger = LoggerFactory.getLogger(SwaggerServiceAutoConfiguration.class);

    @Autowired
    private SwaggerProperties swaggerProperties;

    private BeanFactory beanFactory;

    @Bean
    @ConditionalOnMissingBean(Docket.class)
    public Integer groupApiDoc() {
        ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) beanFactory;
        Set<Map.Entry<String, DocketInfo>> set = swaggerProperties.getDocket().entrySet();
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        for (Map.Entry<String, DocketInfo> mapItem : set) {
            String beanName = mapItem.getKey();
            DocketInfo docketInfo = mapItem.getValue();
            Docket docket = new Docket(DocumentationType.SWAGGER_2)
                    //接口基本信息
                    .apiInfo(
                            apiInfoBuilder
                                    .title(docketInfo.getTitle())
                                    .description(docketInfo.getDescription())
                                    .version(docketInfo.getVersion())
                                    .build()
                    )
                    .groupName(docketInfo.getGroupName())
                    .select()
                    //该包下的接口生成接口文档
                    //注意 扫描的包是以包名为basePackage开头的包
                    //如:配置包 xx.api 若存在xx.api2   xx.api2下的接口也会被扫描进去
                    .apis(RequestHandlerSelectors.basePackage(docketInfo.getBasePackage()))
                    .paths(PathSelectors.any())
                    .build();
            configurableBeanFactory.registerSingleton(beanName, docket);
        }
        return 1;
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
