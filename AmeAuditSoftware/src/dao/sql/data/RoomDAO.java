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
import data.Room;

public class RoomDAO extends AbstractSqlAbtractDAO<Room> {

	private static Logger logger = Logger.getLogger(RoomDAO.class);

	public enum EColomnNameTable implements EColumnName{
		ROOM_VALUE("roomValue");

		private String name;

		EColomnNameTable(String name ){
			this.name = name;
		}

		@Override
		public String toString(){
			return name;
		}
	}

	public RoomDAO(AllControllerDAO allDAOController) {
		super(allDAOController, PropertiesLoader.getRoomTable());
	}

	@Override
	public Room find(int id) {
		Room obj = null;
		ArrayList<EColumnName> columnName=  super.getListColumnName(EColomnNameTable.values());
		String queryStr = SqlLiteHelper.getReqSelectAllData(tableDataBase, columnName, id);
		ResultSet rs = connect.query(queryStr);

		if(rs != null){
			try {
				obj = new Room(rs.getInt(EDefaultColomnName.ID.toString()),
						rs.getString(EColomnNameTable.ROOM_VALUE.toString()),
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
	public Room create(Room obj) {
		Map<EColumnName, Object> valuesMap = getMappingColumnNameAndValue(obj);
		return this.createHelper(obj, valuesMap);
	}

	@Override
	public Room update(Room obj) {
		Map<EColumnName, Object> valuesMap = getMappingColumnNameAndValue(obj);
		return this.updateHelper(obj, valuesMap);
	}

	@Override
	public Map<EColumnName, Object> getMappingColumnNameAndValue(Room obj) {
		Map<EColumnName, Object> valuesMap = super.getMappingColumnNameAndValue(obj);
		valuesMap.put(EColomnNameTable.ROOM_VALUE, obj.getRoomName());
		return valuesMap;
	}

	@Override
	public DataObject getObjetUseByAnotherDataObject(Room obj) {
		for( Entry<Integer, Query> entry :  allControllerDAO.getQueryControllerDao().getAllDataBase().entrySet()){
			if(entry.getValue().getRoom().getId() == obj.getId()){
				return entry.getValue();
			}
		}
		return null;
	}



}