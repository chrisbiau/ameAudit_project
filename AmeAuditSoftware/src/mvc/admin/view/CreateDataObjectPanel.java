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

import data.DataObject;
import data.DataObjectTypeEnum;
import data.Query;

public class CreateDataObjectPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(CreateDataObjectPanel.class);
	private JButton btnSave;
	private final ControllerAdminMVC controllerAdminMVC;

	private UtilBuildViewAdmin utilPanelAdmin;

	public CreateDataObjectPanel(ControllerAdminMVC controllerAdminMVC, DataObjectTypeEnum typeDataObject, Query queryObject) {
		super(new FlowLayout(FlowLayout.LEFT));
		logger.debug("init");
		this.controllerAdminMVC = controllerAdminMVC;

		if(typeDataObject!=null){
			this.utilPanelAdmin = new UtilBuildViewAdmin(typeDataObject);
			if(queryObject != null){
				utilPanelAdmin.setQeury(queryObject);
			}
			JPanel panel = this.utilPanelAdmin.createJpanel();

			this.setLayout(new BorderLayout());
			this.add(panel, BorderLayout.NORTH);

			JPanel btnPanel = new JPanel(new FlowLayout());
			btnSave = new JButton("Ajouter");
			btnSave.addActionListener(this);
			btnPanel.add(btnSave);

			this.add(btnPanel, BorderLayout.SOUTH);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnSave){
			creatObj();
		}
	}

	private void creatObj() {
		DataObject dataObjectCreate = this.utilPanelAdmin.getDataObjectFromMap();
		controllerAdminMVC.addObjetData(dataObjectCreate);
	}
	
	

}
