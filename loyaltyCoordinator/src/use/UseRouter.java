package use;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

/**
 *
 * @author adath325
 */
public class UseRouter {

	public static void main(String[] args) throws Exception {
		CamelContext camel = new DefaultCamelContext();

		ActiveMQConnectionFactory activeMqFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		camel.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(activeMqFactory));

		activeMqFactory.setTrustAllPackages(true);

		camel.addRoutes(new UseBuilder());

		camel.setTracing(false);
		camel.setStreamCaching(true);

		System.out.println("Starting router...");
		camel.start();
		System.out.println("... ready.");
	}
}
