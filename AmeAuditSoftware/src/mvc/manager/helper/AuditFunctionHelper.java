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
import data.Audit;
import data.Creche;

public class AuditFunctionHelper {

//	private static Logger logger = Logger.getLogger(AuditFunctionHelper.class);
	private static  Icon infoIcon = UIManager.getIcon("OptionPane.questionIcon");


	public static boolean newAudit (ManagerMVC managerMVC){

		boolean status = false;
		Map<Integer, Audit> listAuditEnvModel = managerMVC.getModelManager().getListAuditEnvModel().getValue();
		managerMVC.getModelToSave().setSelectedAuditEnv(null);
		//Selection Audit Envvironement
		Object[] listAuditEnvVersion = listAuditEnvModel.values().toArray();
		Audit auditEnvSelected = (Audit) JOptionPane.showInputDialog(
				managerMVC.getApplicationView().getContentPane(),
				"Selectionner une version de grille d'audit environnement: \n",
				"Selection version audit environnement",
				JOptionPane.PLAIN_MESSAGE,
				infoIcon,
				listAuditEnvVersion,"");

		if(auditEnvSelected == null){
			JOptionPane.showMessageDialog(managerMVC.getApplicationView().getContentPane(),
					"Aucune version d'audit environement selectionée ",
					"Erreur: Selection audit",
					JOptionPane.ERROR_MESSAGE);
		} else{
			managerMVC.getModelToSave().setSelectedAuditEnv(auditEnvSelected);
			status = true;
		}



		Map<Integer, Audit> listAuditSocModel = managerMVC.getModelManager().getListAuditSocModel().getValue();
		managerMVC.getModelToSave().setSelectedAuditSoc(null);
		//Selection Audit social
		Object[] listAuditSociaVersion = listAuditSocModel.values().toArray();
		Audit auditSociSelected = (Audit) JOptionPane.showInputDialog(
				managerMVC.getApplicationView().getContentPane(),
				"Selectionner une version de grille d'audit sociale: \n",
				"Selection version audit sociale",
				JOptionPane.PLAIN_MESSAGE,
				infoIcon,
				listAuditSociaVersion,"");

		if(auditSociSelected == null){
			JOptionPane.showMessageDialog(managerMVC.getApplicationView().getContentPane(),
					"Aucune version d'audit sociale selectionée ",
					"Erreur: Selection audit",
					JOptionPane.ERROR_MESSAGE);
		} else{
			managerMVC.getModelToSave().setSelectedAuditSoc(auditSociSelected);
			status = true;
		}


		//Si audit selectionee
		if(status){

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
						"Aucune creche selectionnÃ©e \n Impossible dÃ©marrer l'audit ",
						"Erreur: Selection creche",
						JOptionPane.ERROR_MESSAGE);
				status = false;
			}else{
				status = true;
			}
		}
		return status;
	}

	public static boolean changeAdminMode(ManagerMVC managerMVC) {
		return changeAdminModeOK(managerMVC);
	}

	public static boolean changeAdminModeOK(ManagerMVC managerMVC) {
		boolean status = false;
		JLabel jUserName = new JLabel("User Name");
		JTextField userName = new JTextField("administrator");
		userName.setEditable(false);
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