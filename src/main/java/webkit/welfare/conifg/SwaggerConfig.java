package webkit.welfare.conifg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(true) // Swagger 에서 제공해주는 기본 응답 코드를 표시할 것이면 true
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("webkit.welfare.controller")) // Controller가 들어있는 패키지. 이 경로의 하위에 있는 api만 표시됨.
                .paths(PathSelectors.any()) // 위 패키지 안의 api 중 지정된 path만 보여줌. (any()로 설정 시 모든 api가 보여짐)
                .build()
                .securitySchemes(Collections.singletonList(apiKey()))
                .securityContexts(Arrays.asList(securityContext(), securityContext2(), securityContext3()));
    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Bearer", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(new SecurityReference("Bearer Token", new AuthorizationScope[0])))
                .forPaths(PathSelectors.ant("/auth/getUser"))
                .build();
    }

    private SecurityContext securityContext2() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(new SecurityReference("Bearer Token", new AuthorizationScope[0])))
                .forPaths(PathSelectors.ant("/auth/updateUser"))
                .build();
    }

    private SecurityContext securityContext3() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(new SecurityReference("Bearer Token", new AuthorizationScope[0])))
                .forPaths(PathSelectors.ant("/auth/updatePassword"))
                .build();
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Webkit Welfare Rest API Documentation")
                .description("웹킷3기 1조 팀 프로젝트 맟춤형 복지서비스")
                .version("1.0.0")
                .build();
    }
}