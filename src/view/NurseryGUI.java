package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import model.Customer;
import model.NurseryDB;

@SuppressWarnings("serial")
public class NurseryGUI extends JFrame implements ActionListener, TableModelListener {
	public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit()
			.getScreenSize();
	private static final Dimension PREFERRED_SIZE = new Dimension(
			SCREEN_SIZE.width / 2, SCREEN_SIZE.height / 2);
	
	private NurseryDB db;
	private InventoryPanel inventoryPanel;
	private CustomerPanel customerPanel;
	private OrderPanel orderPanel;

	private JPanel mainPanel, buttonsPanel, southPanel;
	private JButton btnViewInventory, btnViewCustomers, btnViewOrders, 
			btnNewCustomer, btnFinalizeCustomer, btnOrderSelected;
	
	private NurseryGUI() {
		super("UW Tacoma - TCSS 445 Nursery");
		startUp();
		initialize();
		revalidate();
		repaint();
	}
	
	private void startUp() {
		JPanel loading = new JPanel(new GridBagLayout());
		loading.add(new JLabel("Loading..."));
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(loading, BorderLayout.CENTER);
		add(mainPanel, BorderLayout.CENTER);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(PREFERRED_SIZE);
	}
	
	private void initialize() {
		db = new NurseryDB();
		
		inventoryPanel = new InventoryPanel(db);
		inventoryPanel.addTableChangedListener(this);
		customerPanel = new CustomerPanel(db);
		orderPanel = new OrderPanel(db);
		
		buttonsPanel = new JPanel();
		southPanel = new JPanel(new FlowLayout());
		
		btnViewInventory = new JButton("View Inventory");
		btnViewInventory.addActionListener(this);
		
		btnViewOrders = new JButton("View Orders");
		btnViewOrders.addActionListener(this);
		
		btnViewCustomers = new JButton("View Customers");
		btnViewCustomers.addActionListener(this);

		btnNewCustomer = new JButton("Add Customer");
		btnNewCustomer.addActionListener(this);

		buttonsPanel.add(btnViewInventory);
		buttonsPanel.add(btnViewOrders);
		buttonsPanel.add(btnViewCustomers);
		buttonsPanel.add(btnNewCustomer);
		
		btnFinalizeCustomer = new JButton("Finalize");
		btnFinalizeCustomer.addActionListener(this);
		
		btnOrderSelected = new JButton("Into Selected");
		btnOrderSelected.addActionListener(this);
		
		JPanel complete = new JPanel(new GridBagLayout());
		complete.add(new JLabel("Load complete"));
		
		mainPanel.removeAll();
		mainPanel.add(complete, BorderLayout.CENTER);	
		add(buttonsPanel, BorderLayout.NORTH);	
	}
	
	public static void main(String[] args) {
//		NurseryGUI gui = 
				new NurseryGUI();
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		if (source == btnViewInventory) {
			inventoryPanel.update(db);
			mainPanel.removeAll();
			mainPanel.add(inventoryPanel, BorderLayout.CENTER);
			
		} else if(source == btnViewOrders) {
			orderPanel.update(db);
			southPanel.removeAll();
			southPanel.add(btnOrderSelected);
			mainPanel.removeAll();
			mainPanel.add(orderPanel, BorderLayout.CENTER);
			mainPanel.add(southPanel, BorderLayout.SOUTH);
			
		} else if(source == btnViewCustomers) {
			customerPanel.update(db);
			mainPanel.removeAll();
			mainPanel.add(customerPanel, BorderLayout.CENTER);
			
		} else if(source == btnNewCustomer) {
			customerPanel.viewEntryPanel();
			mainPanel.removeAll();
			southPanel.removeAll();
			southPanel.add(btnFinalizeCustomer);
			mainPanel.add(customerPanel, BorderLayout.CENTER);
			mainPanel.add(southPanel, BorderLayout.SOUTH);
			
		} else if(source == btnFinalizeCustomer) {
			try {
				Customer toAdd = customerPanel.getEntered();
				db.addCustomer(toAdd);
				customerPanel.cleanEntries();
				JOptionPane.showMessageDialog(null, "Customer Added Successfully!");
				customerPanel.update(db);
			} catch (IllegalArgumentException illegal) {
				JOptionPane.showMessageDialog(null, illegal.getMessage());
			}
			
		} else if (source == btnOrderSelected) {
			try {
				orderPanel.zoomOrder(db);
				mainPanel.remove(southPanel);
			} catch (IllegalArgumentException illegal) {
				JOptionPane.showMessageDialog(null, illegal.getMessage());
			}
		}
		revalidate();
		repaint();
	}

	public void tableChanged(TableModelEvent event) {
//		if (inventoryPanel.belongsTo(event.getSource())) 
				inventoryPanel.pushChange(db, event);
	}
}
