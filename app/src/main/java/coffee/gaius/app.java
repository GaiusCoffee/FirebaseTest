package coffee.gaius;

import android.app.Application;
import com.firebase.client.Firebase;

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

    private db Db;
    public db getDb(){
        if (this.Db == null){
            this.Db = new db();
        }
        return this.Db;
    }
}
