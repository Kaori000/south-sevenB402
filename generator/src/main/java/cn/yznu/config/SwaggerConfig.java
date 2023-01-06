package cn.yznu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger 配置
 * @author wl
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

//	@Value("${version}")
	private String version="1.0.0";

	@Bean
	public Docket createRestApi() {
		ApiInfo apiInfo = new ApiInfoBuilder().title("代码生成" + version).description("Copyright (c) 2019 - 2020 ")
				.termsOfServiceUrl("http://localost:8082").version(version).build();

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo).select()
				.apis(RequestHandlerSelectors.basePackage("cn.yznu.controller"))
				.paths(PathSelectors.any()).build();
	}
}