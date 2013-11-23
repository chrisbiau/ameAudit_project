package properties.data;

public class TableDataBase {
	
	private String name;

	public TableDataBase(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	

}
