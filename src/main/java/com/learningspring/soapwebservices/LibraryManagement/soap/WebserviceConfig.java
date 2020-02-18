package com.learningspring.soapwebservices.LibraryManagement.soap;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition;

//Enable Spring Web Services
@EnableWs
//Spring Configuration
@Configuration
public class WebserviceConfig {
	
	@Bean
	ServletRegistrationBean messageDispatcherServelet(ApplicationContext context) {
		
		MessageDispatcherServlet messageDispatcherServelet = new MessageDispatcherServlet();
		messageDispatcherServelet.setApplicationContext(context);
		messageDispatcherServelet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(messageDispatcherServelet,"/ws/*");
	}
	
	@Bean
	public XsdSchema bookschema() {
		return new SimpleXsdSchema(new ClassPathResource("book_details.xsd"));
	}
	
	@Bean(name = "books")
	public DefaultWsdl11Definition defwsdl11def(XsdSchema book_schema) {
		
		DefaultWsdl11Definition def = new DefaultWsdl11Definition();
		def.setPortTypeName("BookPort");
		def.setTargetNamespace("http://localhost/books");
		def.setLocationUri("/ws");
		def.setSchema(book_schema);
		return def;
	}
	

}
