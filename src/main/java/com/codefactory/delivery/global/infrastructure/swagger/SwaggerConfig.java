package com.codefactory.delivery.global.infrastructure.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("user-api")
                .displayName("회원 API")
                .pathsToMatch("/v1/user/**")
                .build();
    }

    @Bean
    public GroupedOpenApi orderApi() {
        return GroupedOpenApi.builder()
                .group("order-api")
                .displayName("주문 API")
                .pathsToMatch("/v1/order/**")
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("배달 서비스 REST API")
                        .description("저희 배달 서비스는....")
                        .version("1.0")
                        .contact(new Contact().email("yonggyo00@kakao.com")));
    }
}
