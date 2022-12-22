package cn.yznu.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvc配置
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${kaori.filePrePath}")
    private String filePrePath;

    @Value("${kaori.fileUrlPath}")
    private String fileUrlPath;

    /**
     * 配置以使静态资源能被访问
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/statics/**").addResourceLocations("classpath:/statics/");
        registry.addResourceHandler(fileUrlPath + "**").addResourceLocations("file:" + filePrePath);
    }

}