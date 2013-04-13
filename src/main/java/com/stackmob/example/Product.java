package com.stackmob.example;
 
import com.stackmob.core.customcode.CustomCodeMethod;
import com.stackmob.core.rest.ProcessedAPIRequest;
import com.stackmob.core.rest.ResponseToProcess;
import com.stackmob.sdkapi.SDKServiceProvider;
import com.stackmob.sdkapi.*;
 
import java.lang.Exception;
import com.stackmob.core.InvalidSchemaException;
import com.stackmob.core.DatastoreException;
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
        add("product_id");
    }};
  }
  
  private void incrementAll(String[] new_cats,String[] new_statii,int x,String responseBody,SDKServiceProvider serviceProvider) throws InvalidSchemaException,DatastoreException {
        DataService dataService = serviceProvider.getDataService();   // get the StackMob datastore service and assemble the query
     	if(new_cats!=null&&new_cats.length>0){
 			//update cat
            for(int i=0;i<new_cats.length;i++){
                List<SMUpdate> update = new ArrayList<SMUpdate>();
                update.add(new SMIncrement("count", x));
                SMObject incrementResult = dataService.updateObject("category",new_cats[i], update);  
                //responseBody = incrementResult.toString();
            }
 		}
        if(new_statii!=null&&new_statii.length>0){
 			//update statii
            for(int i=0;i<new_statii.length;i++){
                List<SMUpdate> update = new ArrayList<SMUpdate>();
                update.add(new SMIncrement("count", x));
                SMObject incrementResult = dataService.updateObject("status",new_statii[i], update);  
            //    responseBody = incrementResult.toString();
            }
 		}
    }
  private void saveProduct(){
      
  }
  @Override
  public ResponseToProcess execute(ProcessedAPIRequest request, SDKServiceProvider serviceProvider) {
    // this logger can be used to log any activity and you can view the log at https://dashboard.stackmob.com/data/logs
    // LoggerService logger = serviceProvider.getLoggerService(SetHighScore.class);
 
    Map<String, Object> map = new HashMap<String, Object>();
    String verb = request.getVerb().toString();
    String reqb = null;
    JSONArray new_cats_ja = null;
    String new_cats[] = null;
    JSONArray new_statii_ja = null;
    String new_statii[] = null;
    JSONArray old_cats;
    JSONArray old_statii = null;
    HashSet all_cats_hs = new HashSet<String>();
    HashSet all_statii_hs = new HashSet<String>();
    String all_cats[]=null;
    String responseBody = "";
 
    StringBuilder sb = new StringBuilder(verb + " =>");
 
    // this is where we handle the special case for `POST` and `PUT` requests
    if (!request.getBody().isEmpty()) {
     	try{
     		JSONObject rb= new JSONObject(request.getBody());
     	 	reqb = rb.toString();
     	    
     	 //here we collect all the categories and statii both in the new object
       		if (!rb.isNull("categories")){
           		 new_cats_ja= rb.getJSONArray("categories");
 				if(new_cats_ja.length()>0){
					new_cats = new String[new_cats_ja.length()];
					for(int i=0;i<new_cats_ja.length();i++){
						new_cats[i] = new_cats_ja.getString(i);//
                        if(verb.equalsIgnoreCase("put")){
                            all_cats_hs.add(new String(new_cats_ja.getString(i)));// for comparisonlater//possible error
                        }
				//all_cats_hs.add(new_cats_ja.getString(i));
					}	
			//all_cats=all_cats_hs.toArray();														}
           		}
       		}
      		if (!rb.isNull("status")){
           		new_statii_ja= rb.getJSONArray("status");
           		if(new_statii_ja.length()>0){
           			new_statii = new String[new_statii_ja.length()];
					for(int i=0;i<new_statii_ja.length();i++){
						new_statii[i] = new_statii_ja.getString(i);
						//all_statii_hs.add(new_statii_ja.getString(i));
                        if(verb.equalsIgnoreCase("put")){
                            all_statii_hs.add(new String(new_statii_ja.getString(i)));// for comparisonlater//possible error
                        }
					}
         		}
           //all_statii=all_statii_hs.toArray();
           }
    		if (verb.equalsIgnoreCase("post")){
                //increment all    
                //JSONObject jsonObj = new JSONObject(request.getBody());
                //if (!jsonObj.isNull("category_id")){  
                    //new_cats = new String[1];
                    //new_cats[0]=jsonObj.getString("category_id");
                //}
                 //save item
 			    try{
                    
                    incrementAll(new_cats,new_statii,1,responseBody,serviceProvider);
 			    }
                catch (InvalidSchemaException e) {
                    HashMap<String, String> errMap = new HashMap<String, String>();
                    errMap.put("error", "invalid_schema");
                    errMap.put("detail", e.toString());
                    return new ResponseToProcess(HttpURLConnection.HTTP_INTERNAL_ERROR, errMap); // http 500 - internal server error
                } catch (DatastoreException e) {
                    HashMap<String, String> errMap = new HashMap<String, String>();
                    errMap.put("error", "datastore_exception");
                    errMap.put("detail", e.toString());
                    return new ResponseToProcess(HttpURLConnection.HTTP_INTERNAL_ERROR, errMap); // http 500 - internal server error
                } catch(Exception e) {
                    HashMap<String, String> errMap = new HashMap<String, String>();
                    errMap.put("error", "unknown");
                    errMap.put("detail", e.toString());
                    return new ResponseToProcess(HttpURLConnection.HTTP_INTERNAL_ERROR, errMap); // http 500 - internal server error
                }
                //create object
    		}
    		if (verb.equalsIgnoreCase("put")){
                
     //get all values in new + existing versions and then compare
                JSONObject jsonObj = new JSONObject(request.getBody());
                if (!jsonObj.isNull("product_id")){  
                    
                    
                    //get old copy of obj//done
                    //add old values of cats/statii//done
                    //add new values of cat/statii//done
                    //start comparing
                    
                    //
                    try{
                        DataService ds = serviceProvider.getDataService();
                        List<SMCondition> query = new ArrayList<SMCondition>();
                        query.add(new SMEquals("product_id", new SMString(jsonObj.getString("product_id"))));
                        List<SMObject> results = ds.readObjects("product", query);
                        if (results!=null && results.size()==1){
                            SMObject product= results.get(0);
                            //getting old stats and cats
                            List<SMValue> categories = (List<SMValue>)(product.getValue().get("categories").getValue());
                            for (SMValue smString : categories) {
                                SMString stringValue = (SMString)smString.getValue();
                                //put old values to hashset
                                //all_cats_hs.add(new String(stringValue));
                                all_cats_hs.add(new String(smString.toString()));
                            }
                            List<SMValue> statii = (List<SMValue>)(product.getValue().get("status").getValue());
                            for (SMValue smString : statii) {
                                //SMString stringValue = (SMString)smString.getValue();
                                //put old values to hashset
                                //all_statii_hs.add(new String(stringValue));
                                all_statii_hs.add(smString.toString());
                            }   
                            for (Object s : all_cats_hs) {
                                //System.out.println(s);
                                responseBody+=(String)s;
                            }
                            for (String s : all_statii_hs) {
                                //System.out.println(s);
                            }
                        }
                        else{
                            responseBody="product not found";
                        }
                        //compare and save
                        
                        //responseBody = cats.length;
         		    }
                    catch (InvalidSchemaException e) {
                        HashMap<String, String> errMap = new HashMap<String, String>();
                        errMap.put("error", "invalid_schema");
                        errMap.put("detail", e.toString());
                        return new ResponseToProcess(HttpURLConnection.HTTP_INTERNAL_ERROR, errMap); // http 500 - internal server error
                    } catch (DatastoreException e) {
                        HashMap<String, String> errMap = new HashMap<String, String>();
                        errMap.put("error", "datastore_exception");
                        errMap.put("detail", e.toString());
                        return new ResponseToProcess(HttpURLConnection.HTTP_INTERNAL_ERROR, errMap); // http 500 - internal server error
                    } catch(Exception e) {
                        HashMap<String, String> errMap = new HashMap<String, String>();
                        errMap.put("error", "unknown");
                        errMap.put("detail", e.toString());
                        return new ResponseToProcess(HttpURLConnection.HTTP_INTERNAL_ERROR, errMap); // http 500 - internal server error
                    }
                }
    		}
    		if (verb.equalsIgnoreCase("delete")){
     //decrement all
                 try{
                    incrementAll(new_cats,new_statii,-1,responseBody,serviceProvider);
     		    }
                catch (InvalidSchemaException e) {
                    HashMap<String, String> errMap = new HashMap<String, String>();
                    errMap.put("error", "invalid_schema");
                    errMap.put("detail", e.toString());
                    return new ResponseToProcess(HttpURLConnection.HTTP_INTERNAL_ERROR, errMap); // http 500 - internal server error
                } catch (DatastoreException e) {
                    HashMap<String, String> errMap = new HashMap<String, String>();
                    errMap.put("error", "datastore_exception");
                    errMap.put("detail", e.toString());
                    return new ResponseToProcess(HttpURLConnection.HTTP_INTERNAL_ERROR, errMap); // http 500 - internal server error
                } catch(Exception e) {
                    HashMap<String, String> errMap = new HashMap<String, String>();
                    errMap.put("error", "unknown");
                    errMap.put("detail", e.toString());
                    return new ResponseToProcess(HttpURLConnection.HTTP_INTERNAL_ERROR, errMap); // http 500 - internal server error
                }
                 //delete item
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
 
    //map.put("message", sb.toString());
    //map.put("verb", verb);
    //map.put("reqbody",reqb);
    map.put("response_body", responseBody);
    return new ResponseToProcess(HttpURLConnection.HTTP_OK, map);
  }
 
}

