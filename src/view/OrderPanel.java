package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import model.NurseryDB;
import model.Order;
import model.OrderDetail;

@SuppressWarnings("serial")
public class OrderPanel extends JPanel {
	private String[] ordersColumns = {"Order ID", "Employee ID", "Customer ID", "Total Cost"};
	private String[] detailColumns = {"Order ID", "Item ID", "Item Name", "Quantity", "Subtotal"};
	private List<Order> orderList;
	private List<OrderDetail> detailList;
	private Object[][] data;
	private JTable orderTable, detailTable;
	private JScrollPane scrollPane;
	
	public OrderPanel(NurseryDB db) {
		super(new BorderLayout());
		update(db);
	}

	public void update(NurseryDB db) {
		try	{
			orderList = db.getOrders();
			
			data = new Object[orderList.size()][ordersColumns.length];
			for (int i=0; i<orderList.size(); i++) {
				data[i][0] = orderList.get(i).getOrderID();
				data[i][1] = orderList.get(i).getEmployeeID();
				data[i][2] = orderList.get(i).getCustomerID();
				data[i][3] = orderList.get(i).getTotal();
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		orderTable = new JTable(data, ordersColumns) {
			public boolean isCellEditable(int row, int column) {                
                return false;               
			}
		};
		resizeColumnWidth(orderTable);
		orderTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		orderTable.setAutoCreateRowSorter(true);
		scrollPane = new JScrollPane(orderTable);
		removeAll();
		add(scrollPane, BorderLayout.CENTER);
	}
	
	public void zoomOrder(NurseryDB db) throws IllegalArgumentException {
		int row = orderTable.getSelectedRow();
		if (row < 0) 
			throw new IllegalArgumentException("No row selected");
		row = orderTable.convertRowIndexToModel(row);
		int toQuery = (Integer) orderTable.getModel().getValueAt(row, 0);
		try	{
			detailList = db.getOrderDetails(toQuery);
			data = new Object[detailList.size()][detailColumns.length];
			for (int i=0; i<detailList.size(); i++) {
				data[i][0] = detailList.get(i).getOrderID();
				data[i][1] = detailList.get(i).getItemID();
				data[i][2] = detailList.get(i).getName();
				data[i][3] = detailList.get(i).getQuantity();
				data[i][4] = detailList.get(i).getSubtotal();
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		detailTable = new JTable(data, detailColumns) {
			public boolean isCellEditable(int row, int column) {                
                return false;               
			}
		};
		resizeColumnWidth(detailTable);
		detailTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		detailTable.setAutoCreateRowSorter(true);
		scrollPane = new JScrollPane(detailTable);
		removeAll();
		add(scrollPane, BorderLayout.CENTER);
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

}
