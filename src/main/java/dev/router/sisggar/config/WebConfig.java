package dev.router.sisggar.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import dev.router.sisggar.serialization.converter.YamlJackson2HttpMessageConverter;


@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	private static final MediaType MEDIA_TYPE_YML = MediaType.valueOf("application/x-yaml");
	
	// FABRICO DE INSTANCIAS DE MODELMAPPER
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper;
    }
	
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new YamlJackson2HttpMessageConverter());
	}
	
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
	}
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer ) {
		configurer.favorPathExtension(false)
		.favorParameter(false)
		.ignoreAcceptHeader(false)
		.useRegisteredExtensionsOnly(false)
		.defaultContentType(MediaType.APPLICATION_JSON)
		.mediaType("json", MediaType.APPLICATION_JSON)
		.mediaType("xml", MediaType.APPLICATION_XML)
		.mediaType("x-yaml", MEDIA_TYPE_YML);
	}

}
