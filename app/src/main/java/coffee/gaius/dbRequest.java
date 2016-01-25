package coffee.gaius;

import java.util.HashMap;

public class dbRequest {
    public dbRequest(app App, dbCommands command, HashMap<String, String> params){
        this.App = App;
        this.command = command;
        this.params = params;
    }

    private app App;
    public app getApp(){
        return this.App;
    }

    private dbCommands command;
    public dbCommands getCommand(){
        return this.command;
    }

    private HashMap<String, String> params;
    public HashMap<String, String> getParams(){
        return this.params;
    }
}
