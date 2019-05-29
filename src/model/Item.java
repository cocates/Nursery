package model;

public class Item {
	private int id, stock, ghstock;
	private double price;
	private String name, description;
	
	public Item(int myID, String myName, String myDescription, int myStock, int myGHStock, double myPrice) {
		setID(myID);
		setName(myName);
		setDescription(myDescription);
		setStock(myStock);
		setGHStock(myGHStock);
		setPrice(myPrice);
	}

	private void setID(int myID) {
		id = myID;
	}

	private void setName(String myName) {
		if (myName != null) name = myName;
		else throw new IllegalArgumentException("Invalid item name.");
	}

	private void setDescription(String myDescription) {
		if(!myDescription.isEmpty()) description = myDescription;
		else description = "No description available.";
	}

	private void setStock(int myStock) {
		stock = myStock;
//		if (myStock >= 0) stock = myStock;
//		else throw new IllegalArgumentException("Please enter a valid quantity");
	}

	private void setGHStock(int myGHStock) {
		ghstock = myGHStock;
//		if (myGHStock >= 0) ghstock = myGHStock;
//		else throw new IllegalArgumentException("Please enter a valid quantity.");
	}

	private void setPrice(double myPrice) {
		price = myPrice;
//		if (myPrice >= 0) price = myPrice;
//		else throw new IllegalArgumentException("Please enter a valid price.");
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getStock() {
		return stock;
	}

	public int getGreenHouse() {
		return ghstock;
	}
	
	public double getPrice() {
		return price;
	}
}
