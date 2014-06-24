package campustv.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import campustv.control.LocalController;
import campustv.repository.exceptions.RepositoryException;

@Path("/locations")
public class LocalService {
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLocations(){
		LocalController control = new LocalController();
		try{
			return Response.status(200).entity(control.getLocations()).build();
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
}
