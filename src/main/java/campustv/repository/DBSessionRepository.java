package campustv.repository;

import java.sql.SQLException;

import campustv.repository.exceptions.RepositoryException;
import campustv.domain.Session;
import campustv.repository.exceptions.SessionException;

public class DBSessionRepository implements SessionRepository {


	public long login(Session session) throws RepositoryException, SessionException{
		try{
			int count = DBRepositoryUtils.hasLoggedIn(session);
			if(count == 0){
				return DBRepositoryUtils.login(session);
			}else if(count == 1){
				if(!DBRepositoryUtils.verifyAccessToken(session)){
					DBRepositoryUtils.removeAccessToken(session.getAccessToken());
					return DBRepositoryUtils.login(session);
				}
				else
					throw new SessionException("User is logged in");
			}
		}catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		}
		return -1;
	}

	@Override
	public void logout(long userID, String accessToken)
			throws RepositoryException, SessionException {
		
		try {
			if(DBRepositoryUtils.hasLoggedIn(userID, accessToken))
				DBRepositoryUtils.removeAccessToken(accessToken);
			else
				throw new SessionException("User not logged in");
	
		} catch (SQLException e) {
			throw new RepositoryException(e.getMessage());
		}
		
		
	}
	
}
