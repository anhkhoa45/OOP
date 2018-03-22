
import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import java.security.Key;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ngoc
 */
public class Users {
 
private String userName;
private String passWord;
 
public Users(String username, String password) {
this.userName = username;
this.passWord = password;
}
public boolean isValid(){
    if(userName.equals("Ngoc")&&passWord.equals("123")){
        return true;
    }
    return false;
}
public String jwtToken(String name) { //server generate token
    Key key = MacProvider.generateKey();

    String compactJws = Jwts.builder()
      .setSubject(userName)
      .signWith(SignatureAlgorithm.HS512, key)
      .compact();

    return compactJws;
}
}
