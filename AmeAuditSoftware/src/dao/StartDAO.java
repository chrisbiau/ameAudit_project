package dao;

import org.apache.log4j.Logger;

import dao.service.ServiceDAO;

public class StartDAO {

	private static Logger logger = Logger.getLogger(StartDAO.class);
	private ServiceDAO serviceDAO;
	
	public StartDAO(){
		init();
	}

	private void init() {
		logger.info("Initialize dao model and dao controller");
		serviceDAO = ServiceDAO.getInstance();
	}

	public ServiceDAO getServiceDAO() {
		return serviceDAO;
	}
	
}
