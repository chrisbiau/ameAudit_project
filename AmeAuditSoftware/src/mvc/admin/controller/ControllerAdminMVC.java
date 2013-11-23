package mvc.admin.controller;

import java.awt.Component;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
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

import dao.service.ServiceDAO;
import data.DataObject;

public class ControllerAdminMVC   {

	private BddComponentTree treeList ;

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

	public void addObjetData(DataObject dataObjectCreate) {
		//Create new object in SQL base
		ServiceDAO.getInstance().addObjetData(dataObjectCreate);

		//Update tree model
		MyDefaultTreeModel model = (MyDefaultTreeModel)treeList.getModel();
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) (treeList.getSelectionPath().getLastPathComponent());
		DefaultMutableTreeNode childNode =  new DefaultMutableTreeNode(dataObjectCreate);
		model.insertNodeInto(childNode, parentNode, parentNode.getChildCount());
		model.reload();

		TreePath path = new TreePath(childNode.getPath());
		treeList.scrollPathToVisible(path);
		treeList.setSelectionPath(path);

	}

	public void updateObjetData(DataObject dataObject) {
		//update object in SQL base
		ServiceDAO.getInstance().updateObjetData(dataObject);

		//Update tree model
		MyDefaultTreeModel model = (MyDefaultTreeModel)treeList.getModel();
		TreePath path = treeList.getSelectionPath();
		model.reload();
		treeList.scrollPathToVisible(path);
		treeList.setSelectionPath(path);
	}


	public void removeObjetData(DataObject dataObjectRemove) {
		int resultat=JOptionPane.showConfirmDialog(
				managerMVC.getApplicationView(),
				"Êtes-vous sûr de vouloir supprimer définitivement\n"
						+ "["+dataObjectRemove.getId()+"] "
						+ dataObjectRemove,
						"Êtes-vous sûr de vouloir supprimer définitivement: "+ dataObjectRemove,
						JOptionPane.YES_NO_OPTION);

		if ( resultat == JOptionPane.YES_OPTION) {
			//Remove bject in SQL base
			ServiceDAO.getInstance().removeObjetData(dataObjectRemove);

			MyDefaultTreeModel model = (MyDefaultTreeModel)treeList.getModel();
			TreePath path = treeList.getSelectionPath();

			DefaultMutableTreeNode mNode = treeList.searchNode(dataObjectRemove);
			model.removeNodeFromParent(mNode);

			treeList.setSelectionPath(path.getParentPath());
			treeList.scrollPathToVisible(path);
		}

	}

	
	public TreePath getSelectionPathOfTree(){
		return treeList.getSelectionPath();
	}
}
