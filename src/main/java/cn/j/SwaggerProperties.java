package cn.j;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author J
 **/
@ConfigurationProperties(prefix = "swagger.j")
public class SwaggerProperties {

    //todo 一些全局的参数定义

    /**
     * 当个分组的docket对象
     */
    private static DocketInfo single = new DocketInfo();

    /**
     * 多个分组的docket列表，使用下标[0],[1]配置
     */
    private List<DocketInfo> docket = new ArrayList<>();


    public DocketInfo getSingle() {
        return single;
    }

    public SwaggerProperties setSingle(DocketInfo single) {
        this.single = single;
        return this;
    }

    public List<DocketInfo> getDocket() {
        return docket;
    }

    public SwaggerProperties setDocket(List<DocketInfo> docket) {
        this.docket = docket;
        return this;
    }
}
