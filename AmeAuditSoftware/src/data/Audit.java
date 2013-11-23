package data;

import java.util.Date;

public class Audit extends DataObject {

	private Grid grid;

	private String version;

	private Date creationDate;


	
	public Audit(String version, Date creationDate, Grid grid) {
		this(Integer.MIN_VALUE, version, creationDate, grid, true );
	}

	public Audit(int id, String version, Date creationDate, Grid grid, boolean valid ) {
		super(id, valid);
		this.version = version;
		this.creationDate = creationDate;
		this.grid = grid;
	}

	public Audit() {
		super();
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}
	

//	@Override
//	public String toString (){
//		return "Object Audit[id:"+getId()+"; version:"+getVersion()+"; creationDate:"+getCreationDate()+"; show:"+getValid()+"]";
//	}



	@Override
	public Audit clone() {
		return new Audit(id, version, creationDate, grid, valid);
	}

	@Override
	public String toString(){
		return version;
	}

	@Override
	public  DataObjectTypeEnum getType() {
		return DataObjectTypeEnum.AUDIT;
	}
	
	
}