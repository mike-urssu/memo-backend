package com.mistar.memo.application.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.oas.annotations.EnableOpenApi
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
@EnableOpenApi
class SwaggerConfig {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.OAS_30)
            .apiInfo(apiInfo())
            .useDefaultResponseMessages(false)
            .groupName("MemoController")
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.mistar.memo"))
            .build()
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfo(
            "Memo Api",
            "Memo CRUD",
            "1.0.0",
            "mistar.cloud/swagger-ui/index.html",
            Contact(
                "Joon Hee Song", "mistar.cloud/swagger-ui/index.html", "mornings9047@naver.com"
            ),
            "",
            "",
            ArrayList()
        )
    }
}