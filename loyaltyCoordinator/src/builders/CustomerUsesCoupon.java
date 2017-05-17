package builders;

import domain.Coupon;
import domain.Sale;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

/**
 *
 * @author adath325
 */
public class CustomerUsesCoupon extends RouteBuilder {

	@Override
	public void configure() {
		from("jms:queue:sale-event-coupons")
				  .setHeader("customerId").jsonpath("$.customer_id")
				  .unmarshal().json(JsonLibrary.Gson, Sale.class)
				  .split().simple("${body.items}")
				  .to("jms:queue:sale-products");

		from("jms:queue:sale-products")
				  .setProperty("customerId").header("customerId")
				  .setProperty("productId").simple("${body.productId}")
				  .removeHeaders("*")
				  .setBody(constant(null))
				  .setHeader("Authorization", constant("Bearer CjOC4V9CKp10w3EkgLNtR:um8xRZhhaZpRNUXULT"))
				  .setHeader(Exchange.HTTP_METHOD, constant("GET"))
				  .recipientList().simple("https4://info323otago.vendhq.com/api/"
							 + "products/{exchangeProperty.productId}")
				  .multicast()
				  .to("jms:queue:http-response", "direct:queue:full-products");

		from("direct:queue:full-products")
				  .setHeader("productType").jsonpath("$.products[0].type")
				  .setHeader("productHandle").jsonpath("$.products[0].handle")
				  .log("${header.productType} - ${header.productHandle}")
				  .choice()
				  .when().simple("${header.productType} == 'Coupon'")
				  .to("direct:queue:coupons");

		from("direct:queue:coupons-get")
				  .setProperty("productHandle").header("productHandle")
				  .removeHeaders("*")
				  .setHeader(Exchange.HTTP_METHOD, constant("GET"))
				  .recipientList().simple("http4://localhost:8081/customers/"
							 + "${exchangeProperty.customerId}/coupons/"
							 + "${exchangeProperty.productHandle}")
				  .multicast()
				  .to("jms:queue:http-response", "direct:queue:coupons-put");

		from("direct:queue:coupons-put")
				  .log("${body}")
				  .unmarshal().json(JsonLibrary.Gson, Coupon.class)
				  .to("language:simple:${body.setUsed(true)}?transform=true")
				  .marshal().json(JsonLibrary.Gson)
				  .removeHeaders("*")
				  .setHeader(Exchange.HTTP_METHOD, constant("PUT"))
				  .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				  .recipientList().simple("http4://localhost:8081/customers/"
							 + "${exchangeProperty.customerId}/coupons/"
							 + "${exchangeProperty.productHandle}")
				  .multicast()
				  .to("jms:queue:http-response", "direct:queue:coupons-done");
		
		from("direct:queue:coupons-done")
				  .removeHeaders("*")
				  .setBody(constant(null))
				  .setHeader("Authorization", constant("Bearer CjOC4V9CKp10w3EkgLNtR:um8xRZhhaZpRNUXULT"))
				  .setHeader(Exchange.HTTP_METHOD, constant("DELETE"))
				  .recipientList().simple("https4://info323otago.vendhq.com/api/"
							 + "products/{exchangeProperty.productId}")
				  .to("jms:queue:http-response");
	}
}
