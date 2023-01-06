package cn.yznu.common.config;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * Swagger配置
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig{

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            //加了ApiOperation注解的类，生成接口文档
            .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
            //包下的类，生成接口文档
            .paths(PathSelectors.any())
            .build()
            .securitySchemes(securitySchemes());
    }

    @Bean
    public Docket sysApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("系统模块")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.runget.modules.sys.controller"))
                .paths(PathSelectors.ant("/sys/**"))
                .build();
    }




    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("云脑 smartcity")
            .description("api文档")
            .termsOfServiceUrl("http://www.runget.cn")
            .version("1.0.0")
            .build();
    }

    private List<ApiKey> securitySchemes() {
        return Lists.newArrayList(new ApiKey("X-Token", "X-Token", "header"));
    }

}