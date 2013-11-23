package data;

public class NumericRules extends DataObject {

	private Answer answer;

	private Integer infValue;

	private Integer maxValue;

	private Color color;

	public NumericRules (){
		super(); 
	}
	
	public NumericRules (Answer answer,Integer infValue, Integer maxValue, Color color) {
		this(Integer.MIN_VALUE, answer, infValue, maxValue, color, true);
	}

	public NumericRules(int id, Answer answer,Integer infValue,
			 Integer maxValue, Color color, boolean valid) {
		super(id, valid);
		this.answer = answer;
		this.infValue = infValue;
		this.maxValue = maxValue;
		this.color = color;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public Integer getInfValue() {
		return infValue;
	}

	public void setInfValue(Integer infValue) {
		this.infValue = infValue;
	}

	public Integer getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public NumericRules clone() {
		return new NumericRules(id, answer, infValue, maxValue, color, valid);
	}	
	
	@Override
	public DataObjectTypeEnum getType() {
		return DataObjectTypeEnum.NUMERIC_RULES;
	}

}