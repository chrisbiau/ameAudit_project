package parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import mvc.form.controller.ControllerFormMVC;
import properties.PropertiesLoader;
import dao.util.ConnectionSqlLite;
import data.Answer;
import data.Audit;
import data.Color;
import data.InputDialog;
import data.Query;
import data.Room;
import data.Topic;

public class ReadExcel {

	private final String inputFile;
	private final ControllerFormMVC mvcController;

	public ReadExcel(ControllerFormMVC mvcController) {
		super();
		this.mvcController = mvcController;

		ArrayList<Audit> listaudit = mvcController.getModelFormMVC().getListAudit();


		inputFile = "parseExcel"+File.separator+"newV3bug.xls";
		try {
			read(listaudit.get(0));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.init();
}




  public void read(Audit audit) throws IOException  {
    File inputWorkbook = new File(inputFile);
	WorkbookSettings ws = new WorkbookSettings();
	ws.setEncoding("ISO-8859-1");
	ws.setLocale(Locale.FRANCE);

    Workbook w;

    try {

      w = Workbook.getWorkbook(inputWorkbook,ws);

      ConnectionSqlLite.getInstance().viderTable(PropertiesLoader.getAnswerTable());
      ConnectionSqlLite.getInstance().viderTable(PropertiesLoader.getQueryTable());

      // Get the first sheet
      Sheet sheet = w.getSheet(0);
      // Loop over first 10 column and lines


//      mvcController.getModelMVC().setActivenotif(false);

      for (int row = 1; row < sheet.getRows(); row++) {

    	  String themeStr = 	sheet.getCell(2, row).getContents();
    	  String roomStr = 		sheet.getCell(3, row).getContents();
          String who = 			sheet.getCell(4, row).getContents();
    	  String infoQuestStr = sheet.getCell(5, row).getContents();
    	  String questionStr =  sheet.getCell(6, row).getContents();
    	  String inputStr =  	sheet.getCell(8, row).getContents();

    	  if(inputStr.contains("numérique")){
    		  inputStr = "JSpinner" ;
    	  }else if(inputStr.contains("checkbox")){
    		  inputStr = "JCheckBox" ;
    	  }else if(inputStr.contains("liste")){
    		  inputStr = "JComboBox" ;
    	  }else if(inputStr.contains("ouverte")){
    		  inputStr = "JTextField" ;
    	  }


    	  System.out.println("------------------------------------------");



    	  Room roomSql = mvcController.getModelFormMVC().findRoomObjectByValueOrCreate(new Room(roomStr));
    	  Topic topicSql = mvcController.getModelFormMVC().findTopicObjectByValueOrCreate(new Topic(themeStr,null));
    	  InputDialog inputDialog = mvcController.getModelFormMVC().findInputObjectByValueOrCreate(new InputDialog(inputStr));
    	  Query query =  new  Query(audit, questionStr, infoQuestStr, "",  roomSql, topicSql, inputDialog, 0, 0);
    	  mvcController.getModelFormMVC().findAnswerObjectByValueOrCreate(query);

    	  Color color = null;
    	  if( (inputStr.contains("JTextField") ||   inputStr.contains("JSpinner")) && sheet.getCell(9, row).getContents().isEmpty() ){
    		  color = new Color("Sans", "#000000");
    		  color =  mvcController.getModelFormMVC().findColorObjectByValueOrCreate(color);
			  Answer ans = new Answer(query, "Saisir réponse", color, 0, 0, "", false);
			  ans=   mvcController.getModelFormMVC().findAnswerObjectByValueOrCreate(ans);
    	  }else{
    		  for (int col = 9; col < sheet.getColumns(); col=col+3) {
    			  String reponseStr   = sheet.getCell(col, row).getContents();
    			  if(!reponseStr.isEmpty()){
    				  String reponseColor = "";
    				  if(col+1 < sheet.getColumns()){
    					  reponseColor = sheet.getCell(col+1, row).getContents();
    				  }
    				  String reponseCom = "";
    				  if(col+2 < sheet.getColumns()){
    					  reponseCom   = sheet.getCell(col+2, row).getContents();
    				  }

    				  if(reponseColor.contains("ouge")){
    					  color = new Color("Rouge", null);
    				  }else if(reponseColor.contains("range")){
    					  color = new Color("Orange", null);
    				  }else if(reponseColor.contains("aune")){
    					  color = new Color("Jaune", null);
    				  }else if(reponseColor.contains("clair")){
    					  color = new Color("Vert clair", null);
    				  }else if(reponseColor.contains("fonc")){
    					  color = new Color("Vert fonce", null);
    				  }else if(!reponseColor.isEmpty()) {
    					  color = new Color(reponseColor, null);
    				  }else{
    					  color = new Color("Sans", "#000000");
    				  }


    				  color =  mvcController.getModelFormMVC().findColorObjectByValueOrCreate(color);
    				  Answer ans = new Answer(query, reponseStr, color, 0, 0, "", false);
    				  ans=   mvcController.getModelFormMVC().findAnswerObjectByValueOrCreate(ans);
    			  }
    		  }
    	  }
      }

//      mvcController.getModelMVC().setActivenotif(true);

    } catch (BiffException e) {
      e.printStackTrace();
    }
  }

  public  void init() {

  }

}