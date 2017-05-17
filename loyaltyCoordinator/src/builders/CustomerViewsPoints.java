package builders;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

/**
 *
 * @author adath325
 */
public class CustomerViewsPoints extends RouteBuilder {

	@Override
	public void configure() {
		from("websocket://localhost:8087/customer")
				  .log("Received new customer email via WebSocket: ${body}")
				  .setProperty("email").body()
				  .removeHeaders("*")
				  .setBody(constant(null))
				  .setHeader("Authorization", constant("Bearer CjOC4V9CKp10w3EkgLNtR:um8xRZhhaZpRNUXULT"))
				  .setHeader(Exchange.HTTP_METHOD, constant("GET"))
				  .recipientList().simple("https4://info323otago.vendhq.com/api/"
							 + "customers?email=${exchangeProperty.email}")
				  .multicast()
				  .to("websocket://localhost:8087/customer?sendToAll=true", "jms:queue:http-response");
	}
}
