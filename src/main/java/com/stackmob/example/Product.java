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
import java.util.HashSet;
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
    String reqb=null;
    JSONArray new_cats_ja=null;
    String new_cats[] =null;
    JSONArray new_statii_ja=null;
    String new_statii[] =null;
    JSONArray old_cats=null;
    JSONArray old_statii=null;
    Hashset all_cats_hs=null;
    Hashset all_statii_hs=null;
    Sring all_cats[]=null;
 
    StringBuilder sb = new StringBuilder(verb + " =>");
 
    // this is where we handle the special case for `POST` and `PUT` requests
     if (!request.getBody().isEmpty()) {
     	try{
     		JSONObject rb= new JSONObject(request.getBody());
     	 reqb = rb.toString();
     	 
     	 //here we collect all the categories and statii both in the new object
       if (!rb.isNull("categories")){
           JSONArray new_cats_ja= rb.getJSONArray("categories");
 			if(new_cats_ja.length()>0){
			new_cats = new String[new_cats_ja.length()];
			for(int i=0;i<new_cats_ja.length();i++){
				new_cats[i] = new_cats_ja.getString(i);//
				//all_cats_hs.add(new_cats_ja.getString(i));
			}	
			//all_cats=all_cats_hs.toArray();														}
           }
        if (!rb.isNull("statii")){
           JSONArray new_statii_ja= rb.getJSONArray("statii");
           if(new_statii_ja.length()>0){
           			new_statii = new String[new_statii_ja.length()];
				for(int i=0;i<new_statii_ja.length();i++){
					new_statii[i] = new_statii_ja.getString(i);
				//all_statii_hs.add(new_statii_ja.getString(i));
				}
           }
           //all_statii=all_statii_hs.toArray();
           }
    		if (verb.equalsIgnoreCase("post")){
     //increment all
    		}
    		if (verb.equalsIgnoreCase("put")){
     //get all values in new + existing versions and then compare
    		}
    		if (verb.equalsIgnoreCase("delete")){
     //decrement all
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
