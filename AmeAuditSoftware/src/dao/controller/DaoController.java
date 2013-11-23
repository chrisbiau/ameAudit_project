package dao.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mvc.common.util.DataObservable;

import org.apache.log4j.Logger;

import dao.AbtractDAO;
import dao.sql.data.AbstractSqlAbtractDAO;
import dao.sql.data.EColumnName;
import data.DataObject;

public class  DaoController<T> extends AbtractDAO<T>{

	


	private final DataObservable<Integer> maxProgressBar = new DataObservable<Integer>();
	private final DataObservable<Integer> valueProgressBar = new DataObservable<Integer>();

	protected AbstractSqlAbtractDAO<T> dataBaseDAO;
	private static Logger logger = Logger.getLogger(DaoController.class);

	public DaoController ( AbstractSqlAbtractDAO<T> dataBaseDAO) {
		super();
		this.dataBaseDAO = dataBaseDAO;
	}

	@Override
	public T find(int id) {
		T obj;
			logger.debug("Find object with id:"+id+" in dataBaseDAO: "+dataBaseDAO.toString());
			obj = dataBaseDAO.find(id);		
		return obj;
	}

	@Override
	public T update(T obj) {
		int id = ((DataObject) obj).getId();
		logger.debug("Updatings object with id:"+id+" in dataBaseDAO: "+dataBaseDAO.toString());
		T objDb = dataBaseDAO.update(obj);
		return objDb;
	}


	@Override
	public T create(T obj) {
		int id = ((DataObject) obj).getId();
		logger.info("Creating with id:"+id+" in dataBaseDAO: "+dataBaseDAO.toString());
		T objDb = dataBaseDAO.create(obj);
		id = ((DataObject) objDb).getId();

		return objDb;
	}

	@Override
	public void delete(T obj) {
		int id = ((DataObject) obj).getId();
		logger.debug("Deleting object with id:"+id+" in dataBaseDAO: "+dataBaseDAO.toString());
		dataBaseDAO.delete(obj);
	}


	@Override
	public ArrayList<Integer> getAllIdInDataBase() {
		return dataBaseDAO.getAllIdInDataBase();
	}


	@Override
	public ArrayList<T> getAllValuesDataBase() {
		return (ArrayList<T>) getAllDataBase().values();
	}

	@Override
	public Map<Integer, T> getAllDataBase() {
		Map<Integer, T> map = new HashMap<Integer, T>();
		ArrayList<Integer> allIdDataBase = this.getAllIdInDataBase();
		maxProgressBar.setValue(allIdDataBase.size());
		valueProgressBar.setValue(0);
		for(int id : allIdDataBase){
			map.put(id, this.find(id) );
			valueProgressBar.setValue(valueProgressBar.getValue()+1);
		}
		valueProgressBar.setValue(0);
		return map;
	}

	public ArrayList<Integer> findIdWithValueColunm (Map<EColumnName, Object> map){
		return dataBaseDAO.findIdWithValueColunm(map);
	}



	public T findObjectOrCreateInDAO (T obj){
		T tModel = null;
		Map<EColumnName, Object> map = dataBaseDAO.getMappingColumnNameAndValue(obj);

		ArrayList<Integer> objId = dataBaseDAO.findIdWithValueColunm(map);
		if(!objId.isEmpty())
			tModel = this.find(objId.get(0));
		else
			tModel = this.create(obj);
		return  tModel;
	}


	public DataObservable<Integer> getMaxProgressBar() {
		return maxProgressBar;
	}

	public DataObservable<Integer> getValueProgressBar() {
		return valueProgressBar;
	}



}

