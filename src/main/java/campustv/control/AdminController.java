package campustv.control;

import campustv.domain.Admin;
import campustv.domain.Area;
import campustv.domain.Local;
import campustv.domain.Producer;
import campustv.repository.AdminRepository;
import campustv.repository.DBAdminRepository;
import campustv.repository.exceptions.PermissionException;
import campustv.repository.exceptions.RepositoryException;
import campustv.repository.exceptions.SessionException;

public class AdminController {

	public void addLocation(long userID, Local l, String accessToken) throws RepositoryException, PermissionException, SessionException{
		AdminRepository ar = new DBAdminRepository();
		ar.addLocation(userID, l, accessToken);
	}

	public void addArea(long userID, Area a, String accessToken) throws RepositoryException, SessionException, PermissionException{
		AdminRepository ar = new DBAdminRepository();
		ar.addArea(userID, a, accessToken);
	}

	public void addAdmin(long userID, Admin admin, String accessToken) throws RepositoryException, SessionException, PermissionException{
		AdminRepository ar = new DBAdminRepository();
		ar.addAdmin(userID, admin, accessToken);
	}

	public void addProducer(long userID, Producer producer, String accessToken) throws RepositoryException, SessionException, PermissionException{
		AdminRepository ar = new DBAdminRepository();
		ar.addProducer(userID, producer, accessToken);
	}

}
