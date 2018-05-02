package gui;

/**
 * @author Vasavi Pativada
 */
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CheckoutPanel extends JPanel {

	private static final long serialVersionUID = -4432072535879785295L;
	private JPanel CheckoutPanel;
	private static JTextField FirstName;
	private static JTextField LastName;
	private static JTextField Email;
	private static JTextField Address;
	private static JLabel carttotal;
	private static JTextField cc;

	/**
	 * Creates the checkout panel
	 */
	public CheckoutPanel(float total) {
		CheckoutPanel = new JPanel(new WrapLayout());
		
		CheckoutPanel.setPreferredSize(new Dimension(150, 300));
		FirstName = new JTextField();

		CheckoutPanel.add(new JLabel("TOTAL: $" + total));
		CheckoutPanel.add(new JLabel("First Name: "));
		FirstName = new JTextField();
		CheckoutPanel.add(FirstName);
		FirstName.setColumns(10);

		CheckoutPanel.add(new JLabel("Last Name: "));
		LastName = new JTextField();
		CheckoutPanel.add(LastName);
		LastName.setColumns(10);

		CheckoutPanel.add(new JLabel("Email: "));
		Email = new JTextField();
		CheckoutPanel.add(Email);
		Email.setColumns(10);

		CheckoutPanel.add(new JLabel("Address: "));
		Address = new JTextField();
		CheckoutPanel.add(Address);
		Address.setColumns(10);

		CheckoutPanel.add(new JLabel("Credit Card: "));
		cc = new JTextField();
		cc.setColumns(10);
		cc.setText("1234 1234 1234 1234");

		CheckoutPanel.add(cc);

		carttotal = new JLabel("");
		CheckoutPanel.add(carttotal);
	}

	/**
	 * Returns checkoutpanel
	 */
	public JPanel getPanel() {
		return CheckoutPanel;
	}

	/**
	 * Returns first name from the checkout panel
	 */
	public static String getFirstName() {
		try {
			return FirstName.getText();
		} catch (NullPointerException n) {
			return "null";
		}
	}

	/**
	 * Returns last name from the checkout panel
	 */
	public static String getLastName() {
		try {
			return LastName.getText();
		} catch (NullPointerException n) {
			return "null";
		}
	}

	/**
	 * Returns email address from the checkout panel
	 */
	public static String getEmail() {
		try {
			return Email.getText();
		} catch (NullPointerException n) {
			return "null";
		}
	}

	/**
	 * Returns address from the checkout panel
	 */
	public static String getAddress() {
		try {
			return Address.getText();
		} catch (NullPointerException n) {
			return "null";
		}
	}

	/**
	 * Sets shopping cart total value on the checkout panel
	 */
	public static void setTotalText(float cartTotal) {
		carttotal.setText("TOTAL: " + cartTotal);
	}

	/**
	 * Returns credit card from the checkout panel
	 */
	public static String getCreditCard() {
		try {
			return cc.getText();
		} catch (NullPointerException n) {
			return "null";
		}
	}
}
