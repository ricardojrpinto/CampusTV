package campustv.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import campustv.control.MenuController;
import campustv.domain.Menu;
import campustv.repository.exceptions.RepositoryException;

@Path("/menus")
public class MenuService {

	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMenus() {
		MenuController control = new MenuController();
		try{
			return Response.status(200).entity(control.getMenus()).build();
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/{menuID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMenuByID(@PathParam("menuID") long menuID){
		MenuController control = new MenuController();
		try{
			return Response.status(200).entity(control.getMenuByID(menuID)).build();
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(Menu menu){
		MenuController control = new MenuController();
		String res = "OK";
		try{
			control.addMenu(menu);
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}	
		return Response.status(200).entity(res).build();
	}
	
	@DELETE
	@Path("/delete/{menuID}")
	public Response deleteMenu(@PathParam("menuID") long menuID){
		MenuController control = new MenuController();
		String res = "OK";
		try{
			control.deleteMenu(menuID);
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}	
		return Response.status(200).entity(res).build();
	}
	
	
}
