package dao.sql.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import properties.PropertiesLoader;
import dao.controller.AllControllerDAO;
import dao.util.SqlLiteHelper;
import data.DataObject;
import data.Query;
import data.Topic;

public class TopicDAO extends AbstractSqlAbtractDAO<Topic> {

	private static Logger logger = Logger.getLogger(TopicDAO.class);

	private enum EColomnNameTable implements EColumnName{
		TOPIC_NAME("topicValue"),
		TOPIC_COLOR("topicColor");

		private String name;

		EColomnNameTable(String name ){
			this.name = name;
		}

		@Override
		public String toString(){
			return name;
		}
	}

	public TopicDAO(AllControllerDAO allDAOController) {
		super(allDAOController, PropertiesLoader.getTopicTable());
	}


	@Override
	public Topic find(int id) {
		Topic obj = null;
		ArrayList<EColumnName> columnName=  super.getListColumnName(EColomnNameTable.values());
		String queryStr = SqlLiteHelper.getReqSelectAllData(tableDataBase, columnName, id);
		ResultSet rs = connect.query(queryStr);
		if(rs != null){
			try {

				obj = new Topic(rs.getInt(EDefaultColomnName.ID.toString()),
						rs.getString(EColomnNameTable.TOPIC_NAME.toString()),
						rs.getString(EColomnNameTable.TOPIC_COLOR.toString()),
						rs.getBoolean(EDefaultColomnName.SHOW_ITEM.toString()));
			} catch (SQLException e) {
				logger.error("Error Get ID of object when excuting ["+queryStr+"]: "+e);
			}
		}else{
			logger.error("No object ID: "+id+" fond in DB");
		}
		return obj;
	}

	@Override
	public Topic create(Topic obj) {
		Map<EColumnName, Object> valuesMap = getMappingColumnNameAndValue(obj);
		return this.createHelper(obj, valuesMap);
	}

	@Override
	public Topic update(Topic obj) {
		Map<EColumnName, Object> valuesMap = getMappingColumnNameAndValue(obj);
		return this.updateHelper(obj, valuesMap);
	}

	@Override
	public Map<EColumnName, Object> getMappingColumnNameAndValue(Topic obj) {
		Map<EColumnName, Object> valuesMap = super.getMappingColumnNameAndValue(obj);
		valuesMap.put(EColomnNameTable.TOPIC_NAME, obj.getTopicName());
		valuesMap.put(EColomnNameTable.TOPIC_COLOR, obj.getTopicColor());
		return valuesMap;
	}


	@Override
	public DataObject getObjetUseByAnotherDataObject(Topic obj) {
		for( Entry<Integer, Query> entry :  allControllerDAO.getQueryControllerDao().getAllDataBase().entrySet()){
			if(entry.getValue().getTopic().getId() == obj.getId()){
				return entry.getValue();
			}
		}
		return null;
	}

}