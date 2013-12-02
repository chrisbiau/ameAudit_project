package mvc.admin.view.common;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import dao.service.ServiceDAO;
import data.Answer;
import data.Audit;
import data.Color;
import data.Creche;
import data.DataObject;
import data.DataObjectTypeEnum;
import data.Grid;
import data.InputDialog;
import data.NumericRules;
import data.Query;
import data.Room;
import data.Topic;

public class UtilBuildViewAdmin {


	private static Logger logger = Logger.getLogger(UtilBuildViewAdmin.class);

	private final Class<?> typeClass;
	private DataObject  dataObjectSelected;
	private final   HashMap<Field, JComponent> mapPane = new HashMap<Field, JComponent>();
	private int nblgn = 0;

	private Query queryObject = null;


	public UtilBuildViewAdmin( DataObject dataObjectSelected) {
		super();
		this.typeClass = dataObjectSelected.getClass();
		this.dataObjectSelected = dataObjectSelected;
	}

	public UtilBuildViewAdmin(DataObjectTypeEnum typeDataObject) {
		super();
		this.typeClass = typeDataObject.getTypeClass();
		try {
			Class<?> c = Class.forName(typeDataObject.getTypeClass().getName());
			this.dataObjectSelected = (DataObject) c.newInstance();;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public JPanel createJpanel(){
		JPanel panel = new JPanel(new GridBagLayout());

		GridBagConstraints componentGbc = new GridBagConstraints();
		componentGbc.insets = new Insets(5, 0, 5, 5);
		componentGbc.fill = GridBagConstraints.HORIZONTAL;

		try {
			Field[] fieldSuper = typeClass.getSuperclass().getDeclaredFields();

			//ID
			for(Field field : fieldSuper){
				field.setAccessible(true);
				if(field.getType() ==  int.class){
					JComponent inputComponent  = new JLabel();
					String valueTxt = "[NEW]";
					if(dataObjectSelected != null && field.get(dataObjectSelected) != null ){
						valueTxt = field.get(dataObjectSelected).toString();
					} 
					((JLabel) inputComponent).setText(valueTxt);
					Dimension d = inputComponent.getPreferredSize( );
					d.setSize(500, d.getHeight());
					inputComponent.setPreferredSize(d);
					addComponentsAndLabael(panel, componentGbc, field, inputComponent);
				}

			}

			//All field of class
			Field[] fields = typeClass.getDeclaredFields();
			for(Field field : fields){

				field.setAccessible(true);
				JComponent inputComponent;

				// pour les objets du formulaire n'étant  une reference a un autre dataObject
				if(field.getType().getSuperclass()!= DataObject.class){
					inputComponent = new JTextField();
					String valueTxt = "";
					if(dataObjectSelected != null && field.get(dataObjectSelected) != null ){
						valueTxt = field.get(dataObjectSelected).toString();
						//Si color
						if(valueTxt.startsWith("#") && valueTxt.length() == 7){
							inputComponent.setBackground(java.awt.Color.decode(valueTxt));
							inputComponent.addMouseListener(new colorMouseListener());
						}
					} 
					((JTextField) inputComponent).setText(valueTxt);
					Dimension d = inputComponent.getPreferredSize( );
					d.setSize(500, d.getHeight());
					inputComponent.setPreferredSize(d);
				}else{
					inputComponent = new JComboBox<Object>();
					if(field.getType() == Audit.class){
						inputComponent = new JComboBox<Object>(ServiceDAO.getInstance().getAllAudit().values().toArray());
					}else if(field.getType() == Answer.class){
						inputComponent = new JComboBox<Object>(ServiceDAO.getInstance().getAllAnswer().values().toArray());
					}else if(field.getType() == Color.class){
						inputComponent = new JComboBox<Object>(ServiceDAO.getInstance().getAllColor().values().toArray());
					}else if(field.getType() == Creche.class){
						inputComponent = new JComboBox<Object>(ServiceDAO.getInstance().getAllCreche().values().toArray());
					}else if(field.getType() == Grid.class){
						inputComponent = new JComboBox<Object>(ServiceDAO.getInstance().getAllGrid().values().toArray());
					}else if(field.getType() == InputDialog.class){
						inputComponent = new JComboBox<Object>(ServiceDAO.getInstance().getAllInputDialog().values().toArray());
					}else if(field.getType() == NumericRules.class){
						inputComponent = new JComboBox<Object>(ServiceDAO.getInstance().getAllNumericRules().values().toArray());
					}else if(field.getType() == Query.class){
						inputComponent = new JComboBox<Object>(ServiceDAO.getInstance().getAllQuery().values().toArray());
					}else if(field.getType() == Room.class){
						inputComponent = new JComboBox<Object>(ServiceDAO.getInstance().getAllRoom().values().toArray());
					}else if(field.getType() == Topic.class){
						inputComponent = new JComboBox<Object>(ServiceDAO.getInstance().getAllTopic().values().toArray());
					}else{
						logger.warn("Aucun champs ne correspond à un DataObject: "+field.getType().getClass() );
					}

					if( field.getType() == Query.class && queryObject !=null){
						for ( int i = 0;  i < ((JComboBox)inputComponent).getItemCount(); i++) {
							DataObject data = (DataObject) ((JComboBox)inputComponent).getItemAt(i);
							if(data.getId() == queryObject.getId()){
								
								((JComboBox)inputComponent).setSelectedIndex(i);
							}
						}
					}else{				
						if(dataObjectSelected != null &&  field.get(dataObjectSelected) != null ){
							for ( int i = 0;  i < ((JComboBox)inputComponent).getItemCount(); i++) {
								DataObject data = (DataObject) ((JComboBox)inputComponent).getItemAt(i);
								if(data.getId() == ((DataObject)field.get(dataObjectSelected)).getId()){
									((JComboBox)inputComponent).setSelectedIndex(i);
								}
							}
						}
					}
				}

				addComponentsAndLabael(panel, componentGbc,field, inputComponent);
			}

			//Valid field
			for(Field field : fieldSuper){
				if(field.getType() ==  boolean.class){
					field.setAccessible(true);
					JComponent inputComponent  = new JCheckBox();
					if(dataObjectSelected != null && field.get(dataObjectSelected) != null ){
						((JCheckBox) inputComponent).setSelected((Boolean) field.get(dataObjectSelected));
					}else{
						((JCheckBox) inputComponent).setSelected(true);
					}
					addComponentsAndLabael(panel, componentGbc, field, inputComponent);
				}
			}



		} catch (IllegalArgumentException e) {
			logger.warn("Introspection :Error when creating Edit DataObject panel "+e);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			logger.warn("Introspection : Error when creating Edit DataObject panel "+e);
			e.printStackTrace();
		}

		return panel;
	}



	private void addComponentsAndLabael(JPanel panel, GridBagConstraints componentGbc, Field field,	JComponent inputComponent) {
		if(inputComponent != null){
			JLabel lbl;
			mapPane.put(field, inputComponent);
			lbl = new JLabel(field.getName()+ " : ", JLabel.RIGHT);

			componentGbc.gridx = 0;
			componentGbc.gridy = nblgn;
			panel.add(lbl, componentGbc);

			componentGbc.gridx = 1;
			panel.add(inputComponent, componentGbc);

			nblgn ++;
		}
	}


	public  DataObject getDataObjectFromMap() {
		Set<Field> fields = mapPane.keySet();
		for(Field field : fields){
			try {
				field.setAccessible(true);
				JComponent componentOfPanel = mapPane.get(field);
				if(componentOfPanel instanceof JTextField){
					String valStr = ((JTextField)componentOfPanel).getText();
					if(field.getType() == String.class){
						field.set(dataObjectSelected, valStr);
					}else if(field.getType() == Integer.class){
						field.set(dataObjectSelected, Integer.parseInt(valStr));
					}else{
						logger.warn("updating ObjectData any field of this type:"+ field.getType() +" - this field is not save in object: "+dataObjectSelected.getClass()  );
					}
				}else if(componentOfPanel instanceof JComboBox){
					DataObject valObj = ((DataObject) ((JComboBox)componentOfPanel).getSelectedItem());
					field.set(dataObjectSelected, valObj);
				}else if(componentOfPanel instanceof JCheckBox){
					boolean valObj = ((JCheckBox) componentOfPanel).isSelected();
					field.set(dataObjectSelected, valObj);
				}else{
					logger.warn("updating ObjectData any component of this class:"+ componentOfPanel.getClass() +"  - this component is not save in object: "+dataObjectSelected.getClass()  );
				}

			} catch (IllegalArgumentException e) {
				logger.warn("Introspection :Error when setting Edit DataObject panel "+e);
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				logger.warn("Introspection : Error when setting Edit DataObject panel "+e);
				e.printStackTrace();
			}
		}
		return dataObjectSelected;
	}

	public HashMap<Field, JComponent> getMapPane() {
		return mapPane;
	}




	private class colorMouseListener implements MouseListener {

		@Override
		public void mouseReleased(MouseEvent mouseevent) {
		}

		@Override
		public void mousePressed(MouseEvent mouseevent) {
			dialogColor(mouseevent);
		}

		@Override
		public void mouseExited(MouseEvent mouseevent) {

		}

		@Override
		public void mouseEntered(final MouseEvent mouseevent) {
		}


		@Override
		public void mouseClicked(MouseEvent mouseevent) {
		}
		
		private void dialogColor(MouseEvent mouseevent){
			String value = ((JTextField) mouseevent.getSource()).getText();
			java.awt.Color background = JColorChooser.showDialog(null,"Choisir une couleur: ", java.awt.Color.decode(value));
			if (background != null) {
				((JTextField)	mouseevent.getSource()).setBackground(background);
				String rgb = Integer.toHexString(background.getRGB());
				rgb = "#"+rgb.substring(2, rgb.length());
				((JTextField)	mouseevent.getSource()).setText(rgb);
			}
		}
	}




	public void setQeury(Query queryObject) {
		this.queryObject = queryObject;		
	}

}
