package builders;

import domain.Sale;
import domain.Transaction;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

/**
 *
 * @author adath325
 */
public class CustomerMakesPurchase extends RouteBuilder {

	@Override
	public void configure() {
		from("imaps://outlook.office365.com?username=adath325@student.otago.ac.nz"
				  + "&password=" + getPassword("Enter your E-Mail password")
				  + "&searchTerm.subject=Vend:SaleUpdate"
				  + "&debugMode=false&folderName=Inbox")
				  .convertBodyTo(String.class).log("${body}")
				  .multicast()
				  .to("jms:queue:sale-event", "jms:queue:sale-event-coupons");
		
		from("jms:queue:sale-event")
				  .setHeader("transactionId").jsonpath("$.id")
				  .setHeader("customerId").jsonpath("$.customer_id")
				  .setHeader("shop").jsonpath("$.register_id")
				  .setHeader("price").jsonpath("$.totals.total_payment")
				  .multicast()
				  .to("jms:queue:new-sale", "jms:queue:new-transaction");

		from("jms:queue:new-sale").unmarshal().json(JsonLibrary.Gson, Sale.class)
				  .to("rmi://localhost:1099/sales"
							 + "?remoteInterfaces=server.ISalesAgg"
							 + "&method=newSale");

		from("jms:queue:new-transaction")
				  .setHeader("points").method(CustomerMakesPurchase.class, 
							 "calculatePoints(${headers.price})")
				  .to("jms:queue:calculated-points");
		
		from("jms:queue:calculated-points")
				  .bean(CustomerMakesPurchase.class, "createTransaction("
							 + "${headers.transactionId}, ${headers.shop}, "
							 + "${headers.points})")
				  .to("jms:queue:send-transaction");
		
		from("jms:queue:send-transaction")
				  .marshal().json(JsonLibrary.Gson)
				  .setProperty("customerId").header("customerId")
				  .removeHeaders("*")
				  .setHeader(Exchange.HTTP_METHOD, constant("POST"))
				  .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				  .recipientList().simple("http4://localhost:8081/customers/"
							 + "${exchangeProperty.customerId}/transactions")
				  .to("jms:queue:http-response");
	}

	public static String getPassword(String prompt) {
		JPasswordField txtPasswd = new JPasswordField();
		int resp = JOptionPane.showConfirmDialog(null, txtPasswd, prompt,
				  JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (resp == JOptionPane.OK_OPTION) {
			String password = new String(txtPasswd.getPassword());
			return password;
		}
		return null;
	}
	
	public Integer calculatePoints(Double price) {
		return price.intValue() / 10;
	}
	
	public Transaction createTransaction(String id, String shop, 
			  Integer points) {
		return new Transaction(id, shop, points);
	}
}
