package data;


public class Grid extends DataObject {


	private String name;


	public Grid (){
		super(); 
	}
	
	public Grid(String version) {
		this(Integer.MIN_VALUE, version, true );
	}

	public Grid(int id, String name, boolean valid ) {
		super(id, valid);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public Grid clone() {
		return new Grid(id, name, valid);
	}

	@Override
	public String toString(){
		return name;
	}	
	
	@Override
	public DataObjectTypeEnum getType() {
		return DataObjectTypeEnum.GRID;
	}
}