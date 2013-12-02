package mvc.manager.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;

import mvc.ManagerMVC;
import mvc.manager.model.ExportModelToSave;

import org.apache.log4j.Logger;

import util.FilePreview;
import util.FiltreFile;

public class SaveFunctionHelper {

	private static Logger logger = Logger.getLogger(SaveFunctionHelper.class);
	private final static FiltreFile ameAuditFilter = new FiltreFile("AME Audit Files (*.ameaudit)",".ameaudit");

	public static boolean openAudit (ManagerMVC managerMVC){
		boolean status = false;
		JFileChooser openFileDialog = getFileChooser();
		openFileDialog.setAccessory(new FilePreview(openFileDialog));
		int returnVal = openFileDialog.showOpenDialog(managerMVC.getApplicationView().getContentPane());
		if(returnVal == JFileChooser.APPROVE_OPTION){
			File filePath = openFileDialog.getSelectedFile();
			ExportModelToSave importModelToSave  = openModelResultFile(filePath.toString());
			managerMVC.getModelToSave().importModelToSave(importModelToSave);
			status = true;
		}
		return status;
	}


	public static boolean saveAudit (ManagerMVC managerMVC){
		//http://stackoverflow.com/questions/4738162/java-writting-reading-a-map-from-disk
		boolean status = false;
		JFileChooser saveFileDialog = getFileChooser();
		int returnValSave = saveFileDialog.showSaveDialog(managerMVC.getApplicationView().getContentPane());
		if(returnValSave == JFileChooser.APPROVE_OPTION){
			File fileSave = saveFileDialog.getSelectedFile();
			if(!fileSave.getPath().toLowerCase().endsWith(".ameaudit"))
			{
				fileSave = new File (fileSave.getPath() + ".ameaudit");
			}
			saveModelResultToFile(managerMVC.getModelToSave().exportModelToSave(), fileSave);
			status = true;
		}
		return status;
	}


	private static JFileChooser getFileChooser() {
		JFileChooser saveFileDialog = new JFileChooser();
		saveFileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
		saveFileDialog.setFileFilter(ameAuditFilter);
		return saveFileDialog;
	}


	public static void saveModelResultToFile(ExportModelToSave modelResult, File fileSave){
		try {

			FileOutputStream fos;
			fos = new FileOutputStream(fileSave);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(modelResult);
			oos.close();

		} catch (FileNotFoundException e) {
			logger.error("File to save not found"+fileSave+e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("IOException: "+e);
			e.printStackTrace();
		}
	}

	public static ExportModelToSave openModelResultFile( String filePath){
		ExportModelToSave model = null;
		try {
			FileInputStream fis = new FileInputStream(filePath);
			ObjectInputStream ois = new ObjectInputStream(fis);
			model = (ExportModelToSave) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			logger.error("File to read not found"+filePath+e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("IOException: "+e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException: "+e);
			e.printStackTrace();
		}
		return model;
	}

}