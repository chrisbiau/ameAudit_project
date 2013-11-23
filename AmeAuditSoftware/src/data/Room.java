package data;

public class Room extends DataObject {

	private String roomName;
	
	public Room (){
		super(); 
	}
	
	public Room( String roomName) {
		this(Integer.MIN_VALUE, roomName, true);
	}

	public Room(int id, String roomName, boolean valid) {
		super(id, valid);
		this.roomName = roomName;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	@Override
	public Room clone() {
		return new Room(id, roomName, valid);
	}

	@Override
	public String toString(){
		return roomName;
	}	
	
	@Override
	public DataObjectTypeEnum getType() {
		return DataObjectTypeEnum.ROOM;
	}

}