package mvc.form.view;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import mvc.ManagerMVC;

public class CrecheDialog extends JDialog implements ActionListener{


	private final CrechePanel panelCreche;

	private final JButton valider = new JButton("Valider");
	private final JButton annuler = new JButton("Annuler");


	/**
	 *
	 */
	public CrecheDialog(ManagerMVC managerMVC) {
		super();
		Box box = Box.createVerticalBox();
		setModal(true);
		setTitle("Enregistrer une nouvelle Creche");
		panelCreche = new CrechePanel(managerMVC);

		JPanel actionBoxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		actionBoxPanel.add(valider);
		actionBoxPanel.add(annuler);
		valider.addActionListener(this);
		annuler.addActionListener(this);

		box.add(panelCreche);
		box.add(actionBoxPanel);
		panelCreche.setBorder(BorderFactory.createRaisedBevelBorder());

		this.add(box);
		this.pack();
		this.setLocationRelativeTo(managerMVC.getApplicationView());
		this.setResizable(false);
		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent evt){
		Object source = evt.getSource();
		if(source == valider){
			//set creche in model
			dispose();
		}else{
			dispose();
		}
	}




}
