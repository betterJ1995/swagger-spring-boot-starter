package cn.j;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
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
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

/**
 * @Author J
 * @Date 2018/10/30 16:43
 **/
@Configuration
@ConditionalOnClass(Docket.class)
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(prefix = "swagger.j", value = "enabled", matchIfMissing = true)
public class SwaggerAutoConfiguration implements BeanFactoryAware {

    @Autowired
    private SwaggerProperties swaggerProperties;

    private BeanFactory beanFactory;

    @Bean("swagger.j.flag")
    @ConditionalOnMissingBean(Docket.class)
    public Integer groupApiDoc() {
        ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) beanFactory;
        List<DocketInfo> docketInfoList = swaggerProperties.getDocket();
        String beanNamePre = "swagger.j.docket";

        //若未配置
        if (docketInfoList.size() == 0) {
            DocketInfo docketItem = swaggerProperties.getSingle();
            docketInfoList.add(docketItem);
        }

        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        int size = docketInfoList.size();
        for (int i = 0; i < size; i++) {
            DocketInfo infoItem = docketInfoList.get(i);
            Docket docket = new Docket(DocumentationType.SWAGGER_2)
                    //接口基本信息
                    .apiInfo(
                            apiInfoBuilder
                                    .title(infoItem.getTitle())
                                    .description(infoItem.getDescription())
                                    .version(infoItem.getVersion())
                                    .license(infoItem.getLicense())
                                    .licenseUrl(infoItem.getLicenseUrl())
                                    .termsOfServiceUrl(infoItem.getTermsOfServiceUrl())
                                    .build()
                    )
                    .groupName(infoItem.getGroupName())
                    .select()
                    //该包下的接口生成接口文档
                    //注意 扫描的包是以包名为basePackage开头的包
                    //如:配置包 xx.api 若存在xx.api2   xx.api2下的接口也会被扫描进去
                    .apis(myBasePackage(infoItem.getBasePackage()))
                    .paths(PathSelectors.any())
                    .build();

            String beanName = beanNamePre + i;
            configurableBeanFactory.registerSingleton(beanName, docket);
        }
        return 1;
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }


    /**
     * 自定义扫描包方法
     */
    private static Predicate<RequestHandler> myBasePackage(final String basePackage) {
        return new Predicate<RequestHandler>() {
            public boolean apply(RequestHandler input) {
                return declaringClass(input).transform(myHandlerPackage(basePackage)).or(true);
            }
        };
    }

    /**
     * 处理包路径配置规则
     * 支持多路径扫描
     * 以逗号为分隔符
     */
    private static Function<Class<?>, Boolean> myHandlerPackage(final String basePackage) {
        return new Function<Class<?>, Boolean>() {
            public Boolean apply(Class<?> input) {
                //package 以逗号分离
                String[] packages = basePackage.split(",");
                for (String strPackage : packages) {
                    boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                    if (isMatch) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    /**
     * copy from RequestHandlerSelectors
     */
    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }

}
