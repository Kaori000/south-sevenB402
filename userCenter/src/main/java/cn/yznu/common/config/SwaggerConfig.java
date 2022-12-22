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


//    @Bean
//    public Docket iotApi() {
//        return new Docket(DocumentationType.SWAGGER_2).groupName("平台模块")
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("cn.runget.modules.iot.controller"))
//                .paths(PathSelectors.ant("/iot/**"))
//                .build();
//    }

    @Bean
    public Docket jobApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("定时模块")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.runget.modules.job.controller"))
                .paths(PathSelectors.ant("/sys/**"))
                .build();
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

//    @Bean
//    public Docket fileApi() {
//        return new Docket(DocumentationType.SWAGGER_2).groupName("aliyun文件上传")
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("cn.runget.modules.oss.controller"))
//                .paths(PathSelectors.ant("/file/**"))
//                .build();
//    }

//    @Bean
//    public Docket smartcityApi() {
//        return new Docket(DocumentationType.SWAGGER_2).groupName("云脑模块")
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("cn.runget.modules.smartcity.controller"))
//                .paths(PathSelectors.ant("/smartcity/**"))
//                .build();
//    }

//    @Bean
//    public Docket poiFileApi() {
//        return new Docket(DocumentationType.SWAGGER_2).groupName("文件模块")
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("cn.runget.modules.file.controller"))
//                .paths(PathSelectors.ant("/poi/**"))
//                .build();
//    }

    @Bean
    public Docket logApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("日志模块")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.runget.modules.log.controller"))
                .paths(PathSelectors.ant("/log/**"))
                .build();
    }

    @Bean
    public Docket disputeApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("纠纷模块")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.runget.modules.dispute.controller"))
                .paths(PathSelectors.ant("/dispute/**"))
                .build();
    }

    @Bean
    public Docket adviserApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("顾问模块")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.runget.modules.adviser.controller"))
                .paths(PathSelectors.ant("/adviser/**"))
                .build();
    }
    @Bean
    public Docket abilityApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("赋能平台")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.runget.modules.ability.controller"))
                .paths(PathSelectors.ant("/ability/**"))
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("云脑 smartcity")
            .description("iotcloud-admin文档")
            .termsOfServiceUrl("http://www.runget.cn")
            .version("1.0.0")
            .build();
    }

    private List<ApiKey> securitySchemes() {
        return Lists.newArrayList(new ApiKey("X-Token", "X-Token", "header"));
    }

}