package campustv.control;

import java.util.Iterator;

import campustv.domain.Local;
import campustv.repository.DBLocalRepository;
import campustv.repository.LocalRepository;
import campustv.repository.exceptions.RepositoryException;

public class LocalController {

	public Iterator<Local> getLocations() throws RepositoryException{
		LocalRepository lr = new DBLocalRepository();
		return lr.getLocations();
	}

}
