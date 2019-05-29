package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class NurseryDB {
	private static String server = "localhost:3306";
	private static String user = "root";
	private static String password = "";
	private static Connection conn;
	
//	private List<Item> items;
//	private List<Customer> customers;
	
	/**
	 * Creates a sql connection to MySQL using the properties for
	 * user, password and server information.
	 * @throws SQLException
	 */
	public static void createConnection() throws SQLException {
		Properties connectionProps = new Properties();
		connectionProps.put("user", user);
		connectionProps.put("password", password);
		conn = DriverManager.getConnection("jdbc:" + "mysql" + "://"
			+ server + "/", connectionProps);
		System.out.println("Connected to database");
	}
	
	public List<Item> getInventory() throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "select itemid, name, description, stock, greenhousestock, price "
				+ "from nursery.inventory ";
		List<Item> items = new ArrayList<Item>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("itemid");
				String name = rs.getString("name");
				String description = rs.getString("description");
				int stock = rs.getInt("stock");
				int ghstock = rs.getInt("greenhousestock");
				double price = rs.getDouble("price");
				Item retrieved = new Item(id, name, description, stock, ghstock, price);
				items.add(retrieved);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return items;
	}
	
	public List<Customer> getCustomers() throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "select customerid, firstname, minit, lastname, address, city, phoneNumber, email "
				+ "from nursery.customers ";
		List<Customer> customers = new ArrayList<Customer>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("customerid");
				String first = rs.getString("firstname");
				String minit = rs.getString("minit");
				String last = rs.getString("lastname");
				String address = rs.getString("address");
				String city = rs.getString("city");
				String phone = rs.getString("phoneNumber");
				String email = rs.getString("email");
				Customer retrieved = new Customer(id, first, minit, last, address, city, phone, email);
				customers.add(retrieved);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return customers;
	}
	
	public void addCustomer(Customer customer) {
		String sql = "insert into nursery.customers values " + "(NULL, ?, ?, ?, ?, ?, ?, ?); ";

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, customer.getFirstName());
			preparedStatement.setString(2, customer.getInitial());
			preparedStatement.setString(3, customer.getLastName());
			preparedStatement.setString(4, customer.getAddress());
			preparedStatement.setString(5, customer.getCity());
			preparedStatement.setString(6, customer.getPhone());
			preparedStatement.setString(7, customer.getEmail());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	public List<Order> getOrders() throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "select orderid, employeeid, customerid, TRUNCATE(SUM(quantity*price), 2) total  "
				+ "from nursery.orders natural join nursery.orderlisting natural join nursery.inventory group by orderid ";
		List<Order> orders = new ArrayList<Order>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int orderid = rs.getInt("orderid");
				int employeeid = rs.getInt("employeeid");
				int customerid = rs.getInt("customerid");
				double price = rs.getDouble("total");
				Order retrieved = new Order(orderid, employeeid, customerid, price);
				orders.add(retrieved);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return orders;
	}
	
	public List<OrderDetail> getOrderDetails(int theOrderID) throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "select orderid, itemid, name, quantity, TRUNCATE((quantity*price), 2) subtotal  "
				+ "from nursery.orderlisting natural join nursery.inventory where orderid = ? group by orderid;";
		List<OrderDetail> details = new ArrayList<OrderDetail>();
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, theOrderID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int orderid = rs.getInt("orderid");
				int itemid = rs.getInt("itemid");
				String itemName = rs.getString("name");
				int quantity = rs.getInt("quantity");
				double subtotal = rs.getDouble("subtotal");
				OrderDetail retrieved = new OrderDetail(orderid, itemid, itemName, quantity, subtotal);
				details.add(retrieved);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return details;
	}

	public void updateInventory(Object change, int key, String columnName) {
		String sql = "update nursery.inventory set " + columnName + " = ? where itemid = ? ";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(sql);
			if (change instanceof String)
				preparedStatement.setString(1, (String)change);
			else if (change instanceof Integer)
				preparedStatement.setInt(1, (Integer)change);
			else if (change instanceof Double)
				preparedStatement.setDouble(1, (Double)change);
			preparedStatement.setInt(2, key);
			preparedStatement.executeUpdate();
			System.out.println(sql);
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} 
	}
}
