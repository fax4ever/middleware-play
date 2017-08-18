package com.whiterational.uisproma.spring.application;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.js.ajax.AjaxUrlBasedViewResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.tiles2.TilesConfigurer;
import org.springframework.webflow.mvc.view.FlowAjaxTilesView;

@Configuration
@EnableWebMvc
@ImportResource(value = "classpath:ejb-context.xml")
@Import(WebFlowConfig.class)
@ComponentScan(basePackages = {"com.whiterational.uisproma.spring.controller", "com.whiterational.uisproma.spring.paypal"})
@PropertySource("classpath:application.properties")
public class MvcConfig extends WebMvcConfigurerAdapter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MvcConfig.class);
	
	@Inject
	private Environment env;

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setOrder(2);
		viewResolver.setPrefix("WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setViewClass(JstlView.class);
		return viewResolver;
	} 

	@Bean
	public AjaxUrlBasedViewResolver tilesViewResolver() {
		AjaxUrlBasedViewResolver ajaxUrlBasedViewResolver = new AjaxUrlBasedViewResolver();
		ajaxUrlBasedViewResolver.setOrder(1);
		
		ajaxUrlBasedViewResolver.setViewClass(FlowAjaxTilesView.class);
		
		return ajaxUrlBasedViewResolver;
	}

	@Bean
	public TilesConfigurer tilesConfigurer() {
		String[] defs = { "/WEB-INF/layouts/layouts.xml", "/WEB-INF/views/**/views.xml", "/WEB-INF/flows/**/views.xml" };

		TilesConfigurer config = new TilesConfigurer();
		config.setDefinitions(defs);

		return config;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**/*").addResourceLocations("/resources/",
				"classpath:/META-INF/web-resources/");
	}
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
		
		String pattern = env.getProperty("date.pattern");
		LOGGER.info(pattern);
		
		registry.addFormatter(new DateFormatter(pattern));
	}

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
		source.setBasename("classpath:application");
		source.setDefaultEncoding("UTF-8");
		return source;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localChangeInterceptor());
		registry.addInterceptor(userInterceptor());
	}

	@Bean
	public HandlerInterceptor localChangeInterceptor() {
		LocaleChangeInterceptor localChangeInterceptor = new LocaleChangeInterceptor();
		localChangeInterceptor.setParamName("lang");
		return localChangeInterceptor;
	}
	
	@Bean
	public UserInterceptor userInterceptor() {
		UserInterceptor userInterceptor = new UserInterceptor();
		return userInterceptor;
	}

	@Bean
	public LocaleResolver localeResolver() {
		return new CookieLocaleResolver();
	}
	
}
