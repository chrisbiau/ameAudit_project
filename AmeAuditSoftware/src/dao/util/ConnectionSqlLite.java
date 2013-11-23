package dao.util;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

import properties.PropertiesLoader;
import properties.data.TableDataBase;
import util.FileHelper;

;
//http://guendouz.wordpress.com/2012/11/23/utilisation-de-la-base-de-donnees-sqlite-en-java/
public class ConnectionSqlLite {
	private static Logger logger = Logger.getLogger(ConnectionSqlLite.class);

	private static Connection connection = null;
	private static ConnectionSqlLite instance = null;
	private static String dBPath = null;

	private static Statement statement = null;
	public static ConnectionSqlLite getInstance(){
		if(instance == null){
			instance = new ConnectionSqlLite();
		}
		return instance;
	}



	private ConnectionSqlLite() {
		dBPath = PropertiesLoader.getBddPath();		if(FileHelper.testFileExist(dBPath) ){
			this.connect();
		}else{
			logger.error("Database File: "+ dBPath + " doesn't exit");
		}
	}

	private void connect() {

		try {
			Properties porp = new Properties();

			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" +  dBPath, porp);
			statement = connection.createStatement();
			logger.debug("Connexion a " + dBPath + " avec succes");
		} catch (ClassNotFoundException notFoundException) {
			logger.error("Not found driver: " + notFoundException );
		} catch (SQLException sqlException) {
			logger.error("Erreur de connection: " + sqlException );
		}
	}

	public static void close() {
		try {
			statement.close();
			connection.close();
			logger.debug("Fermetue connection sql");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e+"Erreur fermetue connection sql");
		}
	}

	public ResultSet query(String requet) {
		ResultSet resultat = null;
		String rqt = "";
		try {
			rqt = new String(requet.getBytes(), "UTF-8");
			logger.debug(rqt);
			boolean result = statement.execute(rqt);
			if(result){//get
				resultat = statement.getResultSet();
			}else{ //Insert
				resultat = getLastInsertRowId();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e+" Requete: " + requet);
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.warn("Impossible de convertir la chaine en UTF-8 : " + rqt);
		}
		return resultat;
	}



	private ResultSet getLastInsertRowId() {
		ResultSet resultat = null;
		String rqt = "";
		try {
			rqt = new String(SqlLiteHelper.getReqLastInsertRowId().getBytes(), "UTF-8");
			logger.debug(rqt);
			resultat = statement.executeQuery(rqt);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.warn("Erreur dans la requet : " + SqlLiteHelper.getReqLastInsertRowId());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.warn("Impossible de convertir la chaine en UTF-8 : " + rqt);
		}
		return resultat;
	}

	public void viderTable (TableDataBase table){
		this.query("DELETE FROM "+table+";");
		this.query("DELETE FROM sqlite_sequence WHERE name='"+table+"';");
	}

	public static Connection getConnection() {
		return connection;
	}


}
