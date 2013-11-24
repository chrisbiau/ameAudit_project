package mvc.manager.view;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import mvc.common.enumeration.EAction;
import mvc.common.enumeration.Eview;
import mvc.manager.controller.ManagerController;

public class MenuBar  extends JMenuBar{


	public MenuBar(ManagerController managerController){
		super();
		build(managerController);
	}

	private void build(ManagerController managerController) {

		JMenu menu1 = new JMenu("Fichier");

		JMenuItem nouveau = new JMenuItem(new UserAction(managerController,  EAction.newAudit, "Nouveau"));
		menu1.add(nouveau);
		
		JMenuItem open = new JMenuItem(new UserAction(managerController, EAction.openAudit, "Ouvrir Audit"));
		menu1.add(open);

		JMenuItem save = new JMenuItem(new UserAction(managerController, EAction.saveAudit, "Sauvegarder Audit"));
		menu1.add(save);

		JMenuItem quitter = new JMenuItem(new QuitterAction("Quitter"));
		menu1.add(quitter);
		this.add(menu1);

		JMenu menu2 = new JMenu("Vue");
		JMenuItem parser  = new JMenuItem(new UserAction(managerController, EAction.parser, "Import"));
		menu2.add(parser);
		JMenuItem admin  = new JMenuItem(new UserAction(managerController, EAction.adminMode, "Administrateur"));
		menu2.add(admin);
		this.add(menu2);


		JMenu menu3 = new JMenu("?");
		JMenuItem aPropos = new JMenuItem(new AProposAction(managerController.getApplicationView(), "A propos"));
		menu3.add(aPropos);
		this.add(menu3);
	}

	private class QuitterAction extends AbstractAction {
		public QuitterAction(String texte){
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}


	private class AProposAction extends AbstractAction {
		private final JFrame applicationView;

		public AProposAction(JFrame applicationView, String texte){
			super(texte);

			this.applicationView = applicationView;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(applicationView, "Ce programme a développé par CBIAU");
		}
	}

	private class ChangeViewAction extends AbstractAction {
		private final Eview eView;
		private final ManagerController managerController;

		public ChangeViewAction(ManagerController managerController, Eview eView, String texte){
			super(texte);
			this.eView = eView;
			this.managerController= managerController;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			managerController.displayPanel(eView);
		}
	}

	private class UserAction extends AbstractAction {
		private final EAction eAction;
		private final ManagerController managerController;

		public UserAction(ManagerController managerController, EAction eAction, String texte){
			super(texte);
			this.eAction = eAction;
			this.managerController = managerController;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			managerController.userAction(eAction);
		}
	}



}
