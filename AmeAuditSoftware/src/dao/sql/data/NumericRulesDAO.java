package dao.sql.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import properties.PropertiesLoader;
import dao.controller.AllControllerDAO;
import dao.util.SqlLiteHelper;
import data.NumericRules;

public class NumericRulesDAO extends AbstractSqlAbtractDAO<NumericRules> {

	private static Logger logger = Logger.getLogger(NumericRulesDAO.class);

	private enum EColomnNameTable implements EColumnName{
		ID_ANSWER("idAnswer"),
		INF_VALUE("infValue"),
		SUP_VALUE("supValue"),
		ID_COLOR("idColor");

		private String name;

		EColomnNameTable(String name ){
			this.name = name;
		}

		@Override
		public String toString(){
			return name;
		}
	}

	public NumericRulesDAO(AllControllerDAO allDAOController) {
		super(allDAOController, PropertiesLoader.getNumericRuleTable());
	}

	@Override
	public NumericRules find(int id) {
		NumericRules obj = null;
		ArrayList<EColumnName> columnName=  super.getListColumnName(EColomnNameTable.values());
		ResultSet rs = connect.query(SqlLiteHelper.getReqSelectAllData(tableDataBase, columnName, id));

		if(rs != null){
			try {
				//Get Color Object
				int idColor = new Integer(rs.getInt(EColomnNameTable.ID_COLOR.toString()));

				//Get Query Object
				int idAnswer = new Integer(rs.getInt(EColomnNameTable.ID_ANSWER.toString()));
				
				//Create new Object
				obj = new NumericRules(rs.getInt(EDefaultColomnName.ID.toString()),
						null,
						rs.getInt(EColomnNameTable.INF_VALUE.toString()),
						rs.getInt(EColomnNameTable.SUP_VALUE.toString()),
						null,
						rs.getBoolean(EDefaultColomnName.SHOW_ITEM.toString()));
				obj.setAnswer(allControllerDAO.getAnswerControllerDao().find(idAnswer));
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
	public NumericRules create(NumericRules obj) {
		Map<EColumnName, Object> valuesMap = getMappingColumnNameAndValue(obj);
		return this.createHelper(obj, valuesMap);
	}

	@Override
	public NumericRules update(NumericRules obj) {
		Map<EColumnName, Object> valuesMap = getMappingColumnNameAndValue(obj);
		return this.updateHelper(obj, valuesMap);
	}

	@Override
	public Map<EColumnName, Object> getMappingColumnNameAndValue(NumericRules obj) {
		Map<EColumnName, Object> valuesMap = super.getMappingColumnNameAndValue(obj);
		valuesMap.put(EColomnNameTable.ID_ANSWER, obj.getAnswer().getId());
		valuesMap.put(EColomnNameTable.INF_VALUE, obj.getInfValue());
		valuesMap.put(EColomnNameTable.ID_COLOR, obj.getColor().getId());
		valuesMap.put(EColomnNameTable.SUP_VALUE, obj.getMaxValue());
		return valuesMap;
	}

}