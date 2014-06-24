package campustv.control;

import campustv.repository.DBSessionRepository;
import campustv.repository.SessionRepository;
import campustv.repository.exceptions.RepositoryException;
import campustv.domain.Session;
import campustv.repository.exceptions.SessionException;

public class SessionController {

	public long login(Session session) throws RepositoryException, SessionException{
		SessionRepository sm = new DBSessionRepository();
		return sm.login(session);
	}

	public void logout(long userID, String accessToken) throws RepositoryException, SessionException{
		SessionRepository sm = new DBSessionRepository();
		sm.logout(userID, accessToken);
	}
}
