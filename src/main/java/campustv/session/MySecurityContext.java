package campustv.session;

import java.security.Principal;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import campustv.domain.User;

public class MySecurityContext implements javax.ws.rs.core.SecurityContext {
	 
    private final User user;
    private final Session session;
 
    public MySecurityContext(Session session, User user) {
        this.session = session;
        this.user = user;
    }
 
    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
 
    @Override
    public Principal getUserPrincipal() {
        return user;
    }
 
    @Override
    public boolean isUserInRole(String role) {
 
        if (null == session || !session.isActive()) {
            // Forbidden
            Response denied = Response.status(Response.Status.FORBIDDEN).entity("Permission Denied").build();
            throw new WebApplicationException(denied);
        }
 
        try {
            return user.getRoles().contains(User.Role.valueOf(role));
        } catch (Exception e) {
        }
         
        return false;
    }
    @Override
    public boolean isSecure() {
        return (null != session) ? session.isSecure() : false;
    }
}