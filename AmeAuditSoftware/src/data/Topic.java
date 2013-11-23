package data;

public class Topic extends DataObject {

	private String topicName;

	private String topicColor;

	public Topic (){
		super(); 
	}
	
	public Topic(String topicName, String color) {
		this(Integer.MIN_VALUE, topicName, color, true);
	}

	public Topic(int id, String topicName, String topicColor, boolean valid) {
		super(id, valid);
		this.topicName = topicName;
		this.topicColor = topicColor;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getTopicColor() {
		return topicColor;
	}

	public void setColor(String topicColor) {
		this.topicColor = topicColor;
	}

	@Override
	public Topic clone() {
		return new Topic(id, topicName, topicColor, valid);
	}


	@Override
	public String toString(){
		return topicName;
	}
	
	
	@Override
	public DataObjectTypeEnum getType() {
		return DataObjectTypeEnum.TOPIC;
	}

}