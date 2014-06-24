package campustv.repository;

import campustv.domain.Admin;
import campustv.domain.Area;
import campustv.domain.Local;
import campustv.domain.Producer;
import campustv.repository.exceptions.PermissionException;
import campustv.repository.exceptions.RepositoryException;
import campustv.repository.exceptions.SessionException;

public interface AdminRepository {

	void addLocation(long userID, Local l, String accessToken) throws RepositoryException, PermissionException, SessionException;

	void addArea(long userID, Area a, String accessToken) throws RepositoryException, SessionException, PermissionException;

	void addAdmin(long userID, Admin admin, String accessToken) throws RepositoryException, SessionException, PermissionException;

	void addProducer(long userID, Producer producer, String accessToken) throws RepositoryException, SessionException, PermissionException;

}
