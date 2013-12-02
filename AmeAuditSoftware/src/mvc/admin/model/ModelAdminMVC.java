package mvc.admin.model;

import mvc.ManagerMVC;

import org.apache.log4j.Logger;

public class ModelAdminMVC {


	private static Logger logger = Logger.getLogger(ModelAdminMVC.class);
	protected String logNameModel;

	//DataModel

	/**
	 * @param logNameModel
	 * @param daoController
	 */
	public ModelAdminMVC(String logNameModel, ManagerMVC managerMVC) {
		logger.debug("init");
		this.logNameModel = logNameModel;
	}

}

