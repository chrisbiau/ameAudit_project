package mvc.form.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import mvc.ManagerMVC;
import mvc.manager.model.ModelToSave;
import data.Creche;

public class CrechePanel extends JPanel implements KeyListener {

	private data.Creche creche;
	private final JTextField adresseJTextField;
	private final JTextField codePostalJTextField;
	private final JTextField contactJTextField;
	private final JSpinner idJSpinner;
	private final JTextField nameJTextField;
	private final JTextField numAuditJTextField;
	private final JTextField numDossierJTextField;
	private final JTextField phoneJTextField;
	private final JTextField villeJTextField;


	private final JLabel adresseJLabel = new JLabel();
	private final JLabel codePostalJLabel = new JLabel();
	private final JLabel contactJLabel = new JLabel();
	private final JLabel idJLabel = new JLabel();
	private final JLabel nameJLabel = new JLabel();
	private final JLabel numAuditJLabel = new JLabel();
	private final JLabel numDossierJLabel = new JLabel();
	private final JLabel phoneJLabel = new JLabel();
	private final JLabel villeJLabel = new JLabel();



	private final ModelToSave modelToSave;
	private boolean modeReadWrite ;
	private final JButton annulerJButton;
	private final JButton modifyJButton;
	private final JButton saveJButton;
//	private JPanel panelForm = null;
	private final JLabel idLabel ;
	private final ManagerMVC managerMVC;

	public CrechePanel(final ManagerMVC managerMVC) {
		this.managerMVC = managerMVC;
		this.modelToSave = managerMVC.getModelToSave();
		JPanel panelForm = new  JPanel();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 251, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 , 0 , 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0E-4 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,	0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4 };
		panelForm.setLayout(gridBagLayout);

		Map<JComponent, JComponent> mapLabelWithComponent = new LinkedHashMap<JComponent, JComponent>();
		ArrayList<JLabel> listLabel = new ArrayList< JLabel>();
		//Id
		idLabel = new JLabel("Id:");
		idJSpinner = new JSpinner();
		mapLabelWithComponent.put(idLabel, idJSpinner);
		listLabel.add(idJLabel);

		//Name
		JLabel nameLabel = new JLabel("* Nom de la creche:");
		nameJTextField = new JTextField();
		mapLabelWithComponent.put(nameLabel, nameJTextField);
		listLabel.add(nameJLabel);


		//Contact
		JLabel contactLabel = new JLabel("* Contact:");
		contactJTextField = new JTextField();
		mapLabelWithComponent.put(contactLabel,contactJTextField);
		listLabel.add(contactJLabel);


		//phone
		JLabel phoneLabel = new JLabel("* Phone:");
		phoneJTextField = new JTextField();
		mapLabelWithComponent.put(phoneLabel, phoneJTextField);
		listLabel.add(phoneJLabel);


		//adresse
		JLabel adresseLabel = new JLabel("Adresse:");
		adresseJTextField = new JTextField();
		mapLabelWithComponent.put(adresseLabel, adresseJTextField);
		listLabel.add(adresseJLabel);


		//Ville
		JLabel villeLabel = new JLabel("* Ville: ");
		villeJTextField = new JTextField();
		mapLabelWithComponent.put(villeLabel, villeJTextField);
		listLabel.add(villeJLabel);

		//CodePostal
		JLabel codePostalLabel = new JLabel("Code Postal:");
		codePostalJTextField = new JTextField();
		mapLabelWithComponent.put(codePostalLabel, codePostalJTextField);
		listLabel.add(codePostalJLabel);

		//NumAudit
		JLabel numAuditLabel = new JLabel("NumAudit:");
		numAuditJTextField = new JTextField();
		mapLabelWithComponent.put(numAuditLabel, numAuditJTextField);
		listLabel.add(numAuditJLabel);


		//NumDossier
		JLabel numDossierLabel = new JLabel("NumDossier:");
		numDossierJTextField = new JTextField();
		mapLabelWithComponent.put(numDossierLabel, numDossierJTextField);
		listLabel.add(numDossierJLabel);


		//Bouttons save
		annulerJButton = new JButton("Annuler");
		saveJButton = new JButton("Sauver");
		saveJButton.setEnabled(false);
		mapLabelWithComponent.put(annulerJButton, saveJButton);

		//Bouttons modif
		modifyJButton = new JButton("Modifier");
		mapLabelWithComponent.put(new JLabel(), modifyJButton);


		int row = 0;
		for(Entry<JComponent, JComponent> entry : mapLabelWithComponent.entrySet()){
			GridBagConstraints labelGbc = new GridBagConstraints();
			labelGbc.insets = new Insets(5, 5, 5, 5);
			labelGbc.gridx = 0;
			labelGbc.gridy = row;
			panelForm.add(entry.getKey(), labelGbc);

			GridBagConstraints componentGbc = new GridBagConstraints();
			componentGbc.insets = new Insets(5, 0, 5, 5);
			componentGbc.fill = GridBagConstraints.HORIZONTAL;
			componentGbc.gridx = 1;
			componentGbc.gridy = row;
			entry.getValue().addKeyListener(this);
			panelForm.add(entry.getValue(), componentGbc);

			if(row<listLabel.size()){
				listLabel.get(row).setVisible(false);
				panelForm.add(listLabel.get(row),componentGbc );
			}
			row++;
		}

		this.add(panelForm, BorderLayout.CENTER);

		modifyJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setViewReadWrite();
			}
		});

		annulerJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(modelToSave.getSelectedCreche() != null){
					setViewReadOnly();
					initData();
				}else{
					clearField();
				}
			}
		});

		saveJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setCrecheFormInLocalModel();
				
				managerMVC.getManagerController().updateCrecheData(modelToSave.getSelectedCreche().getValue());
				initData();
			}
		});

		initData();

	}

	private void initData() {
		this.creche = modelToSave.getSelectedCreche().getValue();
		if(creche!=null){
			idJSpinner.setValue(creche.getId());
			nameJTextField.setText(creche.getName());
			phoneJTextField.setText(creche.getPhone());
			adresseJTextField.setText(creche.getAdresse());
			contactJTextField.setText(creche.getContact());
			villeJTextField.setText(creche.getVille());
			codePostalJTextField.setText(creche.getCodePostale());
			numAuditJTextField.setText(creche.getNumAudit());
			numDossierJTextField.setText(creche.getNumDossier());
			setViewReadOnly();
		}else{
			setViewReadWrite();
		}

	}

	public void setCrecheFormInLocalModel() {

		Creche crecheFrom = modelToSave.getSelectedCreche().getValue();

		if(crecheFrom!=null){
			crecheFrom.setId((Integer) idJSpinner.getValue());
			crecheFrom.setAdresse(adresseJTextField.getText());
			crecheFrom.setCodePostale(codePostalJTextField.getText());
			crecheFrom.setContact(contactJTextField.getText());
			crecheFrom.setName(nameJTextField.getText());
			crecheFrom.setNumAudit(numAuditJTextField.getText());
			crecheFrom.setNumDossier(numDossierJTextField.getText());
			crecheFrom.setPhone(phoneJTextField.getText());
			crecheFrom.setVille(villeJTextField.getText());
			crecheFrom.setValid(true);
		}else{
			crecheFrom = new Creche((Integer) idJSpinner.getValue(),
					nameJTextField.getText(),
					contactJTextField.getText(),
					phoneJTextField.getText(),
					adresseJTextField.getText(),
					codePostalJTextField.getText(),
					villeJTextField.getText(),
					numDossierJTextField.getText(),
					numAuditJTextField.getText(),
					true);
			managerMVC.getManagerController().addCreche(crecheFrom);
		}
		modelToSave.setSelectedCreche(crecheFrom);

	}

	private void clearField() {
		idJSpinner.setValue(new Integer(0));
		nameJTextField.setText("");
		phoneJTextField.setText("");
		adresseJTextField.setText("");
		contactJTextField.setText("");
		villeJTextField.setText("");
		codePostalJTextField.setText("");
		numAuditJTextField.setText("");
		numDossierJTextField.setText("");
	}

	private void setViewReadWrite(){
		modeReadWrite = true;
		updateView();
	}

	private void setViewReadOnly(){
		modeReadWrite = false;
		updateView();
	}

	private void updateView() {
//		idLabel.setVisible(!modeReadWrite);
//		idJSpinner.setVisible(modeReadWrite);
		idJSpinner.setEnabled(false);
		nameJTextField.setVisible(modeReadWrite);
		phoneJTextField.setVisible(modeReadWrite);
		adresseJTextField.setVisible(modeReadWrite);
		contactJTextField.setVisible(modeReadWrite);
		villeJTextField.setVisible(modeReadWrite);
		codePostalJTextField.setVisible(modeReadWrite);
		numAuditJTextField.setVisible(modeReadWrite);
		numDossierJTextField.setVisible(modeReadWrite);

		adresseJLabel.setVisible(!modeReadWrite);
		adresseJLabel.setText(adresseJTextField.getText());

		codePostalJLabel.setVisible(!modeReadWrite);
		codePostalJLabel.setText(codePostalJTextField.getText());

		contactJLabel.setVisible(!modeReadWrite);
		contactJLabel.setText(contactJTextField.getText());

		idJSpinner.setVisible(false);
		idJLabel.setVisible(true);
		idJLabel.setText(idJSpinner.getValue().toString());

		nameJLabel.setVisible(!modeReadWrite);
		nameJLabel.setText(nameJTextField.getText());

		numAuditJLabel.setVisible(!modeReadWrite);
		numAuditJLabel.setText(numAuditJTextField.getText());

		numDossierJLabel.setVisible(!modeReadWrite);
		numDossierJLabel.setText(numDossierJTextField.getText());

		phoneJLabel.setVisible(!modeReadWrite);
		phoneJLabel.setText(phoneJTextField.getText());

		villeJLabel.setVisible(!modeReadWrite);
		villeJLabel.setText(villeJTextField.getText());




		modifyJButton.setVisible(!modeReadWrite);
		saveJButton.setVisible(modeReadWrite);
		annulerJButton.setVisible(modeReadWrite);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(nameJTextField.getText().isEmpty() ||
				contactJTextField.getText().isEmpty() ||
				phoneJTextField.getText().isEmpty() ||
				villeJTextField.getText().isEmpty() ){
			saveJButton.setEnabled(false);
		}else{
			saveJButton.setEnabled(true);
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}





}
