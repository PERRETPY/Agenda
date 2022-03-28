package agenda.plateforme.listener;

/**
 * Interface pour l'implémentation du design pattern Observer
 *
 */
public interface Observer {
    public void update(String name, String status, String message);

}