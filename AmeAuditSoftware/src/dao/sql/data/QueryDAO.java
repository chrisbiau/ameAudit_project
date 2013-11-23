package dao.sql.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import properties.PropertiesLoader;
import dao.controller.AllControllerDAO;
import dao.util.SqlLiteHelper;
import data.Query;

public class QueryDAO extends AbstractSqlAbtractDAO<Query> {

	private static Logger logger = Logger.getLogger(QueryDAO.class);

	public enum EQueryColomnNameTable implements EColumnName{
		ID_AUDIT("idAudit"),
		QUERY_VALUE("queryValue"),
		HELP_QUERY("helpQuery"),
		COMMENT_QUERY("commentQuery"),
		ID_ROOM("idRoom"),
		ID_TOPIC("idTopic"),
		ID_DIALOG("idDialog"),
		ID_LINK("idLink"),
		WEIGHTING("weighting");

		private String name;

		EQueryColomnNameTable(String name ){
			this.name = name;
		}

		@Override
		public String toString(){
			return name;
		}
	}

	public QueryDAO(AllControllerDAO allDAOController) {
		super(allDAOController, PropertiesLoader.getQueryTable());
	}


	@Override
	public Query find(int id) {
		Query obj = null;
		ArrayList<EColumnName> columnName=  super.getListColumnName(EQueryColomnNameTable.values());
		ResultSet rs = connect.query(SqlLiteHelper.getReqSelectAllData(tableDataBase, columnName, id));

		if(rs != null){
			try {
				//Get Audit Object
				int idAudit = new Integer(rs.getInt(EQueryColomnNameTable.ID_AUDIT.toString()));
				//Get Room Object
				int idRoom = new Integer(rs.getInt(EQueryColomnNameTable.ID_ROOM.toString()));
				//Get Topic Object
				int idTopic = new Integer(rs.getInt(EQueryColomnNameTable.ID_TOPIC.toString()));
				//Get Dialog Object
				int idDialog =new Integer(rs.getInt(EQueryColomnNameTable.ID_DIALOG.toString()));
				//Get Link Object
				//int idLink = rs.getInt(EColomnNameTable.ID_LINK.toString());

				//Create new Object
					
				obj = new Query(rs.getInt(EDefaultColomnName.ID.toString()),
						null,
						rs.getString(EQueryColomnNameTable.QUERY_VALUE.toString()),
						rs.getString(EQueryColomnNameTable.HELP_QUERY.toString()),
						rs.getString(EQueryColomnNameTable.COMMENT_QUERY.toString()),
						null,
						null,
						null,
						rs.getInt(EQueryColomnNameTable.ID_LINK.toString()),
						rs.getInt(EQueryColomnNameTable.WEIGHTING.toString()),
						rs.getBoolean(EDefaultColomnName.SHOW_ITEM.toString()));
				
				obj.setAudit(allControllerDAO.getAuditControllerDao().find(idAudit));
				obj.setRoom(allControllerDAO.getRoomControllerDao().find(idRoom));
				obj.setTopic(allControllerDAO.getTopicControllerDao().find(idTopic));
				obj.setInputDialog(allControllerDAO.getInputDialogControllerDao().find(idDialog));
				
			} catch (SQLException e) {
				logger.error("Error Get ID of object: "+e);
			}
		}else{
			logger.error("No object ID: "+id+" fond in DB");
		}

		return obj;
	}

	@Override
	public Query create(Query obj) {
		Map<EColumnName, Object> valuesMap = getMappingColumnNameAndValue(obj);
		return this.createHelper(obj, valuesMap);
	}

	@Override
	public Query update(Query obj) {
		Map<EColumnName, Object> valuesMap = getMappingColumnNameAndValue(obj);
		return this.updateHelper(obj, valuesMap);
	}

	@Override
	public Map<EColumnName, Object> getMappingColumnNameAndValue(Query obj) {
		Map<EColumnName, Object> valuesMap = super.getMappingColumnNameAndValue(obj);
		valuesMap.put(EQueryColomnNameTable.ID_AUDIT, obj.getAudit().getId());
		valuesMap.put(EQueryColomnNameTable.QUERY_VALUE, obj.getQueryText());
		valuesMap.put(EQueryColomnNameTable.HELP_QUERY, obj.getHelpText());
		valuesMap.put(EQueryColomnNameTable.COMMENT_QUERY, obj.getCommentText());
		valuesMap.put(EQueryColomnNameTable.ID_ROOM, obj.getRoom().getId());
		valuesMap.put(EQueryColomnNameTable.ID_TOPIC, obj.getTopic().getId());
		valuesMap.put(EQueryColomnNameTable.ID_DIALOG, obj.getInputDialog().getId());
		valuesMap.put(EQueryColomnNameTable.ID_LINK, -1);
		valuesMap.put(EQueryColomnNameTable.WEIGHTING, obj.getWeigthing());
		return valuesMap;
	}
	
}