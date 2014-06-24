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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import campustv.control.SessionController;
import campustv.control.UserController;
import campustv.domain.Bookmark;
import campustv.domain.Consumer;
import campustv.domain.GPS;
import campustv.domain.Preference;
import campustv.repository.exceptions.RepositoryException;
import campustv.domain.Session;
import campustv.repository.exceptions.SessionException;

@Path("/users")
public class UserService {
		
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(Session log){
		SessionController control = new SessionController();
		try{
			return Response.status(200).entity(control.login(log)).build();
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		} catch (SessionException e) {
			return Response.status(500).entity(e.getMessage()).build();
		}	
		
	}
	
	@DELETE
	@Path("/logout/{userID}")
	public Response logout(@PathParam("userID") long userID, @Context HttpHeaders headers){
		SessionController control = new SessionController();
		String accessToken = headers.getRequestHeader("token").get(0);	
		String res = "OK";
		try{
			control.logout(userID, accessToken);
			return Response.status(200).entity(res).build();
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		} catch (SessionException e) {
			return Response.status(500).entity(e.getMessage()).build();
		}	
	}
	
	@POST
	@Path("sign-in/consumer")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createConsumer(Consumer consumer){
		UserController control = new UserController();
		String res = "OK";
		try{
			control.resgistConsumer(consumer);
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}	
		return Response.status(200).entity(res).build();
	}
	
	
	@GET
	@Path("/{userID}/preferences")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPreferences(@PathParam("userID") long userID, @Context HttpHeaders headers){
		UserController control = new UserController();
		String accessToken = headers.getRequestHeader("token").get(0);	
		try{
			return Response.status(200).entity(control.getPreferences(userID, accessToken)).build();
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		} catch (SessionException e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	@PUT
	@Path("/{userID}/preferences")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setPreferences(@PathParam("userID") long userID, List<Preference> preferences, @Context HttpHeaders headers){
		UserController control = new UserController();
		String accessToken = headers.getRequestHeader("token").get(0);	
		String res = "OK";
		try{
			control.setPreferences(userID, preferences.iterator(), accessToken);
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		} catch (SessionException e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.status(200).entity(res).build();
	}
	
	@GET
	@Path("/{userID}/bookmarks")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBookmarks(@PathParam("userID") long userID, @Context HttpHeaders headers){
		UserController control = new UserController();
		String accessToken = headers.getRequestHeader("token").get(0);	
		try{
			return Response.status(200).entity(control.getBookmarks(userID, accessToken)).build();
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		} catch (SessionException e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	
	@POST
	@Path("{userID}/bookmarks")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addBookmarkings(@PathParam("userID") long userID, List<Bookmark> bookmarks, @Context HttpHeaders headers){
		UserController control = new UserController();
		String res = "OK";
		String accessToken = headers.getRequestHeader("token").get(0);	
		try{
			control.addBookmarks(userID, bookmarks.iterator(), accessToken);
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		} catch (SessionException e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.status(200).entity(res).build();
	}
	
	
	@DELETE
	@Path("{userID}/bookmarks")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response removeBookmarks(@PathParam("userID") long userID, List<Bookmark> bookmarks, @Context HttpHeaders headers){
		UserController control = new UserController();
		String res = "OK";
		String accessToken = headers.getRequestHeader("token").get(0);	
		try{
			control.removeBookmarks(userID, bookmarks.iterator(), accessToken);
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		} catch (SessionException e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.status(200).entity(res).build();
	}
	
	@GET
	@Path("{userID}/notifications")
	public Response getNotifications(@PathParam("userID") long userID, GPS coord, @Context HttpHeaders headers){
		UserController control = new UserController();
		String accessToken = headers.getRequestHeader("token").get(0);	
		try{
			return Response.status(200).entity(control.getNotifications(userID, coord, accessToken)).build();
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		} catch (SessionException e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
}
