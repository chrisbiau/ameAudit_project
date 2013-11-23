package data;

public class Answer extends DataObject {

	private Query query;

	private String answerText;

	private Color color;

	private Integer maxValue;

	private Integer minValue;

	private String unit;

	private Boolean defaut;

	public Answer() {
		super();
	}
	
	public Answer(Query query, String answerText,Color color,
			 Integer maxValue, Integer minValue, String unit,Boolean defaut ) {
		this(Integer.MIN_VALUE, query, answerText, color, maxValue, minValue, unit, defaut, true);
	}

	public Answer(int id, Query query, String answerText, Color color,
			Integer maxValue, Integer minValue, String unit, Boolean defaut,
			 boolean valid) {
		super(id, valid);
		this.query = query;
		this.answerText = answerText;
		this.color = color;
		this.maxValue = maxValue;
		this.minValue = minValue;
		this.unit = unit;
		this.defaut = defaut;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Integer getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}

	public Integer getMinValue() {
		return minValue;
	}

	public void setMinValue(Integer minValue) {
		this.minValue = minValue;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Boolean getDefaut() {
		return defaut;
	}

	public void setDefaut(Boolean defaut) {
		this.defaut = defaut;
	}

	@Override
	public Answer clone() {
		return new Answer(id, query, answerText, color, maxValue, minValue, unit, defaut, valid);
	}

	@Override
	public String toString(){
		return answerText;
	}

	@Override
	public DataObjectTypeEnum getType() {
		return DataObjectTypeEnum.ANSWER;
	}

}