package br.com.algamoney.core.openpi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class SpringFoxConfig implements WebMvcConfigurer {

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("br.com.gft")) //Seleção do pacote para geração da doc
				.build().apiInfo(apiInfo()).tags(new Tag("Categorias", "Gerencia as categorias"),						//Criação de Tags para organização
						new Tag("Lancamentos", "Gerencia os lancamentos"), new Tag("Pessoas", "Gerencia as pessoas"));
	}

	public ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("AlgaMoney API").description("API aberta para pessoas").version("1").build(); //Dados da documentação
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/swagger-ui/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/") //Local do swagger-ui v3.0
				.resourceChain(false);
	}

}
