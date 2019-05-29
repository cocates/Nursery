package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import model.Item;
import model.NurseryDB;

@SuppressWarnings("serial")
public class InventoryPanel extends JPanel {
	private String[] columnNames = {"Item ID", "Name", "Description", "Price", "Stock", "Greenhouse Stock"};
	private String[] queryNames = {"itemid", "name", "description", "price", "stock", "greenhousestock"};
	private List<Item> list;
	private Object[][] data;
	private JTable table;
	private JScrollPane scrollPane;
	
	public InventoryPanel(NurseryDB db) {
		super(new BorderLayout());

		update(db);
		add(scrollPane, BorderLayout.CENTER);
	}

	public void update(NurseryDB db) {
		try	{
			list = db.getInventory();
			
			data = new Object[list.size()][columnNames.length];
			for (int i=0; i<list.size(); i++) {
				data[i][0] = list.get(i).getID();
				data[i][1] = list.get(i).getName();
				data[i][2] = list.get(i).getDescription();
				data[i][3] = list.get(i).getPrice();
				data[i][4] = list.get(i).getStock();
				data[i][5] = list.get(i).getGreenHouse();
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		table = new JTable(data, columnNames);
		resizeColumnWidth(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setAutoCreateRowSorter(true);
		scrollPane = new JScrollPane(table);
	}

	/** @see http://stackoverflow.com/questions/17627431/auto-resizing-the-jtable-column-widths */
	private void resizeColumnWidth(JTable table) {
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 0; column < table.getColumnCount(); column++) {
	        int width = columnModel.getColumn(0).getWidth();
	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width +1 , width);
	        }
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	}

	public boolean belongsTo(Object source) {
		return source == table.getModel();
	}
	
	public void pushChange(NurseryDB db, TableModelEvent event) {
		int row = event.getFirstRow();
		int column = event.getColumn();
		TableModel model = (TableModel) event.getSource();
		
		Object change = model.getValueAt(row, column);
		Integer key = (Integer) model.getValueAt(row, 0);
		String columnName = queryNames[column];
		
		db.updateInventory(change, key, columnName);
	}
	
	public void addTableChangedListener(TableModelListener listener) {
		table.getModel().addTableModelListener(listener);
	}
}
