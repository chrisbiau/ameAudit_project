package data;


public class Result extends DataObject  {


	private final String answerText;
	private final Color color;
	private final String unit;
	private final int idAnswer;
	private final int idQuery;

	
	/**
	 * @param id
	 * @param valid
	 * @param answerText
	 * @param color
	 * @param unit
	 * @param idAnswer
	 */
	
	public Result(int id, String answerText, Color color, String unit, int idAnswer, int idQuery, boolean valid) {
		super(id, valid);
		this.answerText = answerText;
		this.color = color;
		this.unit = unit;
		this.idAnswer = idAnswer;
		this.idQuery = idQuery;
	}

	public Result(String answerText, Color color, String unit, int idAnswer, int idQuery) {
		this(Integer.MIN_VALUE, answerText, color, unit, idAnswer,idQuery, true);
	}

	public String getAnswerText() {
		return answerText;
	}

	public Color getColor() {
		return color;
	}

	public String getUnit() {
		return unit;
	}

	public int getIdAnswer() {
		return idAnswer;
	}
	public int getIdQuery() {
		return idQuery;
	}

	@Override
	public Result clone() {
		return new Result(id, answerText, color, unit, idAnswer, idQuery, valid);
	}

	@Override
	public String toString(){
		return answerText;
	}	
	
	@Override
	public DataObjectTypeEnum getType() {
		return DataObjectTypeEnum.RESULT;
	}

}