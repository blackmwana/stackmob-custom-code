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
import java.util.Arrays;
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
                SMObject incrementResult = dataService.updateObject("category",new_cats[i], update);   //SMString
                //responseBody = incrementResult.toString();
            }
 		}
        if(new_statii!=null&&new_statii.length>0){
 			//update statii
            for(int i=0;i<new_statii.length;i++){
                List<SMUpdate> update = new ArrayList<SMUpdate>();
                update.add(new SMIncrement("count", x));
                SMObject incrementResult = dataService.updateObject("status",new_statii[i], update);  //SMString
            //    responseBody = incrementResult.toString();
            }
 		}
    }
  private void incrementCats(String[] old_cats,String[] new_cats,int x,String responseBody,SDKServiceProvider serviceProvider) throws InvalidSchemaException,DatastoreException {
        DataService dataService = serviceProvider.getDataService();   // get the StackMob datastore service and assemble the query
        if(new_cats!=null&&new_cats.length>0){//  old_cats.length==0// not necessary o recheck new_cats!=null
     		//update cat
            for(int i=0;i<new_cats.length;i++){
                List<SMUpdate> update = new ArrayList<SMUpdate>();
                update.add(new SMIncrement("count", x));
                SMObject incrementResult = dataService.updateObject("category",new_cats[i], update);  
                //responseBody = incrementResult.toString();
            }
 		}
        if(old_cats!=null&&old_cats.length>0){//new_cats.length==0// not necessary o recheck old_cats!=null
        	//update cat
            for(int i=0;i<old_cats.length;i++){
                List<SMUpdate> update = new ArrayList<SMUpdate>();
                update.add(new SMIncrement("count", x));
                SMObject incrementResult = dataService.updateObject("category",old_cats[i], update);  
                //responseBody = incrementResult.toString();
            }
 		}
        
  }
  private void incrementStatii(String[] old_statii,String[] new_statii,int x,String responseBody,SDKServiceProvider serviceProvider) throws InvalidSchemaException,DatastoreException {
        DataService dataService = serviceProvider.getDataService();   // get the StackMob datastore service and assemble the query
        if(new_statii!=null&&new_statii.length>0){
         	//update status
            for(int i=0;i<new_statii.length;i++){
                List<SMUpdate> update = new ArrayList<SMUpdate>();
                update.add(new SMIncrement("count", x));
                SMObject incrementResult = dataService.updateObject("status",new_statii[i], update);  
                //responseBody = incrementResult.toString();
            }
 		}
        if(old_statii!=null&&old_statii.length>0){
        	//update status
            for(int i=0;i<old_statii.length;i++){
                List<SMUpdate> update = new ArrayList<SMUpdate>();
                update.add(new SMIncrement("count", x));
                SMObject incrementResult = dataService.updateObject("status",old_statii[i], update);  
                //responseBody = incrementResult.toString();
            }
 		}
        
  }
  private boolean savePostProduct(JSONObject jsonObj,SDKServiceProvider serviceProvider,String[] new_cats,String[] new_statii) throws InvalidSchemaException,DatastoreException{
        
        DataService ds = serviceProvider.getDataService();
         
            Map<String, SMValue> objMap = new HashMap<String, SMValue>();
             if (!jsonObj.isNull("title_en"));
                objMap.put("title_en", new SMString(jsonObj.getString("title_en")));
            if (!jsonObj.isNull("title_pl"));
                objMap.put("title_pl", new SMString(jsonObj.getString("title_pl")));
            if (!jsonObj.isNull("description_en"));
                objMap.put("description_en", new SMString(jsonObj.getString("description_en")));
            if (!jsonObj.isNull("description_pl"));
                objMap.put("description_pl", new SMString(jsonObj.getString("description_pl")));
            if (!jsonObj.isNull("link"));
                objMap.put("link", new SMString(jsonObj.getString("link")));
            if (!jsonObj.isNull("picture"));
               objMap.put("picture", new SMString(jsonObj.getString("picture")));
            if (!jsonObj.isNull("price"));
                objMap.put("price", new SMString(jsonObj.getString("price")));  
                
           SMObject newObj= ds.createObject("product", new SMObject(objMap));
           List<SMObject> catsToCreate=Arrays.toList(new_cats);
           List<SMObject> statiiToCreate=Arrays.toList(new_statii);
           DataService ds = serviceProvider.getDataService();
           BulkResult catsResult = ds.addRelatedObjects("product", new SMString(newObj.getValue().get("product_id").getValue()), "categories", catsToCreate);
           BulkResult statiiResult = ds.addRelatedObjects("product", new SMString(newObj.getValue().get("product_id").getValue()), "status",  statiiToCreate);
  }
  private boolean savePutProduct(JSONObject jsonObj,SDKServiceProvider serviceProvider,String[] add_cats, String[] add_statii,String[] remove_cats,String[] remove_statii) throws InvalidSchemaException,DatastoreException{
        
        DataService ds = serviceProvider.getDataService();
         
            List<SMUpdate> update = new ArrayList<SMUpdate>();
            
            if (!jsonObj.isNull("title_en"));
                update.add(new SMSet("title_en", new SMString(jsonObj.getString("title_en")));
            if (!jsonObj.isNull("title_pl"));
                update.add(new SMSet("title_pl", new SMString(jsonObj.getString("title_pl")));
            if (!jsonObj.isNull("description_en"));
                update.add(new SMSet("description_en", new SMString(jsonObj.getString("description_en")));
            if (!jsonObj.isNull("description_pl"));
                update.add(new SMSet("description_pl", new SMString(jsonObj.getString("description_pl")));
            if (!jsonObj.isNull("link"));
                update.add(new SMSet("link", new SMString(jsonObj.getString("link")));
            if (!jsonObj.isNull("picture"));
                update.add(new SMSet("picture", new SMString(jsonObj.getString("picture")));
            if (!jsonObj.isNull("price"));
                update.add(new SMSet("price", new SMString(jsonObj.getString("price")));    
                
            SMObject result = ds.updateObject("product",  jsonObj.getString("product_id"), update);  
            List<SMObject> catsToAdd=Arrays.toList(add_cats);//can be null
            List<SMObject> catsToRemove=Arrays.toList(remove_cats);//can be null
            List<SMObject> statiiToAdd=Arrays.toList(add_statii);//can be null
            List<SMObject> statiiToRemove=Arrays.toList(remove_statii);//can be null
            
            BulkResult catsAddResult = ds.addRelatedObjects("product", new SMString(jsonObj.getString("product_id")), "categories", catsToAdd);
            BulkResult statiiAddResult = ds.addRelatedObjects("product", new SMString(jsonObj.getString("product_id")), "status",  statiiToAdd);
            ulkResult catsRemoveResult = ds.removeRelatedObjects("product", new SMString(jsonObj.getString("product_id")), "categories", catsToRemove,false);
            BulkResult statiiRemoveResult = ds.removeRelatedObjects("product", new SMString(jsonObj.getString("product_id")), "status",  statiiToRemove,false);
         
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
    String old_cats[]=null;
    String old_statii[] = null;
    List<String> add_cats[]=new ArrayList<String>();
    List<String> add_statii[] =new ArrayList<String>();
    List<String> remove_cats[]=new ArrayList<String>();
    List<String> remove_statii[] = new ArrayList<String>();
    HashSet all_cats_hs = new HashSet<String>();
    HashSet all_statii_hs = new HashSet<String>();
    //String all_cats[]=null;
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
                //create objectsavePostProduct(JSONObject jsonObj,SDKServiceProvider serviceProvider,String[] new_cats,String[] new_statii)
                if(savePoProduct(rb,serviceProvider,new_cats,new_statii))
                    responseBody="product saved";
    		}
    		if (verb.equalsIgnoreCase("put")){
                
     //get all values in new + existing versions and then compare
                JSONObject jsonObj = new JSONObject(request.getBody());
                if (!jsonObj.isNull("product_id")){  
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
                            List<SMValue> statii = (List<SMValue>)(product.getValue().get("status").getValue());
                            
                            old_cats = new String[categories.size()];
                            old_statii = new String[statii.size()];
                            
                            int i =0;
                            for (SMValue smString : categories) {
                               // SMString stringValue = (SMString)smString.getValue();
                                //put old values to hashset
                                //all_cats_hs.add(new String(stringValue));
                                old_cats[i]=new String(smString.toString());i++;
                                all_cats_hs.add(smString.toString());
                            }
                            //
                            i =0;
                            for (SMValue smString : statii) {
                                //SMString stringValue = (SMString)smString.getValue();
                                //put old values to hashset
                                //all_statii_hs.add(new String(stringValue));
                                old_statii[i]=new String(smString.toString());i++;
                                all_statii_hs.add(smString.toString());
                            }   
                            //
                            if(new_cats!=null){
                                
                                if(old_cats.length==0 &&  new_cats.length>0){
                                    incrementCats(old_cats,new_cats,1,responseBody,serviceProvider);
                                    add_cats.add(Arrays.asList(new_cats));
                                }
                                else if(old_cats.length>0&&new_cats.length==0){
                                    incrementCats(old_cats,new_cats,-1,responseBody,serviceProvider);
                                    //populate remove cats with old cats
                                    remove_cats.addAll(Arrays.asList(old_cats));
                                }
                                else if(old_cats.length>0&&new_cats.length>0){                                  
                                    for (Object s : all_cats_hs) {
                                        String S=(String)s;
                                        if(Arrays.asList(old_cats).contains(S)&&!Arrays.asList(new_cats).contains(S)){
                                            //add to cats to remove
                                            remove_cats.add(S);
                                            List<SMUpdate> update = new ArrayList<SMUpdate>();
                                            update.add(new SMIncrement("count", -1));
                                            SMObject incrementResult = ds.updateObject("category",S, update);      
                                        }
                                        else if(!Arrays.asList(old_cats).contains(S)&&Arrays.asList(new_cats).contains(S)){
                                            //add to cats to add
                                            add_cats.add(S);
                                            List<SMUpdate> update = new ArrayList<SMUpdate>();
                                            update.add(new SMIncrement("count", 1));
                                            SMObject incrementResult = ds.updateObject("category",S, update);    
                                        } 
                                    }
                                }
                            }
                            if(new_statii!=null){
                                
                                if(old_statii.length==0 &&  new_statii.length>0){
                                    incrementStatii(old_statii,new_statii,1,responseBody,serviceProvider);
                                    add_statii.add(Arrays.asList(new_statii));
                                }
                                else if(old_statii.length>0&&new_statii.length==0){
                                    incrementStatii(old_statii,new_statii,-1,responseBody,serviceProvider);
                                    //populate remove cats with old cats
                                    remove_statii.addAll(Arrays.asList(old_statii));
                                }
                                else if(old_statii.length>0&&new_statii.length>0){
                                    for (Object s : all_statii_hs) {
                                        String S=(String)s;
                                        if(Arrays.asList(old_statii).contains(S)&&!Arrays.asList(new_statii).contains(S)){
                                            //add to statii to remove
                                            remove_statii.add(S);
                                            List<SMUpdate> update = new ArrayList<SMUpdate>();
                                            update.add(new SMIncrement("count", -1));
                                            SMObject incrementResult = ds.updateObject("status",S, update);      
                                        }
                                        else if(!Arrays.asList(old_statii).contains(S)&&Arrays.asList(new_statii).contains(S)){
                                            //add to statii to add
                                            add_statii.add(S);
                                            List<SMUpdate> update = new ArrayList<SMUpdate>();
                                            update.add(new SMIncrement("count", 1));
                                            SMObject incrementResult = ds.updateObject("status",S, update);    
                                        } 
                                    }
                                }
                            }    //savePutProduct(JSONObject jsonObj,SDKServiceProvider serviceProvider,String[] add_cats, String[] add_statii,String[] remove_cats,String[] remove_statii)
                            if(savePutProduct(rb,serviceProvider,add_cats.toArray(new String[0]),add_statii.toArray(new String[0]),remove_cats.toArray(new String[0]),remove_statii.toArray(new String[0])))
                                responseBody="product saved";
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
                JSONObject jsonObj = new JSONObject(request.getBody());
                if (!jsonObj.isNull("product_id")){  
                   
                    try{
                    incrementAll(new_cats,new_statii,-1,responseBody,serviceProvider);
                    //delete product
                    DataService ds = serviceProvider.getDataService();
                    if(ds.deleteObject("product", jsonObj.getString("product_id")))//remove SMstring constructor
                        responseBody="product deleted";
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
            
    	}
    	catch(JSONException e){
          sb.append("Caught JSON Exception");
          e.printStackTrace();
    	}
    } else sb.append("Request body is empty");
    //map.put("reqbody",reqb);
    map.put("response_body", responseBody);
    return new ResponseToProcess(HttpURLConnection.HTTP_OK, map);
  }
 
}


