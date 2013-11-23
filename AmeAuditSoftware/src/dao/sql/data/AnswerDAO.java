package dao.sql.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import properties.PropertiesLoader;
import dao.controller.AllControllerDAO;
import dao.util.SqlLiteHelper;
import data.Answer;

public class AnswerDAO extends AbstractSqlAbtractDAO<Answer> {

	private static Logger logger = Logger.getLogger(AnswerDAO.class);


	public enum EAnswerColomnNameTable implements EColumnName{
		ID_QUERY("idQuery"),
		ANSWER_VALUE("answerValue"),
		ID_COLOR("idColor"),
		MAX_VALUE("maxValue"),
		MIN_VALUE("minValue"),
		UNIT("unit"),
		DEFAULT_VALUE("defaultValue");

		private String name;

		EAnswerColomnNameTable(String name ){
			this.name = name;
		}

		@Override
		public String toString(){
			return name;
		}
	}

	public AnswerDAO(AllControllerDAO allDAOController) {
		super(allDAOController, PropertiesLoader.getAnswerTable());
	}

	@Override
	public Answer find(int id) {
		Answer obj = null;
		ArrayList<EColumnName> columnName=  super.getListColumnName(EAnswerColomnNameTable.values());
		ResultSet rs = connect.query(SqlLiteHelper.getReqSelectAllData(tableDataBase, columnName, id));

		if(rs != null){
			try {
				//Get Color Object
				int idColor = new Integer(rs.getInt(EAnswerColomnNameTable.ID_COLOR.toString()));

				//Get Query Object
				int idQuery = new Integer(rs.getInt(EAnswerColomnNameTable.ID_QUERY.toString()));

				//Create new Object
				obj = new Answer(rs.getInt(EDefaultColomnName.ID.toString()),
						null,
						rs.getString(EAnswerColomnNameTable.ANSWER_VALUE.toString()),
						null,
						rs.getInt(EAnswerColomnNameTable.MAX_VALUE.toString()),
						rs.getInt(EAnswerColomnNameTable.MIN_VALUE.toString()),
						rs.getString(EAnswerColomnNameTable.UNIT.toString()),
						rs.getBoolean(EAnswerColomnNameTable.DEFAULT_VALUE.toString()),
						rs.getBoolean(EDefaultColomnName.SHOW_ITEM.toString()));
				obj.setQuery(allControllerDAO.getQueryControllerDao().find(idQuery));
				obj.setColor(allControllerDAO.getColorControllerDao().find(idColor));
			} catch (SQLException e) {
				logger.error("Error Get ID of object: "+e);
			}
		}else{
			logger.error("No object ID: "+id+" fond in DB");
		}

		return obj;
	}

	@Override
	public Answer create(Answer obj) {
		Map<EColumnName, Object> valuesMap = getMappingColumnNameAndValue(obj);
		return this.createHelper(obj, valuesMap);
	}

	@Override
	public Answer update(Answer obj) {
		Map<EColumnName, Object> valuesMap = getMappingColumnNameAndValue(obj);
		return this.updateHelper(obj, valuesMap);
	}

	@Override
	public Map<EColumnName, Object> getMappingColumnNameAndValue(Answer obj) {
		
		Map<EColumnName, Object> valuesMap = super.getMappingColumnNameAndValue(obj);
		valuesMap.put(EAnswerColomnNameTable.ID_QUERY, obj.getQuery().getId());
		valuesMap.put(EAnswerColomnNameTable.ANSWER_VALUE, obj.getAnswerText());
		valuesMap.put(EAnswerColomnNameTable.ID_COLOR, obj.getColor().getId());
		valuesMap.put(EAnswerColomnNameTable.MAX_VALUE, obj.getMaxValue());
		valuesMap.put(EAnswerColomnNameTable.MIN_VALUE, obj.getMinValue());
		valuesMap.put(EAnswerColomnNameTable.UNIT, obj.getUnit());
		valuesMap.put(EAnswerColomnNameTable.DEFAULT_VALUE, obj.getDefaut());
		return valuesMap;
	}




}