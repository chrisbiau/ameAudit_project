package dao.util;


import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import properties.data.TableDataBase;
import dao.sql.data.EColumnName;

public class SqlLiteHelper {


	private static Logger logger = Logger.getLogger(SqlLiteHelper.class);

	public static String getReqDeleteDataInTable(TableDataBase table, int id){
		String reqSql = "DELETE from "+table+" ";;
		reqSql += "where id="+id;
		reqSql += ";";
		return reqSql;
	}

	public static String getReqInsertInto(TableDataBase table, Map<EColumnName, Object> valuesMap ){
		String columnSql = "";
		String valuesSql = "";
		for(Entry<EColumnName, Object> entry : valuesMap.entrySet()) {
			String column = entry.getKey().toString();
			Object value = entry.getValue();
			columnSql += column+",";
			valuesSql+= convertObjectToSqlSyntaxe(value)+", ";
		}

		String reqSql = "INSERT INTO "+table+" ";;
		reqSql += "("+columnSql.substring(0,columnSql.length()-1)+") ";
		reqSql += "VALUES ("+valuesSql.substring(0,valuesSql.length()-2)+")";
		reqSql += ";";
		logger.info("Requete : "+reqSql);
		return reqSql;
	}

	public static  String getReqUpdate(TableDataBase table, Map<EColumnName, Object> setUpdatevaluesMap, int whereID ){

		String valuesUpSql = "";
		for(Entry<EColumnName, Object> entry : setUpdatevaluesMap.entrySet()) {
			EColumnName column = entry.getKey();
			Object value = entry.getValue();
			valuesUpSql += column+"="+convertObjectToSqlSyntaxe(value)+", ";
		}

		String reqSql = "UPDATE "+table+" ";;
		reqSql += "SET "+valuesUpSql.substring(0,valuesUpSql.length()-2)+" ";
		reqSql += "WHERE id="+whereID+" ";
		reqSql += ";";
		logger.info("Requete : "+reqSql);
		return reqSql;
	}

	public static String getReqSelectAllData(TableDataBase table ){
		return getReqSelectAllData(table, null, Long.MIN_VALUE,null,false);
	}

	public static  String getReqSelectAllData(TableDataBase table, ArrayList<EColumnName> columnName ){
		return getReqSelectAllData(table, columnName, Long.MIN_VALUE,null,false);
	}

	public static  String getReqSelectAllData(TableDataBase table, ArrayList<EColumnName> columnName, long whereID ){
		return getReqSelectAllData(table, columnName, whereID ,null,false);
	}

	public static  String getReqSelectAllData(TableDataBase table, ArrayList<EColumnName> columnName, long whereID, String orderBy, boolean desc){
		String columnSql = "";
		if(columnName == null){
			columnSql ="* ";
		}else{
			for(EColumnName column : columnName){
				columnSql += column+", ";
			}
		}
		String reqSql = "SELECT ";
		reqSql += ""+columnSql.substring(0,columnSql.length()-2)+" ";
		reqSql += "FROM "+table+" ";
		if(whereID > 0){
			reqSql += "WHERE id="+whereID+" ";
		}
		if(orderBy!=null){
			reqSql += "ORDER by "+orderBy+" ";
		}
		if(desc){
			reqSql += "DESC";
		}
		reqSql += ";";

//		logger.debug(reqSql);
		return reqSql;
	}

	public static  String getReqIdOfDataColumn(TableDataBase table, Map<EColumnName, Object> setUpdatevaluesMap ){
		String valuesWhSql = "";
		for(Entry<EColumnName, Object> entry : setUpdatevaluesMap.entrySet()) {
			EColumnName column = entry.getKey();
			Object value = entry.getValue();
			if(value != null && !column.toString().contains("showItem")){
				valuesWhSql += column+"="+convertObjectToSqlSyntaxe(value)+" AND ";
			}
		}


		String reqSql = "SELECT id ";;
		reqSql += "FROM "+table+" ";;
		reqSql += "WHERE "+valuesWhSql.substring(0,valuesWhSql.length()-5)+" ";
		reqSql += ";";
//		logger.info("Requete : "+reqSql);
		return reqSql;
	}



	public static  String getReqLastInsertRowId(){
		return "SELECT last_insert_rowid();";

	}

	private static String convertObjectToSqlSyntaxe(Object value){
		String valuesSql = "''";
		if(value != null){
			if(value instanceof String){
				valuesSql = "'"+((String) value).replaceAll("'", "''")+"'";
			}else if(value instanceof Boolean){
				valuesSql = ""+((Boolean)value? 1 : 0);
			}else{
				valuesSql = value.toString();
			}
		}
		return valuesSql;
	}

}
