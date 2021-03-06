
import builders.CustomerCreatesCoupon;
import builders.CustomerViewsPoints;
import builders.CustomerMakesPurchase;
import builders.CustomerUsesCoupon;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * The router for the loyaltyCoordinator, taking the routes built in the four
 * RouteBuilder classes in the builders package.
 *
 * @author adath325
 */
public class LoyaltyRouter {

	public static void main(String[] args) throws Exception {
		CamelContext camel = new DefaultCamelContext();

		ActiveMQConnectionFactory activeMqFactory = new ActiveMQConnectionFactory(
				  "tcp://localhost:61616");
		camel.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(
				  activeMqFactory));

		activeMqFactory.setTrustAllPackages(true);

		camel.addRoutes(new CustomerCreatesCoupon());
		camel.addRoutes(new CustomerViewsPoints());
		camel.addRoutes(new CustomerMakesPurchase());
		camel.addRoutes(new CustomerUsesCoupon());

		camel.setTracing(false);
		camel.setStreamCaching(true);

		System.out.println("Starting router...");
		camel.start();
		System.out.println("... ready.");
	}
}
