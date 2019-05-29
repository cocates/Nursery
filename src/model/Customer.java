package model;

public class Customer {
	private int id;
	private String first, minit, last, address, city, phone, email;

	public Customer(int pid, String pfirst, String pminit, String plast,
			String paddress, String pcity, String pphone, String pemail) {
		this(pfirst, pminit, plast, paddress, pcity, pphone, pemail);
		id = pid;
	}

	public Customer(String pfirst, String pminit, String plast, String paddress,
			String pcity, String pphone, String pemail) {
		setFirst(pfirst);
		setMInit(pminit);
		setLast(plast);
		setAddress(paddress);
		setCity(pcity);
		setPhone(pphone);
		setEmail(pemail);
	}

	private void setFirst(String toSet) {
		if(toSet != null && !toSet.isEmpty()) first = toSet;
		else throw new IllegalArgumentException("Invalid first name.");
	}

	private void setMInit(String toSet) {
		if (toSet != null && !toSet.isEmpty()) minit = Character.toString(toSet.charAt(0));	// NULLABLE
	}

	private void setLast(String toSet) {
		if(toSet != null && !toSet.isEmpty()) last = toSet;
		else throw new IllegalArgumentException("Invalid last name.");
	}

	private void setAddress(String toSet) {
		if(toSet != null && !toSet.isEmpty()) address = toSet;
		else throw new IllegalArgumentException("Invalid address.");
	}

	private void setCity(String toSet) {
		if(toSet != null && !toSet.isEmpty()) city = toSet;
		else throw new IllegalArgumentException("Invalid city.");
	}

	private void setPhone(String toSet) {
		if(toSet != null && !toSet.isEmpty()) phone = toSet;	// NULLABLE
	}

	private void setEmail(String toSet) {
		if(toSet != null && !toSet.isEmpty()) email = toSet;	// NULLABLE
	}
	
	public int getID() {
		return id;
	}
	
	public String getFirstName() {
		return first;
	}
	
	public String getInitial() {
		return minit;
	}
	
	public String getLastName() {
		return last;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public String getEmail() {
		return email;
	}
}
