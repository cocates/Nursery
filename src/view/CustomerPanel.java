package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.sql.SQLException;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import model.Customer;
import model.NurseryDB;

@SuppressWarnings("serial")
public class CustomerPanel extends JPanel {
	private String[] columnNames = {"Customer ID", "First Name", "Middle Initial",
			"Last Name", "Address", "City", "Phone Number", "Email"};
	private List<Customer> list;
	private Object[][] data;

	private JPanel pnlEntry;
	private JScrollPane scrollPane;
	private JTable table;
	private JTextField[] txfField = new JTextField[7];
	
	public CustomerPanel(NurseryDB db) {
		super(new BorderLayout());
		update(db);
		createEntryPanel();
	}

	public void update(NurseryDB db) {
		try	{
			list = db.getCustomers();
			
			data = new Object[list.size()][columnNames.length];
			for (int i=0; i<list.size(); i++) {
				data[i][0] = list.get(i).getID();
				data[i][1] = list.get(i).getFirstName();
				data[i][2] = list.get(i).getInitial();
				data[i][3] = list.get(i).getLastName();
				data[i][4] = list.get(i).getAddress();
				data[i][5] = list.get(i).getCity();
				data[i][6] = list.get(i).getPhone();
				data[i][7] = list.get(i).getEmail();
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		table = new JTable(data, columnNames) {
			public boolean isCellEditable(int row, int column) {                
                return false;               
			}
		};
		resizeColumnWidth(table);
		table.setAutoCreateRowSorter(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane = new JScrollPane(table);
		removeAll();
		add(scrollPane, BorderLayout.CENTER);
	}
	
	/** @see http://stackoverflow.com/questions/17627431/auto-resizing-the-jtable-column-widths */
	private void resizeColumnWidth(JTable table) {
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 0; column < table.getColumnCount(); column++) {
	        int width = columnModel.getColumn(0).getWidth(); // Min width
	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width +1 , width);
	        }
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	}
	
	/** @see http://stackoverflow.com/questions/21658144/jpanel-components-change-position-automatically/21659516#21659516 */
	private void createEntryPanel() {
		pnlEntry = new JPanel();
		GroupLayout layout = new GroupLayout(pnlEntry);
		pnlEntry.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        GroupLayout.Group yLabelGroup = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);
        hGroup.addGroup(yLabelGroup);   
        GroupLayout.Group yFieldGroup = layout.createParallelGroup();
        hGroup.addGroup(yFieldGroup);
        layout.setHorizontalGroup(hGroup);
        GroupLayout.SequentialGroup xGroup = layout.createSequentialGroup();
        layout.setVerticalGroup(xGroup);	
		String labelNames[] = {"First Name : ", "Middle Initial : ", "Last Name : ", "Address : ",
				"City : ", "Phone Number : ", "Email : "};
        int p = GroupLayout.PREFERRED_SIZE;	
		for (int i=0; i<labelNames.length; i++) {
			txfField[i] = new JTextField(32);
			JLabel label = new JLabel(labelNames[i]);
			yLabelGroup.addComponent(label);
			yFieldGroup.addComponent(txfField[i], p, p, p);
			xGroup.addGroup(layout.createParallelGroup().
                    addComponent(label).
                    addComponent(txfField[i], p, p, p));
		}
	}

	public void viewEntryPanel() {
		removeAll();
		cleanEntries();
		add(pnlEntry, BorderLayout.CENTER);
	}

	public void cleanEntries() {
		for (int i=0; i<txfField.length; i++) 
			txfField[i].setText("");
	}
	
	public Customer getEntered() {
		return new Customer(txfField[0].getText(), txfField[1].getText(),
				txfField[2].getText(), txfField[3].getText(), txfField[4].getText(),
				txfField[5].getText(), txfField[6].getText());
	}
}
