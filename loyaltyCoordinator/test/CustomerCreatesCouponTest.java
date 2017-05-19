
import builders.CustomerCreatesCoupon;
import org.apache.camel.CamelContext;
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
public class CustomerCreatesCouponTest extends CamelTestSupport {

	private final String createdCoupon;
	private final String createdProduct;

	public CustomerCreatesCouponTest() {
		createdCoupon = "{\"handle\":1495156948665,\"type\":\"coupon\",\"name\":\"adath325 Coupon\",\"retail_price\":-5}";
		createdProduct = "{\"products\":[{\"id\":\"0af7b240-abd7-11e7-eddc-3c31a013ba54\",\"source_id\":\"\",\"variant_source_id\":\"\",\"handle\":\"1495156948665\",\"type\":\"Coupon\",\"has_variants\":false,\"variant_parent_id\":\"\",\"variant_option_one_name\":\"\",\"variant_option_one_value\":\"\",\"variant_option_two_name\":\"\",\"variant_option_two_value\":\"\",\"variant_option_three_name\":\"\",\"variant_option_three_value\":\"\",\"active\":true,\"name\":\"adath325 Coupon\",\"base_name\":\"adath325 Coupon\",\"description\":\"\",\"image\":\"https://info323otago.vendhq.com/images/placeholder/product/no-image-white-thumb.png\",\"image_large\":\"https://info323otago.vendhq.com/images/placeholder/product/no-image-white-original.png\",\"images\":[],\"sku\":\"10566\",\"tags\":\"\",\"brand_id\":\"\",\"brand_name\":\"\",\"supplier_name\":\"\",\"supplier_code\":\"\",\"supply_price\":\"0.00\",\"account_code_purchase\":\"\",\"account_code_sales\":\"\",\"track_inventory\":true,\"button_order\":\"\",\"inventory\":[],\"price_book_entries\":[{\"id\":\"07b41c4f-21db-7dd3-cb70-7936da609380\",\"product_id\":\"0af7b240-abd7-11e7-eddc-3c31a013ba54\",\"price_book_id\":\"06bf537b-c77f-11e7-ff13-0c871e87cd6e\",\"price_book_name\":\"General Price Book (All Products)\",\"type\":\"BASE\",\"outlet_name\":\"\",\"outlet_id\":\"\",\"customer_group_name\":\"All Customers\",\"customer_group_id\":\"06bf537b-c77f-11e7-ff13-0c871e85cb6d\",\"price\":-4.34783,\"loyalty_value\":null,\"tax\":-0.65217,\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\",\"tax_rate\":0.15,\"tax_name\":\"GST\",\"display_retail_price_tax_inclusive\":1,\"min_units\":\"\",\"max_units\":\"\",\"valid_from\":\"\",\"valid_to\":\"\"}],\"price\":-4.34783,\"tax\":-0.65217,\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\",\"tax_rate\":0.15,\"tax_name\":\"GST\",\"taxes\":[{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cbfccecfcbf\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cbff716ca41\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc0375f365d\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc1b5487984\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc1deaff4ca\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc26595c692\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc1fd889603\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc27fbd567c\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc29c181c0b\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc2b69f637e\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc2ca699476\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc2e1ccdba1\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc2f640ca94\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc30beac6d6\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc320c59b65\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc348412b06\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc3719f437c\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc38db61190\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc3abeba818\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc3cda4e809\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc3eb7216ea\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc4150632e9\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc434d785e3\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc44ae9f216\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c77f-11e7-ff13-0c871e939ab5\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc46908e7fa\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-22f05c5578ad\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc4883fdfb4\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc4a384466a\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc4c11615ae\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc4d89fa9e0\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc4f56572e8\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc50b2da27a\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc51fc83e26\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc53797fce3\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc54f679dde\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc5722df835\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc59bcffab7\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"},{\"outlet_id\":\"06bf537b-c7d7-11e7-ff13-2cc5bcc27082\",\"tax_id\":\"06bf537b-c77f-11e7-ff13-0c871e89e399\"}],\"display_retail_price_tax_inclusive\":1,\"updated_at\":\"2017-05-19 01:22:30\",\"deleted_at\":\"\"}]}";
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
			}
		};
	}

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		RouteBuilder routeBuilder = new CustomerCreatesCoupon();
		routeBuilder.includeRoutes(createInterceptRoutes());
		return routeBuilder;
	}

	private void createMockVendProducts() {
		MockEndpoint products = getMockEndpoint("mock:vend");
		products.whenAnyExchangeReceived(new Processor() {
			@Override
			public void process(Exchange exchng) throws Exception {
				String interceptedURI = exchng.getIn().getHeader("CamelInterceptedEndpoint", String.class);
				assertStringContains(interceptedURI, "https4://info323otago.vendhq.com/api/products");
				exchng.getIn().setBody(createdProduct);
			}
		});
	}

	@Test
	public void testServiceInteraction() throws Exception {
		ProducerTemplate producer = this.context().createProducerTemplate();
		producer.sendBody("jms:queue:coupon-received", createdCoupon);

		createMockVendProducts();

		assertMockEndpointsSatisfied();
	}

}
