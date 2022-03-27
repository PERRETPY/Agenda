package agenda.models;

public class DefaultEnumeration {

	public static String[] getPriorite() {
		String[] valeurPossible = {"Faible","Normale","Forte"};
		return valeurPossible;
	}
	public static String[] getStatut() {
		String[] valeurPossible = {"Confirmer","Refuser","Annuler","Indeterminer"};
		return valeurPossible;
	}
	public static String[] getType() {
		String[] valeurPossible = {"Personnel","Professionnel","Universitaire","Medical","Divers"};
		return valeurPossible;
	}
}
