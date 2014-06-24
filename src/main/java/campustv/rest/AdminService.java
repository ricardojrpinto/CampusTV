package campustv.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import campustv.control.AdminController;
import campustv.domain.Admin;
import campustv.domain.Area;
import campustv.domain.Local;
import campustv.domain.Producer;
import campustv.repository.exceptions.PermissionException;
import campustv.repository.exceptions.RepositoryException;
import campustv.repository.exceptions.SessionException;

@Path("/admin")
public class AdminService {


	@POST
	@Path("{userID}/create/areas")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createArea(@PathParam("userID") long userID, Area a,  @Context HttpHeaders headers){
		AdminController control = new AdminController();
		String accessToken = headers.getRequestHeader("token").get(0);	
		String res = "OK";
		try{
			control.addArea(userID, a, accessToken);
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		} catch (SessionException e) {
			return Response.status(500).entity(e.getMessage()).build();
		} catch (PermissionException e) {
			return Response.status(500).entity(e.getMessage()).build();
		}	
		return Response.status(200).entity(res).build();
	}
	
	@POST
	@Path("{userID}/create/locations")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createLocation(@PathParam("userID") long userID, Local l,  @Context HttpHeaders headers){
		AdminController control = new AdminController();
		String accessToken = headers.getRequestHeader("token").get(0);	
		String res = "OK";
		try{
			control.addLocation(userID, l, accessToken);
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		} catch (PermissionException e) {
			return Response.status(500).entity(e.getMessage()).build();
		} catch (SessionException e) {
			return Response.status(500).entity(e.getMessage()).build();
		}	
		return Response.status(200).entity(res).build();
	}
	
	
	@POST
	@Path("{userID}/create/admin")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createAdmin(@PathParam("userID") long userID, Admin admin, @Context HttpHeaders headers){
		AdminController control = new AdminController();
		String accessToken = headers.getRequestHeader("token").get(0);	
		String res = "OK";
		try{
			control.addAdmin(userID, admin, accessToken);
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		} catch (SessionException e) {
			return Response.status(500).entity(e.getMessage()).build();
		} catch (PermissionException e) {
			return Response.status(500).entity(e.getMessage()).build();
		}	
		return Response.status(200).entity(res).build();
	}
	
	@POST
	@Path("{userID}/create/producer")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createProducer(@PathParam("userID") long userID, Producer producer, @Context HttpHeaders headers){
		AdminController control = new AdminController();
		String accessToken = headers.getRequestHeader("token").get(0);	
		String res = "OK";
		try{
			control.addProducer(userID, producer, accessToken);
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		} catch (SessionException e) {
			return Response.status(500).entity(e.getMessage()).build();
		} catch (PermissionException e) {
			return Response.status(500).entity(e.getMessage()).build();
		}	
		return Response.status(200).entity(res).build();
	}
	
	
}
