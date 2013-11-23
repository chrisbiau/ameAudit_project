package dao.sql.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import properties.PropertiesLoader;
import dao.controller.AllControllerDAO;
import dao.util.SqlLiteHelper;
import data.InputDialog;

public class InputDialogDAO extends AbstractSqlAbtractDAO<InputDialog> {

	private static Logger logger = Logger.getLogger(InputDialogDAO.class);

	private enum EColomnNameTable implements EColumnName{
		DIALOG_TYPE("dialogType");

		private String name;

		EColomnNameTable(String name ){
			this.name = name;
		}

		@Override
		public String toString(){
			return name;
		}
	}

	public InputDialogDAO(AllControllerDAO allDAOController) {
		super(allDAOController, PropertiesLoader.getInputDialogTable());
	}
	@Override
	public InputDialog find(int id) {
		InputDialog obj = null;
		ArrayList<EColumnName> columnName=  super.getListColumnName(EColomnNameTable.values());
		ResultSet rs = connect.query(SqlLiteHelper.getReqSelectAllData(tableDataBase, columnName, id));

		if(rs != null){
			try {
				//Create new Object
				obj = new InputDialog(rs.getInt(EDefaultColomnName.ID.toString()),
						rs.getString(EColomnNameTable.DIALOG_TYPE.toString()),
						rs.getBoolean(EDefaultColomnName.SHOW_ITEM.toString()));
			} catch (SQLException e) {
				logger.error("Error Get ID of object: "+e);
			}
		}else{
			logger.error("No object ID: "+id+" fond in DB");
		}

		return obj;
	}

	@Override
	public InputDialog create(InputDialog obj) {
		Map<EColumnName, Object> valuesMap = getMappingColumnNameAndValue(obj);
		return this.createHelper(obj, valuesMap);
	}

	@Override
	public InputDialog update(InputDialog obj) {
		Map<EColumnName, Object> valuesMap = getMappingColumnNameAndValue(obj);
		return this.updateHelper(obj, valuesMap);
	}

	@Override
	public Map<EColumnName, Object> getMappingColumnNameAndValue(InputDialog obj) {
		Map<EColumnName, Object> valuesMap = super.getMappingColumnNameAndValue(obj);
		valuesMap.put(EColomnNameTable.DIALOG_TYPE, obj.getDialogType());
		return valuesMap;
	}



}