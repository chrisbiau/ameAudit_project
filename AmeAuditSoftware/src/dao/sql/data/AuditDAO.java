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
import data.Audit;
import data.DataObject;
import data.Query;

public class AuditDAO extends AbstractSqlAbtractDAO<Audit> {

	private static Logger logger = Logger.getLogger(AuditDAO.class);

	private enum EColomnNameTable implements EColumnName{
		ID_GRID("idGrid"),
		VERSION("version"),
		CREATION_DATE("creationDate");

		private String name;

		EColomnNameTable(String name ){
			this.name = name;
		}

		@Override
		public String toString(){
			return name;
		}
	}

	public AuditDAO(AllControllerDAO allDAOController) {
		super(allDAOController, PropertiesLoader.getAuditTable());
	}

	@Override
	public Audit find(int id) {
		Audit obj = null;
		ArrayList<EColumnName> columnName=  super.getListColumnName(EColomnNameTable.values());
		String queryStr = SqlLiteHelper.getReqSelectAllData(tableDataBase, columnName, id);
		ResultSet rs = connect.query(queryStr);
		if(rs != null){
			try {
				//Get Grid Object
				int idGrid = new Integer(rs.getInt(EColomnNameTable.ID_GRID.toString()));
				//Create new Object
				obj = new Audit(rs.getInt(EDefaultColomnName.ID.toString()),
						rs.getString(EColomnNameTable.VERSION.toString()),
						rs.getDate(EColomnNameTable.CREATION_DATE.toString()),
						null,
						rs.getBoolean(EDefaultColomnName.SHOW_ITEM.toString()));

				obj.setGrid(allControllerDAO.getGridControllerDao().find(idGrid));
				//Set into model
			} catch (SQLException e) {
				logger.error("Error Get ID of object when excuting ["+queryStr+"]: "+e);
			}
		}else{
			logger.error("No object ID: "+id+" fond in DB");
		}
		return obj;
	}

	@Override
	public Audit create(Audit obj) {
		Map<EColumnName, Object> valuesMap = getMappingColumnNameAndValue(obj);
		return this.createHelper(obj, valuesMap);
	}

	@Override
	public Audit update(Audit obj) {
		Map<EColumnName, Object> valuesMap = getMappingColumnNameAndValue(obj);
		return this.updateHelper(obj, valuesMap);
	}

	@Override
	public Map<EColumnName, Object> getMappingColumnNameAndValue(Audit obj) {
		Map<EColumnName, Object> valuesMap = super.getMappingColumnNameAndValue(obj);
		valuesMap.put(EColomnNameTable.ID_GRID, obj.getGrid().getId());
		valuesMap.put(EColomnNameTable.VERSION, obj.getVersion());
		valuesMap.put(EColomnNameTable.CREATION_DATE, obj.getCreationDate().toString());
		return valuesMap;
	}

	@Override
	public DataObject getObjetUseByAnotherDataObject(Audit obj) {
		for( Entry<Integer, Query> entry : allControllerDAO.getQueryControllerDao().getAllDataBase().entrySet()){
			if(entry.getValue().getAudit().getId() == obj.getId()){
				return entry.getValue();
			}
		}
		return null;
	}


}