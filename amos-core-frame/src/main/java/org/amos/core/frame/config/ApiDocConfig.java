package org.amos.core.frame.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @desc: knife4j 配置
 * @author: liubt
 * @date: 2022-08-08 13:21
 **/
@Configuration
@EnableSwagger2WebMvc
public class ApiDocConfig {

    @Bean(value = "knife4jApi")
    public Docket knife4jApi() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //分组名称
                .groupName("2.X版本")
                .select()
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .description("# AMOS RESTful APIs")
                .termsOfServiceUrl("http://www.amos.vip")
                .contact(new Contact("刘伯韬", "http://www.amos.vip", "1211265557@qq.com"))
                .version("1.0")
                .build();
    }
}
