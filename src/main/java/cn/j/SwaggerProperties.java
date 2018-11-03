package cn.j;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author J
 * @Date 2018/10/30 16:41
 * @Description
 **/
@ConfigurationProperties(prefix = "swagger.j")
public class SwaggerProperties {

    private DocketInfo single = new DocketInfo();

    private List<DocketInfo> docket = new ArrayList<DocketInfo>();


    public DocketInfo getSingle() {
        return single;
    }

    public void setSingle(DocketInfo single) {
        this.single = single;
    }

    public List<DocketInfo> getDocket() {
        return docket;
    }

    public void setDocket(List<DocketInfo> docket) {
        this.docket = docket;
    }
}
