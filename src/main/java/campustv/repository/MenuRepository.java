package campustv.repository;

import java.util.Iterator;

import campustv.domain.Menu;
import campustv.repository.exceptions.PermissionException;
import campustv.repository.exceptions.RepositoryException;
import campustv.repository.exceptions.SessionException;

public interface MenuRepository {
	
	public void addMenu(Menu m, String accessToken) throws RepositoryException, SessionException, PermissionException;
	
	public Iterator<Menu> listMenus() throws RepositoryException;
	
	public void removeMenu(long menuID, long producerID, String accessToken) throws RepositoryException, SessionException, PermissionException;

	public Menu getMenuByID(long menuID) throws RepositoryException;

}
