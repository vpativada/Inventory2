package control;
/**
 * @author Vasavi Pativada
*/
import data.Model;
import gui.View;

public class MVCShoppingCart {

	public static void main(String[] args) {
		Model model = new Model(); 
		View view = new View();
		new Controller(model, view);
		view.setBounds(100, 100, 500, 500);
		view.setVisible(true);
	}
}
