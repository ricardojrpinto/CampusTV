package campustv.repository;

import java.util.Iterator;

import campustv.domain.Local;
import campustv.repository.exceptions.RepositoryException;

public interface LocalRepository {

	Iterator<Local> getLocations() throws RepositoryException;
	
}
