package mvc.common.enumeration;

public enum Eview {
	form("Audit"),
	open("Ouvrir"),
	admin("Administrateur"),
	rapport("Rapport"),
	home("Accueil");


	private String name = "";

	//Constructeur
	Eview(String name){
		this.name = name;
	}

	@Override
	public String toString(){
		return name;
	}
}
