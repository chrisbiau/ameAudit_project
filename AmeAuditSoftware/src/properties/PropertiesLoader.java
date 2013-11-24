package properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import properties.data.TableDataBase;


public class PropertiesLoader {



	private static Logger logger = Logger.getLogger(PropertiesLoader.class);

	private static String BDD_PATH_KEY="dbbPath";

	private static String ANWER_TABLE_KEY="answerTable";
	private static String AUDIT_TABLE_KEY="auditTable";
	private static String COLOR_TABLE_KEY="colorTable";
	private static String DIALOG_TABLE_KEY="inputDialogTable";
	private static String NUMERI_RULE_TABLE_KEY="numericRuleTable";
	private static String QUERY_TABLE_KEY="queryTable";
	private static String ROOM_TABLE_KEY="roomTable";
	private static String TOPIC_TABLE_KEY="topicTable";
	private static String CRECHE_TABLE_KEY="crecheTable";
	private static String GRID_TABLE_KEY="gridTable";


	private static String propertiesFiles;;


	private static Properties instance = null;

	private static InputStream inputStream;


	private PropertiesLoader() {
		super();
	}

	private static Properties getInstance() {
        if (null == instance) { // Premier appel
            instance = new Properties();
            try {
				instance.load(new FileInputStream(propertiesFiles));
			} catch (FileNotFoundException e) {
				logger.error("Properties file not found: "+e);
				e.printStackTrace();
			} catch (IOException e) {
				logger.error("load exception: "+e);
				e.printStackTrace();
			}
        }
        return instance;
    }

 
	
	public static  void setPropertiesFiles(String propertiesFiles) {
		PropertiesLoader.propertiesFiles = propertiesFiles;
	}

	public static String getBddPath(){
		String bdd = "";
		bdd = PropertiesLoader.getInstance().getProperty(BDD_PATH_KEY);
		return bdd.replace("\\", File.separator.toString());
	}


	public static TableDataBase getAnswerTable(){
		return new TableDataBase( PropertiesLoader.getInstance().getProperty(ANWER_TABLE_KEY));
	}

	public static TableDataBase getAuditTable(){
		return new TableDataBase(PropertiesLoader.getInstance().getProperty(AUDIT_TABLE_KEY));
	}

	public static TableDataBase getColorTable(){
		return new TableDataBase(PropertiesLoader.getInstance().getProperty(COLOR_TABLE_KEY));
	}

	public static TableDataBase getInputDialogTable(){
		return new TableDataBase(PropertiesLoader.getInstance().getProperty(DIALOG_TABLE_KEY));
	}

	public static TableDataBase getNumericRuleTable(){
		return new TableDataBase(PropertiesLoader.getInstance().getProperty(NUMERI_RULE_TABLE_KEY));
	}

	public static TableDataBase getQueryTable(){
		return new TableDataBase(PropertiesLoader.getInstance().getProperty(QUERY_TABLE_KEY));
	}

	public static TableDataBase getRoomTable(){
		return new TableDataBase(PropertiesLoader.getInstance().getProperty(ROOM_TABLE_KEY));
	}

	public static TableDataBase getTopicTable(){
		return new TableDataBase(PropertiesLoader.getInstance().getProperty(TOPIC_TABLE_KEY));
	}

	public static TableDataBase getCrecheTable() {
		return new TableDataBase(PropertiesLoader.getInstance().getProperty(CRECHE_TABLE_KEY));
	}

	public static TableDataBase getGridTable() {
		return new TableDataBase(PropertiesLoader.getInstance().getProperty(GRID_TABLE_KEY));
	}




}
