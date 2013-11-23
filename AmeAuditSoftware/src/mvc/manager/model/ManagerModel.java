package mvc.manager.model;

import java.util.HashMap;
import java.util.Map;

import mvc.common.util.DataObservable;
import dao.service.ServiceDAO;
import data.Audit;
import data.Creche;

public class ManagerModel {

		private final DataObservable<Map<Integer, Audit>> mvcAuditModel = new DataObservable<Map<Integer,Audit>>();
		private final DataObservable<Map<Integer, Creche>> mvcCrecheModel = new DataObservable<Map<Integer,Creche>>();
		private final DataObservable<Boolean> adminMode = new DataObservable<Boolean>();

		


		public ManagerModel (Map<Integer, Audit>  daoAuditModel, Map<Integer, Creche>  daoCrecheModel){
			updateAuditModel(daoAuditModel);
			updateCrecheModel(daoCrecheModel);
		}

		public void updateAuditModel(final Map<Integer, Audit> daoAuditModel) {

			Thread t = new Thread(){
				@Override
				public void run(){
					Map<Integer, Audit> auditModel = new HashMap<Integer, Audit>();
					int iBar = 0;
//					jBar.setMaximum(daoAuditModel.size());
					for(int id : daoAuditModel.keySet()) { 
						auditModel.put(id, ServiceDAO.getInstance().findAuditObjectByID(id).clone());
//						jBar.setValue(iBar++);
					}
					adminMode.setValue(false);
					mvcAuditModel.setValue(auditModel);
//					jBar.setValue(0);
				}
			};
			t.start();
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

		public DataObservable<Map<Integer, Audit>> getListAuditModel() {
			return mvcAuditModel;
		}

		public DataObservable<Boolean> getAdminMode() {
			return adminMode;
		}

}
