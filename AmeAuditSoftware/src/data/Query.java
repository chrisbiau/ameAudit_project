package data;

public class Query extends DataObject {

	private Audit audit;

	private String queryText;

	private String helpText;

	private String commentText;

	private Room room;

	private Topic topic;

	private InputDialog inputDialog;

	private Integer link;

	private Integer weigthing;
	
	public Query (){
		super(); 
	}
	
	public Query( Audit audit, String queryText,
			String helpText, String commentText, Room room, Topic topic,
			InputDialog inputDialog, Integer link, Integer weigthing) {
		this(Integer.MIN_VALUE, audit, queryText, helpText, commentText, room, topic, inputDialog, link, weigthing, true);
	}


	public Query(int id, Audit audit, String queryText,
			String helpText, String commentText, Room room, Topic topic,
			InputDialog inputDialog, Integer link, Integer weigthing, boolean valid) {
		super(id, valid);
		this.audit = audit;
		this.queryText = queryText;
		this.helpText = helpText;
		this.commentText = commentText;
		this.room = room;
		this.topic = topic;
		this.inputDialog = inputDialog;
		this.link = link;
		this.weigthing = weigthing;
	}

	public Audit getAudit() {
		return audit;
	}

	public void setAudit(Audit audit) {
		this.audit = audit;
	}

	public String getQueryText() {
		return queryText;
	}

	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}

	public String getHelpText() {
		return helpText;
	}

	public void setHelpText(String helpText) {
		this.helpText = helpText;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public InputDialog getInputDialog() {
		return inputDialog;
	}

	public void setInputDialog(InputDialog inputDialog) {
		this.inputDialog = inputDialog;
	}

	public Integer getLink() {
		return link;
	}

	public void setLink(Integer link) {
		this.link = link;
	}

	public Integer getWeigthing() {
		return weigthing;
	}

	public void setWeigthing(Integer weigthing) {
		this.weigthing = weigthing;
	}


	@Override
	public Query clone() {
		return new Query(id, audit, queryText, helpText, commentText, room, topic, inputDialog, link, weigthing, valid) ;
	}

	@Override
	public String toString(){
		return queryText;
	}	
	
	@Override
	public DataObjectTypeEnum getType() {
		return DataObjectTypeEnum.QUERY;
	}

}