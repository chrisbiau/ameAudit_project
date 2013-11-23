package mvc.admin.view;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.tree.DefaultMutableTreeNode;

import mvc.admin.controller.ControllerAdminMVC;
import mvc.admin.view.common.UtilBuildViewAdmin;

import org.apache.log4j.Logger;

import data.DataObject;
import data.DataObjectTypeEnum;

public class ReportDataObjectPanel extends JScrollPane {

	private static Logger logger = Logger.getLogger(ReportDataObjectPanel.class);
	private final  DataObjectTypeEnum typeDataObject ;
	private JButton btnSave;
	private HashMap<String, JComponent> mapPane ;
	private final ControllerAdminMVC controllerAdminMVC;

	private UtilBuildViewAdmin utilPanelAdmin;

	public ReportDataObjectPanel(ControllerAdminMVC controllerAdminMVC, DataObjectTypeEnum typeDataObject) {
		super();
		this.controllerAdminMVC = controllerAdminMVC;
		this.typeDataObject = typeDataObject;
		
		
		ArrayList<String> entetes = new ArrayList<String>();
		
		//All field of class
		Field[] fields = typeDataObject.getTypeClass().getDeclaredFields();
		
		for(Field field : fields){
			entetes.add(field.getName());
		}
		
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
		
	    
	    String[][] donnees = new String[listObj.size()][entetes.size()];
	    for (DataObject obj : listObj)
	    {
	    	int idx = 0;
	    	for(Field field : obj.getClass().getFields()){
	    		field.setAccessible(true);
	    		try {
					donnees[lgn][idx++] = field.get(obj).toString();
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
		
	    JTable tableau = new JTable(donnees, entetes.toArray());
	    
	    this.getViewport().add(tableau);
	}

	

}
