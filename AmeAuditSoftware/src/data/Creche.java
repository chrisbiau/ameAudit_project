package data;



public class Creche extends DataObject  {

	
	private  String name;
	private  String contact;
	private  String phone;
	private  String adresse;
	private  String codePostal;
	private  String ville;
	private  String numDossier;
	private  String numAudit;

	public Creche() {
		super();
	}
	
	public Creche (String name, boolean valid){
		super(Integer.MIN_VALUE, valid);
		this.name = name;
	}

	public Creche(int id, String name, String contact, String phone, String adresse,
			String codePostal, String ville, String numDossier, String numAudit, boolean valid) {
		super(id, valid);
		this.name = name;
		this.contact = contact;
		this.phone = phone;
		this.adresse = adresse;
		this.codePostal = codePostal;
		this.ville = ville;
		this.numDossier = numDossier;
		this.numAudit = numAudit;
	}



	public Creche(String name, String contact, String phone, String adresse,
			String codePostale, String ville,String numDossier, String numAudit)  {
		this(Integer.MIN_VALUE, name, contact, phone, adresse, codePostale,
				ville, numDossier, numAudit, true);
	}





	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getContact() {
		return contact;
	}



	public void setContact(String contact) {
		this.contact = contact;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getAdresse() {
		return adresse;
	}



	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}



	public String getCodePostale() {
		return codePostal;
	}



	public void setCodePostale(String codePostale) {
		this.codePostal = codePostale;
	}



	public String getVille() {
		return ville;
	}



	public void setVille(String ville) {
		this.ville = ville;
	}



	public String getNumDossier() {
		return numDossier;
	}



	public void setNumDossier(String numDossier) {
		this.numDossier = numDossier;
	}



	public String getNumAudit() {
		return numAudit;
	}



	public void setNumAudit(String numAudit) {
		this.numAudit = numAudit;
	}



	@Override
	public Creche clone() {
		return new Creche(id, name, contact, phone, adresse, codePostal,
				ville, numDossier, numAudit, valid);
	}

	@Override
	public String toString(){
		return name;
	}
	
	@Override
	public DataObjectTypeEnum getType() {
		return DataObjectTypeEnum.CRECHE;
	}
	
}