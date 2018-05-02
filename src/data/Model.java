package data;

/**
 * @author Vasavi Pativada
*/
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class Model {
	private String CSVLocation;

	/**
	 * Add items to the cart
	 */
	public void cartAdd(String id, String name, String type, String price, String invoiceprice) {
		boolean replace = true;
		List<String[]> allLines = getCSV(CSVLocation);
		String[] newCartItem = { "", "", "", "", "", "" };
		if (getStock(name) == 0)
			return;
		try {
			for (String[] product : allLines) {
				String cartName = product[1];
				String cartQuantity = product[5];

				if (cartName.equals(name)) {
					cartQuantity = String.valueOf(Integer.parseInt(cartQuantity) + 1);
					product[5] = cartQuantity;
					replace = false;
				}
			}
		} catch (NullPointerException n) {
			createAccountCSV(CSVLocation);
		}
		if (replace) {
			newCartItem[0] = id;
			newCartItem[1] = name;
			newCartItem[2] = type;
			newCartItem[3] = price;
			newCartItem[4] = invoiceprice;
			newCartItem[5] = String.valueOf(1);
			try {
				allLines.add(newCartItem);
			} catch (NullPointerException n) {
			}
		}
		decrementStock(name);
		writeCSV(allLines, CSVLocation, false);
		return;
	}

	/**
	 * Remove items from the cart
	 */
	public void cartRemove(String id, String name, String type, String price) {
		String filepath = CSVLocation;
		List<String[]> allLines = getCSV(filepath);
		int x = 0;
		try {
			try {
				for (String[] product : allLines) {
					String cartName = product[1];
					String cartQuantity = product[5];

					if (cartName.equals(name)) {
						if (Integer.parseInt(cartQuantity) > 0) {
							cartQuantity = String.valueOf(Integer.parseInt(cartQuantity) - 1);
						} else if (Integer.parseInt(cartQuantity) == 0) {
							allLines.remove(x);
							writeCSV(allLines, filepath, false);
							return;
						}
						product[5] = cartQuantity;
					}
					x++;
				}
			} catch (ConcurrentModificationException c) {
			}
		} catch (NullPointerException n) {
			createAccountCSV(CSVLocation);
		}
		incrementStock(name);
		writeCSV(allLines, filepath, false);
		return;
	}

	/**
	 * Saves checkout information into sales.csv
	 */
	public void completeTransaction(String firstname, String lastname, String cc, String email, String address) {
		List<String[]> sales = getCSV("sales.csv");
		String[] newSale = new String[8];

		newSale[0] = String.valueOf(getCartTotal());
		newSale[1] = String.valueOf(getCartCost());
		newSale[2] = "";
		newSale[3] = firstname;
		newSale[4] = lastname;
		newSale[5] = cc;
		newSale[6] = email;
		newSale[7] = address;
		sales.add(newSale);
		writeCSV(sales, "sales.csv", false);
	}

	/**
	 * Decreases item from the inventory by 1 Remove the last item from shopping cart
	 */
	public void decrementStock(String name) {
		List<String[]> inventory = getCSV("products.csv");
		for (String[] product : inventory) {
			if (product[5].equals(name)) {
				if (product[2].equals("0")) {
				} else {
					product[2] = String.valueOf(Integer.parseInt(product[2]) - 1);
				}
			}
		}
		writeCSV(inventory, "products.csv", false);
	}

	/**
	 * Increases item by adding 1 one item at a time to shopping cart
	 */
	public void incrementStock(String productName) {
		List<String[]> inventory = getCSV("products.csv");
		for (String[] product : inventory) {
			if (product[5].equals(productName)) {
				product[2] = String.valueOf(Integer.parseInt(product[2]) + 1);
			}
		}
		writeCSV(inventory, "products.csv", false);
	}

	/**
	 * Creates user CSV file to hold cart information
	 */
	private void createAccountCSV(String csvlocation) {
		System.out.println("CREATING CSV...");
		FileWriter newfile;
		try {
			newfile = new FileWriter(csvlocation);
			newfile.write("");
			newfile.close();
		} catch (IOException e) {
			System.out.println("CREATE NEW CSV FAILED");
		}
	}

	/**
	 * Get items in the cart
	 */
	public List<String[]> getAccountCart() {
		String AccountCartLocation = CSVLocation;
		try {
			CSVReader reader = new CSVReader(new FileReader(AccountCartLocation));
			List<String[]> readerToReturn = reader.readAll();
			reader.close();
			return readerToReturn;
		} catch (IOException e) {
			getCSV(AccountCartLocation);
		}
		return null;
	}

	public void setCSVLocation() {
		CSVLocation = "root_cart.csv";
	}

	public String getAccountCSVLocation() {
		return CSVLocation;
	}

	/**
	 * converts product list into array
	 */
	public List<String[]> getCSV(String csvlocation) {
		try {
			CSVReader reader = new CSVReader(new FileReader(csvlocation));
			List<String[]> readerToReturn = sortListOfStringArray(reader.readAll());
			reader.close();
			return readerToReturn;
		} catch (FileNotFoundException fnf) {
			createAccountCSV(csvlocation);
			getCSV(csvlocation);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String[]> sortListOfStringArray(List<String[]> readerToReturn) {
		readerToReturn.sort((String[] o1, String[] o2) -> o1[5].compareTo(o2[5]));
		return readerToReturn;
	}

	/**
	 * Writes to a CSV file
	 */
	public void writeCSV(List<String[]> allLines, String csvlocation, boolean replace) {
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(csvlocation, replace));
			try {
				writer.writeAll(allLines);
			} catch (NullPointerException n) {
				System.out.println("csv is empty");
				createAccountCSV(csvlocation);
				writeCSV(allLines, csvlocation, replace);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void clearCSV(String csvlocation) {
		try {
			FileWriter writer = new FileWriter(csvlocation);
			writer.write("");
			writer.close();
		} catch (NullPointerException n) {
			System.out.println("csv is empty");
			createAccountCSV(csvlocation);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean setup() {
		String csvlocation = "root_cart.csv";
		try {
			System.out.println("Creating New File");
			FileWriter newfile = new FileWriter(csvlocation);
			newfile.write("");
			newfile.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * returns the inventory for product otherwise it returns null. 
	 */
	public int getStock(String name) {
		List<String[]> inventory = getCSV("products.csv");
		for (String[] product : inventory) {
			if (product[5].equals(name))
				return Integer.parseInt(product[2]);
		}
		return 0;
	}

	/**
	 * Returns the cart total
	 */
	public float getCartTotal() {
		float total = 0;
		List<String[]> inventory = getCSV(CSVLocation);
		for (String[] product : inventory) {
			total += Float.parseFloat(product[3]) * Integer.parseInt(product[5]);
		}
		return total;
	}

	/**
	 * Returns the total invoice cost on the cart
	 */
	public float getCartCost() {
		float total = 0;
		List<String[]> inventory = getCSV(CSVLocation);
		for (String[] product : inventory) {
			total += Float.parseFloat(product[4]) * Integer.parseInt(product[5]);
		}
		return total;
	}

}
