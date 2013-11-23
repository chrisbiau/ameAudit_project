package mvc.form.view;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import mvc.ManagerMVC;
import data.Query;

public class QueryListJPanel extends JScrollPane {

	private final ManagerMVC managerMVC;
	ArrayList<Query> listQuery;
	JPanel jpanel = new JPanel() ;

	public QueryListJPanel(ManagerMVC managerMVC, ArrayList<Query> listQuery) {
		super();
		this.managerMVC = managerMVC;
		this.listQuery = listQuery;
		this.setViewportView(jpanel);
		init();
	}


	private void init() {
		jpanel.setLayout(new BoxLayout(jpanel, BoxLayout.X_AXIS));
		AnswerTreeViewJPanel queryTreeView = new AnswerTreeViewJPanel(managerMVC, listQuery);
		jpanel.add(queryTreeView);
	}
}
