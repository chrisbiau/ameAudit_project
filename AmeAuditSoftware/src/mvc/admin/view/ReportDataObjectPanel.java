package mvc.admin.view;

import java.awt.Component;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import mvc.admin.controller.ControllerAdminMVC;

import org.apache.log4j.Logger;

import data.DataObject;
import data.DataObjectTypeEnum;

public class ReportDataObjectPanel extends JScrollPane {



	private static Logger logger = Logger.getLogger(ReportDataObjectPanel.class);


	public ReportDataObjectPanel(ControllerAdminMVC controllerAdminMVC, DataObjectTypeEnum typeDataObject) {
		super();

		if(controllerAdminMVC.getSelectionPathOfTree().getLastPathComponent()  != null){
			final ArrayList<Field> entetesField = new ArrayList<Field>();
			Field[] fieldSuper = typeDataObject.getTypeClass().getSuperclass().getDeclaredFields();
			//ID
			for(Field field : fieldSuper){
				field.setAccessible(true);
				if(field.getType() ==  int.class){
					entetesField.add(field);
				}
			}

			//All field of class
			Field[] fields = typeDataObject.getTypeClass().getDeclaredFields();
			for(Field field : fields){
				entetesField.add(field);
			}

			for(Field field : fieldSuper){
				field.setAccessible(true);
				if(field.getType() ==  boolean.class){
					entetesField.add(field);
				}
			}


			// Get all list objet of selected node
			int lgn = 0;
			DefaultMutableTreeNode m_rootNode = (DefaultMutableTreeNode) ( controllerAdminMVC.getSelectionPathOfTree().getLastPathComponent() );
			Enumeration e = m_rootNode.breadthFirstEnumeration();
			ArrayList<DataObject> listObj = new ArrayList<DataObject>();

			while (e.hasMoreElements()) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
				if(node.getUserObject() instanceof DataObject){

					DataObject obj = ((DataObject) node.getUserObject());
					if(obj.getClass() ==  typeDataObject.getTypeClass())
						listObj.add(obj);
				}
			}

			//Convert Hashmap to array
			Object[][] donnees = new Object[listObj.size()][entetesField.size()];
			for (DataObject obj : listObj)
			{
				int idx = 0;
				for(Field field : entetesField){
					field.setAccessible(true);
					try {
						donnees[lgn][idx++] = field.get(obj);
					} catch (IllegalArgumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				lgn++;
			}

			//Convert entete field to string
			ArrayList<String> entetesStr = new ArrayList<String>();
			for(Field f : entetesField){
				entetesStr.add(f.getName());
			}

			//Create table
			JTable tableau = new JTable();
			DefaultTableModel tableModel = new DefaultTableModel(donnees, entetesStr.toArray()) {
				@Override
				public boolean isCellEditable(int row, int column) {
					//all cells false
					return false;
				}

				@Override
				public Class getColumnClass(int c) {  
					Field field = entetesField.get(c);
					return field.getType();
				}
			};

			tableau.setModel(tableModel);
			tableau.setDefaultRenderer(String.class, new StringRenderer());
			tableau.setDefaultRenderer(boolean.class, new BooleanRenderer());

			this.getViewport().add(tableau);
		}
	}


	private class BooleanRenderer extends DefaultTableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
			Component newCell = super.getTableCellRendererComponent( table,  value,  isSelected,  hasFocus,  row,  column);
			boolean bool = (boolean) value;
			newCell = new JCheckBox("");
			((JCheckBox)newCell).setSelected(bool);
			return newCell;
		}

	}

	private class StringRenderer extends DefaultTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
			Component newCell = super.getTableCellRendererComponent( table,  value,  isSelected,  hasFocus,  row,  column);
			if(value instanceof String){
				String colorValue = (String) value;
				if(colorValue.startsWith("#") && colorValue.length() == 7)
					this.setBackground(java.awt.Color.decode(colorValue));
				else
					this.setBackground(java.awt.Color.white);
				this.setText(colorValue);
			}
			return newCell;
		}

	}

}
