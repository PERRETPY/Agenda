package agenda.models;


public class Evenement {
    private String id;
    private String jour;
    private String heureDebut;
    private String heureFin;
    private String titre;
    private String type;
    private String description;
    private String organisateur;
    private String participant;
    private String lieu;
    private String statut;
    private String priorite;
    private String commentaire;
    private String dateCreation;
    private String dateUpdate;

    
	public Evenement(String id, String jour, String heureDebut, String heureFin, String titre, String type,
			String description, String organisateur, String participant, String lieu, String statut, String priorite,
			String commentaire, String dateCreation, String dateUpdate) {
		super();
		this.id = id;
		this.jour = jour;
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
		this.titre = titre;
		this.type = type;
		this.description = description;
		this.organisateur = organisateur;
		this.participant = participant;
		this.lieu = lieu;
		this.statut = statut;
		this.priorite = priorite;
		this.commentaire = commentaire;
		this.dateCreation = dateCreation;
		this.dateUpdate = dateUpdate;
	}


	public Evenement(String jour, String heureDebut, String heureFin, String titre, String type, String description,
			String organisateur, String participant, String lieu, String statut, String priorite, String commentaire,
			String dateCreation, String dateUpdate) {
		super();
		this.jour = jour;
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
		this.titre = titre;
		this.type = type;
		this.description = description;
		this.organisateur = organisateur;
		this.participant = participant;
		this.lieu = lieu;
		this.statut = statut;
		this.priorite = priorite;
		this.commentaire = commentaire;
		this.dateCreation = dateCreation;
		this.dateUpdate = dateUpdate;
	}


	public Evenement() {
		// TODO Auto-generated constructor stub
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getJour() {
		return jour;
	}


	public void setJour(String jour) {
		this.jour = jour;
	}


	public String getHeureDebut() {
		return heureDebut;
	}


	public void setHeureDebut(String heureDebut) {
		this.heureDebut = heureDebut;
	}


	public String getHeureFin() {
		return heureFin;
	}


	public void setHeureFin(String heureFin) {
		this.heureFin = heureFin;
	}


	public String getTitre() {
		return titre;
	}


	public void setTitre(String titre) {
		this.titre = titre;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getOrganisateur() {
		return organisateur;
	}


	public void setOrganisateur(String organisateur) {
		this.organisateur = organisateur;
	}


	public String getParticipant() {
		return participant;
	}


	public void setParticipant(String participant) {
		this.participant = participant;
	}


	public String getLieu() {
		return lieu;
	}


	public void setLieu(String lieu) {
		this.lieu = lieu;
	}


	public String getStatut() {
		return statut;
	}


	public void setStatut(String statut) {
		this.statut = statut;
	}


	public String getPriorite() {
		return priorite;
	}


	public void setPriorite(String priorite) {
		this.priorite = priorite;
	}


	public String getCommentaire() {
		return commentaire;
	}


	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}


	public String getDateCreation() {
		return dateCreation;
	}


	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}


	public String getDateUpdate() {
		return dateUpdate;
	}


	public void setDateUpdate(String dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

    public String toCSV() {
        return null;
    }
}
