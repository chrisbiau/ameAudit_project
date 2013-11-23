package mvc.form.view;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import mvc.ManagerMVC;
import mvc.common.util.DataListenerInterface;
import mvc.form.model.ModelFormMVC;
import start.AppProgressBar;
import data.Audit;
import data.Query;

public class AuditJTabbedPane extends JTabbedPane {



	/**
	 *
	 */

	private final JScrollPane crecheContainer = new JScrollPane();

	private JComponent crechePanel = null;
	private final ManagerMVC managerMVC;
	private Component adminComponent;

	public AuditJTabbedPane(ManagerMVC managerMVC) {
		super();	
		this.setVisible(true);
		setCrechePanel(new CrechePanel(managerMVC));
		this.managerMVC = managerMVC;
		managerMVC.getModelManager().getAdminMode().addListener(new AdminModeListener());

	
	}


	public void addAudit(final Audit audit){
		ModelFormMVC modelForm = managerMVC.getFormMVC().getControllerFormMVC().getModelFormMVC();
		HashMap<Integer, HashMap<Integer, ArrayList<Query>>> mapRoomTopicQuery = modelForm.getMapRoomTopicQuery(audit);
		AppProgressBar.getInstance().setMaximumProgressBar(modelForm.getNbQueryOfSelectedAudit());
		add(audit.getGrid().getName(),new RoomJTabbedPane(managerMVC, mapRoomTopicQuery));
	}


	private void setCrechePanel(JComponent crechePanel) {
		if(this.crechePanel !=null){
			this.remove(this.crechePanel);
			crecheContainer.remove(crechePanel);
		}
		this.crechePanel = crechePanel;
		crecheContainer.setViewportView(crechePanel);
		this.add("Creche",crecheContainer);
	}
	
	
	private class AdminModeListener implements DataListenerInterface {

		@Override
		public void dataChange(Object ojbUpdated) {
			if((Boolean) ojbUpdated){
//				adminComponent = add("Administrator",managerMVC.getAdminMVC().getControllerMVC().get);
			}else{
				remove(adminComponent);
			}
		}

	}

	
	

}
