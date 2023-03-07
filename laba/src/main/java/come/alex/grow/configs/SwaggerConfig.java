package come.alex.grow.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("come.alex.grow.controllers"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo("Swagger2 Api Documentation",
                "How to generate Swagger documentation for your Rest API",
                "1.0", "urn:tos",
                new Contact("Java Dev Journal", "www.javadevjournal.com", "contact-us@javadevjournal.com"),
                "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList());
    }
}
