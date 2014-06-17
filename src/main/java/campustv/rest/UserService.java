package campustv.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import campustv.control.UserController;
import campustv.domain.Bookmark;
import campustv.domain.Preference;
import campustv.repository.exceptions.RepositoryException;

@Path("/users")
public class UserService {
		
	@GET
	@Path("/{userID}/preferences")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPreferences(@PathParam("userID") long userID){
		UserController control = new UserController();
		try{
			return Response.status(200).entity(control.getPreferences(userID)).build();
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	@PUT
	@Path("/{userID}/preferences")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setPreferences(@PathParam("userID") long userID, List<Preference> preferences){
		UserController control = new UserController();
		String res = "OK";
		try{
			control.setPreferences(userID, preferences.iterator());
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.status(200).entity(res).build();
	}
	
	@GET
	@Path("/{userID}/bookmarks")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBookmarks(@PathParam("userID") long userID){
		UserController control = new UserController();
		try{
			return Response.status(200).entity(control.getBookmarks(userID)).build();
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	
	@POST
	@Path("{userID}/bookmarks")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addBookmarkings(@PathParam("userID") long userID, List<Bookmark> bookmarks){
		UserController control = new UserController();
		String res = "OK";
		try{
			control.addBookmarks(userID, bookmarks.iterator());
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.status(200).entity(res).build();
	}
	
	
	@DELETE
	@Path("{userID}/bookmarks")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response removeBookmarks(@PathParam("userID") long userID, List<Bookmark> bookmarks){
		UserController control = new UserController();
		String res = "OK";
		try{
			control.removeBookmarks(userID, bookmarks.iterator());
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.status(200).entity(res).build();
	}
	
}
