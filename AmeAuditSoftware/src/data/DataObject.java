package data;

import java.io.Serializable;

public abstract class DataObject implements Serializable {

	/**
	 * 
	 */

	protected int id;

	protected boolean valid;
	
	public DataObject() {
		this(Integer.MIN_VALUE,false);
	}
	
	protected DataObject(int id, boolean valid) {
		super();
		this.id = id;
		this.valid = valid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	public  abstract DataObjectTypeEnum getType();
	
	@Override
	public abstract DataObject clone();
	
	

}