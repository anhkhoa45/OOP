package resources.auth;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import model.User;
import repository.UserRepository;
import resources.auth.util.KeyGenerator;
import resources.auth.util.SimpleKeyGenerator;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.util.Date;

@Path("authentication")
public class Authentication {

    private UserRepository userRepository = new UserRepository();

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("email") String email, @FormParam("password") String password){
        User user = userRepository.findByEmail(email);

        if(user != null && user.getPassword().equals(password)) {
            String token = createJWT(email, "Anh Khoa", "subject", -1);
            String json = "{ \"token\": \"" + token + "\"}";
            return Response.status(Response.Status.OK)
                    .entity(json)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Unauthorized")
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .build();
        }
    }

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response register(@FormParam("email") String email, @FormParam("password") String password) {
        User user = new User(email, password);
        try {
            userRepository.save(user);
        } catch (BadRequestException e){
            return Response.status(422)
                    .entity("Unprocessable Entity")
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .build();
        }

        return login(email, password);
    }

    //Sample method to construct a JWT
    private String createJWT(String id, String issuer, String subject, long ttlMillis) {
        KeyGenerator keyGenerator = new SimpleKeyGenerator();
        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        Key signingKey = keyGenerator.generateKey();

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }
}
