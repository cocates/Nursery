package model;

public class OrderDetail {
	private int myOrderID, myItemID, myQuantity;
	private double mySubtotal;
	private String myItemName;
	
	public OrderDetail(int theOrder, int theItemID, String theName, int theQuantity, double theSubtotal) {
		setOrderID(theOrder);
		setItemID(theItemID);
		setName(theName);
		setQuantity(theQuantity);
		setSubtotal(theSubtotal);
	}
	
	private void setOrderID(int theOrderID) {
		myOrderID = theOrderID;
	}
	
	private void setItemID(int theItemID) {
		myItemID = theItemID;
	}
	
	private void setName(String theItemName) {
		myItemName = theItemName;
	}
	
	private void setQuantity(int theQuantity) {
		myQuantity = theQuantity;
	}
	
	private void setSubtotal(double theSubtotal) {
		mySubtotal = theSubtotal;
	}
	
	public int getOrderID() {
		return myOrderID;
	}
	
	public int getItemID() {
		return myItemID;
	}
	
	public String getName() {
		return myItemName;
	}
	
	public int getQuantity() {
		return myQuantity;
	}
	
	public double getSubtotal() {
		return mySubtotal;
	}
}
