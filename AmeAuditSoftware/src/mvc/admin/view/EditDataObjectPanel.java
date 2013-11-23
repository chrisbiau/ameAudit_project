package mvc.admin.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import mvc.admin.controller.ControllerAdminMVC;
import mvc.admin.view.common.UtilBuildViewAdmin;

import org.apache.log4j.Logger;

import dao.service.ServiceDAO;
import data.DataObject;
import data.DataObjectTypeEnum;
import data.Query;

public class EditDataObjectPanel extends JPanel implements ActionListener {

	private static Logger logger = Logger.getLogger(EditDataObjectPanel.class);
	
	private DataObject dataObject;
	private JButton btnSave;
	private JButton btnAddNew;
	private final ControllerAdminMVC controllerAdminMVC;
	private UtilBuildViewAdmin utilPanelAdmin;
	
	public EditDataObjectPanel(ControllerAdminMVC controllerAdminMVC, DataObject dataObject) {
		super(new FlowLayout(FlowLayout.LEFT));
		this.controllerAdminMVC = controllerAdminMVC;
		this.dataObject= dataObject;
		if(dataObject!=null){
			
		this.utilPanelAdmin = new UtilBuildViewAdmin(dataObject);
		JPanel panel = this.utilPanelAdmin.createJpanel();
		
			this.setLayout(new BorderLayout());
			this.add(panel, BorderLayout.NORTH);

			JPanel btnPanel = new JPanel(new FlowLayout());
			btnSave = new JButton("Enregistrer");
			btnSave.addActionListener(this);
			btnPanel.add(btnSave);
			if(dataObject instanceof Query){
				btnAddNew = new JButton("Ajouter une "+ DataObjectTypeEnum.ANSWER);
				btnAddNew.addActionListener(this);
				btnPanel.add(btnAddNew);
			}
			this.add(btnPanel, BorderLayout.SOUTH);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnSave){
			updateObj();
		}else if(e.getSource() == btnAddNew){
		//TODO:
			logger.warn("TODO: TRAITER CAS NEW ANSWER");
		}
	}

	private void updateObj() {
		dataObject = this.utilPanelAdmin.getDataObjectFromMap();
		ServiceDAO.getInstance().updateObjetData(dataObject);
		controllerAdminMVC.reloadTree(dataObject,false);
	}

}
