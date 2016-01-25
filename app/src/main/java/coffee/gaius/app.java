package coffee.gaius;

import android.app.Application;
import com.firebase.client.Firebase;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class app extends Application {
    private static app singleton;
    public app getInstance(){
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        // Initialize Firebase
        Firebase.setAndroidContext(this);
    }

    private String firebaseURL = "https://myandroidtest.firebaseio.com";
    public String getFirebaseURL(){
        return firebaseURL;
    }

    private Firebase db;
    public Firebase getDb(){
        if (this.db == null){
            this.db = new Firebase(this.getFirebaseURL());
        }
        return this.db;
    }

    // Helper Functions
    public String getHash(String input)
    {
        String  Algorithm = "SHA-256",
                Encoding = "UTF-8";
        try {
            MessageDigest md = MessageDigest.getInstance(Algorithm);
            md.reset();
            byte[] buffer = input.getBytes(Encoding);
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
