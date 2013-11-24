package mvc.admin.view;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JScrollPane;
import javax.swing.JTable;
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


		ArrayList<Field> entetesField = new ArrayList<Field>();

		
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
		
		
		
		if(controllerAdminMVC.getSelectionPathOfTree().getLastPathComponent()  != null){
			DefaultMutableTreeNode m_rootNode = (DefaultMutableTreeNode) ( controllerAdminMVC.getSelectionPathOfTree().getLastPathComponent() );
			Enumeration e = m_rootNode.breadthFirstEnumeration();
			int lgn = 0;

			ArrayList<DataObject> listObj = new ArrayList<DataObject>();

			while (e.hasMoreElements()) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
				if(node.getUserObject() instanceof DataObject){
					DataObject obj = ((DataObject) node.getUserObject());
					listObj.add(obj);
				}
			}


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

			
			JTable tableau = new JTable();
			DefaultTableModel tableModel = new DefaultTableModel(donnees, entetesField.toArray()) {
				@Override
				public boolean isCellEditable(int row, int column) {
					//all cells false
					return false;
				}
			};

			tableau.setModel(tableModel);
			this.getViewport().add(tableau);
		}
	}

}
