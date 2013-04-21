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
  private boolean savePostProduct(JSONObject jsonObj,SDKServiceProvider serviceProvider,String[] new_cats,String[] new_statii) throws InvalidSchemaException,DatastoreException,JSONException{
        
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
           List<SMValue> new_cats_sm= new ArrayList<SMValue>(); 
           for(int i=0;i<new_cats.length;i++){
               new_cats_sm.add(new SMString(new_cats[i]));
           }
           List<SMValue> new_statii_sm= new ArrayList<SMValue>();//left it her
           for(int i=0;i<new_statii.length;i++){
               new_statii_sm.add(new SMString(new_statii[i]));
           }
           //remove these two lines
           List<SMValue> catsToCreate=new_cats_sm;
           List<SMValue> statiiToCreate=new_statii_sm;
            
           ds.addRelatedObjects("product", new SMString(newObj.getValue().get("product_id").getValue().toString()), "categories", catsToCreate);
           ds.addRelatedObjects("product", new SMString(newObj.getValue().get("product_id").getValue().toString()), "status",  statiiToCreate);
           return true;
  }
  private boolean savePutProduct(JSONObject jsonObj,SDKServiceProvider serviceProvider,List<SMValue>add_cats, List<SMValue> add_statii,List<SMValue> remove_cats,List<SMValue> remove_statii) throws InvalidSchemaException,DatastoreException,JSONException{
        
        DataService ds = serviceProvider.getDataService();
         
            List<SMUpdate> update = new ArrayList<SMUpdate>();
            
            if (!jsonObj.isNull("title_en"));
                update.add(new SMSet("title_en", new SMString(jsonObj.getString("title_en"))));
            if (!jsonObj.isNull("title_pl"));
                update.add(new SMSet("title_pl", new SMString(jsonObj.getString("title_pl"))));
            if (!jsonObj.isNull("description_en"));
                update.add(new SMSet("description_en", new SMString(jsonObj.getString("description_en"))));
            if (!jsonObj.isNull("description_pl"));
                update.add(new SMSet("description_pl", new SMString(jsonObj.getString("description_pl"))));
            if (!jsonObj.isNull("link"));
                update.add(new SMSet("link", new SMString(jsonObj.getString("link"))));
            if (!jsonObj.isNull("picture"));
                update.add(new SMSet("picture", new SMString(jsonObj.getString("picture"))));
            if (!jsonObj.isNull("price"));
                update.add(new SMSet("price", new SMString(jsonObj.getString("price"))));    
                
            SMObject result = ds.updateObject("product",  jsonObj.getString("product_id"), update);  
            //remove the following 4 lines if they are not necessary
            List<SMValue> catsToAdd=add_cats;//Arrays.asList(add_cats);//can be null//
            List<SMValue> catsToRemove=remove_cats;// Arrays.asList(remove_cats);//can be null
            List<SMValue> statiiToAdd= add_statii;//Arrays.asList(add_statii);//can be null
            List<SMValue> statiiToRemove=remove_statii;// Arrays.asList(remove_statii);//can be null
            
            ds.addRelatedObjects("product", new SMString(jsonObj.getString("product_id")), "categories", catsToAdd);
            ds.addRelatedObjects("product", new SMString(jsonObj.getString("product_id")), "status",  statiiToAdd);
            ds.removeRelatedObjects("product", new SMString(jsonObj.getString("product_id")), "categories", catsToRemove,false);
            ds.removeRelatedObjects("product", new SMString(jsonObj.getString("product_id")), "status",  statiiToRemove,false);
            
            return true;
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
    List<SMValue> add_cats=new ArrayList<SMValue>();
    List<SMValue> add_statii =new ArrayList<SMValue>();
    List<SMValue> remove_cats=new ArrayList<SMValue>();
    List<SMValue> remove_statii = new ArrayList<SMValue>();
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
                if(savePostProduct(rb,serviceProvider,new_cats,new_statii))
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
                                    
                                    for(String S:new_cats){
                                        //remove_cats.add(new SMString(S));    
                                        add_cats.add(new SMString(S));
                                    }
                                }
                                else if(old_cats.length>0 && new_cats.length==0){
                                    incrementCats(old_cats,new_cats,-1,responseBody,serviceProvider);
                                    //populate remove cats with old cats
                                    for(String S:old_cats){
                                        remove_cats.add(new SMString(S));    
                                    }
                                    
                                }
                                else if(old_cats.length>0 && new_cats.length>0){                                  
                                    for (Object s : all_cats_hs) {
                                        String S=(String)s;
                                        if(Arrays.asList(old_cats).contains(S) && !Arrays.asList(new_cats).contains(S)){
                                            //add to cats to remove
                                            remove_cats.add(new SMString(S));
                                            List<SMUpdate> update = new ArrayList<SMUpdate>();
                                            update.add(new SMIncrement("count", -1));
                                            SMObject incrementResult = ds.updateObject("category",S, update);      
                                        }
                                        else if(!Arrays.asList(old_cats).contains(S) && Arrays.asList(new_cats).contains(S)){
                                            //add to cats to add
                                            add_cats.add(new SMString(S));
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
                                    for(String S:new_statii){
                                        add_statii.add(new SMString(S));
                                    }
                                    
                                }
                                else if(old_statii.length>0 && new_statii.length==0){
                                    incrementStatii(old_statii,new_statii,-1,responseBody,serviceProvider);
                                    //populate remove cats with old cats
                                    //remove_statii.addAll(Arrays.asList(old_statii));
                                    for(String S:old_statii){
                                        remove_statii.add(new SMString(S));
                                    }
                                }
                                else if(old_statii.length>0 && new_statii.length>0){
                                    for (Object s : all_statii_hs) {
                                        String S=(String)s;
                                        if(Arrays.asList(old_statii).contains(S)&&!Arrays.asList(new_statii).contains(S)){
                                            //add to statii to remove
                                            remove_statii.add(new SMString(S));
                                            List<SMUpdate> update = new ArrayList<SMUpdate>();
                                            update.add(new SMIncrement("count", -1));
                                            SMObject incrementResult = ds.updateObject("status",S, update);      
                                        }
                                        else if(!Arrays.asList(old_statii).contains(S)&&Arrays.asList(new_statii).contains(S)){
                                            //add to statii to add
                                            add_statii.add(new SMString(S));
                                            List<SMUpdate> update = new ArrayList<SMUpdate>();
                                            update.add(new SMIncrement("count", 1));
                                            SMObject incrementResult = ds.updateObject("status",S, update);    
                                        } 
                                    }
                                }
                            }    //savePutProduct(JSONObject jsonObj,SDKServiceProvider serviceProvider,String[] add_cats, String[] add_statii,String[] remove_cats,String[] remove_statii)
                            if(savePutProduct(rb,serviceProvider,add_cats,add_statii,remove_cats ,remove_statii))
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
                else{
                    responseBody="product_id not defined";
                }
    		}
            
    	}
    	catch(JSONException e){
          sb.append("Caught JSON Exception");
          e.printStackTrace();
    	}
        catch (InvalidSchemaException e){
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
    } else sb.append("Request body is empty");
    //map.put("reqbody",reqb);
    map.put("response_body", responseBody);
    return new ResponseToProcess(HttpURLConnection.HTTP_OK, map);
  }
 
}


