package dao.sql.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import properties.data.TableDataBase;
import dao.AbtractDAO;
import dao.controller.AllControllerDAO;
import dao.util.ConnectionSqlLite;
import dao.util.SqlLiteHelper;
import data.DataObject;

//http://cyrille-herby.developpez.com/tutoriels/java/mapper-sa-base-donnees-avec-pattern-dao/

public abstract class AbstractSqlAbtractDAO<T> extends AbtractDAO<T> {
	private static Logger logger = Logger.getLogger(AbstractSqlAbtractDAO.class);
	protected ConnectionSqlLite connect = ConnectionSqlLite.getInstance();
	protected TableDataBase tableDataBase = null;
	protected AllControllerDAO allControllerDAO= null;

	public enum EDefaultColomnName implements EColumnName{
		ID("id"),
		SHOW_ITEM("showItem");

		private String name;

		EDefaultColomnName(String name ){
			this.name = name;
		}

		@Override
		public String toString(){
			return name;
		}
	}

	public AbstractSqlAbtractDAO(AllControllerDAO allDAOController, TableDataBase tableDataBase) {
		super();
		this.allControllerDAO = allDAOController;
		this.tableDataBase = tableDataBase;
	}


	@Override
	public void delete(T obj){
		connect.query(SqlLiteHelper.getReqDeleteDataInTable(tableDataBase, ((DataObject) obj).getId()));
	}

	@Override
	public ArrayList<Integer> getAllIdInDataBase() {
		ArrayList<EColumnName> listColumn = new ArrayList<EColumnName>();
		listColumn.add(EDefaultColomnName.ID);
		ArrayList<Integer> listId = new ArrayList<Integer>();
		ResultSet rs  = connect.query(SqlLiteHelper.getReqSelectAllData(tableDataBase,listColumn));
		int id;
		try {
			while(rs.next()){
				id = rs.getInt(EDefaultColomnName.ID.toString());
				listId.add(id);
			}
		} catch (SQLException e) {
			logger.error("Error :"+e);
		}
		return listId;
	}


	public Map<EColumnName, Object> getMappingColumnNameAndValue(T obj) {
		Map<EColumnName, Object> valuesMap = new HashMap<EColumnName, Object>();
		//valuesMap.put(EDefaultColomnName.ID, ((DataObject) obj).getId());
		valuesMap.put(EDefaultColomnName.SHOW_ITEM, ((DataObject) obj).getValid());
		return valuesMap;
	}
	protected ArrayList<EColumnName> getListColumnName(EColumnName[] eColomnNameTables) {
		ArrayList<EColumnName> columnNameList = new ArrayList<EColumnName>(Arrays.asList(eColomnNameTables));
		columnNameList.add(EDefaultColomnName.ID);
		columnNameList.add(EDefaultColomnName.SHOW_ITEM);
		return columnNameList;
	}


	public ArrayList<Integer> findIdWithValueColunm(Map<EColumnName, Object> map) {
		ArrayList<Integer> id = new ArrayList<Integer>();

		String strQuery = SqlLiteHelper.getReqIdOfDataColumn(tableDataBase,map);
		ResultSet rs  = connect.query(strQuery);
		try {
			
			while(rs.next()){
				id.add(rs.getInt(EDefaultColomnName.ID.toString()));
			}
		} catch (SQLException e) {
			logger.error("Error :"+e);
		}
		if(id.isEmpty() ){
			logger.warn("NOT FOUND Id in "+this.toString()+" dataBaseDAO - Query is: "+strQuery);
		}else{
//			logger.info("Find Id(s) of "+this.toString()+" in dataBaseDAO size: "+id.size() );
		}
		return id;
	}

	protected T createHelper(T obj, Map<EColumnName, Object> valuesMap){
		ResultSet rs = connect.query(SqlLiteHelper.getReqInsertInto(tableDataBase, valuesMap));
		if(rs != null){
			try {
				//Get ID in database
				((DataObject) obj).setId(rs.getInt(1));
				logger.info("Insert objet "+this.toString()+" ID is :"+((DataObject) obj).getId());
			} catch (SQLException e) {
				logger.error("Get ID of create object: "+e);
			}
		}else{
			logger.error("Error to get ID of create object: "+SqlLiteHelper.getReqInsertInto(tableDataBase, valuesMap));
		}
		return obj;
	}

	protected T updateHelper(T obj, Map<EColumnName, Object> valuesMap){
		ResultSet rs = connect.query(SqlLiteHelper.getReqUpdate(tableDataBase, valuesMap,((DataObject) obj).getId()));
		if(rs != null){
			//Update into model
		}else{
			logger.error("Error to update object");
		}
		return obj;
	}


	@Override
	public String toString (){
		return tableDataBase.getName();
	}

	@Override
	public Map<Integer, T> getAllDataBase() {
		return null;
	}

	@Override
	public ArrayList<T> getAllValuesDataBase() {
		// TODO Auto-generated method stub
		return null;
	}




}

