package com.mars.template.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.AllArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ForwardedHeaderFilter;

@Configuration
@AllArgsConstructor
public class OpenApiConfig {


	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(apiInfo());
	}

	@Bean
	public GroupedOpenApi apiGroupCustomServer() {
		return GroupedOpenApi
				.builder()
				.addOperationCustomizer((operation, handlerMethod) -> operation)
				.group("v1")
				.build();
	}

	@Bean
	ForwardedHeaderFilter forwardedHeaderFilter() {
		return new ForwardedHeaderFilter();
	}

	private Info apiInfo() {
		return new Info()
				.title("springboot-api-template")
				.description("springboot-api-template")
				.contact(new Contact().email("").name(""))
				.license(new License().name("").url(""))
				.version("0.0.1");
	}

}
