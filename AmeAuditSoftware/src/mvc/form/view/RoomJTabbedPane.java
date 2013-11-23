package mvc.form.view;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTabbedPane;

import mvc.ManagerMVC;
import dao.service.ServiceDAO;
import data.Query;
import data.Room;

public class RoomJTabbedPane extends JTabbedPane {

	HashMap<Integer, HashMap<Integer, ArrayList<Query>>> map;
	private final ManagerMVC managerMVC;
	public RoomJTabbedPane(ManagerMVC managerMVC, HashMap<Integer, HashMap<Integer, ArrayList<Query>>> map) {
		this.managerMVC = managerMVC;
		this.map=map;
		
		init();
	}

	private void init() {
		for(Integer idRoom : map.keySet()){
			Room room = ServiceDAO.getInstance().getInstance().findRoomObjectByID(idRoom);
			if(room.getValid()){
				this.add(room.toString(),new TopicJTabbedPane(managerMVC, map.get(idRoom)));
			}
		}

	}
	

}
