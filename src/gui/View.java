package gui;
/**
 * @author Vasavi Pativada
*/
import javax.swing.JFrame;
import javax.swing.JPanel;

public class View extends JFrame {

	private static final long serialVersionUID = 2421311994314470378L;

	public View() {
		viewRefresh();
	}

	public void addPanel(JPanel panel) {
		getContentPane().add(panel);
	}

	public void removePanel(JPanel panel) {
		getContentPane().remove(panel);
	}

	public void viewRefresh() {
		getContentPane().repaint();
		getContentPane().revalidate();
	}
}
