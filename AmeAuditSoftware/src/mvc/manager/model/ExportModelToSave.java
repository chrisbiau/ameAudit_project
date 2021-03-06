package mvc.manager.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import data.Audit;
import data.Creche;
import data.Result;

public class ExportModelToSave  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4961291416224065632L;

	private static Logger logger = Logger.getLogger(ExportModelToSave.class);

	protected  Map<Integer, HashMap<Integer, Result>> resultByQueryId = null;
	protected  Creche selectedCreche = null;
	protected  Audit selectedAuditEnv = null;

	protected  Audit selectedAuditSoc= null;

	

	public ExportModelToSave(ModelToSave modelToSave) {
		resultByQueryId = modelToSave.getResultByQueryId().getValue();
		selectedAuditEnv = modelToSave.getSelectedAuditEnv().getValue();
		selectedAuditSoc = modelToSave.getSelectedAuditSoc().getValue();
		selectedCreche = modelToSave.getSelectedCreche().getValue();
		logger.debug("Export: Audit: "+selectedAuditEnv+ " - Creche: "+selectedCreche );
	}


	public  void importModelToSave(ModelToSave modelToSave) {
		modelToSave.setSelectedAuditEnv(selectedAuditEnv);
		modelToSave.setSelectedAuditSoc(selectedAuditSoc);
		modelToSave.setSelectedCreche(selectedCreche);
		modelToSave.setResultByQueryId(resultByQueryId);
		logger.info("Import: Audit: "+selectedAuditEnv+ " - Creche: "+selectedCreche );
	}

}
