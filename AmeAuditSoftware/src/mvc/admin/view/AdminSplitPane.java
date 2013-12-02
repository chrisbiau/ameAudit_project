package mvc.admin.view;

import javax.swing.JSplitPane;

import mvc.ManagerMVC;

public class AdminSplitPane extends JSplitPane {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AdminSplitPane(ManagerMVC managerMVC) {
		super(JSplitPane.HORIZONTAL_SPLIT);
		this.setOneTouchExpandable(true);
		this.setDividerLocation(0.75);

	}






}
