package persistence;

import org.json.JSONObject;

//Interface for writable; from: JsonSerializationDemo
public interface Writable {

    //EFFECTS: returns this as JSON object
    JSONObject toJson();
}
