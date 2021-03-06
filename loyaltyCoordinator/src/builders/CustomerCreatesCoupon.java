package builders;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

/**
 * A route builder for the CustomerCreatesCoupon component interactions.
 *
 * @author adath325
 */
public class CustomerCreatesCoupon extends RouteBuilder {

	@Override
	public void configure() {
		from("websocket://localhost:8087/product")
				  .log("Received new coupon via WebSocket: ${body}")
				  .to("jms:queue:coupon-received");

		from("jms:queue:coupon-received")
				  .removeHeaders("*")
				  .setHeader("Authorization", constant(
							 "Bearer CjOC4V9CKp10w3EkgLNtR:um8xRZhhaZpRNUXULT"))
				  .setHeader(Exchange.HTTP_METHOD, constant("POST"))
				  .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				  .to("https4://info323otago.vendhq.com/api/products");
	}
}
