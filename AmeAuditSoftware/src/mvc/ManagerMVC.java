package mvc;

import javax.swing.JProgressBar;

import mvc.admin.StartAdminMVC;
import mvc.common.util.DataObservable;
import mvc.form.StartFormMVC;
import mvc.manager.controller.ManagerController;
import mvc.manager.model.ManagerModel;
import mvc.manager.model.ModelToSave;
import mvc.manager.view.ApplicationView;
import start.AppProgressBar;
import dao.StartDAO;
import dao.service.ServiceDAO;

public class ManagerMVC {

	private final ServiceDAO serviceDAO;
	private final StartAdminMVC formAdminMVC;

	private final StartFormMVC startFormMVC;
	private final ManagerController managerController;
	private ManagerModel modelManager = null;
	private ModelToSave modelToSave = null;
	private final ApplicationView applicationView;
	private final DataObservable<JProgressBar> progressBarValue = new DataObservable<JProgressBar>();

	
	public ManagerMVC(){
		
		//init Progresse Bar
		progressBarValue.setValue(new JProgressBar());
		AppProgressBar prg = AppProgressBar.getInstance();
		prg.setProgressBarValue(progressBarValue);
		
		
		serviceDAO = new StartDAO().getServiceDAO();
		this.applicationView = new ApplicationView(this);
		
		this.managerController = new ManagerController(this);

		this.modelToSave = new ModelToSave("ModelToSave");
		
		this.modelManager  = new ManagerModel(serviceDAO.getAllAudit(), serviceDAO.getAllCreche());
		
		this.formAdminMVC = new StartAdminMVC(this);
		
		this.startFormMVC = new StartFormMVC(this);
	}

	public StartAdminMVC getAdminMVC() {
		return formAdminMVC;
	}

	public StartFormMVC getFormMVC() {
		return startFormMVC;
	}

	public ServiceDAO getServiceDAO() {
		return serviceDAO;
	}

	public ManagerController getManagerController() {
		return managerController;
	}

	public ManagerModel getModelManager() {
		return modelManager;
	}

	public ModelToSave getModelToSave() {
		return modelToSave;
	}

	public ApplicationView getApplicationView() {
		return applicationView;
	}
	
	public DataObservable<JProgressBar> getProgressBarValue() {
		return progressBarValue;
	}
	

}
