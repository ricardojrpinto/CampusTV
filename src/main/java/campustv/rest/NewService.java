package campustv.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import campustv.control.EventController;
import campustv.control.NewController;
import campustv.domain.New;
import campustv.repository.exceptions.PermissionException;
import campustv.repository.exceptions.RepositoryException;
import campustv.repository.exceptions.SessionException;

@Path("/news")
public class NewService {
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNews() {
		NewController control = new NewController();
		try{
			return Response.status(200).entity(control.getNews()).build();
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/{author}/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNewsByAuthor(@PathParam("author") String author) {
		NewController control = new NewController();
		try{
			return Response.status(200).entity(control.getNewsByAuthor(author)).build();
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/list/videos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getVideosOfNews() {
		NewController control = new NewController();
		try{
			return Response.status(200).entity(control.getVideosOfNews()).build();
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/{newID}/videos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getVideosOfNew(@PathParam("newID") long newID) {
		NewController control = new NewController();
		try{
			return Response.status(200).entity(control.getVideosOfNew(newID)).build();
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	
	@GET
	@Path("/{newID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNewByID(@PathParam("newID") int newID){
		NewController control = new NewController();
		try{
			return Response.status(200).entity(control.getNewByID(newID)).build();
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(New n, @Context HttpHeaders headers){
		NewController control = new NewController();
		String res = "OK";
		String accessToken = headers.getRequestHeader("token").get(0);	
		try{
			control.addNew(n,accessToken);
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		} catch (SessionException e) {
			return Response.status(500).entity(e.getMessage()).build();
		} catch (PermissionException e) {
			return Response.status(500).entity(e.getMessage()).build();
		}	
		return Response.status(200).entity(res).build();
	}
	
	@DELETE
	@Path("/delete/{newID}/{producerID}")
	public Response deleteNew(@PathParam("newID") long newID, @PathParam("producerID") long producerID, @Context HttpHeaders headers){
		NewController control = new NewController();
		String res = "OK";
		String accessToken = headers.getRequestHeader("token").get(0);	
		try{
			control.removeNew(newID, producerID, accessToken);
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		} catch (SessionException e) {
			return Response.status(500).entity(e.getMessage()).build();
		} catch (PermissionException e) {
			return Response.status(500).entity(e.getMessage()).build();
		}	
		return Response.status(200).entity(res).build();
	}
	
	@GET
	@Path("/{scheduleDay}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNewsDay(@PathParam("scheduleDay")  String scheduleDay){
		NewController control = new NewController();
		try{
			return Response.status(200).entity(control.getNewsDay(scheduleDay)).build();
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	

}
