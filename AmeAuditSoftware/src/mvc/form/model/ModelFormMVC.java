package mvc.form.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import mvc.ManagerMVC;
import mvc.common.util.DataObservable;
import mvc.form.view.AuditJTabbedPane;

import org.apache.log4j.Logger;

import dao.service.ServiceDAO;
import data.Answer;
import data.Audit;
import data.Color;
import data.Creche;
import data.InputDialog;
import data.Query;
import data.Room;
import data.Topic;

public class ModelFormMVC {



	private final DataObservable<AuditJTabbedPane>  auditGridTabPanel = new DataObservable<AuditJTabbedPane>();
	private final ServiceDAO serviceDAO ;
	private static Logger logger = Logger.getLogger(ModelFormMVC.class);
	protected String logNameModel;

	//DataModel

	protected Map<Integer, Audit> mvcAuditModel = new HashMap<Integer, Audit>();
	protected Map<Integer, Creche> mvcCrecheModel = new HashMap<Integer, Creche>();
	protected DataObservable<Map<Integer, Query>> queryOfAuditModel = new DataObservable<Map<Integer,Query>>();
	protected HashMap<Integer, //Audit 
				HashMap<Integer,
					HashMap<Integer, ArrayList<Query> >>> mapAuditRoomTopicQuey = new HashMap< Integer, HashMap<Integer, HashMap<Integer,ArrayList<Query>>>>();

	private int nbQueryOfSelectedAudit ;
	
	public int getNbQueryOfSelectedAudit() {
		return nbQueryOfSelectedAudit;
	}


	/**
	 * @param logNameModel
	 * @param daoController
	 */
	public ModelFormMVC(String logNameModel, ManagerMVC managerMVC) {
		this.logNameModel = logNameModel;
		this.serviceDAO = managerMVC.getServiceDAO();
		queryOfAuditModel.setValue(new HashMap<Integer, Query>());
	}


	public void getQueryModelByAudit(final Audit audit) {
//		Thread t = new Thread(){
//
//			@Override
//			public void run(){
				nbQueryOfSelectedAudit=0;
				queryOfAuditModel.setValue(serviceDAO.getQuerysOfAudit(audit));

				if(!mapAuditRoomTopicQuey.containsKey(audit.getId())){
					mapAuditRoomTopicQuey.put(audit.getId(), new HashMap<Integer,HashMap<Integer, ArrayList<Query> >>());
				}
				
				HashMap<Integer, HashMap<Integer, ArrayList<Query>>> mapRoomTopicQuey = mapAuditRoomTopicQuey.get(audit.getId());
				
				
				for(Entry<Integer, Query> entry : queryOfAuditModel.getValue().entrySet()){

					Query query = entry.getValue();

					if(!mapRoomTopicQuey.containsKey(query.getRoom().getId())){
						mapRoomTopicQuey.put(query.getRoom().getId(), new HashMap<Integer, ArrayList<Query>>());
					}

					HashMap<Integer, ArrayList<Query>>  mapTopicQuery = mapRoomTopicQuey.get(query.getRoom().getId());

					if(!mapTopicQuery.containsKey(query.getTopic().getId())){
						mapTopicQuery.put(query.getTopic().getId(), new ArrayList<Query>());
					}

					mapTopicQuery.get(query.getTopic().getId()).add(query);
					nbQueryOfSelectedAudit++;

				}

//				for(Entry<Integer, HashMap<Integer, ArrayList<Query>>> entry : mapRoomTopicQuey.entrySet()){
//					Integer room = entry.getKey();
//					HashMap<Integer, ArrayList<Query>> topicQuery = entry.getValue();
//					for(Entry<Integer, ArrayList<Query>> entry2 : topicQuery.entrySet()){
//						Integer topic = entry2.getKey();
//						ArrayList<Query> lsitquery = entry2.getValue();
//						for(Query query : lsitquery){
//							System.out.println("R:"+room+" T:"+topic+" Q:"+query);
//						}
//					}
//				}
//			}
//		};
//		t.start();
	}


	public ArrayList<Audit> getListAudit() {
		return new ArrayList<Audit>(mvcAuditModel.values());
	}

	public Room findRoomObjectByValueOrCreate(Room roomSearch) {
		return serviceDAO.findRoomObjectByValueOrCreate(roomSearch);
	}


	public Topic findTopicObjectByValueOrCreate(Topic topic) {
		return serviceDAO.findTopicObjectByValueOrCreate(topic);
	}


	public InputDialog findInputObjectByValueOrCreate(InputDialog inputDialog) {
		return serviceDAO.findInputObjectByValueOrCreate(inputDialog);
	}


	public Query findQueryObjectByValueOrCreate(Query query) {
		return serviceDAO.findQueryObjectByValueOrCreate(query);
	}


	public Color findColorObjectByValueOrCreate(Color color) {
		return serviceDAO.findColorObjectByValueOrCreate(color);
	}


	public Answer findAnswerObjectByValueOrCreate(Answer ans) {
		return serviceDAO.findAnswerObjectByValueOrCreate(ans);
	}


	public Query findAnswerObjectByValueOrCreate(Query query) {
		return serviceDAO.findAnswerObjectByValueOrCreate(query);
	}



	public  HashMap<Integer, HashMap<Integer, ArrayList<Query>>> getMapRoomTopicQuery(Audit audit) {
		return mapAuditRoomTopicQuey.get(audit.getId());
	}



	public DataObservable<AuditJTabbedPane> getAuditGridTabPanel() {
		return auditGridTabPanel;
	}



}

