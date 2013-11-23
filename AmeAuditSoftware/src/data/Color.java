package data;

public class Color extends DataObject {

	private String colorName;

	private String colorValue;

	public Color() {
		super();
	}
	
	public Color(String colorName, String colorValue) {
		this(Integer.MIN_VALUE, colorName, colorValue, true);
	}

	public Color(int id, String colorName, String colorValue, boolean valid) {
		super(id, valid);
		this.colorName = colorName;
		this.colorValue = colorValue;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getColorValue() {
		return colorValue;
	}

	public void setColorValue(String colorValue) {
		this.colorValue = colorValue;
	}

	@Override
	public Color clone() {
		return new Color(id, colorName, colorValue, valid);
	}

	@Override
	public DataObjectTypeEnum getType() {
		return DataObjectTypeEnum.COLOR;
	}
	
	@Override
	public String toString(){
		return colorName;
	}

}