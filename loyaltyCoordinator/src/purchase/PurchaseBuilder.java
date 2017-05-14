package purchase;

import domain.Sale;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

/**
 *
 * @author adath325
 */
public class PurchaseBuilder extends RouteBuilder {

	@Override
	public void configure() {
		from("imaps://outlook.office365.com?username=adath325@student.otago.ac.nz"
				  + "&password=" + getPassword("Enter your E-Mail password")
				  + "&searchTerm.subject=Vend:SaleUpdate"
				  + "&debugMode=false&folderName=Inbox")
				  .convertBodyTo(String.class).log("${body}")
				  .to("jms:queue:sale-event");
		from("jms:queue:sale-event")
				  .setHeader("id").jsonpath("$.id")
				  .setHeader("customerId").jsonpath("$.customer_id")
				  .setHeader("price").jsonpath("$.totals.total_price")
				  .multicast()
				  .to("jms:queue:new-sale", "jms:queue:new-transaction");

		from("jms:queue:new-sale").unmarshal().json(JsonLibrary.Gson, Sale.class)
				  .to("rmi://localhost:1099/sales"
							 + "?remoteInterfaces=server.ISalesAgg"
							 + "&method=newSale");
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
}
