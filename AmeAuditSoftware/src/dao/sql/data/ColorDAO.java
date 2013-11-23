package dao.sql.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import properties.PropertiesLoader;
import dao.controller.AllControllerDAO;
import dao.util.SqlLiteHelper;
import data.Color;

public class ColorDAO extends AbstractSqlAbtractDAO<Color> {

	private static Logger logger = Logger.getLogger(ColorDAO.class);

	private enum EColomnNameTable implements EColumnName{
		COLOR_VALUE("colorValue"),
		COLOR_NAME("colorName");

		private String name;

		EColomnNameTable(String name ){
			this.name = name;
		}

		@Override
		public String toString(){
			return name;
		}
	}

	public ColorDAO(AllControllerDAO allDAOController) {
		super(allDAOController, PropertiesLoader.getColorTable());
	}


	@Override
	public Color find(int id) {
		Color obj = null;
		ArrayList<EColumnName> columnName=  super.getListColumnName(EColomnNameTable.values());
		ResultSet rs = connect.query(SqlLiteHelper.getReqSelectAllData(tableDataBase, columnName, id));

		if(rs != null){
			try {
				obj = new Color(rs.getInt(EDefaultColomnName.ID.toString()),
						rs.getString(EColomnNameTable.COLOR_NAME.toString()),
						rs.getString(EColomnNameTable.COLOR_VALUE.toString()),
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
	public Color create(Color obj) {
		if(obj.getColorValue() == null){
			obj.setColorValue("");
		}
		Map<EColumnName, Object> valuesMap = getMappingColumnNameAndValue(obj);
		return this.createHelper(obj, valuesMap);
	}


	@Override
	public Color update(Color obj) {
		Map<EColumnName, Object> valuesMap = getMappingColumnNameAndValue(obj);
		return this.updateHelper(obj, valuesMap);
	}

	@Override
	public Map<EColumnName, Object> getMappingColumnNameAndValue(Color obj) {
		Map<EColumnName, Object> valuesMap = super.getMappingColumnNameAndValue(obj);
		valuesMap.put(EColomnNameTable.COLOR_NAME, obj.getColorName());
		if(obj.getColorValue() != null){
			valuesMap.put(EColomnNameTable.COLOR_VALUE, obj.getColorValue().toString());
		}
		return valuesMap;
	}


}