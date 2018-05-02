package gui;

/**
 * @author Vasavi Pativada
*/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import control.Controller;

public class StorePanel extends JPanel {

	private static final long serialVersionUID = 4457600231521877900L;
	private JPanel store;
	private JPanel ProductsPanel;
	private JScrollPane scrollPane;
	private NavPanel NavPanel;
	private CheckoutPanel CheckoutPanel;
	private FooterPanel FooterPanel;
	private int storeStatus = 0;

	public StorePanel() {
		createStorePanel();
		createProductsPanel();
		createNavigationPanel();
		createFooterPanel();
		scrollPane = new JScrollPane(store);
	}

	/**
	 * Displays the products from the product model
	 */
	public void viewProducts(List<String[]> products, Controller.BuyNowListener[] buyNowListener) {
		int x = 0;
		removeProductsFromDisplay();
		for (String[] product : products) {
			if (product[0].equals("ID")) {
				continue;
			}
			++x;
			ProductPanel newproduct = new ProductPanel(product[0], product[1], product[2], product[3], product[4],
					product[5], product[6], buyNowListener[x]);
			ProductsPanel.add(newproduct.getPanel());
		}
		store.add(ProductsPanel, BorderLayout.CENTER);
		storeStatus = 1;
	}

	/**
	 * Displays the cart view
	 */
	public void viewCart(List<String[]> accountCart, Controller.IncrementListener[] incrementListener,
			Controller.DecrementListener[] decrementListener) {
		removeProductsFromDisplay();
		int x = 0;
		for (String[] product : accountCart) {
			if (product[0].equals("ID")) {
				continue;
			}
			ProductPanel newproduct = new ProductPanel(product[0], product[1], product[2], product[3], product[4],
					product[5], incrementListener[x], decrementListener[x]);
			++x;
			ProductsPanel.add(newproduct.getPanel());
		}
		store.add(ProductsPanel, BorderLayout.CENTER);
		storeStatus = 2;
	}

	public void viewCheckout(float total) {
		removeProductsFromDisplay();
		CheckoutPanel = new CheckoutPanel(total);
		ProductsPanel.add(CheckoutPanel.getPanel());
		store.add(ProductsPanel, BorderLayout.CENTER);
		storeStatus = 3;
	}

	/**
	 * Removes products from display
	 */
	public void removeProductsFromDisplay() {
		ProductsPanel.removeAll();
		store.remove(ProductsPanel);
	}

	/**
	 * Returns panel of the store
	 */
	public JPanel getPanel() {
		return store;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	/**
	 * Returns panel of the navigation.
	 */
	public NavPanel getNav() {
		return NavPanel;
	}

	/**
	 * Returns the panel of the navigation.
	 */
	public CheckoutPanel getCheckout() {
		return CheckoutPanel;
	}

	/**
	 * Returns footer JPanel
	 */
	public FooterPanel getFooter() {
		return FooterPanel;
	}

	/**
	 * Creates the store panel
	 */
	public void createStorePanel() {
		store = new JPanel();
		store.setLayout(new BorderLayout());
		store.setBorder(new EmptyBorder(10, 5, 10, 5));
	}

	/**
	 * Creates the products panel.
	 */
	public void createProductsPanel() {
		ProductsPanel = new JPanel();
		ProductsPanel.setLayout(new WrapLayout());
		ProductsPanel.setBackground(new Color(255, 255, 255));
		ProductsPanel.setBounds(100, 100, 100, 100);
	}

	private void createNavigationPanel() {
		NavPanel = new NavPanel("User");
		NavPanel.setLayout(new FlowLayout(0, 10, 10));
		NavPanel.setBackground(new Color(0, 0, 0));
		store.add(NavPanel.getPanel(), BorderLayout.NORTH);
	}

	private void createFooterPanel() {
		FooterPanel = new FooterPanel();
		FooterPanel.setLayout(new FlowLayout(0, 10, 10));
		FooterPanel.setBackground(new Color(0, 0, 0));
		store.add(FooterPanel.getPanel(), BorderLayout.SOUTH);
	}

	public String getCurrentView() {
		if (storeStatus == 5)
			return "Finance";
		else if (storeStatus == 4)
			return "Admin";
		else if (storeStatus == 3)
			return "Checkout";
		else if (storeStatus == 2)
			return "Cart";
		else if (storeStatus == 1)
			return "Store";
		return "Login";
	}
}
