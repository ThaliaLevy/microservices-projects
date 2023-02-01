package br.com.erudio.configurations;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
	
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		return builder.routes()
					  .route(p -> p.path("/get")
						  .filters(f -> f.addRequestHeader("oi", "header")
								  		 .addRequestParameter("ola", "parameter"))
						  .uri("http://httpbin.org:80"))
					  .route(p -> p.path("/cambio-service/**")
							  	   .uri("lb://cambioservice-udemyleandro"))
					  .route(p -> p.path("/book-service/**")	//parte do endpoint
						  	   .uri("lb://bookservice-udemyleandro"))	//como está no eureka
					  .build();
	}
}
