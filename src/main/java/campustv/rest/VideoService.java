package campustv.rest;

import java.io.FileNotFoundException;
import java.io.IOException;

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

import campustv.control.NewController;
import campustv.control.VideoController;
import campustv.domain.Video;
import campustv.repository.exceptions.PermissionException;
import campustv.repository.exceptions.RepositoryException;
import campustv.repository.exceptions.SessionException;

@Path("/videos")
public class VideoService {
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getVideos() {
		VideoController control = new VideoController();
		try{
			return Response.status(200).entity(control.getVideos()).build();
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/{author}/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getVideosByAuthor(@PathParam("author") String author) {
		VideoController control = new VideoController();
		try{
			return Response.status(200).entity(control.getVideosByAuthor(author)).build();
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/{videoID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getVideoByID(@PathParam("videoID") long videoID){
		VideoController control = new VideoController();
		try{
			return Response.status(200).entity(control.getVideoByID(videoID)).build();
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/{videoID}/data")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	public Response getVideoData(@PathParam("videoID") long videoID){
		VideoController control = new VideoController();
		try{
			return Response.status(200).entity(control.getVideoData(videoID)).build();
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		} catch(FileNotFoundException e){
			return Response.status(404).entity(e.getMessage()).build();
		} catch(IOException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(Video video, @Context HttpHeaders headers){
		VideoController control = new VideoController();
		String res = "OK";
		String accessToken = headers.getRequestHeader("token").get(0);	
		try{
			control.addVideo(video, accessToken);
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
	@Path("/delete/{videoID}/{producerID}")
	public Response deleteVideo(@PathParam("videoID") long videoID, @PathParam("producerID") long producerID, @Context HttpHeaders headers){
		VideoController control = new VideoController();
		String res = "OK";
		String accessToken = headers.getRequestHeader("token").get(0);	
		try{
			control.removeVideo(videoID, producerID, accessToken);;
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
	public Response getVideosDay(@PathParam("scheduleDay")  String scheduleDay){
		VideoController control = new VideoController();
		try{
			return Response.status(200).entity(control.getVideosDay(scheduleDay)).build();
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

}
