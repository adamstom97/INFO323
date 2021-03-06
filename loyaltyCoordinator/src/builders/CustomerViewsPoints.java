package builders;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

/**
 * A route builder for the CustomerViewsPoints component interactions.
 *
 * @author adath325
 */
public class CustomerViewsPoints extends RouteBuilder {

	@Override
	public void configure() {
		from("websocket://localhost:8087/customer")
				  .log("Received new customer email via WebSocket: ${body}")
				  .setProperty("email").body()
				  .to("direct:queue:email-received");

		from("direct:queue:email-received")
				  .removeHeaders("*")
				  .setBody(constant(null))
				  .setHeader("Authorization", constant(
							 "Bearer CjOC4V9CKp10w3EkgLNtR:um8xRZhhaZpRNUXULT"))
				  .setHeader(Exchange.HTTP_METHOD, constant("GET"))
				  .recipientList().simple("https4://info323otago.vendhq.com/api/"
							 + "customers?email=${exchangeProperty.email}")
				  .to("websocket://localhost:8087/customer?sendToAll=true");
	}
}
