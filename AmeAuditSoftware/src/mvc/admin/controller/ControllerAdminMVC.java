package mvc.admin.controller;

import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import mvc.ManagerMVC;
import mvc.admin.model.ModelAdminMVC;
import mvc.admin.view.AdminSplitPane;
import mvc.admin.view.BddComponentTree;
import mvc.admin.view.MyDefaultTreeModel;
import mvc.admin.view.NodeDetailJscrollPane;
import mvc.common.enumeration.Eview;
import mvc.common.util.DataListenerInterface;
import mvc.manager.model.ModelToSave;
import mvc.manager.view.ApplicationView;

import org.apache.log4j.Logger;

import data.DataObject;

public class ControllerAdminMVC   {

	private JTree treeList ;

	private static Logger logger = Logger.getLogger(ControllerAdminMVC.class);

	//Panels

	private AdminSplitPane adminPanel;
	private final ManagerMVC managerMVC;
	private final ModelAdminMVC modelFormMVC;
	private final ApplicationView applicationView;
	private final ModelToSave modelToSave;
	private   Component treePanel ;
	private   NodeDetailJscrollPane optionPanel;


	
	public ControllerAdminMVC(ManagerMVC managerMVC, ModelAdminMVC modelFormMVC) {
		managerMVC.getModelManager().getAdminMode().addListener(new adminModeListener());
		this.managerMVC = managerMVC;
		this.modelFormMVC = modelFormMVC; 
		this.applicationView = managerMVC.getApplicationView();
		this.modelToSave = managerMVC.getModelToSave();
	}

	public void displayPanel(Eview view ){

		switch(view){
		case form:
			break;
		default:
			break;
		}
	}


	public void buildForm(){
		optionPanel = new NodeDetailJscrollPane(this);
		SwingWorker sw = new SwingWorker(){
			@Override
			protected Object doInBackground() throws Exception {
				adminPanel = new AdminSplitPane(managerMVC);
				treeList = new BddComponentTree();
				treePanel =  new JScrollPane(treeList);
				treeList.addTreeSelectionListener(optionPanel);
				return null;
			}

			@Override
			public void done(){  
				if(SwingUtilities.isEventDispatchThread()){
					adminPanel.setLeftComponent(treePanel);
					adminPanel.setRightComponent(optionPanel);
					applicationView.addCenterPanel("Aministrateur",adminPanel);
				}
			}         
		};
		sw.execute(); 
	}
	
	
	public void reloadTree(DataObject dataObject, boolean isNewObject){
		logger.debug("Update Tree model of Jtree Admin");
		MyDefaultTreeModel model = (MyDefaultTreeModel)treeList.getModel();
		TreePath path = treeList.getSelectionPath();
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) (path.getLastPathComponent());
		if(isNewObject){
		    DefaultMutableTreeNode childNode =  new DefaultMutableTreeNode(dataObject);
		    model.insertNodeInto(childNode, parentNode, parentNode.getChildCount());
		    model.reload();
		    path =  new TreePath(childNode.getPath());
		}
		
		//TODO: CHANGER LE MODEL si c'est une réponse qui change de question 
		model.reload();
		treeList.scrollPathToVisible(path);
		treeList.setSelectionPath(path);
		

	}

	private class adminModeListener implements DataListenerInterface {

		@Override
		public void dataChange(Object ojbUpdated) {
			
			if(ojbUpdated != null && ojbUpdated instanceof Boolean)
			if((Boolean) ojbUpdated){
				buildForm();
			}else{
				applicationView.removeCenterPanel(adminPanel);
			}
		}
	}


}
