package cn.j;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author J
 * @Date 2018/10/30 16:41
 * @Description
 **/
@ConfigurationProperties(prefix = "swagger.doc")
public class SwaggerProperties {

    private String title;
    private String description;
    private String version;
    private String basePackage;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
}
