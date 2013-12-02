package mvc.form.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import mvc.ManagerMVC;
import mvc.manager.model.ModelToSave;

import org.apache.log4j.Logger;

import data.Answer;
import data.Query;
import data.Result;

public class AnswersTacPanel extends JPanel {

	private static Logger logger = Logger.getLogger(AnswersTacPanel.class);

	/**
	 * Create the panel.
	 * @param modelMVC2
	 * @param mvcController
	 */

	private Query query = null;
	private JPanel answerPanel = new JPanel();
	private final ModelToSave modelToSave;


	private HashMap<Integer, Result> mapResultOfQuerySelected = null;

	private final ManagerMVC managerMVC;
	public AnswersTacPanel(ManagerMVC managerMVC, Query query) {
		super();
		this.setLayout(new BorderLayout());
		this.managerMVC =managerMVC ;
		this.modelToSave = managerMVC.getModelToSave();
		this.query = query;

		//Scroll answer Panel
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(answerPanel);
		this.add(scrollPane,BorderLayout.CENTER);
		this.setQuery(query);

	}

	public void setQuery(Query query){
		this.query = query;
		if(	modelToSave.getResultByQueryId().getValue().containsKey(query.getId())){
			mapResultOfQuerySelected = modelToSave.getResultByQueryId().getValue().get((query.getId()));
		}else{
			mapResultOfQuerySelected = null;
		}

		this.updatePanel();
		this.validate();
		this.repaint();
	}

	private void updatePanel() {
		if(answerPanel == null){
			answerPanel = new JPanel();
		}else{
			answerPanel.removeAll();
		}
		ArrayList<Answer> listAnswer = this.managerMVC.getServiceDAO().getAnswersByQuery(query);

		if(query.getInputDialog().getDialogType().equals("JComboBox")){
			putListAnswerComboBoxToPanel(listAnswer);
		}else if (query.getInputDialog().getDialogType().equals("JSpinner")){
			putListAnswerNumericToPanel(listAnswer);
		}else if (query.getInputDialog().getDialogType().equals("JCheckBox")){
			putListAnswerCheckBoxToPanel(listAnswer);
		}else if (query.getInputDialog().getDialogType().equals("JTextField")){
			putListAnswerTextFieldToPanel(listAnswer);
		}else{
			logger.error("Unknow this input dialog type: ["+query.getInputDialog().getDialogType()+"] of this query: "+query.getId()+" "+query);
		}
	}

	private void putListAnswerCheckBoxToPanel(ArrayList<Answer> listAnswer) {
		answerPanel.setLayout(new BoxLayout(answerPanel, BoxLayout.X_AXIS));
		for(Answer answer : listAnswer){
			JCheckBox checkBox = new JCheckBox(answer.getAnswerText());

			//Set save value
			if(mapResultOfQuerySelected != null){
				if(mapResultOfQuerySelected.containsKey(answer.getId())){
					if(mapResultOfQuerySelected.get(answer.getId()).getAnswerText().contains("true")){
						checkBox.setSelected(true);
					}
				}
			}

			answerPanel.add(checkBox);
			checkBox.addItemListener(new listenerJCheckBoxResult(answer.getId()));
		}
	}

	private void putListAnswerComboBoxToPanel(ArrayList<Answer> listAnswer) {
		answerPanel.setLayout(new FlowLayout());
		JComboBox<String> combo = new JComboBox<String>();
		combo.addItem("<choix>");
		for(Answer answer : listAnswer){
			combo.addItem(answer.getAnswerText());
		}

		//Set save value
		if(mapResultOfQuerySelected != null){
			if(mapResultOfQuerySelected.containsKey(listAnswer.get(0).getId())){
				combo.setSelectedIndex(Integer.parseInt(mapResultOfQuerySelected.get(listAnswer.get(0).getId()).getAnswerText()));
			}
		}
		answerPanel.add(combo);
		combo.addItemListener(new listenerJComboBoxResult(listAnswer.get(0).getId()));
	}


	private void putListAnswerNumericToPanel(ArrayList<Answer> listAnswer) {
		//www.coderanch.com/t/342654/GUI/java/JSpinner-decimal-input
		answerPanel.setLayout(new GridBagLayout());

		JPanel answerTempPanel = new JPanel(new GridBagLayout()) ;
		GridBagConstraints gbcLbl = new GridBagConstraints();
		gbcLbl.anchor = GridBagConstraints.NORTHWEST ;
		gbcLbl.fill = GridBagConstraints.BOTH;
		gbcLbl.insets = new Insets(5, 15, 5, 0);
		gbcLbl.gridx = 0;

		GridBagConstraints gbcComp = new GridBagConstraints();
		gbcComp.anchor = GridBagConstraints.NORTHWEST ;
		gbcComp.fill = GridBagConstraints.BOTH;
		gbcComp.insets = new Insets(5, 5, 5, 0);
		gbcComp.gridx = 1;

		int i = 0;
		String unit ="";
		for(Answer answer : listAnswer){
			if(answer.getUnit() != null && !answer.getUnit().isEmpty() ){
				unit = " ["+answer.getUnit()+"] : ";
			}else{
				unit = ":";
			}

			gbcComp.gridy = gbcLbl.gridy = i++;
			JSpinner numericSpinner = new JSpinner();
			SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE,1);
			numericSpinner.setModel(spinnerNumberModel);
			DecimalFormat df = ((JSpinner.NumberEditor) numericSpinner.getEditor()).getFormat();
			df.setMinimumFractionDigits(0);
			numericSpinner.getPreferredSize().width=85;

			//Set save value
			if(mapResultOfQuerySelected != null){
				if(mapResultOfQuerySelected.containsKey(answer.getId())){
					numericSpinner.setValue(Integer.parseInt(mapResultOfQuerySelected.get(answer.getId()).getAnswerText() ));
				}
			}

			answerTempPanel.add(new JLabel(answer.getAnswerText()),gbcLbl);
			answerTempPanel.add(numericSpinner,gbcComp);
			numericSpinner.addChangeListener(new listenerJSpinnerResult(answer.getId()));
			numericSpinner.getEditor().addKeyListener(new listenerJSpinnerResult(answer.getId()));

		}

		GridBagConstraints gbcAnswPan = new GridBagConstraints();
		gbcAnswPan.anchor = GridBagConstraints.NORTHWEST ;
		gbcAnswPan.weightx = gbcAnswPan.weighty =1;
		answerPanel.add(answerTempPanel,gbcAnswPan);
	}



	private void putListAnswerTextFieldToPanel(ArrayList<Answer> listAnswer) {
		answerPanel.setLayout(new GridBagLayout());

		JPanel answerTempPanel = new JPanel(new GridBagLayout()) ;
		GridBagConstraints gbcLbl = new GridBagConstraints();
		gbcLbl.anchor = GridBagConstraints.NORTHWEST ;
		gbcLbl.fill = GridBagConstraints.BOTH;
		gbcLbl.insets = new Insets(5, 15, 5, 0);
		gbcLbl.gridx = 0;

		GridBagConstraints gbcComp = new GridBagConstraints();
		gbcComp.anchor = GridBagConstraints.NORTHWEST ;
		gbcComp.fill = GridBagConstraints.BOTH;
		gbcComp.insets = new Insets(5, 5, 5, 0);
		gbcComp.gridx = 1;

		int i = 0;
		for(Answer answer : listAnswer){
			gbcComp.gridy = gbcLbl.gridy = i++;
			JTextField textField = new JTextField(20);
			//Set save value
			if(mapResultOfQuerySelected != null){
				if(mapResultOfQuerySelected.containsKey(answer.getId())){
					textField.setText(mapResultOfQuerySelected.get(answer.getId()).getAnswerText());
				}
			}

			answerTempPanel.add(new JLabel(answer.getAnswerText()),gbcLbl);
			answerTempPanel.add(textField,gbcComp);
			textField.addKeyListener(new listenerJTextFieldResult(answer.getId()));
		}

		GridBagConstraints gbcAnswPan = new GridBagConstraints();
		gbcAnswPan.anchor = GridBagConstraints.NORTHWEST ;
		gbcAnswPan.weightx = gbcAnswPan.weighty =1;
		answerPanel.add(answerTempPanel,gbcAnswPan);
	}

	private class listenerJCheckBoxResult implements ItemListener   {
		private final int id;
		public listenerJCheckBoxResult(int id) {
			this.id = id;
		}

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			if (arg0.getStateChange() == ItemEvent.SELECTED) {
				save("true", id);
			}
			else{
				save("", id);
			}

		}
	}

	private class listenerJComboBoxResult implements ItemListener   {
		private final int id;
		public listenerJComboBoxResult(int id) {
			this.id = id;
		}

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			if (arg0.getStateChange() == ItemEvent.SELECTED)
				if(((JComboBox<?>)arg0.getSource()).getSelectedIndex() == 0){
					save("", id);
				}else{
					save(Integer.toString(((JComboBox<?>)arg0.getSource()).getSelectedIndex()), id);
				}
		}
	}


	private class listenerJSpinnerResult implements KeyListener, ChangeListener {

		private final int id;

		public listenerJSpinnerResult(int id) {
			this.id = id;
		}

		@Override
		public void keyPressed(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {
			save(Integer.toString((Integer) ((JSpinner)e.getSource()).getValue()), id);
		}

		@Override
		public void keyTyped(KeyEvent e) {	}

		@Override
		public void stateChanged(ChangeEvent arg0) {
			save(Integer.toString((Integer) ((JSpinner)arg0.getSource()).getValue()), id);
		}

	}

	private class listenerJTextFieldResult implements KeyListener {

		private final int id;

		public listenerJTextFieldResult(int id) {
			this.id = id;
		}

		@Override
		public void keyPressed(KeyEvent e) {		}

		@Override
		public void keyReleased(KeyEvent e) {
			save( ((JTextField)e.getSource()).getText(), id);
		}

		@Override
		public void keyTyped(KeyEvent e) {	}

	}


	private void save(String txt, int idAnswer){
		HashMap<Integer, Result> value = null;
		if(modelToSave.getResultByQueryId().getValue().containsKey(query.getId()) ){
			value = modelToSave.getResultByQueryId().getValue().get(query.getId());
		}else{
			value = new HashMap<Integer, Result>();
		}

		//Si pas de reponse selectionnee supprimer reponse
		if(txt == "" || txt.length() == 0){
			if(value.containsKey(idAnswer) ){
				value.remove(idAnswer);
			}
		}else{
			value.put(idAnswer, new Result(txt, null, null, idAnswer, query.getId()));
		}

		//Si pas de reponse selectionnee supprimer question
		if(value.size() == 0){
			modelToSave.getResultByQueryId().getValue().remove(query.getId());
		}else{
			modelToSave.getResultByQueryId().getValue().put(query.getId(), value);
		}
		modelToSave.getResultByQueryId().notiffyAllListeners();
		//Set modify form
		managerMVC.getModelManager().setNewModifForm(true);
	}



}



