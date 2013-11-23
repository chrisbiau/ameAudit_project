package mvc.common.enumeration;

public enum EAction {
	openAudit("openAudit"),
	parser("Import"),
	newAudit("newAudit"), 
	saveAudit("saveAudit"), 
	adminMode("adminMode");

	private String name = "";

	//Constructeur
	EAction(String name){
		this.name = name;
	}

	@Override
	public String toString(){
		return name;
	}
}
