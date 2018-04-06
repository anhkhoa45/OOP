package resources;

import com.google.gson.Gson;
import model.User;
import resources.filter.JWTTokenNeeded;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user")
public class UserResource {
    @GET
    @JWTTokenNeeded
    public Response user(ContainerRequestContext request){
        Gson gson = new Gson();
        User user = (User)request.getProperty("user");
        return Response.status(Response.Status.OK)
                .entity(gson.toJson(user))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

}
