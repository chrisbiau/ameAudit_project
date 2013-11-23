package mvc.form.view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import mvc.ManagerMVC;
import dao.service.ServiceDAO;
import data.Query;
import data.Topic;

public class TopicJTabbedPane extends JTabbedPane {
	



	private final ManagerMVC managerMVC;
	HashMap<Integer, ArrayList<Query>> mapTopicQuery;

	Map<Integer, Color> mapcolor = new HashMap<Integer, Color>();

	public TopicJTabbedPane(ManagerMVC managerMVC, HashMap<Integer, ArrayList<Query>> mapTopicQuery) {

		this.managerMVC = managerMVC;
		this.mapTopicQuery = mapTopicQuery; 
		
		init();
	}

	
	private void init() {
		for(Integer idTopic : this.mapTopicQuery.keySet()){
			Topic topic =  ServiceDAO.getInstance().getInstance().findTopicObjectByID(idTopic);
			if(topic.getValid()){
				QueryListJPanel dd = new QueryListJPanel(this.managerMVC, this.mapTopicQuery.get(idTopic));
				dd.setPreferredSize(this.getPreferredSize());
				this.add(topic.toString(),dd);
				if(!topic.getTopicColor().isEmpty()){
					this.setBackgroundAt(this.getComponentCount()-1, Color.decode(topic.getTopicColor()) );
					this.mapcolor.put(this.getComponentCount()-1,  Color.decode(topic.getTopicColor()));
				}else{
					this.setBackgroundAt(this.getComponentCount()-1, Color.WHITE );
					this.mapcolor.put(this.getComponentCount()-1,  Color.WHITE );
				}

				Object old = UIManager.get("TabbedPane.selected");
				UIManager.put("TabbedPane.selected",Color.TRANSLUCENT );
				setUI(new BasicTabbedPaneUI());
				UIManager.put("TabbedPane.selected",old );
			}
		}
	}
	

	
	
}
