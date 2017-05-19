
import builders.CustomerViewsPoints;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

/**
 *
 * @author adath325
 */
public class CustomerViewsPointsTest extends CamelTestSupport {

	private final String requestedEmail;
	private final String retrievedCustomer;

	public CustomerViewsPointsTest() {
		requestedEmail = "boris@email.com";
		retrievedCustomer = "{\"customers\":[{\"id\":\"06bf537b-c7d7-11e7-ff13-2d957f9ff0f0\",\"name\":\"Boris Smith\",\"customer_code\":\"Boris-F39Z\",\"customer_group_id\":\"06bf537b-c77f-11e7-ff13-0c871e85cb6d\",\"customer_group_name\":\"All Customers\",\"first_name\":\"Boris\",\"last_name\":\"Smith\",\"company_name\":\"\",\"phone\":\"\",\"mobile\":\"\",\"fax\":\"\",\"email\":\"boris@email.com\",\"twitter\":\"\",\"website\":\"\",\"physical_address1\":\"\",\"physical_address2\":\"\",\"physical_suburb\":\"\",\"physical_city\":\"\",\"physical_postcode\":\"\",\"physical_state\":\"\",\"physical_country_id\":\"NZ\",\"postal_address1\":\"\",\"postal_address2\":\"\",\"postal_suburb\":\"\",\"postal_city\":\"\",\"postal_postcode\":\"\",\"postal_state\":\"\",\"postal_country_id\":\"NZ\",\"updated_at\":\"2017-05-19 01:13:42\",\"deleted_at\":\"\",\"balance\":\"-200.000\",\"year_to_date\":\"87667.77344\",\"date_of_birth\":\"1984-07-16\",\"sex\":\"M\",\"custom_field_1\":\"\",\"custom_field_2\":\"\",\"custom_field_3\":\"\",\"custom_field_4\":\"\",\"note\":\"\",\"contact\":{\"company_name\":\"\",\"phone\":\"\",\"email\":\"boris@email.com\"}}]}";
	}

	private RouteBuilder createInterceptRoutes() {
		return new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				interceptSendToEndpoint("https4://*")
						  .skipSendToOriginalEndpoint()
						  .log("Mock Vend called")
						  .to("mock:vend");
				interceptSendToEndpoint("websocket://*")
						  .skipSendToOriginalEndpoint()
						  .log("Mock Websocket called")
						  .to("mock:ws");
			}
		};
	}

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		RouteBuilder routeBuilder = new CustomerViewsPoints();
		routeBuilder.includeRoutes(createInterceptRoutes());
		return routeBuilder;
	}

	private void createMockVendProducts() {
		MockEndpoint customers = getMockEndpoint("mock:vend");
		customers.whenAnyExchangeReceived(new Processor() {
			@Override
			public void process(Exchange exchng) throws Exception {
				String interceptedURI = exchng.getIn().getHeader("CamelInterceptedEndpoint", String.class);
				assertStringContains(interceptedURI, "https4://info323otago.vendhq.com/api/customers");
				assertStringContains(interceptedURI, "?email=boris@email.com");
				exchng.getIn().setBody(retrievedCustomer);
			}
		});
	}
	
	private void createMockWebsocket() {
		MockEndpoint customers = getMockEndpoint("mock:ws");
		customers.whenAnyExchangeReceived(new Processor() {
			@Override
			public void process(Exchange exchng) throws Exception {
				String interceptedURI = exchng.getIn().getHeader("CamelInterceptedEndpoint", String.class);
				assertStringContains(interceptedURI, "id\":\"06bf537b-c7d7-11e7-ff13-2d957f9ff0f0");
			}
		});
	}

	@Test
	public void testServiceInteraction() throws Exception {
		ProducerTemplate producer = this.context().createProducerTemplate();
		producer.sendBody("direct:queue:email-received", requestedEmail);

		createMockVendProducts();
		createMockWebsocket();

		assertMockEndpointsSatisfied();
	}

}