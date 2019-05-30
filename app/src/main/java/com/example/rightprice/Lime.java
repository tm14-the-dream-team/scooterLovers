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

//Use phone number not email
public class Lime {
    private Vehicle[] limes;
    private  JsonObjectRequest initReq;
    private String token;
    private String id;
    private String expiration;
    private String phoneNumber;

    public Vehicle[] getLimes() {
        return limes;
    }

    public JsonObjectRequest getInitReq() {
        return initReq;
    }

    public Lime(String phoneNumber/*MUST BE INTERNATIONAL FORMAT EX: 15552221234*/) throws JSONException{
        this.phoneNumber = phoneNumber;
        JSONObject obj = new JSONObject();
        //Phone number added to request manually here
        String url = "https://web-production.lime.bike/api/rider/v1/login?phone="+this.phoneNumber;
        //String url = "https://web-production.lime.bike/api/rider/v1/login";
        //obj.put("phone",this.phoneNumber);


        Response.ErrorListener onErr = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //bad request or something
                System.out.println("Error Request when constructing Lime()");

            }
        };
        Response.Listener<JSONObject> onRes = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("GET RECIEVED from Lime");
                System.out.println(response.toString());
            }
        };

        initReq = new JsonObjectRequest(Request.Method.GET,url,obj,onRes,onErr);
        System.out.println("HERE IS THE LIME INITREQ:"+initReq.toString());
        System.out.println("HERE IS THE LIME obj:"+obj.toString());
    }
}
