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
import campustv.domain.Event;
import campustv.repository.exceptions.PermissionException;
import campustv.repository.exceptions.RepositoryException;
import campustv.repository.exceptions.SessionException;

@Path("/events")
public class EventService {
	
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEvents() {
		EventController control = new EventController();
		try{
			return Response.status(200).entity(control.getEvents()).build();
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/{author}/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEventsByAuthor(@PathParam("author") String author) {
		EventController control = new EventController();
		try{
			return Response.status(200).entity(control.getEventsByAuthor(author)).build();
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/list/videos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getVideosOfEvents() {
		EventController control = new EventController();
		try{
			return Response.status(200).entity(control.getVideosOfEvents()).build();
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/{eventID}/videos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getVideosOfEvent(@PathParam("eventID") long eventID) {
		EventController control = new EventController();
		try{
			return Response.status(200).entity(control.getVideosOfEvent(eventID)).build();
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	

	@GET
	@Path("/{eventID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEventByID(@PathParam("eventID") long eventID){
		EventController control = new EventController();
		try{
			return Response.status(200).entity(control.getEventByID(eventID)).build();
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(Event event,  @Context HttpHeaders headers){
		EventController control = new EventController();
		String accessToken = headers.getRequestHeader("token").get(0);	
		String res = "OK";
		try{
			control.addEvent(event, accessToken);
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
	@Path("/delete/{eventID}/{producerID}")
	public Response deleteEvent(@PathParam("eventID") long eventID, @PathParam("producerID") long producerID,  @Context HttpHeaders headers){
		EventController control = new EventController();
		String accessToken = headers.getRequestHeader("token").get(0);	
		String res = "OK";
		try{
			control.removeEvent(eventID, producerID, accessToken);
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
	public Response getEventsDay(@PathParam("scheduleDay")  String scheduleDay){
		EventController control = new EventController();
		try{
			return Response.status(200).entity(control.getEventsDay(scheduleDay)).build();
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
}
