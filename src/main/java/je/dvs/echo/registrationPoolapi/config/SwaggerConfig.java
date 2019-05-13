package je.dvs.echo.registrationPoolapi.config;

import je.dvs.echo.registrationPoolapi.constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//import java.util.Arrays;
import java.util.Collections;
//import java.util.List;

import static java.lang.System.getenv;
//import static org.hibernate.validator.internal.util.CollectionHelper.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

//    public static final String securitySchemaOAuth2 = "oauth2";
//    public static final String authorizationScopeGlobal = "global";
//    public static final String authorizationScopeGlobalDesc = "accessEverything";


//    String CLIENT_ID= getenv("spring.security.oauth2.client.registration.azure.client-id");
//
//    String CLIENT_SECRET = getenv("spring.security.oauth2.client.registration.azure.client-secret");
//
//    String AUTH_SERVER= "https://login.microsoftonline.com/govje.onmicrosoft.com/oauth2/";


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("je.dvs.echo.registrationPoolapi.controller"))
                .paths(regex("/registrationNumber.*"))
                .build();
//                .securitySchemes(Arrays.asList(securitySchema()))
//                .securityContexts(Arrays.asList(securityContext()));
    }


    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Registration Number",
                "Management of registration numbers allocated to vehicles",
                constants.VERSION.toString(),
                "Terms of service",
                new Contact("John Doe", "www.example.com", "myeaddress@company.com"),
                "License of API", "API license URL", Collections.emptyList());
    }


//    private OAuth securitySchema() {
//
//        List<AuthorizationScope> authorizationScopeList = newArrayList();
//        authorizationScopeList.add(new AuthorizationScope("global", "access all"));
//
//        List<GrantType> grantTypes = newArrayList();
//        final TokenRequestEndpoint tokenRequestEndpoint = new TokenRequestEndpoint(AUTH_SERVER + "token", CLIENT_ID, CLIENT_SECRET);
//        final TokenEndpoint tokenEndpoint = new TokenEndpoint(AUTH_SERVER + "token", "access_token");
//        AuthorizationCodeGrant authorizationCodeGrant = new AuthorizationCodeGrant(tokenRequestEndpoint, tokenEndpoint);
//
//        grantTypes.add(authorizationCodeGrant);
//
//        OAuth oAuth = new OAuth("oauth", authorizationScopeList, grantTypes);
//
//        return oAuth;
//    }
//
//
//
//    private List<SecurityReference> defaultAuth() {
//
//        final AuthorizationScope authorizationScope =
//                new AuthorizationScope(authorizationScopeGlobal, authorizationScopeGlobalDesc);
//        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return Collections
//                .singletonList(new SecurityReference(securitySchemaOAuth2, authorizationScopes));
//    }
//
//    private SecurityContext securityContext() {
//        return SecurityContext.builder().securityReferences(defaultAuth())
//                .forPaths(PathSelectors.ant("/api/**")).build();
//    }
}
