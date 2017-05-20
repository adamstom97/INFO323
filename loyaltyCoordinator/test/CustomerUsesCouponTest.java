
import builders.CustomerUsesCoupon;
import com.google.gson.Gson;
import domain.Coupon;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import static org.apache.camel.test.junit4.TestSupport.assertStringContains;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author adath325
 */
public class CustomerUsesCouponTest extends CamelTestSupport {

	private final String receivedEmail;
	private final String createdProduct;
	private final String statusReturned;
	private final String couponReturned;

	public CustomerUsesCouponTest() {
		receivedEmail = "{\"id\":\"9264dfe3-3776-b610-11e7-3cf4369569b1\",\"source\":\"USER\",\"source_id\":null,\"sale_date\":\"2017-05-20T00:35:38Z\",\"status\":\"CLOSED\",\"user_id\":\"06bf537b-c7d7-11e7-ff13-2ccd07ae296b\",\"customer_id\":\"06bf537b-c7d7-11e7-ff13-2d957f9ff0f0\",\"register_id\":\"06bf537b-c7d7-11e7-ff13-2cc53d618067\",\"market_id\":\"1\",\"invoice_number\":\"27\",\"short_code\":\"vai1g3\",\"totals\":{\"total_price\":\"-4.34783\",\"total_loyalty\":\"0.00000\",\"total_tax\":\"-0.65217\",\"total_payment\":\"-5.00000\",\"total_to_pay\":\"0.00000\"},\"note\":\"\",\"updated_at\":\"2017-05-20T00:35:40+00:00\",\"created_at\":\"2017-05-20 00:35:40\",\"customer\":{\"id\":\"06bf537b-c7d7-11e7-ff13-2d957f9ff0f0\",\"customer_code\":\"Boris-F39Z\",\"customer_group_id\":\"06bf537b-c77f-11e7-ff13-0c871e85cb6d\",\"first_name\":\"Boris\",\"last_name\":\"Smith\",\"company_name\":\"\",\"email\":\"boris@email.com\",\"phone\":\"\",\"mobile\":\"\",\"fax\":\"\",\"balance\":\"-598.790\",\"loyalty_balance\":\"0.00000\",\"enable_loyalty\":false,\"points\":0,\"note\":\"\",\"year_to_date\":\"100000.00000\",\"sex\":\"M\",\"date_of_birth\":\"1984-07-16\",\"custom_field_1\":\"\",\"custom_field_2\":\"\",\"custom_field_3\":\"\",\"custom_field_4\":\"\",\"updated_at\":\"2017-05-20 00:35:13\",\"created_at\":\"2017-04-30 11:09:37\",\"deleted_at\":null,\"contact_first_name\":\"Boris\",\"contact_last_name\":\"Smith\"},\"user\":{\"id\":\"06bf537b-c7d7-11e7-ff13-2ccd07ae296b\",\"name\":\"adath325\",\"display_name\":\"Thomas Adams\",\"email\":\"adath325@student.otago.ac.nz\",\"outlet_id\":null,\"target_daily\":null,\"target_weekly\":null,\"target_monthly\":null,\"created_at\":\"2017-04-29 11:14:36\",\"updated_at\":\"2017-04-29 11:14:36\"},\"register_sale_products\":[{\"id\":\"9264dfe3-3776-b610-11e7-3cf438350ff4\",\"product_id\":\"0af7b240-abd7-11e7-eddc-3cf42e321e48\",\"quantity\":1,\"price\":\"-4.34783\",\"discount\":\"0.00000\",\"loyalty_value\":\"0.00000\",\"price_set\":false,\"tax\":\"-0.65217\",\"price_total\":\"-4.34783\",\"tax_total\":\"-0.65217\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"}],\"register_sale_payments\":[{\"id\":\"9264dfe3-3776-b610-11e7-3cf43f257d95\",\"payment_date\":\"2017-05-20T00:35:38Z\",\"amount\":\"-5\",\"retailer_payment_type_id\":\"06bf537b-c77f-11e7-ff13-0c871e96482b\",\"payment_type_id\":1,\"retailer_payment_type\":{\"id\":\"06bf537b-c77f-11e7-ff13-0c871e96482b\",\"name\":\"Cash\",\"payment_type_id\":\"1\",\"config\":\"{\\\"rounding\\\":\\\"0.10\\\",\\\"algorithm\\\":\\\"round-mid-down\\\"}\"},\"payment_type\":{\"id\":\"1\",\"name\":\"Cash\",\"has_native_support\":false},\"register_sale\":{\"id\":\"9264dfe3-3776-b610-11e7-3cf4369569b1\",\"source\":\"USER\",\"source_id\":null,\"sale_date\":\"2017-05-20T00:35:38Z\",\"status\":\"CLOSED\",\"user_id\":\"06bf537b-c7d7-11e7-ff13-2ccd07ae296b\",\"customer_id\":\"06bf537b-c7d7-11e7-ff13-2d957f9ff0f0\",\"register_id\":\"06bf537b-c7d7-11e7-ff13-2cc53d618067\",\"market_id\":\"1\",\"invoice_number\":\"27\",\"short_code\":\"vai1g3\",\"totals\":{\"total_price\":\"-4.34783\",\"total_loyalty\":\"0.00000\",\"total_tax\":\"-0.65217\",\"total_payment\":\"-5.00000\",\"total_to_pay\":\"0.00000\"},\"note\":\"\",\"updated_at\":\"2017-05-20T00:35:40+00:00\",\"created_at\":\"2017-05-20 00:35:40\"}}],\"taxes\":[{\"id\":\"1e929694-0c87-11e7-bf13-06bf537bc77f\",\"name\":\"GST\",\"rate\":\"0.15000\",\"tax\":-0.65217}]}";
		createdProduct = "{\"products\":[{\"id\":\"0af7b240-abd7-11e7-eddc-3cf42e321e48\",\"source_id\":\"\",\"variant_source_id\":\"\",\"handle\":\"1495240509420\",\"type\":\"Coupon\",\"has_variants\":false,\"variant_parent_id\":\"\",\"variant_option_one_name\":\"\",\"variant_option_one_value\":\"\",\"variant_option_two_name\":\"\",\"variant_option_two_value\":\"\",\"variant_option_three_name\":\"\",\"variant_option_three_value\":\"\",\"active\":true,\"name\":\"adath325 Coupon\",\"base_name\":\"adath325 Coupon\",\"description\":\"\",\"image\":\"https://info323otago.vendhq.com/images/placeholder/product/no-image-white-thumb.png\",\"image_large\":\"https://info323otago.vendhq.com/images/placeholder/product/no-image-white-original.png\",\"images\":[],\"sku\":\"10682\",\"tags\":\"\",\"brand_id\":\"\",\"brand_name\":\"\",\"supplier_name\":\"\",\"supplier_code\":\"\",\"supply_price\":\"0.00\",\"account_code_purchase\":\"\",\"account_code_sales\":\"\",\"track_inventory\":true,\"button_order\":\"\",\"inventory\":[{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc53797fce3\",\"outlet_name\":\"Tom&#039;s outlet\",\"count\":\"-1.00000\",\"reorder_point\":\"\",\"restock_level\":\"\"}],\"price_book_entries\":[{\"id\":\"b1713cbd-a373-5f62-e0d5-dae3a43c0ca9\",\"product_id\":\"0af7b240-abd7-11e7-eddc-3cf42e321e48\",\"price_book_id\":\"06bf537b-c77f-11e7-ff13-0c871e87cd6e\",\"price_book_name\":\"General Price Book (All Products)\",\"type\":\"BASE\",\"outlet_name\":\"\",\"outlet_id\":\"\",\"customer_group_name\":\"All Customers\",\"customer_group_id\":\"06bf537b-c77f-11e7-ff13-0c871e85cb6d\",\"price\":-4.34783,\"loyalty_value\":null,\"tax\":-0.65217,\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\",\"tax_rate\":0.15,\"tax_name\":\"GST\",\"display_retail_price_tax_inclusive\":1,\"min_units\":\"\",\"max_units\":\"\",\"valid_from\":\"\",\"valid_to\":\"\"}],\"price\":-4.34783,\"tax\":-0.65217,\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\",\"tax_rate\":0.15,\"tax_name\":\"GST\",\"taxes\":[{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cbfccecfcbf\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cbff716ca41\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc0375f365d\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc1b5487984\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc1deaff4ca\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc26595c692\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc1fd889603\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc27fbd567c\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc29c181c0b\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc2b69f637e\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc2ca699476\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc2e1ccdba1\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc2f640ca94\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc30beac6d6\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc320c59b65\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc348412b06\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc3719f437c\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc38db61190\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc3abeba818\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc3cda4e809\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc3eb7216ea\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc4150632e9\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc434d785e3\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc44ae9f216\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c77f-11e7-ff13-0c871e939ab5\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc46908e7fa\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-22f05c5578ad\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc4883fdfb4\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc4a384466a\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc4c11615ae\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc4d89fa9e0\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc4f56572e8\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc50b2da27a\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc51fc83e26\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc53797fce3\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc54f679dde\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc5722df835\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc59bcffab7\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc5bcc27082\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"}],\"display_retail_price_tax_inclusive\":1,\"updated_at\":\"2017-05-20 00:35:11\",\"deleted_at\":\"\"}]}";
		statusReturned = "{\"status\":\"success\"}";
		couponReturned = "<coupon><id>1495240509420</id><points>5</points><used>false</used></coupon>";
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
				interceptSendToEndpoint("https4://*")
						  .skipSendToOriginalEndpoint()
						  .log("Mock Vend called")
						  .to("mock:vend");
				interceptSendToEndpoint("http4://*")
						  .skipSendToOriginalEndpoint()
						  .log("Mock REST called")
						  .to("mock:rest");
			}
		};
	}

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		RouteBuilder routeBuilder = new CustomerUsesCoupon();
		routeBuilder.includeRoutes(createInterceptRoutes());
		return routeBuilder;
	}

	private void createMockVend() {
		MockEndpoint vend = getMockEndpoint("mock:vend");

		vend.expectedMessageCount(2);

		vend.whenAnyExchangeReceived(new Processor() {
			@Override
			public void process(Exchange exchng) throws Exception {
				String interceptedURI = exchng.getIn().getHeader("CamelInterceptedEndpoint", String.class);
				assertStringContains(interceptedURI, "https4://info323otago.vendhq.com/api/products");
				assertStringContains(interceptedURI, "0af7b240-abd7-11e7-eddc-3cf42e321e48");

				String httpMethod = exchng.getIn().getHeader("CamelHttpMethod", String.class);
				if (httpMethod.equals("GET")) {
					exchng.getIn().setBody(createdProduct);
				} else if (httpMethod.equals("DELETE")) {
					exchng.getIn().setBody(statusReturned);
				} else {
					fail("Invalid HTTP method");
				}
			}
		});
	}

	private void createMockREST() {
		MockEndpoint rest = getMockEndpoint("mock:rest");

		rest.expectedMessageCount(2);

		rest.whenAnyExchangeReceived(new Processor() {
			@Override
			public void process(Exchange exchng) throws Exception {
				String interceptedURI = exchng.getIn().getHeader("CamelInterceptedEndpoint", String.class);
				assertStringContains(interceptedURI, "http4://localhost:8081/customers/");
				assertStringContains(interceptedURI, "06bf537b-c7d7-11e7-ff13-2d957f9ff0f0");
				assertStringContains(interceptedURI, "1495240509420");

				String httpMethod = exchng.getIn().getHeader("CamelHttpMethod", String.class);
				if (httpMethod.equals("GET")) {
					exchng.getIn().setBody(couponReturned);
				} else if (httpMethod.equals("PUT")) {
					String contentType = exchng.getIn().getHeader("Content-Type", String.class);
					assertEquals(contentType, "application/json");

					String payload = exchng.getIn().getBody(String.class);
					Gson gson = new Gson();
					Coupon c = gson.fromJson(payload, Coupon.class);

					assertEquals(c.getId(), (Long) 1495240509420L);
					assertEquals(c.getPoints(), (Integer) 5);
					assertEquals(c.getUsed(), true);
				} else {
					fail("Invalid HTTP method");
				}
			}
		});
	}

	@Test
	public void testServiceInteraction() throws Exception {
		ProducerTemplate producer = this.context().createProducerTemplate();

		createMockVend();
		createMockREST();
		
		producer.sendBody("jms:queue:sale-event-coupons", receivedEmail);
		assertMockEndpointsSatisfied();
	}
}
