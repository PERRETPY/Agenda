package agenda.plateforme.listener;

public enum Status {
	FAILURE("Echec du chargement"),
	LOADED("Charge"),
	ASKED("Demande de chargement"),
	AVAILABLE("Disponible dans la config")
	;
	

	private final String value;
	
	Status(String string) {
		this.value=string;
	}

	public String value() {
		return this.value;
	}
}