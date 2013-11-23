package mvc.admin.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import dao.service.ServiceDAO;
import data.Answer;
import data.DataObject;
import data.DataObjectTypeEnum;
import data.Query;

public class BddComponentTree extends JTree {

	public BddComponentTree(){
		super();
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Adminsitrateur");
		MyDefaultTreeModel modelNew = new MyDefaultTreeModel(root);
		
		root.add(this.buildDefaultTreeModel(DataObjectTypeEnum.GRID,  ServiceDAO.getInstance().getAllGrid().values() ));
		root.add(this.buildDefaultTreeModel(DataObjectTypeEnum.AUDIT,  ServiceDAO.getInstance().getAllAudit().values() ));
		root.add(this.buildDefaultTreeModel(DataObjectTypeEnum.CRECHE,  ServiceDAO.getInstance().getAllCreche().values() ));
		root.add(this.buildDefaultTreeModel(DataObjectTypeEnum.INPUT_DIALOG,  ServiceDAO.getInstance().getAllInputDialog().values() ));
		root.add(this.buildDefaultTreeModel(DataObjectTypeEnum.NUMERIC_RULES,  ServiceDAO.getInstance().getAllNumericRules().values() ));
		root.add(this.buildDefaultTreeModel(DataObjectTypeEnum.ROOM,  ServiceDAO.getInstance().getAllRoom().values() ));
		root.add(this.buildDefaultTreeModel(DataObjectTypeEnum.TOPIC,  ServiceDAO.getInstance().getAllTopic().values() ));
		root.add(this.buildDefaultTreeModel(DataObjectTypeEnum.COLOR, ServiceDAO.getInstance().getAllColor().values() ));
		
		
		root.add(this.buildDefaultTreeModelQuery(DataObjectTypeEnum.QUERY, ServiceDAO.getInstance().getAllQuery().values()));
		
		
		this.setModel(modelNew);
		
		
		
		
	}
	
	private DefaultMutableTreeNode buildDefaultTreeModel(DataObjectTypeEnum dataObjectTypeEnum,  Object child){
		Collection<DataObject> inputValues = (Collection<DataObject>) child;
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(dataObjectTypeEnum);
		for(DataObject data : inputValues){
			root.add(new DefaultMutableTreeNode(data));
		}
		return root;
	}
	
	
	
	private DefaultMutableTreeNode buildDefaultTreeModelQuery(DataObjectTypeEnum dataObjectTypeEnum, Collection<Query> collection) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(dataObjectTypeEnum);
		DefaultMutableTreeNode rootQuery = null;
		for(DataObject dataQuery : collection){
			 rootQuery =  new DefaultMutableTreeNode(dataQuery);
			ArrayList<Answer> listAnswer = ServiceDAO.getInstance().getAnswersByQuery((Query) dataQuery);
				for(DataObject dataAnswer : listAnswer){
					rootQuery.add(new DefaultMutableTreeNode(dataAnswer));
				}
				root.add(rootQuery);
		}
		return root;
	}

	
	public DefaultMutableTreeNode searchNode(DataObject nodeObj) {
	    DefaultMutableTreeNode node = null;
	    
	    DefaultMutableTreeNode m_rootNode = (DefaultMutableTreeNode) this.getModel().getRoot();
	    Enumeration e = m_rootNode.breadthFirstEnumeration();
	    while (e.hasMoreElements()) {
	    	node = (DefaultMutableTreeNode) e.nextElement();
	    	if (node.getUserObject() instanceof DataObject &&
	    			nodeObj.getClass() == node.getUserObject().getClass() &&
	    			nodeObj.getId() == ((DataObject) node.getUserObject()).getId()) {
	    		return node;
	    	}
	    }
	    return null;
	}



}
