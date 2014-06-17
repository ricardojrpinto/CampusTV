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

import campustv.control.NewController;
import campustv.domain.New;
import campustv.repository.exceptions.RepositoryException;

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
	public Response create(New n){
		NewController control = new NewController();
		String res = "OK";
		try{
			control.addNew(n);
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}	
		return Response.status(200).entity(res).build();
	}
	
	@DELETE
	@Path("/delete/{newID}")
	public Response deleteNew(@PathParam("newID") int newID){
		NewController control = new NewController();
		String res = "OK";
		try{
			control.removeNew(newID);
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}	
		return Response.status(200).entity(res).build();
	}
	

}
