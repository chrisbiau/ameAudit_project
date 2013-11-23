package data;


public enum DataObjectTypeEnum {

	ANSWER("Réponses", Answer.class),
	AUDIT("Audits", Audit.class),
	COLOR("Couleurs réponses", Color.class),
	CRECHE("Creches", Creche.class),
	GRID("Grilles", Grid.class),
	INPUT_DIALOG("Types de question", InputDialog.class),
	NUMERIC_RULES("Regles", NumericRules.class),
	QUERY("Questions", Query.class),
	RESULT("Résultats", Result.class),
	ROOM("Pieces", Room.class),
	TOPIC("Themes", Topic.class);

	private String name = "";
	private Class<?> typeClass = null; 

	// Constructeur
	DataObjectTypeEnum(String name, Class<?> typeClass) {
		this.name = name;
		this.typeClass = typeClass;
	}

	@Override
	public String toString() {
		return name;
	}

	public Class<?> getTypeClass() {
		return typeClass;
	}

}
