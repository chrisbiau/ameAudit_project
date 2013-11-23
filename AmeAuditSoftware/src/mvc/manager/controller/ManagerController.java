package mvc.manager.controller;

import javax.swing.JComponent;
import javax.swing.JPanel;

import mvc.ManagerMVC;
import mvc.common.enumeration.EAction;
import mvc.common.enumeration.Eview;
import mvc.manager.helper.AuditFunctionHelper;
import mvc.manager.helper.SaveFunctionHelper;
import mvc.manager.view.ApplicationView;
import mvc.manager.view.MenuBar;

import org.apache.log4j.Logger;

import dao.service.ServiceDAO;
import data.Creche;

public class ManagerController {

	private static Logger logger = Logger.getLogger(ManagerController.class);


	public ApplicationView applicationView = null;


	private ServiceDAO serviceDAO = null;
	private ManagerMVC managerMVC = null;
	
	public ManagerController(ManagerMVC managerMVC ) {
		this.applicationView = managerMVC.getApplicationView();
		this.serviceDAO = managerMVC.getServiceDAO();
		this.managerMVC = managerMVC;
		//TODO: ADD LISTENER IF MODEL DAO CHANGE AND UPDATE CRECHE & AUDIT.

		//INFO PANEL
		init();
	}


	private void init() {
		applicationView.setJMenuBar(new MenuBar(this));
		this.displayPanel(Eview.home);
	}


	public void displayPanel(Eview view ){
		JComponent panelCenter = null;
		JComponent panelRight = null;

		switch(view){
		case home:
			break;
		case open:
			panelCenter = new  JPanel();
			panelCenter.setVisible(true);
			managerMVC.getFormMVC().getControllerFormMVC().displayPanel(Eview.form);
			break;
		default:
			panelCenter = null;
			break;
		}
		applicationView.setRightPanel(panelRight);
	}

	public void userAction(EAction eAction) {

		switch(eAction){
		case newAudit:
			if(AuditFunctionHelper.newAudit(managerMVC)){
				managerMVC.getFormMVC().getControllerFormMVC().displayPanel(Eview.form);
				managerMVC.getApplicationView().setTitle("Nouveau audit");
			}else{
				logger.info("Selected creche or audit is invalid");
			}

			break;
		case openAudit:
			if(SaveFunctionHelper.openAudit(managerMVC)){
				managerMVC.getFormMVC().getControllerFormMVC().displayPanel(Eview.form);
				
				managerMVC.getApplicationView().setTitle(managerMVC.getModelToSave().getSelectedAudit().getValue().getGrid().getName());
			}else{
				logger.error("Error when opening file");
			}
			break;

		case saveAudit:
			if(SaveFunctionHelper.saveAudit(managerMVC)){
				logger.info("Audit is save");
			}else{
				logger.error("Error when saving audit");
			}
			break;
			
			
		case adminMode:
			if(managerMVC.getModelManager().getAdminMode().getValue() == false){
				if(AuditFunctionHelper.changeAdminMode(managerMVC)){
					managerMVC.getModelManager().getAdminMode().setValue(true);
					logger.info("Administrator is logged");
				}else{
					logger.error("Error when Administrator is loging");
				}
			}else{
				managerMVC.getModelManager().getAdminMode().setValue(false);
				logger.info("Administrator is disconnect");
			}
			break;		

		case parser:
//			ReadExcel r = new ReadExcel(this);
			break;
		default:
			logger.error("EAction not reference"+eAction);
			break;
		}

	}

	public ApplicationView getApplicationView() {
		return  this.applicationView ;
	}

	public void addCreche(Creche crecheFrom) {
		serviceDAO.addObjetData(crecheFrom);
	}

	public void updateCrecheData(Creche crecheSelected) {
		serviceDAO.updateObjetData(crecheSelected);
	}

}
