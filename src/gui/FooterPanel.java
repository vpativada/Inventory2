package gui;
/**
 * @author Vasavi Pativada
*/
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import control.Controller;

public class FooterPanel extends JPanel {

	private static final long serialVersionUID = -7966356537880005172L;
	private static JPanel FooterPanel;
	private static JButton btnCheckout;
	private static JButton btnCompleteTransaction;
	private static JLabel carttotal;

	/**
	 * Creates the footer
	 */
	public FooterPanel() {
		FooterPanel = new JPanel();

		carttotal = new JLabel("");
		FooterPanel.add(carttotal);

		btnCheckout = new JButton("Checkout");
		FooterPanel.add(btnCheckout);

		btnCompleteTransaction = new JButton("Complete Purchase");
	}

	/**
	 * Adds listener for the checkout button
	 */
	public void addCheckoutListener(Controller.CheckoutListener listener) {
		btnCheckout.addActionListener(listener);
	}

	/**
	 * Adds listener for the complete purchase button
	 */
	public void completeTransactionListener(Controller.CompleteTransactionListener listner) {
		btnCompleteTransaction.addActionListener(listner);
	}

	/**
	 * Adds complete transaction to the panel
	 */
	public static void addCompleteTransactionBtn(Controller.CompleteTransactionListener completeTransactionListener) {
		FooterPanel.add(btnCompleteTransaction);
	}

	/**
	 * Removes the complete transaction to the panel
	 */
	public void removeCompleteTransactionBtn() {
		FooterPanel.remove(btnCompleteTransaction);
	}

	/**
	 * Adds checkout button to footer panel
	 */
	public static void addCheckoutBtn() {
		FooterPanel.add(btnCheckout);
		FooterPanel.remove(btnCompleteTransaction);
	}

	public void removeCheckoutBtn() {
		FooterPanel.remove(btnCheckout);
		FooterPanel.add(btnCompleteTransaction);
	}

	/**
	 * Returns footer JPanel
	 */
	public JPanel getPanel() {
		return FooterPanel;
	}

	/**
	 * Sets total cost of all items in the cart
	 */
	public static void setTotalText(float cartTotal) {
		carttotal.setText("TOTAL: " + cartTotal);
	}
}
