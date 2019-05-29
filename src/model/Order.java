package model;

public class Order {
	private int myID, myEmployee, myCustomer;
	private double myTotal;

	
	public Order(int theID, int theEmployee, double theTotal) {
		setID(theID);
		setEmployee(theEmployee);
		setTotal(theTotal);
	}

	public Order(int theID, int theEmployee, int theCustomer, double theTotal) {
		this(theID, theEmployee, theTotal);
		setCustomer(theCustomer);
	}
	
	private void setID(int theID) {
		myID = theID;
	}
	
	private void setEmployee(int theEmployee) {
		myEmployee = theEmployee;
	}
	
	private void setCustomer(int theCustomer) {
		myCustomer = theCustomer;	// NULLABLE
	}

	private void setTotal(double theTotal) {
		myTotal = theTotal;
	}
	
	public int getOrderID() {
		return myID;
	}
	
	public int getEmployeeID() {
		return myEmployee;
	}
	
	public int getCustomerID() {
		return myCustomer;
	}
	
	public double getTotal() {
		return myTotal;
	}
}
