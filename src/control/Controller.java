package control;
/**
 * @author Vasavi Pativada
*/
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import data.Model;
import gui.CheckoutPanel;
import gui.FooterPanel;
import gui.StorePanel;
import gui.View;

public class Controller {
	private Model model;
	private View view;
	private StorePanel store;

	/**
	 * controls the flow of the application
	 */
	public Controller(Model model, View view) {
		model.setup();
		this.model = model;
		this.view = view;
		store = new StorePanel();
		store.getNav().addStoreListener(new StoreListener());
		store.getNav().addCartListener(new CartListener());
		store.getFooter().addCheckoutListener(new CheckoutListener());
		store.getFooter().completeTransactionListener(new CompleteTransactionListener());
		model.setCSVLocation();
		view.addPanel(store.getPanel());
		store.viewProducts(model.getCSV("products.csv"), createBuyNowListeners(model.getCSV("products.csv").size()));
		view.viewRefresh();
	}

	class StoreListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			displayStore();
		}
	}

	class CartListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			displayCart();
		}
	}

	public class BuyNowListener implements ActionListener {
		private String ID;
		private String Name;
		private String Type;
		private String Price;
		private String InvoicePrice;

		public BuyNowListener(String id, String name, String type, String price, String invoiceprice) {
			ID = id;
			Name = name;
			Type = type;
			Price = price;
			InvoicePrice = invoiceprice;
		}

		public void actionPerformed(ActionEvent e) {
			if (model.getStock(Name) == 0) {
			} else {
				model.cartAdd(ID, Name, Type, Price, InvoicePrice);
				store.viewProducts(model.getCSV("products.csv"),
						createBuyNowListeners(model.getCSV("products.csv").size()));
				FooterPanel.setTotalText(model.getCartTotal());
				view.viewRefresh();
			}
		}
	}

	// shopping cart increment the items listener

	public class IncrementListener implements ActionListener {
		private String ID;
		private String Name;
		private String Type;
		private String Price;
		private String InvoicePrice;

		public IncrementListener(String id, String name, String type, String price, String invoiceprice) {
			ID = id;
			Name = name;
			Type = type;
			Price = price;
		}

		public void actionPerformed(ActionEvent e) {
			model.incrementStock(Name);
			model.cartAdd(ID, Name, Type, Price, InvoicePrice);
			store.viewCart(model.getCSV(model.getAccountCSVLocation()),
					createIncrementListeners(model.getCSV(model.getAccountCSVLocation()).size()),
					createDecrementListeners(model.getCSV(model.getAccountCSVLocation()).size()));
			FooterPanel.setTotalText(model.getCartTotal());
			view.viewRefresh();
		}
	}

	// shopping cart decrement the items listener
	public class DecrementListener implements ActionListener {
		private String ID;
		private String Name;
		private String Type;
		private String Price;

		public DecrementListener(String id, String name, String type, String price) {
			ID = id;
			Name = name;
			Type = type;
			Price = price;
		}

		public void actionPerformed(ActionEvent e) {
			model.cartRemove(ID, Name, Type, Price);
			store.viewCart(model.getCSV(model.getAccountCSVLocation()),
					createIncrementListeners(model.getCSV(model.getAccountCSVLocation()).size()),
					createDecrementListeners(model.getCSV(model.getAccountCSVLocation()).size()));
			FooterPanel.setTotalText(model.getCartTotal());
			view.viewRefresh();
		}
	}

	public class CheckoutListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			displayCheckout();
			model.getCartTotal();
		}
	}

	public class CompleteTransactionListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			completeTransaction();
		}
	}

	/**
	 * Creates buy now listeners.
	 *
	 */
	private BuyNowListener[] createBuyNowListeners(int amount) {
		BuyNowListener[] buynowlistenerarray = new BuyNowListener[amount];
		if (model.getCSV("products.csv").isEmpty())
			return null;
		else {
			int count = 0;
			for (String[] product : model.getCSV("products.csv")) {
				if (product[0].equals("ID")) {
					continue;
				}
				++count;
				String name = product[5];
				String price = product[4];
				String invoiceprice = product[3];
				String type = product[1];
				String id = product[0];
				buynowlistenerarray[count] = new BuyNowListener(id, name, type, price, invoiceprice);
			}
			return buynowlistenerarray;
		}
	}

	private IncrementListener[] createIncrementListeners(int amount) {
		IncrementListener[] incrementlistenerarray = new IncrementListener[amount];
		if (model.getCSV(model.getAccountCSVLocation()).isEmpty())
			return null;
		else {
			int count = 0;
			for (String[] product : model.getCSV(model.getAccountCSVLocation())) {
				if (product[0].equals("ID")) {
					continue;
				}
				String id = product[0];
				String name = product[1];
				String type = product[2];
				String price = product[3];
				String invoiceprice = product[4];
				incrementlistenerarray[count] = new IncrementListener(id, name, type, price, invoiceprice);
				++count;
			}
			return incrementlistenerarray;
		}
	}

	private DecrementListener[] createDecrementListeners(int amount) {
		DecrementListener[] decrementlistenerarray = new DecrementListener[amount];
		if (model.getCSV(model.getAccountCSVLocation()).isEmpty())
			return null;
		else {
			int count = 0;
			for (String[] product : model.getCSV(model.getAccountCSVLocation())) {
				if (product[0].equals("ID")) {
					continue;
				}
				String id = product[0];
				String name = product[1];
				String type = product[2];
				String price = product[3];
				decrementlistenerarray[count] = new DecrementListener(id, name, type, price);
				++count;
			}
			return decrementlistenerarray;
		}
	}

	/**
	 * Displays the store 
	 */
	public void displayStore() {
		FooterPanel.setTotalText(model.getCartTotal());
		if (store.getCurrentView().equals("Store")) {
			System.out.println("You're already on the store page.");
		} else {
			store.getPanel().add(store);
			view.add(store.getPanel());
			store.removeProductsFromDisplay();
			store.viewProducts(model.getCSV("products.csv"),
					createBuyNowListeners(model.getCSV("products.csv").size()));
			FooterPanel.addCheckoutBtn();
			view.viewRefresh();
		}
	}

	/**
	 * Display the cart
	 */
	public void displayCart() {
		if (store.getCurrentView().equals("Cart")) {
			System.out.println("You're already on the cart page.");
		} else {
			view.addPanel(store.getPanel());
			store.removeProductsFromDisplay();
			store.viewCart(model.getAccountCart(),
					createIncrementListeners(model.getCSV(model.getAccountCSVLocation()).size()),
					createDecrementListeners(model.getCSV(model.getAccountCSVLocation()).size()));
			FooterPanel.addCheckoutBtn();
			view.viewRefresh();
		}
	}

	/**
	 * Display the checkout
	 */
	public void displayCheckout() {
		store.removeProductsFromDisplay();
		store.viewCheckout(model.getCartTotal());
		store.getCheckout();
		store.getFooter();
		CheckoutPanel.setTotalText(model.getCartTotal());
		FooterPanel.addCompleteTransactionBtn(new CompleteTransactionListener());
		store.getFooter().removeCheckoutBtn();
		store.viewCheckout(model.getCartTotal());
		view.viewRefresh();
	}

	public void completeTransaction() {
		store.getCheckout();
		model.completeTransaction(CheckoutPanel.getFirstName(), CheckoutPanel.getLastName(),
				CheckoutPanel.getCreditCard(), CheckoutPanel.getEmail(), CheckoutPanel.getAddress());
		model.clearCSV(model.getAccountCSVLocation());

		store.removeProductsFromDisplay();
		store.viewProducts(model.getCSV("products.csv"), createBuyNowListeners(model.getCSV("products.csv").size()));
		FooterPanel.addCheckoutBtn();
		FooterPanel.setTotalText(model.getCartTotal());
		view.viewRefresh();
	}

}
