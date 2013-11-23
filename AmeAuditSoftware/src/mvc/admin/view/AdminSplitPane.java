package mvc.admin.view;

import javax.swing.JSplitPane;

import mvc.ManagerMVC;

public class AdminSplitPane extends JSplitPane {

	private final ManagerMVC managerMVC;

	public AdminSplitPane(ManagerMVC managerMVC) {
		super(JSplitPane.HORIZONTAL_SPLIT);
		this.setOneTouchExpandable(true);
		this.managerMVC = managerMVC;
		this.setDividerLocation(0.40);

	}






}
