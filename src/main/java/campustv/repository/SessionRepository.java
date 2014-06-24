package campustv.repository;

import campustv.repository.exceptions.RepositoryException;
import campustv.domain.Session;
import campustv.repository.exceptions.SessionException;

public interface SessionRepository {

	long login(Session session) throws RepositoryException, SessionException;

	void logout(long userID, String accessToken) throws RepositoryException, SessionException;

}
