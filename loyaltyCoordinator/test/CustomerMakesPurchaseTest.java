
import builders.CustomerMakesPurchase;
import com.google.gson.Gson;
import domain.Transaction;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import static org.apache.camel.test.junit4.TestSupport.assertStringContains;
import org.junit.Test;

/**
 * A test class for the CustomerMakesPurchase route builder.
 *
 * @author adath325
 */
public class CustomerMakesPurchaseTest extends CamelTestSupport {

	private final String receivedEmail;

	public CustomerMakesPurchaseTest() {
		receivedEmail = "{\"id\":\"9264dfe3-3776-8cc4-11e7-3c30650b46c0\",\"source\":\"USER\",\"source_id\":null,\"sale_date\":\"2017-05-19T07:48:06Z\",\"status\":\"CLOSED\",\"user_id\":\"06bf537b-c7d7-11e7-ff13-2ccd07ae296b\",\"customer_id\":\"06bf537b-c7d7-11e7-ff13-2d957f9ff0f0\",\"register_id\":\"06bf537b-c7d7-11e7-ff13-2cc53d618067\",\"market_id\":\"1\",\"invoice_number\":\"22\",\"short_code\":\"6wisdn\",\"totals\":{\"total_price\":\"173.91304\",\"total_loyalty\":\"0.00000\",\"total_tax\":\"26.08696\",\"total_payment\":\"200.00000\",\"total_to_pay\":\"0.00000\"},\"note\":\"\",\"updated_at\":\"2017-05-19T07:48:07+00:00\",\"created_at\":\"2017-05-19 07:48:07\",\"customer\":{\"id\":\"06bf537b-c7d7-11e7-ff13-2d957f9ff0f0\",\"customer_code\":\"Boris-F39Z\",\"customer_group_id\":\"06bf537b-c77f-11e7-ff13-0c871e85cb6d\",\"first_name\":\"Boris\",\"last_name\":\"Smith\",\"company_name\":\"\",\"email\":\"boris@email.com\",\"phone\":\"\",\"mobile\":\"\",\"fax\":\"\",\"balance\":\"-398.800\",\"loyalty_balance\":\"0.00000\",\"enable_loyalty\":false,\"points\":0,\"note\":\"\",\"year_to_date\":\"100000.00000\",\"sex\":\"M\",\"date_of_birth\":\"1984-07-16\",\"custom_field_1\":\"\",\"custom_field_2\":\"\",\"custom_field_3\":\"\",\"custom_field_4\":\"\",\"updated_at\":\"2017-05-19 07:46:31\",\"created_at\":\"2017-04-30 11:09:37\",\"deleted_at\":null,\"contact_first_name\":\"Boris\",\"contact_last_name\":\"Smith\"},\"user\":{\"id\":\"06bf537b-c7d7-11e7-ff13-2ccd07ae296b\",\"name\":\"adath325\",\"display_name\":\"Thomas Adams\",\"email\":\"adath325@student.otago.ac.nz\",\"outlet_id\":null,\"target_daily\":null,\"target_weekly\":null,\"target_monthly\":null,\"created_at\":\"2017-04-29 11:14:36\",\"updated_at\":\"2017-04-29 11:14:36\"},\"register_sale_products\":[{\"id\":\"9264dfe3-3776-8cc4-11e7-3c6777591413\",\"product_id\":\"06bf537b-c7d7-11e7-ff13-0c871ec9808b\",\"quantity\":1,\"price\":\"173.91304\",\"discount\":\"0.00000\",\"loyalty_value\":\"0.00000\",\"price_set\":false,\"tax\":\"26.08696\",\"price_total\":\"173.91304\",\"tax_total\":\"26.08696\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"}],\"register_sale_payments\":[{\"id\":\"9264dfe3-3776-8cc4-11e7-3c677e874e04\",\"payment_date\":\"2017-05-19T07:48:06Z\",\"amount\":\"200\",\"retailer_payment_type_id\":\"06bf537b-c77f-11e7-ff13-0c871e96482b\",\"payment_type_id\":1,\"retailer_payment_type\":{\"id\":\"06bf537b-c77f-11e7-ff13-0c871e96482b\",\"name\":\"Cash\",\"payment_type_id\":\"1\",\"config\":\"{\\\"rounding\\\":\\\"0.10\\\",\\\"algorithm\\\":\\\"round-mid-down\\\"}\"},\"payment_type\":{\"id\":\"1\",\"name\":\"Cash\",\"has_native_support\":false},\"register_sale\":{\"id\":\"9264dfe3-3776-8cc4-11e7-3c30650b46c0\",\"source\":\"USER\",\"source_id\":null,\"sale_date\":\"2017-05-19T07:48:06Z\",\"status\":\"CLOSED\",\"user_id\":\"06bf537b-c7d7-11e7-ff13-2ccd07ae296b\",\"customer_id\":\"06bf537b-c7d7-11e7-ff13-2d957f9ff0f0\",\"register_id\":\"06bf537b-c7d7-11e7-ff13-2cc53d618067\",\"market_id\":\"1\",\"invoice_number\":\"22\",\"short_code\":\"6wisdn\",\"totals\":{\"total_price\":\"173.91304\",\"total_loyalty\":\"0.00000\",\"total_tax\":\"26.08696\",\"total_payment\":\"200.00000\",\"total_to_pay\":\"0.00000\"},\"note\":\"\",\"updated_at\":\"2017-05-19T07:48:07+00:00\",\"created_at\":\"2017-05-19 07:48:07\"}}],\"taxes\":[{\"id\":\"1e929694-0c87-11e7-bf13-06bf537bc77f\",\"name\":\"GST\",\"rate\":\"0.15000\",\"tax\":26.08696}]}";
	}

	@Override
	protected CamelContext createCamelContext() throws Exception {
		CamelContext camel = super.createCamelContext();
		camel.addComponent("jms", camel.getComponent("direct"));
		return camel;
	}

	private RouteBuilder createInterceptRoutes() {
		return new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				interceptSendToEndpoint("rmi://*")
						  .skipSendToOriginalEndpoint()
						  .log("Mock RMI called")
						  .to("mock:rmi");
				interceptSendToEndpoint("http4://*")
						  .skipSendToOriginalEndpoint()
						  .log("Mock REST called")
						  .to("mock:rest");
			}
		};
	}

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		RouteBuilder routeBuilder = new CustomerMakesPurchase();
		routeBuilder.includeRoutes(createInterceptRoutes());
		return routeBuilder;
	}

	private void createMockRMI() {
		MockEndpoint rmi = getMockEndpoint("mock:rmi");

		rmi.expectedMessageCount(1);

		rmi.whenAnyExchangeReceived(new Processor() {
			@Override
			public void process(Exchange exchng) throws Exception {
				String interceptedURI = exchng.getIn().getHeader(
						  "CamelInterceptedEndpoint", String.class);
				assertStringContains(interceptedURI, "rmi://localhost:1099/sales");
				assertStringContains(interceptedURI, 
						  "remoteInterfaces=server.ISalesAgg");
				assertStringContains(interceptedURI, "method=newSale");
			}
		});
	}

	private void createMockREST() {
		MockEndpoint rest = getMockEndpoint("mock:rest");

		rest.expectedMessageCount(1);

		rest.whenAnyExchangeReceived(new Processor() {
			@Override
			public void process(Exchange exchng) throws Exception {
				String interceptedURI = exchng.getIn().getHeader(
						  "CamelInterceptedEndpoint", String.class);
				assertStringContains(interceptedURI, 
						  "http4://localhost:8081/customers/");
				assertStringContains(interceptedURI, 
						  "06bf537b-c7d7-11e7-ff13-2d957f9ff0f0");

				String httpMethod = exchng.getIn().getHeader("CamelHttpMethod", 
						  String.class);
				assertEquals(httpMethod, "POST");

				String contentType = exchng.getIn().getHeader("Content-Type", 
						  String.class);
				assertEquals(contentType, "application/json");

				String payload = exchng.getIn().getBody(String.class);
				Gson gson = new Gson();
				Transaction t = gson.fromJson(payload, Transaction.class);

				assertEquals(t.getId(), "9264dfe3-3776-8cc4-11e7-3c30650b46c0");
				assertEquals(t.getShop(), "06bf537b-c7d7-11e7-ff13-2cc53797fce3");
				assertEquals(t.getPoints(), (Integer) 20);
			}
		});
	}

	@Test
	public void testServiceInteraction() throws Exception {
		ProducerTemplate producer = this.context().createProducerTemplate();

		createMockRMI();
		createMockREST();

		producer.sendBody("jms:queue:sale-event", receivedEmail);
		assertMockEndpointsSatisfied();
	}

}
