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

import campustv.control.VideoController;
import campustv.domain.Video;
import campustv.repository.exceptions.RepositoryException;

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
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(Video video){
		VideoController control = new VideoController();
		String res = "OK";
		try{
			control.addVideo(video);
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}	
		return Response.status(200).entity(res).build();
	}
	
	@DELETE
	@Path("/delete/{videoID}")
	public Response deleteVideo(@PathParam("videoID") long videoID){
		VideoController control = new VideoController();
		String res = "OK";
		try{
			control.removeVideo(videoID);;
		} catch(RepositoryException e){
			return Response.status(500).entity(e.getMessage()).build();
		}	
		return Response.status(200).entity(res).build();
	}
	


}
