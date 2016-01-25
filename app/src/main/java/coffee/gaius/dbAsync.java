package coffee.gaius;

import android.os.AsyncTask;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public abstract class dbAsync extends AsyncTask<dbRequest, Integer, dbResponse> {
    @Override
    protected dbResponse doInBackground(dbRequest... req) {
        final dbRequest request = req[0];
        final dbResponse response = new dbResponse(request);

        switch (request.getCommand()) {
            case userAuth: {
                // Get Parameters
                String username = "", password = "";
                for (Map.Entry<String, String> pair : request.getParams().entrySet()) {
                    if (pair.getKey().toLowerCase().equals("username"))
                        username = pair.getValue();
                    if (pair.getKey().toLowerCase().equals("password"))
                        password = pair.getValue();
                }
                // Hash password
                String hash = request.getApp().getHash(username + "|" + password);
                // Process
                request.getApp().getDb().authWithPassword(username, hash, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        response.setFail(false);
                        response.getParams().put("uid", authData.getUid());
                        response.getParams().put("provider", authData.getProvider());
                        response.getParams().put("token", authData.getToken());
                        response.getParams().put("expires", String.valueOf(authData.getExpires()));
                        response.getParams().put("email",
                                authData.getProviderData().get("email").toString());
                        response.getParams().put("profileImageURL",
                                authData.getProviderData().get("profileImageURL").toString());
                        response.setDone(true);
                    }
                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        response.setFail(true);
                        response.getParams().clear();
                        response.getParams().put("error", firebaseError.getMessage());
                        response.getParams().put("details", firebaseError.getDetails());
                        response.setDone(true);
                    }
                });
                break;
            }
            case userRegister: {
                // Get Parameters
                String username = "", password = "";
                for (Map.Entry<String, String> pair : request.getParams().entrySet()) {
                    if (pair.getKey().toLowerCase().equals("username"))
                        username = pair.getValue();
                    if (pair.getKey().toLowerCase().equals("password"))
                        password = pair.getValue();
                }
                // Hash password
                String hash = request.getApp().getHash(username + "|" + password);
                // Process
                request.getApp().getDb().createUser(username, hash,
                        new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> result) {
                        response.setFail(false);
                        response.getParams().put("uid", result.get("uid").toString());
                        response.setDone(true);
                    }
                    @Override
                    public void onError(FirebaseError firebaseError) {
                        response.setFail(true);
                        response.getParams().clear();
                        response.getParams().put("error", firebaseError.getMessage());
                        response.getParams().put("details", firebaseError.getDetails());
                        response.setDone(true);
                    }
                });
                break;
            }
            default: {
                response.setFail(true);
                response.getParams().clear();
                response.getParams().put("error", "Command not recognized.");
                response.setDone(true);
            }
        }
        int ctr = 0;
        while (!response.isDone()) {
            try {
                ctr++;
                if (ctr >= 100) {
                    response.setDone(true);
                    response.setFail(true);
                    response.getParams().clear();
                    response.getParams().put("error", "Thread timed out.");
                } else {
                    Thread.currentThread();
                    Thread.sleep(100,0);
                }
            } catch (InterruptedException ex) {
                response.setDone(true);
                response.setFail(true);
                response.getParams().clear();
                response.getParams().put("error", "Thread interrupted. " + ex.toString());
            }
        }
        return response;
    }
    protected void onProgressUpdate(Integer... progress) {
        //Override Optional
    }
    protected abstract void onPostExecute(dbResponse result);
}
