package coffee.gaius;

import com.firebase.client.Firebase;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class db {
    Firebase ref;
    String FirebaseURL = "https://myandroidtest.firebaseio.com";

    public db(){
        this.ref = new Firebase(FirebaseURL);
    }

    public void auth(String username, String password,
                     Firebase.AuthResultHandler result){
        String passhash = hash(username + "|" + password);
        this.ref.authWithPassword(username, passhash, result);
    }

    public void register(String username, String password,
                         Firebase.ValueResultHandler<Map<String, Object>> result){
        String passhash = hash(username + "|" + password);
        this.ref.createUser(username, passhash, result);
    }

    private String hash(String input)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.reset();
            byte[] buffer = input.getBytes("UTF-8");
            md.update(buffer);
            byte[] digest = md.digest();
            String hexStr = "";
            for (int i = 0; i < digest.length; i++) {
                hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
            }
            return hexStr;
        } catch (NoSuchAlgorithmException ex1) {
            return null;
        } catch (UnsupportedEncodingException ex2){
            return null;
        }
    }
}
