package eternal.hoge.spring.boot.example.simple.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.mappers.VendorExtensionsMapperImpl;

import java.util.Arrays;

@Configuration
@EnableSwagger2
//@EnableWebMvc
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {

    //  AAAAAAAAAAA-123   REST-JPA-H2-SWAGGER-APP    "wQtjvPXTefJcWEwX9J9EWRl1JP4a";
    // f59712ed-be34-4beb-8ed3-bbb63551f405                                 y3Y4WX7eaQHpKABt5RTcaSB7BBca
    // http://localhost:8080/auth/realms/master/protocol/openid-connect
    private static final String CLIENT_ID ="AAAAAAAAAAA-123";
    private static final String CLIENT_SECRET = "f59712ed-be34-4beb-8ed3-bbb63551f405";
    private static final String AUTH_SERVER = "http://localhost:8080/auth/realms/master/protocol/openid-connect";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Arrays.asList(oAuthSecurityScheme(),basicSecurityScheme(),apiKeySecurityScheme()))
                .securityContexts(Arrays.asList(securityContext()));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public SecurityConfiguration security() {

        return SecurityConfigurationBuilder.builder()
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .scopeSeparator(" ")
                .useBasicAuthenticationWithAccessCodeGrant(true)
                .build();
    }
    private SecurityScheme basicSecurityScheme() {
        SecurityScheme basic =  new BasicAuth("basic");
        return  basic;
    }
    private SecurityScheme apiKeySecurityScheme() {
        SecurityScheme  apiKey = new ApiKey(""," ","");
        return  apiKey;
    }
    private SecurityScheme oAuthSecurityScheme() {
        GrantType grantType = new AuthorizationCodeGrantBuilder()
                .tokenEndpoint(new TokenEndpoint(AUTH_SERVER + "/token", "oauthtoken"))
                .tokenRequestEndpoint(
                        new TokenRequestEndpoint(AUTH_SERVER + "/authorize", CLIENT_ID, CLIENT_SECRET))
                .build();

         ;



        SecurityScheme oauth = new OAuthBuilder().name("OAuth2Security")
                .grantTypes(Arrays.asList(grantType))
                .scopes(Arrays.asList(scopes()))
                .build();
        return oauth;
    }

    private AuthorizationScope[] scopes() {
        AuthorizationScope[] scopes = {
                new AuthorizationScope("viewBookGet", "for read operations"),
                new AuthorizationScope("viewBookPost", "for write operations"),
                new AuthorizationScope("viewBookById", "Access foo API"),
                new AuthorizationScope("searchBook", "Access foo API"),


                new AuthorizationScope("createBook", "for read operations"),
                new AuthorizationScope("createBooks", "for write operations"),
                new AuthorizationScope("deleteBook", "Access foo API"),



                new AuthorizationScope("upateEmployee", "Access foo API"),
                new AuthorizationScope("deleteEmployee", "for read operations"),
                new AuthorizationScope("createEmployee", "for write operations"),
                new AuthorizationScope("viewEmployee", "Access foo API"),

        };
        return scopes;
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(
                        Arrays.asList(new SecurityReference("spring_oauth", scopes())))
                .forPaths(PathSelectors.any())
                .build();
    }
}
