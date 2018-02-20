package resources;

import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

// Plain old Java Object it does not extend as class or implements
// an interface

// The class registers its methods for the HTTP GET request using the @GET annotation.
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML.

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/hello")
public class Hello {
    private Gson _gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String sayHtmlHello() {
        String myCar[] = {"A", "B", "C"};
        return _gson.toJson(myCar);
    }

    @POST
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public String postHello(@Context UriInfo ui, MultivaluedMap<String, String> formParams){
        MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
        MultivaluedMap<String, String> pathParams = ui.getPathParameters();

        Object a[] = {
                queryParams,
                pathParams,
                formParams
        };

        return _gson.toJson(a);
    }
}