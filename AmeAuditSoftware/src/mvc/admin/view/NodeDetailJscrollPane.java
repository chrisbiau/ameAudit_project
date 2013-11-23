package mvc.admin.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import mvc.admin.controller.ControllerAdminMVC;

import org.apache.log4j.Logger;

import data.DataObject;
import data.DataObjectTypeEnum;

public class NodeDetailJscrollPane extends JScrollPane implements  TreeSelectionListener {
	private static Logger logger = Logger.getLogger(NodeDetailJscrollPane.class);
	JPanel panel = new JPanel();
	private final ControllerAdminMVC controllerAdminMVC;

	public NodeDetailJscrollPane(ControllerAdminMVC controllerAdminMVC) {
		super();
		this.controllerAdminMVC = controllerAdminMVC;
	}

	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		DefaultMutableTreeNode defaultMutableTreeNode = (DefaultMutableTreeNode) ((BddComponentTree) arg0.getSource()).getLastSelectedPathComponent();		
		if(defaultMutableTreeNode!=null){
			if( defaultMutableTreeNode.getUserObject() instanceof DataObject ){
				DataObject dataObjectSelected = (DataObject) defaultMutableTreeNode.getUserObject();
				buildEditDataObjectJpanel(dataObjectSelected);
			}else if(defaultMutableTreeNode.getUserObject() instanceof DataObjectTypeEnum){
				final DataObjectTypeEnum type = (DataObjectTypeEnum) defaultMutableTreeNode.getUserObject();
				panel = new JPanel();
				JButton addButton = new JButton("Ajouter: "+type);
				addButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						buildCreateDataObjectJpanel(type);
					}
				});
				panel.add(addButton);
				setViewportView(panel);
			}else{
				//TODO: traiter ??
				logger.info("TODO traiter ??"+ defaultMutableTreeNode.getUserObject().getClass() );
			}
		}else{
			logger.error("defaultMutableTreeNode is null");
		}

	}

	private void buildEditDataObjectJpanel(final DataObject dataObjectSelected){
//		logger.debug("SwingWorker buildEditJpanel of this object"+dataObjectSelected);
//		SwingWorker sw = new SwingWorker(){
//			@Override
//			protected Object doInBackground() throws Exception {
				logger.debug("SwingWorker doInBackground of this object"+dataObjectSelected);
				panel  = new EditDataObjectPanel(controllerAdminMVC, dataObjectSelected  );
//				return null;
//			}
//
//			@Override
//			public void done(){  
//				if(SwingUtilities.isEventDispatchThread()){
					logger.debug("SwingWorker done "+dataObjectSelected.toString());
					setViewportView(panel);
//				}
//			}         
//		};
//		sw.execute();
	}


	private void buildCreateDataObjectJpanel(final DataObjectTypeEnum type){
//		logger.debug("SwingWorker buildNewDataJpanel of this type"+type);
//		SwingWorker sw = new SwingWorker(){
//			@Override
//			protected Object doInBackground() throws Exception {
				logger.debug("SwingWorker doInBackground of this object"+type);
				panel  = new CreateDataObjectPanel(controllerAdminMVC, type  );
//				return null;
//			}
//
//			@Override
//			public void done(){  
				if(SwingUtilities.isEventDispatchThread()){
					logger.debug("SwingWorker done "+type.toString());
					setViewportView(panel);
				}
//			}         
//		};
//		sw.execute();
	}


}
