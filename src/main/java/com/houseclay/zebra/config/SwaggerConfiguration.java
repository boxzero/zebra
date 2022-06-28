package com.houseclay.zebra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.lang.reflect.Array;
import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources");

        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public Docket configureSwagger2() {
//        SecurityReference securityReference = SecurityReference.builder()
//                .reference("basicAuth")
//                .scopes(new AuthorizationScope[0])
//                .build();
//
//        ArrayList<SecurityReference> references = new ArrayList<>(1);
//        references.add(securityReference);

//        ArrayList<SecurityContext> securityContexts = new ArrayList<>(1);
//        securityContexts.add(SecurityContext.builder().securityReferences(references).build());
//
//        ArrayList<SecurityScheme> auth = new ArrayList<>(1);
//        auth.add(new BasicAuth("basicAuth"));

        return new Docket(DocumentationType.SWAGGER_2)
//                .securitySchemes(auth).
//                securityContexts(securityContexts)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.houseclay.zebra"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData());

    }

    private ApiInfo metaData(){
       ApiInfo apiInfo = new ApiInfo("HouseClay(Zebra) Backend Portal", "Apis for Backend", "1.0.0",
               "Terms of Service", new Contact("HouseClay","www.houseclay.com","support@houseclay.com").toString(),"HouseClay Licence","http://www.houseclay.com");
    return apiInfo;
    }
}
