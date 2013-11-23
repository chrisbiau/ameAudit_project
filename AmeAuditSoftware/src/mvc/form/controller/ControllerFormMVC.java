package mvc.form.controller;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import mvc.ManagerMVC;
import mvc.common.enumeration.Eview;
import mvc.common.util.DataListenerInterface;
import mvc.form.model.ModelFormMVC;
import mvc.form.view.AuditJTabbedPane;
import mvc.manager.view.ApplicationView;

import org.apache.log4j.Logger;

public class ControllerFormMVC{


	private static Logger logger = Logger.getLogger(ControllerFormMVC.class);
	private final ManagerMVC managerMVC;
	private final ModelFormMVC modelFormMVC;
	private final ApplicationView applicationView;

	public ControllerFormMVC(ManagerMVC managerMVC, ModelFormMVC modelFormMVC) {
		this.managerMVC = managerMVC;
		this.modelFormMVC = modelFormMVC; 
		this.applicationView = managerMVC.getApplicationView();
	}


	public void displayPanel(Eview view ){
		switch(view){
		case form:
			this.getModelFormMVC().getAuditGridTabPanel().addListener(new AuditGridListener());
			buildForm();
			break;
		default:
			break;
		}
	}


	public void buildForm(){
		logger.debug("SwingWorker buildform auditGridTabPanel");
		SwingWorker sw = new SwingWorker(){
			private final AuditJTabbedPane auditGridTabPanel = new AuditJTabbedPane(managerMVC);
			@Override
			protected Object doInBackground() throws Exception {
				logger.debug("SwingWorker doInBackground auditGridTabPanel");
				auditGridTabPanel.addAudit(managerMVC.getModelToSave().getSelectedAudit().getValue());
				auditGridTabPanel.setSelectedIndex(1);
				return null;
			}

			@Override
			public void done(){  
				if(SwingUtilities.isEventDispatchThread()){
					logger.debug("SwingWorker done auditGridTabPanel");
					auditGridTabPanel.setVisible(true);
					if(getModelFormMVC().getAuditGridTabPanel().getValue() != null){
						applicationView.removeCenterPanel(getModelFormMVC().getAuditGridTabPanel().getValue());
						//TODO: Voulez sauvegarder?
					}
					getModelFormMVC().getAuditGridTabPanel().setValue(auditGridTabPanel);
					applicationView.addCenterPanel("Audit",getModelFormMVC().getAuditGridTabPanel().getValue());
				}
			}         
		};
		
		sw.execute();
	}



	public ModelFormMVC getModelFormMVC() {
		return modelFormMVC;
	}

	

	private class AuditGridListener implements DataListenerInterface {

		@Override
		public void dataChange(Object ojbUpdated) {
			// TODO Auto-generated method stub
		}

	}



}
