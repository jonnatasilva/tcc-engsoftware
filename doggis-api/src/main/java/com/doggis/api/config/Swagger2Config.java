package com.doggis.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

import static com.google.common.collect.Lists.newArrayList;

@EnableSwagger2
@Configuration
public class Swagger2Config {

	public static void main(String[] args) {
		System.out.println(Arrays.toString(sortByHeight(new int[] {-1, 150, 190, 170, -1, -1, 160, 180})));
	}

	private static int[] sortByHeight(int[] a) {
		Arrays.stream(a)
		.boxed()
		.sorted((x1, x2) -> {
//			System.out.println(x1 + " || " + x2);
			return x1 == -1 || x2 == -1 ? 0 : x1.compareTo(x2);
		})
		.forEach(System.out::println);
		
		return null;
	}

	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				    .apis(RequestHandlerSelectors.basePackage("com.animals.care.animalscare.controller"))
				    .paths(PathSelectors.any())
				    .build()
				.pathMapping("/")
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, 
						newArrayList(new ResponseMessageBuilder()
								.code(500)
								.message("500 message")
								.build()
								, new ResponseMessageBuilder()
								.code(400)
								.message("400 Bad Request")
								.build()
								, new ResponseMessageBuilder()
								.code(404)
								.message("404 Not Found")
								.build()
								, new ResponseMessageBuilder()
								.code(200)
								.message("200 Ok")
								.build()))
//				.enableUrlTemplating(true)
				.apiInfo(new ApiInfoBuilder()
						.title("Animals Care REST API")
						.version("1.0.0")
						.contact(new Contact("Jonnatas Silva", null, "jonnatas.silva17@gmail.com"))
						.build());
	}
}
