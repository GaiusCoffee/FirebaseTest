package coffee.gaius;

import java.util.HashMap;

public class dbResponse {
    public dbResponse(dbRequest request){
        this.request = request;

        this.failed = true;
        this.params = new HashMap<String, String>();
        this.done = false;
    }

    private dbRequest request;
    public dbRequest getRequest(){
        return this.request;
    }

    private boolean failed;
    public void setFail(boolean value){
        this.failed = value;
    }
    public boolean hasFailed(){
        return this.failed;
    }

    private boolean done;
    public void setDone(boolean value){
        this.done = value;
    }
    public boolean isDone(){
        return this.done;
    }

    private HashMap<String, String> params;
    public HashMap<String, String> getParams(){
        return this.params;
    }
}
