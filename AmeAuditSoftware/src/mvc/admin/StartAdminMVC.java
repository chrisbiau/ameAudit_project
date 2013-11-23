package mvc.admin;

import mvc.ManagerMVC;
import mvc.admin.controller.ControllerAdminMVC;
import mvc.admin.model.ModelAdminMVC;

public class StartAdminMVC {



	private ControllerAdminMVC controllerMVC = null;
	public StartAdminMVC(ManagerMVC managerMVC) {
		ModelAdminMVC modelFormMVC = new ModelAdminMVC("Model Form", managerMVC);
		controllerMVC = new ControllerAdminMVC(managerMVC, modelFormMVC);
	}
	public ControllerAdminMVC getControllerMVC() {
		return controllerMVC;
	}




}
