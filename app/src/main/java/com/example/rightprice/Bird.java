package com.example.rightprice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Bird {

    public Vehicle[] getBirds() {
        return birds;
    }

    private Vehicle [] birds;

    private JsonObjectRequest initReq;

    public JsonObjectRequest getVehicleReq() {
        return vehicleReq;
    }

    private JsonObjectRequest vehicleReq;

    public String getToken() {
        return token;
    }

    public String getId() {
        return id;
    }

    public String getExpiration() {
        return expiration;
    }

    private String token;
    private String id;
    private String expiration;
    private String email;

    public void generateVehicleReq(final Location point, int radius) throws JSONException {


        String url = "https://api.bird.co/bird/nearby";
        JSONObject obj = new JSONObject();
        obj.put("latitude",String.format("%.5f",point.getLatitude()));
        obj.put("longitude",String.format("%.5f",point.getLongitude()));
        obj.put("radius",Integer.toString(radius));

        Response.ErrorListener onErr = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error on GET request from bird");
                //bad request or something

            }
        };
        Response.Listener<JSONObject> onRes = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("GET RECIEVED");
                System.out.println(response.toString());
            }
        };
        vehicleReq = new JsonObjectRequest(Request.Method.POST,url,obj,onRes,onErr) {
            /**
             * Passing some request headers*
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization-Type", "Bird "+token);
                headers.put("Device-id:", "000bca56-fb54-4704-9abe-60efc4d9993c");
                headers.put("App-Version","3.0.5");
                JSONObject loc = new JSONObject();
                try {
                    loc.put("latitude",String.format("%.5f",point.getLatitude()));
                    loc.put("longitude",String.format("%.5f",point.getLongitude()));
                    loc.put("altitude","500");
                    loc.put("speed","-1");
                    loc.put("heading","-1");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                headers.put("Location",loc.toString());
                return headers;
            }
        };

    }

    public JsonObjectRequest getInitReq() {
        return initReq;
    }
    //idk what this is but i commented it out


    public Bird(String email, final Location loc) throws JSONException {
        //first init with bird
        this.email = email;
        String url = "https://api.bird.co/user/login";
        JSONObject obj = new JSONObject();
        obj.put("email",email);
        token = "none";

        Response.ErrorListener onErr = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error Request when constructing Bird()");
                //bad request or something

            }
        };
        Response.Listener<JSONObject> onRes = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Request success");
                System.out.println(response.toString());
                System.out.println("$$$$$$$$$$$$$$$$$");
                if(response.has("token")) {
                    try {
                        token =  response.getString("token");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                  /*TODO: retrive/renew token based on ID or email
                    tokens are only sent back if the email passed in is sent for the very
                    first time
                   */
                  System.out.println("NO TOKEN FROM BIRD");
                }
                if (response.has("id")) {
                    try {
                        id = response.getString("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                //shouldn't happen
                else
                    System.out.println("ERROR -- BIRD REQUEST GAVE NO ID");
                if(response.has("expires_at")){
                    try {
                        expiration = response.getString("expires_at");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //shouldn't happen
                else
                    System.out.println("ERROR -- BIRD REQUEST GAVE NO EXPIRES_AT");
                try {
                    generateVehicleReq(loc, 1000);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        };
        initReq = new JsonObjectRequest(Request.Method.POST,url,obj,onRes,onErr) {
            /**
             * Passing some request headers*
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("Device-id:", "000bca56-fb54-4704-9abe-60efc4d9993c");
                headers.put("Platform","android");
                return headers;
            }
        };
    }
}
