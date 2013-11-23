package data;

public class InputDialog extends DataObject {

	private String dialogType;

	public InputDialog (){
		super(); 
	}
	
	public InputDialog(String dialogType) {
		this(Integer.MIN_VALUE, dialogType, true);
	}


	public InputDialog(int id, String dialogType, boolean valid) {
		super(id, valid);
		this.dialogType = dialogType;
	}

	public String getDialogType() {
		return dialogType;
	}

	public void setDialogType(String dialogType) {
		this.dialogType = dialogType;
	}


	@Override
	public InputDialog clone() {
		return new InputDialog(id, dialogType, valid);
	}	
	
	@Override
	public DataObjectTypeEnum getType() {
		return DataObjectTypeEnum.INPUT_DIALOG;
	}


	@Override
	public String toString(){
		return dialogType;
	}	
}