package dao.sql.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import properties.PropertiesLoader;
import dao.controller.AllControllerDAO;
import dao.util.SqlLiteHelper;
import data.Grid;

public class GridDAO extends AbstractSqlAbtractDAO<Grid> {

	private static Logger logger = Logger.getLogger(GridDAO.class);

	private enum EColomnNameTable implements EColumnName{
		NAME("name");

		private String name;

		EColomnNameTable(String name ){
			this.name = name;
		}

		@Override
		public String toString(){
			return name;
		}
	}

	public GridDAO(AllControllerDAO allDAOController) {
		super(allDAOController, PropertiesLoader.getGridTable());
	}

	@Override
	public Grid find(int id) {
		Grid obj = null;
			ArrayList<EColumnName> columnName=  super.getListColumnName(EColomnNameTable.values());
			ResultSet rs = connect.query(SqlLiteHelper.getReqSelectAllData(tableDataBase, columnName, id));
			if(rs != null){
				try {
					//Create new Object
					obj = new Grid(rs.getInt(EDefaultColomnName.ID.toString()),
							rs.getString(EColomnNameTable.NAME.toString()),
							rs.getBoolean(EDefaultColomnName.SHOW_ITEM.toString()));
					//Set into model
				} catch (SQLException e) {
					logger.error("Error Get ID of object: "+e);
				}
			}else{
				logger.error("No object ID: "+id+" fond in DB");
			}
		return obj;
	}

	@Override
	public Grid create(Grid obj) {
		Map<EColumnName, Object> valuesMap = getMappingColumnNameAndValue(obj);
		return this.createHelper(obj, valuesMap);
	}

	@Override
	public Grid update(Grid obj) {
		Map<EColumnName, Object> valuesMap = getMappingColumnNameAndValue(obj);
		return this.updateHelper(obj, valuesMap);
	}

	@Override
	public Map<EColumnName, Object> getMappingColumnNameAndValue(Grid obj) {
		Map<EColumnName, Object> valuesMap = super.getMappingColumnNameAndValue(obj);
		valuesMap.put(EColomnNameTable.NAME, obj.getName());
		return valuesMap;
	}


}