package com.avianca.pagos.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
 
public class dapioauth2Test extends CamelSpringTestSupport {

	private static final String PROPERTIES_FILE_DIR = "src/test/resources/";
	private static Properties testProperties = new Properties();
 
	@Test
	public void testRoute() throws Exception {
		final String fromRoute = "direct:fromRoute";

		context.getRouteDefinition("restServerRoute").adviceWith(context, new AdviceWithRouteBuilder() {

			@Override
			public void configure() throws Exception {
				replaceFromWith(fromRoute);
//		weaveById("validateHeaders").replace().to("mock:generateHaders").process(new Processor() {
//			
//			public void process(Exchange ex) throws Exception {
//				System.out.println("Ingresando al process HEADER");
//				ex.getIn().setHeader("client_secret", "4qlhyjeqGU04htis");
//				ex.getIn().setHeader("client_id", "oAA0TRXj2fVrK4c3HkzyxBGcQdKRo61Z");
//				ex.getIn().setHeader("guest_office_id", "BOGAV08LK");
//			
//			}
//		});
				weaveAddLast().log("Finishing the unit test of the route ").to("mock://endroute");
			}

		});
		context.start();
		// Agregamos un mock endpoint
		MockEndpoint mockEndpoint = getMockEndpoint("mock://endroute");
		mockEndpoint.expectedMinimumMessageCount(1);
		Map<String, Object> map = new HashMap<>();
		map.put("client_id", "oAA0TRXj2fVrK4c3HkzyxBGcQdKRo61Z");
		map.put("client_secret", "4qlhyjeqGU04htis");
		template.sendBodyAndHeaders(fromRoute, "", map);

		mockEndpoint.assertIsSatisfied(2000L);
	}

	/**
	 * Carga del archivo de propiedades para los Test Unitarios
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void init() throws Exception {
		testProperties.load(dapioauth2Test.class.getResourceAsStream("/dapioauth2.properties"));
	}

	@BeforeClass
	public static void setUpProperties() throws Exception {
		System.setProperty("karaf.home", PROPERTIES_FILE_DIR);
		System.setProperty("project.artifactId", "Test-Maven-Artifact");
	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/camel-context.xml", "META-INF/spring/properties-beans.xml");
	}

}
