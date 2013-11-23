package mvc.manager.model;

import java.util.HashMap;
import java.util.Map;

import mvc.common.util.DataObservable;

import org.apache.log4j.Logger;

import data.Audit;
import data.Creche;
import data.Result;

public class ModelToSave  {


	/**
	 *
	 */
	private static final long serialVersionUID = 3495464793169725950L;

	private static Logger logger = Logger.getLogger(ModelToSave.class);
	protected String logNameModel;


	protected DataObservable<Map<Integer, HashMap<Integer, Result> > > resultByQueryId = new DataObservable<Map<Integer,HashMap<Integer,Result>>>();


	protected DataObservable<Creche> selectedCreche = new DataObservable<Creche>();
	protected DataObservable<Audit> selectedAudit = new DataObservable<Audit>();


	public ModelToSave(String logNameModel) {
		super();
		this.logNameModel = logNameModel;

		resultByQueryId.setValue(new HashMap<Integer, HashMap<Integer, Result> >());
		selectedCreche.setValue(new Creche("", false));
		selectedAudit.setValue(new Audit());
	}


	public DataObservable<Creche> getSelectedCreche() {
		return selectedCreche;
	}

	public void setSelectedCreche(Creche selectedCreche) {
		this.selectedCreche.setValue(selectedCreche);
	}

	public void setSelectedAudit(Audit selectedAduit) {
		this.selectedAudit.setValue(selectedAduit);
	}

	public DataObservable<Audit> getSelectedAudit() {
		return selectedAudit;
	}


	public DataObservable<Map<Integer, HashMap<Integer, Result>>> getResultByQueryId() {
		return resultByQueryId;
	}


	public void setResultByQueryId(Map<Integer, HashMap<Integer, Result>> resultByQueryId) {
		this.resultByQueryId.setValue(resultByQueryId);
	}



/**Save helper**/
	public ExportModelToSave exportModelToSave (){
		return new ExportModelToSave(this);
	}

	public void importModelToSave (ExportModelToSave exportModelToSave){
		exportModelToSave.importModelToSave(this);

	}



}
