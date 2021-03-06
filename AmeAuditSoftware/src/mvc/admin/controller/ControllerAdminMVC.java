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
import mvc.manager.view.ApplicationView;

import org.apache.log4j.Logger;

import dao.service.ServiceDAO;
import data.Answer;
import data.DataObject;

public class ControllerAdminMVC   {

	private BddComponentTree treeList ;

	private static Logger logger = Logger.getLogger(ControllerAdminMVC.class);

	//Panels

	private AdminSplitPane adminPanel;
	private final ManagerMVC managerMVC;
	private final ApplicationView applicationView;
	private   Component treePanel ;
	private   NodeDetailJscrollPane optionPanel;



	public ControllerAdminMVC(ManagerMVC managerMVC, ModelAdminMVC modelFormMVC) {
		logger.debug("init");
		managerMVC.getModelManager().getAdminMode().addListener(new adminModeListener());
		this.managerMVC = managerMVC;
		this.applicationView = managerMVC.getApplicationView();
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
		SwingWorker<Object, Object> sw = new SwingWorker<Object, Object>(){
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

	public NodeDetailJscrollPane getOptionPanel() {
		return optionPanel;
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
		if(dataObject instanceof Answer){
			//Remove in model
			DefaultMutableTreeNode mNode = treeList.searchNode(dataObject);
			model.removeNodeFromParent(mNode);

			//Add in model
			DefaultMutableTreeNode parentNode = treeList.searchNode(((Answer) dataObject).getQuery());
			DefaultMutableTreeNode childNode =  new DefaultMutableTreeNode(dataObject);
			model.insertNodeInto(childNode, parentNode, parentNode.getChildCount());
			path = new TreePath(treeList.searchNode(dataObject).getPath());
		}


		model.reload();
		treeList.scrollPathToVisible(path);
		treeList.setSelectionPath(path);
	}


	public void removeObjetData(DataObject dataObjectRemove) {
		DataObject testUseByAnother = ServiceDAO.getInstance().isUseByAnotherDataObject(dataObjectRemove);
		if(	testUseByAnother != null){
			JOptionPane.showMessageDialog(
					managerMVC.getApplicationView(),
					"Impossible de supprimer cet objet :\n>"
							+ dataObjectRemove.getType()+" : ["+dataObjectRemove.getId()+"] "
							+ dataObjectRemove+"\ncar il est utilis� par cet objet :\n>" 
							+ testUseByAnother.getType()+" :["+testUseByAnother.getId()+"] "
							+ testUseByAnother+".",
							"Impossible de supprimer cet objet: "+ dataObjectRemove,
							JOptionPane.ERROR_MESSAGE);
		}else{
			int resultat=JOptionPane.showConfirmDialog(
					managerMVC.getApplicationView(),
					"�tes-vous s�r de vouloir supprimer d�finitivement\n"
							+ "["+dataObjectRemove.getId()+"] "
							+ dataObjectRemove,
							"�tes-vous s�r de vouloir supprimer d�finitivement: "+ dataObjectRemove,
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
	}


	public TreePath getSelectionPathOfTree(){
		return treeList.getSelectionPath();
	}
}
