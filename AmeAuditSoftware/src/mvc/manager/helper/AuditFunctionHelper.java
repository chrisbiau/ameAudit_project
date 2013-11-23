package mvc.manager.helper;

import java.util.ArrayList;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import mvc.ManagerMVC;
import mvc.form.view.CrecheDialog;

import org.apache.log4j.Logger;

import data.Audit;
import data.Creche;

public class AuditFunctionHelper {

	private static Logger logger = Logger.getLogger(AuditFunctionHelper.class);
	private static  Icon infoIcon = UIManager.getIcon("OptionPane.questionIcon");
	
	
	public static boolean newAudit (ManagerMVC managerMVC){

		boolean status = true;
		Map<Integer, Audit> listAuditModel = managerMVC.getModelManager().getListAuditModel().getValue();
		if(status = true){
			managerMVC.getModelToSave().setSelectedAudit(null);
			//Selection Audit
			Object[] listAuditVersion = listAuditModel.values().toArray();
			Audit auditSelected = (Audit) JOptionPane.showInputDialog(
					managerMVC.getApplicationView().getContentPane(),
					"Selectionner une version de grille d'audit: \n",
					"Selection version Audit",
					JOptionPane.PLAIN_MESSAGE,
					infoIcon,
					listAuditVersion,"");

			managerMVC.getModelToSave().setSelectedAudit(auditSelected);
			if(auditSelected == null){
				JOptionPane.showMessageDialog(managerMVC.getApplicationView().getContentPane(),
						"Aucune version d'audit selectionée ",
						"Erreur: Selection audit",
						JOptionPane.ERROR_MESSAGE);
				status = false;
			} 
		}
		if(status = true){
			
			managerMVC.getModelToSave().setSelectedCreche(new Creche("", false));
			//Selection creche

			Map<Integer, Creche> listCrecheModel = managerMVC.getModelManager().getListCrecheModel().getValue();
			ArrayList<Creche> listCreche = new ArrayList<Creche>();
			listCreche.add(new Creche("Enregistrer nouvelle creche",false));
			listCreche.addAll(listCrecheModel.values());
			Creche crecheSelected = (Creche) JOptionPane.showInputDialog(
					managerMVC.getApplicationView().getContentPane(),
					"Selectionner une creche a auditer: \n",
					"Selection creche",
					JOptionPane.PLAIN_MESSAGE,
					infoIcon,
					listCreche.toArray(),"");

			if(crecheSelected != null && crecheSelected.getValid()){
				managerMVC.getModelToSave().setSelectedCreche(crecheSelected);
			}else if(crecheSelected != null && !crecheSelected.getValid()) {
				new CrecheDialog(managerMVC);
			}

			if(managerMVC.getModelToSave().getSelectedCreche() == null){
				JOptionPane.showMessageDialog(managerMVC.getApplicationView().getContentPane(),
						"Aucune creche selectionnée \n Impossible démarrer l'audit ",
						"Erreur: Selection creche",
						JOptionPane.ERROR_MESSAGE);
				status = false;
			}
		}
		return status;
	}

	public static boolean changeAdminMode(ManagerMVC managerMVC) {
		return true;
	}

	public static boolean changeAdminModeOK(ManagerMVC managerMVC) {
		boolean status = false;
		JLabel jUserName = new JLabel("User Name");
		JLabel userName = new JLabel("administrator");
		//JTextField userName = new JTextField();
		JLabel jPassword = new JLabel("Password");
		JTextField password = new JPasswordField();
		Object[] ob = {jUserName, userName, jPassword, password};
		
		int result = JOptionPane.showConfirmDialog(managerMVC.getApplicationView().getContentPane(), 
				ob,
				"Please input password for JOptionPane showConfirmDialog",
				JOptionPane.OK_CANCEL_OPTION);
		
		if (result == JOptionPane.OK_OPTION) {
			//String userNameValue = userName.getText();
			String passwordValue = password.getText();
			if(passwordValue.equals("admin")){
				status = true;
			}
			else{
				status = false;
				JOptionPane.showMessageDialog(managerMVC.getApplicationView().getContentPane(),
						"Le Password est incorrect ! ",
						"Erreur: login",
						JOptionPane.ERROR_MESSAGE);
			}
				
		}
		
		
		return status;
	}

}