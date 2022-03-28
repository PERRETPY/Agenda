package agenda.plateforme.listener;
import agenda.plateforme.listener.Observer;

public interface Subject {
	 public void addSubscriber(Observer observer);
	    public void removeSubscriber(Observer observer);
		void notifySubscribers(String name, String status, String message);
}
