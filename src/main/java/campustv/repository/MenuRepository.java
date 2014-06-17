package campustv.repository;

import java.util.Iterator;

import campustv.domain.Menu;
import campustv.repository.exceptions.RepositoryException;

public interface MenuRepository {
	
	public void addMenu(Menu m) throws RepositoryException;
	
	public Iterator<Menu> listMenus() throws RepositoryException;
	
	public void removeMenu(long menuID) throws RepositoryException;

	public Menu getMenuByID(long menuID) throws RepositoryException;

}
