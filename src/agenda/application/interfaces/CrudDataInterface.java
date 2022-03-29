package agenda.application.interfaces;

import java.util.List;

import agenda.models.Evenement;

public interface CrudDataInterface extends Runnable{

	 List<Evenement> getAllEventList();
	 void saveAllEventList();
}
