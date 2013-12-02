package mvc.manager.model;

import java.util.HashMap;
import java.util.Map;

import mvc.common.util.DataObservable;
import dao.service.ServiceDAO;
import data.Audit;
import data.Creche;

public class ManagerModel {

	private final DataObservable<Map<Integer, Audit>> mvcAuditEnvModel = new DataObservable<Map<Integer,Audit>>();
	private final DataObservable<Map<Integer, Audit>> mvcAuditSocModel = new DataObservable<Map<Integer,Audit>>();
	private final DataObservable<Map<Integer, Creche>> mvcCrecheModel = new DataObservable<Map<Integer,Creche>>();
	private final DataObservable<Boolean> adminMode = new DataObservable<Boolean>();


	private  boolean newModifForm = false;


	public ManagerModel (Map<Integer, Audit>  daoAuditModel, Map<Integer, Creche>  daoCrecheModel){
		updateAuditModel(daoAuditModel);
		updateCrecheModel(daoCrecheModel);
	}

	public void updateAuditModel(final Map<Integer, Audit> daoAuditModel) {
		//PAS EXCELLENT A AMELIORER	
		Map<Integer, Audit> auditEnvModel = new HashMap<Integer, Audit>();
		Map<Integer, Audit> auditSocModel = new HashMap<Integer, Audit>();

		for(int id : daoAuditModel.keySet()) { 
			Audit audit = ServiceDAO.getInstance().findAuditObjectByID(id).clone();
			if(audit.getGrid().getId() == 1){
				auditEnvModel.put(id, audit);
			}else{
				auditSocModel.put(id, audit);
			}
		}
		adminMode.setValue(false);
		mvcAuditEnvModel.setValue(auditEnvModel);
		mvcAuditSocModel.setValue(auditSocModel);

	}






	public void updateCrecheModel( final Map<Integer, Creche> daoCrecheModel) {
		Thread t = new Thread(){
			@Override
			public void run(){
				Map<Integer, Creche> crecheModel = new HashMap<Integer, Creche>();
				for(int id : daoCrecheModel.keySet()) {
					crecheModel.put(id, daoCrecheModel.get(id).clone());
				}
				mvcCrecheModel.setValue(crecheModel);
			}
		};
		t.start();
	}







	public DataObservable<Map<Integer, Creche>> getListCrecheModel() {
		return mvcCrecheModel;
	}

	public DataObservable<Map<Integer, Audit>> getListAuditEnvModel() {
		return mvcAuditEnvModel;
	}
	
	public DataObservable<Map<Integer, Audit>> getListAuditSocModel() {
		return mvcAuditSocModel;
	}

	public DataObservable<Boolean> getAdminMode() {
		return adminMode;
	}


	public boolean isNewModifForm() {
		return newModifForm;
	}

	public void setNewModifForm(boolean newModifForm) {
		this.newModifForm = newModifForm;
	}
}
