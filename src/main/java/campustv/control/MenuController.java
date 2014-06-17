package campustv.control;

import java.util.Iterator;

import campustv.domain.Menu;
import campustv.repository.DBMenuRepository;
import campustv.repository.MenuRepository;
import campustv.repository.exceptions.RepositoryException;

public class MenuController {
	
	public void addMenu(Menu m) throws RepositoryException{
		MenuRepository mr = new DBMenuRepository();
		mr.addMenu(m);
	}
	
	public Iterator<Menu> getMenus() throws RepositoryException{
		MenuRepository mr = new DBMenuRepository();
		return mr.listMenus();
	}
	
	public void deleteMenu(long menuID) throws RepositoryException{
		MenuRepository mr = new DBMenuRepository();
		mr.removeMenu(menuID);
	}

	public Menu getMenuByID(long menuID) throws RepositoryException{
		MenuRepository mr = new DBMenuRepository();
		return mr.getMenuByID(menuID);
	}

}
