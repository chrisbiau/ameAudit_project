package dao.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import start.AppProgressBar;
import dao.controller.AllControllerDAO;
import dao.sql.data.AnswerDAO.EAnswerColomnNameTable;
import dao.sql.data.EColumnName;
import dao.sql.data.QueryDAO.EQueryColomnNameTable;
import data.Answer;
import data.Audit;
import data.Color;
import data.Creche;
import data.DataObject;
import data.Grid;
import data.InputDialog;
import data.NumericRules;
import data.Query;
import data.Room;
import data.Topic;




/**
 * Classe qui permet d'appeler les méthodes DAO
 * 
 */
public class ServiceDAO {

	private static Logger logger = Logger.getLogger(ServiceDAO.class);
	
	private static final AllControllerDAO allDAOController = new AllControllerDAO();
	private static ServiceDAO instance = null;

	private ServiceDAO() {
		super();
		
	}

	public static ServiceDAO getInstance() {
		if(instance == null){
			instance = new ServiceDAO();
		}
		return instance;
	}
	

	
	/** ADD  **/

	public void addObjetData(DataObject dataObject) {
		if(dataObject instanceof Answer){
			allDAOController.getAnswerControllerDao().create((Answer) dataObject);
		}else if(dataObject instanceof Audit){
			allDAOController.getAuditControllerDao().create((Audit) dataObject);
		}else if(dataObject instanceof Color){
			allDAOController.getColorControllerDao().create((Color) dataObject);
		}else if(dataObject instanceof Creche){
			allDAOController.getCrecheControllerDao().create((Creche) dataObject);
		}else if(dataObject instanceof Grid){
			allDAOController.getGridControllerDao().create((Grid) dataObject);
		}else if(dataObject instanceof InputDialog){
			allDAOController.getInputDialogControllerDao().create((InputDialog) dataObject);
		}else if(dataObject instanceof NumericRules){
			allDAOController.getNumericRulesControllerDao().create((NumericRules) dataObject);
		}else if(dataObject instanceof Query){
			allDAOController.getQueryControllerDao().create((Query) dataObject);
		}else if(dataObject instanceof Room){
			allDAOController.getRoomControllerDao().create((Room) dataObject);
		}else if(dataObject instanceof Topic){
			allDAOController.getTopicControllerDao().create((Topic) dataObject);
		} else{
			logger.warn("Any conditions is define for creating this DataObject: "+dataObject.getClass());
		}
		
	}
	
	
	
	

	/** UPDATE **/
	
	public void updateObjetData(DataObject dataObject) {
		if(dataObject instanceof Answer){
			allDAOController.getAnswerControllerDao().update((Answer) dataObject);
		}else if(dataObject instanceof Audit){
			allDAOController.getAuditControllerDao().update((Audit) dataObject);
		}else if(dataObject instanceof Color){
			allDAOController.getColorControllerDao().update((Color) dataObject);
		}else if(dataObject instanceof Creche){
			allDAOController.getCrecheControllerDao().update((Creche) dataObject);
		}else if(dataObject instanceof Grid){
			allDAOController.getGridControllerDao().update((Grid) dataObject);
		}else if(dataObject instanceof InputDialog){
			allDAOController.getInputDialogControllerDao().update((InputDialog) dataObject);
		}else if(dataObject instanceof NumericRules){
			allDAOController.getNumericRulesControllerDao().update((NumericRules) dataObject);
		}else if(dataObject instanceof Query){
			allDAOController.getQueryControllerDao().update((Query) dataObject);
		}else if(dataObject instanceof Room){
			allDAOController.getRoomControllerDao().update((Room) dataObject);
		}else if(dataObject instanceof Topic){
			allDAOController.getTopicControllerDao().update((Topic) dataObject);
		} else{
			logger.warn("Any conditions is define for updating this DataObject: "+dataObject.getClass());
		}
		
	}

	
	/** REMOVE **/
	public void removeObjetData(DataObject dataObject) {
		if(dataObject instanceof Answer){
			allDAOController.getAnswerControllerDao().remove((Answer) dataObject);
		}else if(dataObject instanceof Audit){
			allDAOController.getAuditControllerDao().remove((Audit) dataObject);
		}else if(dataObject instanceof Color){
			allDAOController.getColorControllerDao().remove((Color) dataObject);
		}else if(dataObject instanceof Creche){
			allDAOController.getCrecheControllerDao().remove((Creche) dataObject);
		}else if(dataObject instanceof Grid){
			allDAOController.getGridControllerDao().remove((Grid) dataObject);
		}else if(dataObject instanceof InputDialog){
			allDAOController.getInputDialogControllerDao().remove((InputDialog) dataObject);
		}else if(dataObject instanceof NumericRules){
			allDAOController.getNumericRulesControllerDao().remove((NumericRules) dataObject);
		}else if(dataObject instanceof Query){
			allDAOController.getQueryControllerDao().remove((Query) dataObject);
		}else if(dataObject instanceof Room){
			allDAOController.getRoomControllerDao().remove((Room) dataObject);
		}else if(dataObject instanceof Topic){
			allDAOController.getTopicControllerDao().remove((Topic) dataObject);
		} else{
			logger.warn("Any conditions is define for updating this DataObject: "+dataObject.getClass());
		}
	}

	
	
	/** FIND **/
	public Room findRoomObjectByValueOrCreate(Room roomSearch) {
		return allDAOController.getRoomControllerDao().findObjectOrCreateInDAO(roomSearch);
	}

	
	public Room findRoomObjectByID(int roomSearchId) {
		return allDAOController.getRoomControllerDao().find(roomSearchId);
	}
	
	public Audit findAuditObjectByID(int idAudit) {
		return allDAOController.getAuditControllerDao().find(idAudit);
	}
	
	public Topic findTopicObjectByID(int topicSearchId) {
		return allDAOController.getTopicControllerDao().find(topicSearchId);
	}


	public Topic findTopicObjectByValueOrCreate(Topic topic) {
		return allDAOController.getTopicControllerDao().findObjectOrCreateInDAO(topic);
	}


	public InputDialog findInputObjectByValueOrCreate(InputDialog inputDialog) {
		return allDAOController.getInputDialogControllerDao().findObjectOrCreateInDAO(inputDialog);
	}


	public Query findQueryObjectByValueOrCreate(Query query) {
		return allDAOController.getQueryControllerDao().findObjectOrCreateInDAO(query);
	}


	public Color findColorObjectByValueOrCreate(Color color) {
		return allDAOController.getColorControllerDao().findObjectOrCreateInDAO(color);

	}


	public Answer findAnswerObjectByValueOrCreate(Answer ans) {
		return allDAOController.getAnswerControllerDao().findObjectOrCreateInDAO(ans);
	}


	public Query findAnswerObjectByValueOrCreate(Query query) {
		return allDAOController.getQueryControllerDao().findObjectOrCreateInDAO(query);
	}

	
	/** GET ALL DATABASE **/
	
	public Map<Integer, Answer> getAllAnswer() {
		return allDAOController.getAnswerControllerDao().getAllDataBase();
	}
	
	public Map<Integer, Audit> getAllAudit() {
		return allDAOController.getAuditControllerDao().getAllDataBase();
	}
	
	public Map<Integer, Color> getAllColor() {
		return allDAOController.getColorControllerDao().getAllDataBase();
	}
	
	public Map<Integer, Creche> getAllCreche() {
		return allDAOController.getCrecheControllerDao().getAllDataBase();
	}
	
	public Map<Integer, Grid> getAllGrid() {
		return allDAOController.getGridControllerDao().getAllDataBase();
	}

	public Map<Integer, InputDialog> getAllInputDialog() {
		return allDAOController.getInputDialogControllerDao().getAllDataBase();
	}

	public Map<Integer, NumericRules> getAllNumericRules() {
		return allDAOController.getNumericRulesControllerDao().getAllDataBase();
	}
	
	public Map<Integer, Query> getAllQuery() {
		return allDAOController.getQueryControllerDao().getAllDataBase();
	}
	
	public Map<Integer, Room> getAllRoom() {
		return allDAOController.getRoomControllerDao().getAllDataBase();
	}

	public Map<Integer, Topic> getAllTopic() {
		return allDAOController.getTopicControllerDao().getAllDataBase();
	}

	
	/** SPECIALE **/
	
	public Map<Integer, Query> getQuerysOfAudit(Audit audit) {
		
		Map<Integer, Query> listQueryOfAudit = new HashMap<Integer, Query>();
		
		int iBar = 0;

		Map<EColumnName, Object> mapSearch = new HashMap<EColumnName, Object>();
		mapSearch.put(EQueryColomnNameTable.ID_AUDIT, audit.getId());
		ArrayList<Integer> listId = allDAOController.getQueryControllerDao().findIdWithValueColunm(mapSearch);

		
		AppProgressBar.getInstance().setMaximumProgressBar(listId.size());
		for (int idQuery : listId) {
			listQueryOfAudit.put(idQuery, allDAOController.getQueryControllerDao().find(idQuery));
			AppProgressBar.getInstance().setOneMoreProgressBar();
		}
		
		return listQueryOfAudit;
	}

	public ArrayList<Answer> getAnswersByQuery(Query query) {
		ArrayList<Answer>  listAnswersByQuery = new ArrayList<Answer>();
		Map<EColumnName, Object> mapSearch = new HashMap<EColumnName, Object>();
		mapSearch.put(EAnswerColomnNameTable.ID_QUERY, query.getId());
		ArrayList<Integer> listId = allDAOController.getAnswerControllerDao().findIdWithValueColunm(mapSearch);
		AppProgressBar.getInstance().setMaximumProgressBar(listId.size());
		for (int idAnswer : listId) {
			listAnswersByQuery.add(allDAOController.getAnswerControllerDao().find(idAnswer));
			AppProgressBar.getInstance().setOneMoreProgressBar();
		}
				
		return listAnswersByQuery;
	}




	
}
