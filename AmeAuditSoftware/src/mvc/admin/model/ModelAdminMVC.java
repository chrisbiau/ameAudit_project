package mvc.admin.model;

import mvc.ManagerMVC;

import org.apache.log4j.Logger;

import dao.service.ServiceDAO;

public class ModelAdminMVC {


	private static Logger logger = Logger.getLogger(ModelAdminMVC.class);
	protected String logNameModel;
	private final ServiceDAO serviceDAO;

	//DataModel

	/**
	 * @param logNameModel
	 * @param daoController
	 */
	public ModelAdminMVC(String logNameModel, ManagerMVC managerMVC) {
		this.logNameModel = logNameModel;
		this.serviceDAO = managerMVC.getServiceDAO();
	}

}

