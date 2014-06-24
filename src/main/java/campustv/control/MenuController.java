package campustv.control;

import java.util.Iterator;

import campustv.domain.Menu;
import campustv.repository.DBMenuRepository;
import campustv.repository.MenuRepository;
import campustv.repository.exceptions.PermissionException;
import campustv.repository.exceptions.RepositoryException;
import campustv.repository.exceptions.SessionException;

public class MenuController {
	
	public void addMenu(Menu m, String accessToken) throws RepositoryException, SessionException, PermissionException{
		MenuRepository mr = new DBMenuRepository();
		mr.addMenu(m, accessToken);
	}
	
	public Iterator<Menu> getMenus() throws RepositoryException{
		MenuRepository mr = new DBMenuRepository();
		return mr.listMenus();
	}
	
	public void deleteMenu(long menuID, long producerID, String accessToken) throws RepositoryException, SessionException, PermissionException{
		MenuRepository mr = new DBMenuRepository();
		mr.removeMenu(menuID, producerID, accessToken);
	}

	public Menu getMenuByID(long menuID) throws RepositoryException{
		MenuRepository mr = new DBMenuRepository();
		return mr.getMenuByID(menuID);
	}

}
