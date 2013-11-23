package dao.sql.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import properties.PropertiesLoader;
import dao.controller.AllControllerDAO;
import dao.util.SqlLiteHelper;
import data.Creche;

public class CrecheDAO extends AbstractSqlAbtractDAO<Creche> {

	private static Logger logger = Logger.getLogger(CrecheDAO.class);

	private enum EColomnNameTable implements EColumnName{
		NAME("name"),
		CONTACT("contact"),
		PHONE("phone"),
		ADRESSE("adresse"),
		CODE_POSTAL("codePostal"),
		VILLE("ville"),
		NUM_DOSSIER("numDossier"),
		NUM_AUDIT("numAudit");

		private String name;

		EColomnNameTable(String name ){
			this.name = name;
		}

		@Override
		public String toString(){
			return name;
		}
	}


	public CrecheDAO(AllControllerDAO allDAOController) {
		super(allDAOController, PropertiesLoader.getCrecheTable());
	}

	@Override
	public Creche find(int id) {
		Creche obj = null;
		ArrayList<EColumnName> columnName=  super.getListColumnName(EColomnNameTable.values());
		ResultSet rs = connect.query(SqlLiteHelper.getReqSelectAllData(tableDataBase, columnName, id));
		if(rs != null){
			try {
				//Create new Object
				obj = new Creche(rs.getInt(EDefaultColomnName.ID.toString()),
						rs.getString(EColomnNameTable.NAME.toString()),
						rs.getString(EColomnNameTable.CONTACT.toString()),
						rs.getString(EColomnNameTable.PHONE.toString()),
						rs.getString(EColomnNameTable.ADRESSE.toString()),
						rs.getString(EColomnNameTable.CODE_POSTAL.toString()),
						rs.getString(EColomnNameTable.VILLE.toString()),
						rs.getString(EColomnNameTable.NUM_DOSSIER.toString()),
						rs.getString(EColomnNameTable.NUM_AUDIT.toString()),
						rs.getBoolean(EDefaultColomnName.SHOW_ITEM.toString()) );
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
	public Creche create(Creche obj) {
		Map<EColumnName, Object> valuesMap = getMappingColumnNameAndValue(obj);
		return this.createHelper(obj, valuesMap);
	}

	@Override
	public Creche update(Creche obj) {
		Map<EColumnName, Object> valuesMap = getMappingColumnNameAndValue(obj);
		return this.updateHelper(obj, valuesMap);
	}

	@Override
	public Map<EColumnName, Object> getMappingColumnNameAndValue(Creche obj) {
		Map<EColumnName, Object> valuesMap = super.getMappingColumnNameAndValue(obj);
		valuesMap.put(EColomnNameTable.NAME, obj.getName());
		valuesMap.put(EColomnNameTable.CONTACT, obj.getContact());
		valuesMap.put(EColomnNameTable.PHONE, obj.getPhone());
		valuesMap.put(EColomnNameTable.ADRESSE, obj.getAdresse());
		valuesMap.put(EColomnNameTable.CODE_POSTAL, obj.getCodePostale());
		valuesMap.put(EColomnNameTable.VILLE, obj.getVille());
		valuesMap.put(EColomnNameTable.NUM_DOSSIER, obj.getNumDossier());
		valuesMap.put(EColomnNameTable.NUM_AUDIT, obj.getNumAudit());
		return valuesMap;
	}
}
