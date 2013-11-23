package mvc.form;

import mvc.ManagerMVC;
import mvc.form.controller.ControllerFormMVC;
import mvc.form.model.ModelFormMVC;

public class StartFormMVC {



	private ControllerFormMVC controllerMVC = null;
	public StartFormMVC(ManagerMVC managerMVC) {
		ModelFormMVC modelFormMVC = new ModelFormMVC("Model Formulaire", managerMVC);
		controllerMVC = new ControllerFormMVC(managerMVC, modelFormMVC);
	}
	public ControllerFormMVC getControllerFormMVC() {
		return controllerMVC;
	}




}
