package com.stackmob.example;
 
import com.stackmob.core.customcode.CustomCodeMethod;
import com.stackmob.core.rest.ProcessedAPIRequest;
import com.stackmob.core.rest.ResponseToProcess;
import com.stackmob.sdkapi.SDKServiceProvider;
 
import java.lang.Exception;
import java.lang.String;
import java.lang.StringBuilder;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
public class Product implements CustomCodeMethod {
 
  /**
   * Name our custom code method
   */
  @Override
  public String getMethodName() {
    return "update_product";
  }
 
  /**
   * Specify parameters for our custom code method
   */
  @Override
  public List<String> getParams() {
    return new ArrayList<String>() {{
      //add("username");
      //add("score");
      add("product");
    }};
  }
 
  @Override
  public ResponseToProcess execute(ProcessedAPIRequest request, SDKServiceProvider serviceProvider) {
    // this logger can be used to log any activity and you can view the log at https://dashboard.stackmob.com/data/logs
    // LoggerService logger = serviceProvider.getLoggerService(SetHighScore.class);
 
    Map<String, Object> map = new HashMap<String, Object>();
    String verb = request.getVerb().toString();
 
    StringBuilder sb = new StringBuilder(verb + " =>");
 
    // this is where we handle the special case for `POST` and `PUT` requests
     if (!request.getBody().isEmpty()) {
     	try{
     		JSONObject rb= new JSONObject(request.getBody());
     		String reqb = rb.toString();
    		if (verb.equalsIgnoreCase("post")){
     
    		}
    		if (verb.equalsIgnoreCase("put")){
     
    		}
    		if (verb.equalsIgnoreCase("delete")){
     
    		}
    	
    	}
    	catch(JSONException e){
          sb.append("Caught JSON Exception");
          e.printStackTrace();
    	}
     } else sb.append("Request body is empty");
    /*if (verb.equalsIgnoreCase("post") || verb.equalsIgnoreCase("put")) {
 
      if (!request.getBody().isEmpty()) {
        try {
          JSONObject jsonObj = new JSONObject(request.getBody());
          if (!jsonObj.isNull("username")) sb.append(" --username: " + jsonObj.getString("username"));
          if (!jsonObj.isNull("score")) sb.append(" --score: " + jsonObj.getString("score"));
        } catch (JSONException e) {
          sb.append("Caught JSON Exception");
          e.printStackTrace();
        }
      } else sb.append("Request body is empty");*/
     
    // this is where we handle the case for `GET` and `DELETE` requests
    //} else sb.append( String.format("username: %s | score: %s", request.getParams().get("username"), request.getParams().get("score")) );
 
    map.put("message", sb.toString());
    map.put("verb", verb);
    map.put("reqbody",reqb);
    return new ResponseToProcess(HttpURLConnection.HTTP_OK, map);
  }
 
}
