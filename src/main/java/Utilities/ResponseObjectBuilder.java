/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author CHRISTIAN
 */
public class ResponseObjectBuilder {
    
    public static <T> String dataReturn(T obj) {
        JSONObject json = new JSONObject(); 
        try {
        json.put("erc", 1)
            .put("data", obj);
        }catch(JSONException ex) {
            ex.printStackTrace();
        }
      
        return json.toString();
    }
}
