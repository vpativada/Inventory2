package gui;
/**
 * @author Vasavi Pativada
*/
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NavPanel extends JPanel {

	private static final long serialVersionUID = 657242257303212071L;
	private JPanel NavPanel;
	private JButton btnStore;
	private JButton btnInventory;
	private JButton btnFinance; 
	private JButton btnCheckout;
    private JLabel welcome;


	public NavPanel(String accountTypeString) { 
		NavPanel = new JPanel();

		welcome = new JLabel("");
		NavPanel.add(welcome);
		if(accountTypeString.equals("Admin")){
			createAdminButtons();
		}else{
			createUserButtons();
		}
	}

	public void addStoreListener(ActionListener listener) {
		btnStore.addActionListener(listener);
	}
	
	public void addCartListener(ActionListener listener) {
		btnCheckout.addActionListener(listener);
	}

	public JPanel getPanel() {
		return NavPanel;
	}

	public void createAdminButtons(){
		btnInventory = new JButton("Inventory");
		NavPanel.add(btnInventory);
		
		btnFinance = new JButton("Finance");
		NavPanel.add(btnFinance);

		btnStore = new JButton("Store");
		NavPanel.add(btnStore);
		
		btnCheckout = new JButton("Shopping Cart");
		NavPanel.add(btnCheckout);
	}
	
	public void createUserButtons(){
		btnStore = new JButton("Store");
		NavPanel.add(btnStore);

		btnCheckout = new JButton("Shopping Cart");
		NavPanel.add(btnCheckout);
	}
}
