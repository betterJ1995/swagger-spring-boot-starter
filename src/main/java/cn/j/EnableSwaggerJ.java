package cn.j;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.annotation.*;

/**
 * 开启注解
 * created on 2018/10/31.
 *
 * @author J
 */
@EnableSwagger2
@EnableSwaggerBootstrapUI
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({SwaggerAutoConfiguration.class})
public @interface EnableSwaggerJ {
}
